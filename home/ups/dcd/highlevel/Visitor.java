package dcd.highlevel;

import dcd.highlevel.ast.Block;
import dcd.highlevel.ast.program.*;
import dcd.highlevel.fapl.Apply;
import dcd.highlevel.fapl.PrimName;

public interface Visitor {
    public void visitConditional(Conditional conditional);
    public void visitGoto(Goto gotostatement);
    public void visitLabel(Label label);
    public void visitPrimOp(PrimOp primop);
    public void visitCenterPos(CenterPos cp);
    public void visitConstantRef(ConstantRef ref);
    public void visitDirection(Direction dir);
    public void visitFunction(Function fun);
    public void visitNumeric(Numeric num);
    public void visitSelfFunction(SelfFunction sfun);
    public void visitBinExp(BinExp exp);
    public void visitNop(Nop nop);
    public void visitSingleExp(SingleExp exp);
    public void visitSendCommand(SendCommand command);
    public void visitPredefined(Predefined predefined);
    public void visitAssumeRole(AssumeRole role);
    public void visitUnaryExp(UnaryExp exp);
    public void visitBlock(Block block);
    public void visitNegate(Negate negativeConstant);
    public void visitApply(Apply apply);
    public void visitPrimName(PrimName primName);
}
