package dcd.highlevel;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
import dcd.highlevel.ast.program.PrimOp;
import dcd.highlevel.ast.program.SelfFunction;

public class CFileGenerator {
    private Program program;
    VTableResolver resolver;
    
    public CFileGenerator(Program program, VTableResolver resolver) {
        this.program = program;
        this.resolver = resolver;
    }

    public void generate(PrintWriter writer) {
        generateRoleSelectors(writer);
        generateStartupMethods(writer);
        generateBehaviorMethods(writer);
        generateCommandMethods(writer);
        writer.flush();
    }

    private void generateRoleSelectors(PrintWriter writer) {
        for(Role role: program.getRoles()) {
            if(role.isAbstract()) continue;
            writer.println("/* Role selection code for "+role.getName()+" */"); writer.flush();
            Block checkInvariant = generateCheckInvariant(role);
            generateCodeBlock(writer,role,programName("find_"+role.getName().getName()),checkInvariant);
        }
    }

    private void generateCodeBlock(PrintWriter writer, Role role, String name, Block checkInvariant) {
        ByteCodeSequence compiled = new ByteCodeCompiler(role,resolver).compileCodeBlock(checkInvariant);
        compiled.peepHoleOptimize();
        compiled.resolveGoto();
        writer.println("#define SIZE_"+name+" "+compiled.getSize());
        writer.println("unsigned char "+name+"[SIZE_"+name+"]= {");
        compiled.generate(writer);
        writer.println("};");
    }

    public String programName(String string) {
        return "program_"+string;
    }

    private Block generateCheckInvariant(Role role) {
        Statement cascade = generateInvariantCascade(role,role.getInvariants().iterator());
        return new Block(new ArrayList<Statement>(Arrays.asList(new Statement[] { cascade, PrimOp.MIGRATE_CONTINUE })));
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
                generateCodeBlock(writer,role,programName("startup_"+role.getName().getName()),body);
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
                generateCodeBlock(writer,role,programName("behavior_"+role.getName().getName()),body);
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
                generateCodeBlock(writer,role,programName("command_"+role.getName().getName()),body);
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
        return new Block(statements);
    }
}
