package dcd.highlevel.fapl;

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

import dcd.highlevel.ByteCode;
import dcd.highlevel.ByteCodeCompiler;
import dcd.highlevel.ByteCodeSequence;
import dcd.highlevel.CodeGeneratorImpl;
import dcd.highlevel.GlobalSource;
import dcd.highlevel.IName;
import dcd.highlevel.InvariantSource;
import dcd.highlevel.OutputBuilder;
import dcd.highlevel.Resolver;
import dcd.highlevel.ast.Block;
import dcd.highlevel.ast.ConstantDef;
import dcd.highlevel.ast.Invariant;
import dcd.highlevel.ast.Method;
import dcd.highlevel.ast.Modifier;
import dcd.highlevel.ast.Name;
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

public class FAPLCodeGenerator extends CodeGeneratorImpl {
    private String name;
    private Program program;
    
    public FAPLCodeGenerator(String name, Program program, Resolver resolver) {
        super(resolver);
        this.name = name;
        this.program = program;
    }

    public void generate(OutputBuilder output) {
        int count = 0;
        Set<String> fragments = new HashSet<String>();
        for(Unit unit: program.getUnits()) {
            String fragmentName = "FAPL_"+name+"_"+count++;
            fragments.add(fragmentName);
            ByteCodeSequence compiled;
            if(unit instanceof Function)
                compiled = generateFunctionCodeBlock(output, (Function)unit);
            else if(unit instanceof Evaluate)
                compiled = generateEvaluateCodeBlock(output, (Evaluate)unit);
            else
                throw new Error("Not supported yet: "+unit);
            compiled.add(ByteCode.INS_TERMINATE());                     
            compiled.peepHoleOptimize();
            compiled.resolveGoto();
            output.startFragment(fragmentName,compiled.getSize());
            compiled.generate(output);
            output.finishFragment();
        }
        output.startFragmentScheduling(name);
        for(String fragment: fragments)
            output.scheduleFragmentSend(fragment,true);
        output.finish();
    }

    private ByteCodeSequence generateEvaluateCodeBlock(OutputBuilder output, Evaluate unit) {
        Block source = new Block(new Statement[] { new SingleExp(unit.getExp()) });
        ByteCodeSequence compiled = new ByteCodeCompiler(program,resolver).compileCodeBlock(source);
        return compiled;
    }

    private ByteCodeSequence generateFunctionCodeBlock(OutputBuilder output, Function function) {
        Block source = function.getBody();
        SourceAdapter adapter = new SourceAdapter(function);
        source = super.installize(adapter, function, false);
        source = super.mobilize(adapter, source);
        // installize source
        ByteCodeSequence compiled = new ByteCodeCompiler(program,resolver).compileCodeBlock(source);
        return compiled;
    }

    class SourceAdapter implements GlobalSource {

        private Function function;
        
        public SourceAdapter(Function function) {
            this.function = function;
        }

        public ConstantDef getConstant(IName name) {
            // TODO Auto-generated method stub
            // return null;
            throw new Error("Method not implemented");
        }

        public List<? extends InvariantSource> getInvariants() {
            // TODO Auto-generated method stub
            // return null;
            throw new Error("Method not implemented");
        }

        public IName getName() {
            return new Name(function.getRole());
        }

        public boolean hasModifier(Modifier modifier) {
            return false;
        }
        
    }
}
