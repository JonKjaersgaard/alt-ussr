package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Exp;

public class UnaryExp extends Exp {
    public static UnaryExp NEGATE(Exp exp) { return new UnaryExp("-","NEGATE",exp); }
    
    private Exp exp;
    private String representation, operation;
    
    private UnaryExp(String representation, String operation, Exp exp) {
        this.representation = representation;
        this.operation = operation;
        this.exp = exp;
    }
    
    @Override
    public void visit(Visitor visitor) {
        visitor.visitUnaryExp(this);
    }

    /**
     * @return the exp
     */
    public Exp getExp() {
        return exp;
    }

    /**
     * @param exp the exp to set
     */
    public void setExp(Exp exp) {
        this.exp = exp;
    }

    /**
     * @return the operation
     */
    public String getOperation() {
        return operation;
    }

    /**
     * @param operation the operation to set
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * @return the representation
     */
    public String getRepresentation() {
        return representation;
    }

    /**
     * @param representation the representation to set
     */
    public void setRepresentation(String representation) {
        this.representation = representation;
    }

}
