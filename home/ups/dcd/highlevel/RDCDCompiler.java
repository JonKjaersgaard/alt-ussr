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
import dcd.highlevel.generic.IName;
import dcd.highlevel.generic.Resolver;

public class RDCDCompiler extends CodeGeneratorImpl {
    private Program program;
    private Map<String,String> invariantFragmentMap = new HashMap<String,String>();
    private Set<String> otherFragments = new HashSet<String>();
    
    public RDCDCompiler(Program program, Resolver resolver) {
        super(resolver);
        this.program = program;
    }

    public void generate(OutputBuilder writer) {
        generateRoleSelectors(writer);
        generateStartupMethods(writer);
        generateBehaviorMethods(writer);
        generateCommandMethods(writer);
        writer.startFragmentScheduling();
        generateFragmentSends(writer);
        writer.finish();
    }

    private void generateRoleSelectors(OutputBuilder writer) {
        for(Role role: program.getRoles()) {
            if(role.isAbstract()) continue;
            writer.addComment("Role selection code for "+role.getName());
            Block checkInvariant = generateCheckInvariant(role);
            generateCodeBlock(writer,role,programName("find_"+role.getName().getName()),checkInvariant,true);
        }
    }

    private void generateCodeBlock(OutputBuilder writer, Role role, String name, Block source, boolean roleInvariant) {
        ByteCodeSequence compiled = new ByteCodeCompiler(role,resolver).compileCodeBlock(source);
        compiled.peepHoleOptimize();
        compiled.resolveGoto();
        String programName = "RDCD_"+name;
        writer.startFragment(programName,compiled.getSize());
        compiled.generate(writer);
        writer.finishFragment();
        if(roleInvariant)
            invariantFragmentMap.put(role.getName().getName(),programName);
        else
            otherFragments.add(programName);
    }

    private String programName(String string) {
        return "program_"+string;
    }

    private void generateStartupMethods(OutputBuilder writer) {
        for(Role role: program.getRoles()) {
            if(role.isAbstract()) continue;
            for(Method method: role.getMethods()) {
                if(!(method.getModifier()==Modifier.STARTUP)) continue;
                writer.addComment("Startup method for "+role.getName());
                Block body = generateStartupBody(role,method);
                generateCodeBlock(writer,role,programName("startup_"+role.getName().getName()),body,false);
            }
        }
    }

    private void generateBehaviorMethods(OutputBuilder writer) {
        for(Role role: program.getRoles()) {
            if(role.isAbstract()) continue;
            for(Method method: role.getMethods()) {
                if(!(method.getModifier()==Modifier.BEHAVIOR)) continue;
                writer.addComment("Behavior method for "+role.getName());
                Block body = generateBehaviorBody(role,method);
                generateCodeBlock(writer,role,programName("behavior_"+role.getName().getName()),body,false);
            }
        }
    }

    private void generateCommandMethods(OutputBuilder writer) {
        for(Role role: program.getRoles()) {
            if(role.isAbstract()) continue;
            for(Method method: role.getMethods()) {
                if(!(method.getModifier()==Modifier.COMMAND)) continue;
                writer.addComment("Command method for "+role.getName());
                Block body = generateCommandBody(role,method);
                generateCodeBlock(writer,role,programName("command_"+role.getName().getName()),body,false);
            }
        }
    }
    
    private void generateFragmentSends(OutputBuilder writer) {
        for(Name role: program.getDeployment()) {
            String fragmentName = invariantFragmentMap.get(role.getName());
            if(fragmentName==null) throw new Error("Undefined fragment name: "+role.getName());
            writer.scheduleFragmentSend(fragmentName);
        }
        for(String fragment: otherFragments)
            writer.scheduleFragmentSend(fragment);
    }

}
