package dcd.highlevel;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import dcd.highlevel.ast.Block;
import dcd.highlevel.ast.Invariant;
import dcd.highlevel.ast.Method;
import dcd.highlevel.ast.Modifier;
import dcd.highlevel.ast.Name;
import dcd.highlevel.ast.Program;
import dcd.highlevel.ast.Role;
import dcd.highlevel.ast.Statement;
import dcd.highlevel.ast.program.Conditional;
import dcd.highlevel.ast.program.Goto;
import dcd.highlevel.ast.program.Label;
import dcd.highlevel.ast.program.Nop;
import dcd.highlevel.ast.program.Numeric;
import dcd.highlevel.ast.program.PrimOp;
import dcd.highlevel.ast.program.SelfFunction;

public class CFileGenerator {
    private Program program;
    VTableResolver resolver;
    private Map<String,String> invariantFragmentMap = new HashMap<String,String>();
    private Set<String> otherFragments = new HashSet<String>();
    
    public CFileGenerator(Program program, VTableResolver resolver) {
        this.program = program;
        this.resolver = resolver;
    }

    public void generate(PrintWriter writer) {
        generateRoleSelectors(writer);
        generateStartupMethods(writer);
        generateBehaviorMethods(writer);
        generateCommandMethods(writer);
        generateMethodHeader(writer);
        generateFragmentSends(writer);
        generateMethodFooter(writer);
        writer.flush();
    }

    private void generateRoleSelectors(PrintWriter writer) {
        for(Role role: program.getRoles()) {
            if(role.isAbstract()) continue;
            writer.println("/* Role selection code for "+role.getName()+" */"); writer.flush();
            Block checkInvariant = generateCheckInvariant(role);
            generateCodeBlock(writer,role,programName("find_"+role.getName().getName()),checkInvariant,true);
        }
    }

    private void generateCodeBlock(PrintWriter writer, Role role, String name, Block source, boolean roleInvariant) {
        ByteCodeSequence compiled = new ByteCodeCompiler(role,resolver).compileCodeBlock(source);
        compiled.peepHoleOptimize();
        compiled.resolveGoto();
        String programName = "RDCD_"+name;
        writer.println("#define SIZE_RDCD_"+name+" "+compiled.getSize());
        writer.println("unsigned char "+programName+"[SIZE_RDCD_"+name+"+1]= {");
        compiled.generate(writer);
        writer.println("0\n};");
        if(roleInvariant)
            invariantFragmentMap.put(role.getName().getName(),programName);
        else
            otherFragments.add(programName);
    }

    public String programName(String string) {
        return "program_"+string;
    }

    private Block generateCheckInvariant(Role role) {
        Statement cascade = generateInvariantCascade(role,role.getInvariants().iterator());
        return annotize(role,new Block(new ArrayList<Statement>(Arrays.asList(new Statement[] { cascade, PrimOp.MIGRATE_CONTINUE }))));
    }
    
    private Block annotize(Role role, Block body) {
        if(!role.hasModifier(Modifier.DEBUG)) return body;
        Block wrapper = new Block(new ArrayList<Statement>(Arrays.asList(new Statement[] { PrimOp.DEBUG, body })));
        return wrapper;
    }

    private Statement generateInvariantCascade(Role role, Iterator<Invariant> invariants) {
        if(invariants.hasNext()) {
            Invariant invariant = invariants.next();
            return new Conditional(
                    invariant.getCondition(),
                    generateInvariantCascade(role,invariants),
                    PrimOp.MIGRATE_CONTINUE
                    );
        } else {
            return PrimOp.SET_ROLE_NOTIFY(role.getName());
        }
    }

    public static String roleName(Name roleName) {
        return "ROLE_"+roleName;
    }

    private void generateStartupMethods(PrintWriter writer) {
        for(Role role: program.getRoles()) {
            if(role.isAbstract()) continue;
            for(Method method: role.getMethods()) {
                if(!(method.getModifier()==Modifier.STARTUP)) continue;
                writer.println("/* Startup method for "+role.getName()+" */"); writer.flush();
                Block body = generateStartupBody(role,method);
                generateCodeBlock(writer,role,programName("startup_"+role.getName().getName()),body,false);
            }
        }
    }

    private void generateBehaviorMethods(PrintWriter writer) {
        for(Role role: program.getRoles()) {
            if(role.isAbstract()) continue;
            for(Method method: role.getMethods()) {
                if(!(method.getModifier()==Modifier.BEHAVIOR)) continue;
                writer.println("/* Behavior method for "+role.getName()+" */"); writer.flush();
                Block body = generateBehaviorBody(role,method);
                generateCodeBlock(writer,role,programName("behavior_"+role.getName().getName()),body,false);
            }
        }
    }

    private void generateCommandMethods(PrintWriter writer) {
        for(Role role: program.getRoles()) {
            if(role.isAbstract()) continue;
            for(Method method: role.getMethods()) {
                if(!(method.getModifier()==Modifier.COMMAND)) continue;
                writer.println("/* Command method for "+role.getName()+" */"); writer.flush();
                Block body = generateCommandBody(role,method);
                generateCodeBlock(writer,role,programName("command_"+role.getName().getName()),body,false);
            }
        }
    }

    private Block generateStartupBody(Role role, Method method) {
        return mobilize(role,method.getBody());
    }

    private Block generateBehaviorBody(Role role, Method method) {
        return mobilize(role,installize(role,method,true));
    }
    
    private Block generateCommandBody(Role role, Method method) {
        return mobilize(role,installize(role,method,false));
    }
    
    private Block mobilize(Role role, Block body) {
        List<Statement> statements = new ArrayList<Statement>();
        statements.add(new Conditional(
                SelfFunction.HAS_ROLE(role.getName().getName()),
                body,
                new Nop()));
        statements.add(PrimOp.MIGRATE_CONTINUE);
        return new Block(statements);
    }
    
    private Block installize(Role role, Method method, boolean isBehavior) {
        List<Statement> statements = new ArrayList<Statement>();
        int index = resolver.getMethodIndex(role.getName().getName(), method.getName().getName())+128;
        statements.add(PrimOp.INSTALL_COMMAND(role.getName(),index,method.getBody(),isBehavior));
        if(isBehavior) {
            statements.add(PrimOp.EVAL_COMMAND(index,new Numeric(0)));
        }
        return new Block(statements);
    }
    
    private void generateFragmentSends(PrintWriter writer) {
        for(Name role: program.getDeployment()) {
            String fragmentName = invariantFragmentMap.get(role.getName());
            if(fragmentName==null) throw new Error("Undefined fragment name: "+role.getName());
            generateFragmentSend(fragmentName,writer);
        }
        for(String fragment: otherFragments)
            generateFragmentSend(fragment,writer);
    }
    
    private void generateFragmentSend(String fragmentName, PrintWriter writer) {
        writer.println("  printf(\"*** Sending program: "+fragmentName+" \\n\");");
        writer.println("  for(channel=0; channel<8; channel++) {");
        writer.println("    sendProgramMaybe(USSRONLYC(env) &context, channel, "+fragmentName+", SIZE_"+fragmentName+");");
        writer.println("  }");
        writer.println("  context.program_id++;");
        writer.println("  printf(\"*** Waiting\\n\");");
        writer.println("  delay(USSRONLYC(env) WAITTIME);");
    }

    private void generateMethodHeader(PrintWriter writer) {
        writer.println("static void dcd_action(USSRONLY(USSREnv *env)) {");
        writer.println("  char channel;");
        writer.println("  InterpreterContext context = { 0,0,0,ARG_NORTH_SOUTH, 0, 0, 0 };");
        writer.println("  printf(\"################################\\n\");");
        writer.println("  printf(\"## CAR ACTION DCD CONTROLLER ###\\n\");");
        writer.println("  printf(\"################################\\n\");");
        writer.println("  delay(USSRONLYC(env) WAITTIME);");
    }

    private void generateMethodFooter(PrintWriter writer) {
        writer.println("  printf(\"Done!\\n\");");
        writer.println("}");
    }


}
