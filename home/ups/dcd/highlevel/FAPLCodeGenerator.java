package dcd.highlevel;

import java.io.ByteArrayOutputStream;
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
import dcd.highlevel.fapl.Function;
import dcd.highlevel.fapl.Program;
import dcd.highlevel.fapl.Unit;
import dcd.highlevel.ast.Role;
import dcd.highlevel.ast.Statement;
import dcd.highlevel.ast.program.Conditional;
import dcd.highlevel.ast.program.Goto;
import dcd.highlevel.ast.program.Label;
import dcd.highlevel.ast.program.Nop;
import dcd.highlevel.ast.program.Numeric;
import dcd.highlevel.ast.program.PrimOp;
import dcd.highlevel.ast.program.SelfFunction;
import dcd.highlevel.ast.program.SingleExp;
import dcd.highlevel.generic.IName;
import dcd.highlevel.generic.Resolver;

public class FAPLCodeGenerator extends CodeGeneratorImpl {
    
    public static class Packet {
        private byte[] content;
        public Packet(byte[] content) {
            this.content = content;
        }
    }
    private Program program;
    private Map<String,String> invariantFragmentMap = new HashMap<String,String>();
    private Set<String> otherFragments = new HashSet<String>();
    
    public FAPLCodeGenerator(Program program, Resolver resolver) {
        super(resolver);
        this.program = program;
    }

    public void generate(OutputBuilder output) {
        for(Unit unit: program.getUnits()) {
            if(unit instanceof Function)
                generateCodeBlock(output, (Function)unit);
            else
                throw new Error("Not supported yet: "+unit);
        }
        output.finish();
    }

    private void generateCodeBlock(OutputBuilder output, Function function) {
        Block source = new Block(new Statement[] { new SingleExp(function.getBody()) });
        ByteCodeSequence compiled = new ByteCodeCompiler(program,resolver).compileCodeBlock(source);
        compiled.peepHoleOptimize();
        compiled.resolveGoto();
        compiled.generate(output);
    }

}