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
        List<String> fragments = new ArrayList<String>();
        for(Unit unit: program.getUnits()) {
            String fragmentName = "FAPL_"+name+"_"+count++;
            ByteCodeSequence compiled;
            if(unit instanceof FunctionDef)
                compiled = generateFunctionCodeBlock(output, (FunctionDef)unit);
            else if(unit instanceof Evaluate)
                compiled = generateEvaluateCodeBlock(output, (Evaluate)unit);
            else if(unit instanceof RoleInvariantDef)
                compiled = generateInvariantCodeBlock(output, (RoleInvariantDef)unit);
            else
                throw new Error("Not supported yet: "+unit);
            if(compiled==null) continue;
            fragments.add(fragmentName);
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

    private ByteCodeSequence generateInvariantCodeBlock(OutputBuilder output, RoleInvariantDef unit) {
        if(unit.isPartial()) return null;
        output.addComment("Invariants for role "+unit.getRoleName());
        Block source = new Block(new Statement[] { super.generateInvariantCascade(new Name(unit.getRoleName()), unit.getInvariants().iterator())});
        ByteCodeSequence compiled = new ByteCodeCompiler(program,resolver,null).compileCodeBlock(source);
        return compiled;
    }

    private ByteCodeSequence generateEvaluateCodeBlock(OutputBuilder output, Evaluate unit) {
        output.addComment("Anonymous evaluation block");
        Block source = new Block(new Statement[] { new SingleExp(unit.getExp()) });
        ByteCodeSequence compiled = new ByteCodeCompiler(program,resolver,null).compileCodeBlock(source);
        return compiled;
    }

    private ByteCodeSequence generateFunctionCodeBlock(OutputBuilder output, FunctionDef function) {
        output.addComment("Function definition for "+function.getName().getName()+"("+function.getRole()+")");
        Block source = function.getBody();
        SourceAdapter adapter = new SourceAdapter(function);
        source = super.installize(adapter, function, false);
        source = super.mobilize(adapter, source);
        // installize source
        ByteCodeSequence compiled = new ByteCodeCompiler(program,resolver,new Name(function.getParameter())).compileCodeBlock(source);
        return compiled;
    }

    class SourceAdapter implements GlobalSource {

        private FunctionDef function;
        
        public SourceAdapter(FunctionDef function) {
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
