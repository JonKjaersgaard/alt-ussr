package dcd.highlevel;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dcd.highlevel.ast.Block;
import dcd.highlevel.ast.ConstantDef;
import dcd.highlevel.ast.Exp;
import dcd.highlevel.ast.Name;
import dcd.highlevel.ast.Program;
import dcd.highlevel.ast.Role;
import dcd.highlevel.ast.Statement;
import dcd.highlevel.ast.program.*;

public class ByteCodeCompiler implements Visitor {
    private ByteCodeSequence result = new ByteCodeSequence();
    private Role role;
    private Map<String,Block> blockMap = new HashMap<String,Block>();
    private VTableResolver vtableResolver;
    
    public ByteCodeCompiler(Role role, VTableResolver vtableResolver) {
        this.role = role;
        this.vtableResolver = vtableResolver;
    }
    
    public ByteCodeSequence compileCodeBlock(Block block) {
        for(Statement statement: block.getStatements())
            statement.visit(this);
        //result.add(ByteCode.INS_TERMINATE());
        for(String label: blockMap.keySet()) {
            Block local = blockMap.get(label);
            result.addLabel(label);
            for(Statement statement: local.getStatements())
                statement.visit(this);
        }
        return result;
    }

    private void addBlockArgument(String label, Block block) {
        blockMap.put(label, block);
    }
    
    private int conditionalCounter = 0;
    public void visitConditional(Conditional conditional) {
        int id = conditionalCounter++;
        String else_label = "cond_"+id+"_else";
        String end_label = "cond_"+id+"_end";
        // Push condition on stack
        conditional.getCondition().visit(this);
        // Test if false (take else branch)
        result.add(ByteCode.INS_IF_FALSE_GOTO(else_label));
        // True branch
        conditional.getThenBranch().visit(this);
        result.add(ByteCode.INS_GOTO(end_label));
        // False branch
        result.addLabel(else_label);
        conditional.getElseBranch().visit(this);
        result.addLabel(end_label);
    }

    public void visitGoto(Goto gotostatement) {
        result.add(ByteCode.INS_GOTO(gotostatement.getLabel()));
    }

    public void visitLabel(Label label) {
        result.addLabel(label.getLabel());
    }

    private int blockCounter = 0;
    public void visitPrimOp(PrimOp primop) {
        int blockArgIndex = -1;
        if(primop.hasBlockArgument()) {
            String label = "_block_"+(blockCounter++);
            this.addBlockArgument(label,primop.getBlockArgument());
            primop.setBlockResidualAddress(label);
        }
        Exp[] arguments = primop.duplicate().getArguments();
        String name = "INS_"+mapNameMaybe(primop.getName(),primop.needsMapping(),arguments);
        String[] residual = new String[arguments.length];
        for(int i=0; i<arguments.length; i++) {
            Exp arg = arguments[i];
            if(arg instanceof Label) {
                residual[i] = ((Label)arg).getLabel();
                blockArgIndex = i;
            } else if(arg instanceof ConstantRef)
                residual[i] = role.getConstant(((ConstantRef)arg).getName()).getValue().residualize();
            else if(arg instanceof Literal)
                residual[i] = ((Literal)arg).residualize();
            else
                throw new Error("Primop argument not supported: "+arg);
        }
        ByteCode bc;
        if(blockArgIndex==-1)
            bc = new ByteCode(name,arguments.length+1,residual);
        else
            bc = new ByteCode(name,arguments.length+1,residual,new int[] { blockArgIndex });
        result.add(bc);
    }

    private String mapNameMaybe(String name, boolean needsMapping, Exp[] arguments) {
        if(!needsMapping) return name;
        if(name.equals("EVAL_COMMAND")) {
            if(!(arguments[0] instanceof Predefined)) return name;
            String operation = ((Predefined)arguments[0]).getName();
            if(operation.equals("TURN_CONTINUOUSLY")) {
                int direction;
                if(arguments[1] instanceof Numeric)
                    direction = ((Numeric)arguments[1]).getValue();
                else if(arguments[1] instanceof ConstantRef)
                    direction = ((Numeric)role.getConstant(((ConstantRef)arguments[1]).getName()).getValue()).getValue();
                else throw new Error("Unknown argument type to "+operation+":"+arguments[1]);
                if(direction==1) {
                    arguments[0] = new Predefined("TURN_CLOCKWISE");
                    return name;
                } else {
                    arguments[0] = new Predefined("TURN_COUNTERCLOCKWISE");
                    arguments[1] = new Numeric(1);
                    return name;
                }
            }
        }
        throw new Error("Mapping not implemented for "+name);
    }

    public void visitBinExp(BinExp exp) {
        // Generate code to push left value on stack
        exp.getLeft().visit(this);
        // Generate code to push right value on stack
        exp.getRight().visit(this);
        // Compare
        result.add(new ByteCode("INS_"+exp.getName(),1,new String[0]));
    }

    public void visitCenterPos(CenterPos cp) {
        // TODO Auto-generated method stub
        // 
        throw new Error("Method not implemented");
    }

    public void visitConstantRef(ConstantRef ref) {
        result.add(ByteCode.INS_PUSHC(literalize(ref).residualize()));
    }

    public void visitDirection(Direction dir) {
        result.add(ByteCode.INS_PUSHC(dir.residualize()));
    }

    public void visitFunction(Function fun) {
        // Push argument on stack, if any
        if(fun.hasArgument()) for(Exp argument: fun.getArguments()) argument.visit(this);
        // Call primop
        result.add(new ByteCode("INS_"+fun.getName(),1,new String[0]));
    }

    public void visitNumeric(Numeric num) {
        result.add(ByteCode.INS_PUSHC(num.residualize()));
    }

    public void visitSelfFunction(SelfFunction sfun) {
        List<String> arguments = Collections.emptyList();
        // If an argument, push on stack
        if(sfun.hasArgument() && sfun.hasInlineArgument()) {
            arguments = new ArrayList<String>();
            for(Exp argument: sfun.getArguments())
                arguments.add((literalize(argument)).residualize());
        }
        else if(sfun.hasArgument()) for(Exp argument: sfun.getArguments()) argument.visit(this);
        // Call primop
        result.add(new ByteCode("INS_"+sfun.getName(),1+arguments.size(),arguments.toArray(new String[0])));
    }

    public Literal literalize(Exp expression) {
        if(expression instanceof Literal) return (Literal)expression;
        if(expression instanceof ConstantRef) {
            ConstantDef def = role.getConstant(((ConstantRef)expression).getName());
            return def.getValue();
        }
        throw new Error("Unable to handle expression type:"+expression);
    }
    
    public void visitNop(Nop nop) {
        ;
    }

    public void visitSingleExp(SingleExp exp) {
        exp.getExpression().visit(this);
    }

    public void visitCommand(Command command) {
        int methodIndex = vtableResolver.getMethodIndex(command.getRole(), command.getMethod())+128;
        result.add(new ByteCode("INS_COMMAND",3,new String[] { "ROLE_"+command.getRole(), Integer.toString(methodIndex) }));
    }

    public void visitPredefined(Predefined predefined) {
        // TODO Auto-generated method stub
        // 
        throw new Error("Method not implemented");
    }

    public void visitAssumeRole(AssumeRole assume) {
        result.add(new ByteCode("SET_ROLE_NOTIFY",2,new String[] { "ROLE_"+assume.getRole() }));
        for(Statement statement: assume.getBehavior().getStatements())
            statement.visit(this);
        result.add(new ByteCode("SET_ROLE_NOTIFY",2,new String[] { "ROLE_"+role.getName().getName() }));
    }

    public void visitUnaryExp(UnaryExp exp) {
        exp.getExp().visit(this);
        result.add(new ByteCode("INS_"+exp.getOperation(),1,new String[0]));
    }

    public void visitBlock(Block block) {
        for(Statement statement: block.getStatements())
            statement.visit(this);
    }


}
