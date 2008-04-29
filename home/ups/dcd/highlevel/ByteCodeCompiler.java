package dcd.highlevel;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dcd.highlevel.ByteCodeSequence.AddressSpec;
import dcd.highlevel.ast.Block;
import dcd.highlevel.ast.ConstantDef;
import dcd.highlevel.ast.Exp;
import dcd.highlevel.ast.Name;
import dcd.highlevel.ast.Program;
import dcd.highlevel.ast.Role;
import dcd.highlevel.ast.Statement;
import dcd.highlevel.ast.program.*;
import dcd.highlevel.fapl.Apply;

public class ByteCodeCompiler implements Visitor {
    
    private ByteCodeSequence result = new ByteCodeSequence();
    private GlobalSource role;
    private Map<String,Block> blockMap = new HashMap<String,Block>();
    private Resolver vtableResolver;
    
    public ByteCodeCompiler(GlobalSource role, Resolver vtableResolver) {
        this.role = role;
        this.vtableResolver = vtableResolver;
    }
    
    public ByteCodeSequence compileCodeBlock(Block block) {
        for(Statement statement: block.getStatements())
            statement.visit(this);
        boolean addedBlocks = false;
        for(String label: blockMap.keySet()) {
            Block local = blockMap.get(label);
            result.addLabel(label);
            result.setBlockStart();
            for(Statement statement: local.getStatements())
                statement.visit(this);
            result.addLabel(label+"_END");
            addedBlocks = true;
        }
        if(addedBlocks) result.add(ByteCode.NOP());
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
        String label = null;
        if(primop.hasBlockArgument()) {
            label = "_block_"+(blockCounter++);
            this.addBlockArgument(label,primop.getBlockArgument());
            primop.setBlockResidualAddress(label);
        }
        Exp[] arguments = primop.duplicate().getArguments();
        String name = "INS_"+mapNameMaybe(primop.getName(),primop.needsMapping(),arguments);
        String[] residual = new String[arguments.length];
        for(int i=0; i<arguments.length; i++) {
            blockArgIndex = arg2residual(blockArgIndex, arguments[i], residual, i);
        }
        ByteCode bc;
        if(blockArgIndex==-1)
            bc = new ByteCode(name,arguments.length+1,residual);
        else {
            final int finalIndex = blockArgIndex;
            final String finalLabel = label;
            bc = new ByteCode(name,arguments.length+1,residual,new int[] { finalIndex }) {
                public void patch(Map<String, AddressSpec> table) {
                    int start = this.getTargetAddresses()[0];
                    Integer end = table.get(finalLabel+"_END").getReal();
                    if(end==null) throw new Error("Ending label for "+finalLabel+" not found in "+table);
                    this.getArguments()[finalIndex+1] = new Integer(end-start).toString()+" /*"+end+"-"+start+"*/";
                }
            };
        }
        result.add(bc);
    }

    private int arg2residual(int blockArgIndex, Exp arg, String[] residual, int i) {
        if(arg instanceof Label) {
            residual[i] = ((Label)arg).getLabel();
            blockArgIndex = i;
        } else if(arg instanceof ConstantRef)
            residual[i] = role.getConstant(((ConstantRef)arg).getName()).getValue().residualize();
        else if(arg instanceof Literal)
            residual[i] = ((Literal)arg).residualize();
        else if(arg instanceof Negate) {
            blockArgIndex = arg2residual(blockArgIndex, ((Negate)arg).getExp(), residual, i);
            residual[i] = residual[i].startsWith("-") ? residual[i].substring(1) : "-"+residual[i]; // textual negate
        } else
            throw new Error("Primop argument not supported: "+arg);
        return blockArgIndex;
    }

    private String mapNameMaybe(String name, boolean needsMapping, Exp[] arguments) {
        if(!needsMapping) return name;
        if(name.equals("EVAL_COMMAND")) {
            if(!(arguments[0] instanceof Predefined)) return name;
            String operation = ((Predefined)arguments[0]).getName();
            if(operation.equals("TURN_CONTINUOUSLY")) {
                int direction = arg2dir(arguments[1], operation);
                if(direction==1) {
                    arguments[0] = new Predefined("CMD_ROTATE_CLOCKWISE");
                    return name;
                } else {
                    arguments[0] = new Predefined("CMD_ROTATE_COUNTERCLOCKWISE");
                    arguments[1] = new Numeric(1);
                    return name;
                }
            }
            // Default case: add "CMD" as prefix to command name
            Predefined command = (Predefined)arguments[0];
            arguments[0] = new Predefined("CMD_"+command.getName());
            return name;
        }
        throw new Error("Mapping not implemented for "+name);
    }

    private int arg2dir(Exp argument, String operation) {
        int direction;
        if(argument instanceof Numeric)
            direction = ((Numeric)argument).getValue();
        else if(argument instanceof ConstantRef)
            direction = ((Numeric)role.getConstant(((ConstantRef)argument).getName()).getValue()).getValue();
        else if(argument instanceof Negate)
            direction = -arg2dir(((Negate)argument).getExp(),operation);
        else throw new Error("Unknown argument type to "+operation+":"+argument);
        return direction;
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

    public void visitSendCommand(SendCommand command) {
        int methodIndex = vtableResolver.getMethodIndex(command.getRole(), command.getMethod())+128;
        result.add(new ByteCode("INS_SEND_COMMAND",4,new String[] { "ROLE_"+command.getRole(), Integer.toString(methodIndex), command.getArgument().residualize() }));
    }

    public void visitPredefined(Predefined predefined) {
        // TODO Auto-generated method stub
        // 
        throw new Error("Method not implemented");
    }

    public void visitAssumeRole(AssumeRole assume) {
        result.add(new ByteCode("INS_SET_ROLE_NOTIFY",2,new String[] { "ROLE_"+assume.getRole() }));
        for(Statement statement: assume.getBehavior().getStatements())
            statement.visit(this);
        result.add(new ByteCode("INS_SET_ROLE_NOTIFY",2,new String[] { "ROLE_"+role.getName().getName() }));
    }

    public void visitUnaryExp(UnaryExp exp) {
        exp.getExp().visit(this);
        result.add(new ByteCode("INS_"+exp.getOperation(),1,new String[0]));
    }

    public void visitBlock(Block block) {
        for(Statement statement: block.getStatements())
            statement.visit(this);
    }

    public void visitNegate(Negate negativeConstant) {
        negativeConstant.getExp().visit(this);
        result.add(new ByteCode("INS_NEGATE",1,new String[] { }));
    }

    public void visitApply(Apply apply) {
        
    }


}
