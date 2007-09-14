package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.*;

public class SingleExp extends Statement {
    private Exp expression;
    
    public SingleExp(Exp expression) {
        this.expression = expression;
    }
    
    /**
     * @return the expression
     */
    public Exp getExpression() {
        return expression;
    }

    /**
     * @param expression the expression to set
     */
    public void setExpression(Exp expression) {
        this.expression = expression;
    }

    @Override
    public void visit(Visitor compiler) {
        compiler.visitSingleExp(this);
    }

}
