package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Block;
import dcd.highlevel.ast.Exp;

public class Negate extends Exp {
    private Exp exp;
    
    public Negate(Exp exp) {
        this.exp = exp;
    }
    
    @Override
    public void visit(Visitor visitor) {
        visitor.visitNegate(this);
    }

    public Exp getExp() {
        return exp;
    }

}
