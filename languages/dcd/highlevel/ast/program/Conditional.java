package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Exp;
import dcd.highlevel.ast.Statement;

public class Conditional extends Statement {
    private Exp condition;
    private Statement thenBranch, elseBranch;
    /**
     * @param condition
     * @param thenBranch
     * @param elseBranch
     */
    public Conditional(Exp condition, Statement thenBranch, Statement elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }
    /**
     * @return the condition
     */
    public Exp getCondition() {
        return condition;
    }
    /**
     * @param condition the condition to set
     */
    public void setCondition(Exp condition) {
        this.condition = condition;
    }
    /**
     * @return the elseBranch
     */
    public Statement getElseBranch() {
        return elseBranch;
    }
    /**
     * @param elseBranch the elseBranch to set
     */
    public void setElseBranch(Statement elseBranch) {
        this.elseBranch = elseBranch;
    }
    /**
     * @return the thenBranch
     */
    public Statement getThenBranch() {
        return thenBranch;
    }
    /**
     * @param thenBranch the thenBranch to set
     */
    public void setThenBranch(Statement thenBranch) {
        this.thenBranch = thenBranch;
    }
    @Override
    public void visit(Visitor processor) {
        processor.visitConditional(this);
    }

}
