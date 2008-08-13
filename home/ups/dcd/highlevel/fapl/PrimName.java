package dcd.highlevel.fapl;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.program.NameImpl;

public class PrimName extends NameImpl {
    public PrimName(String name) {
        super(name);
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visitPrimName(this);
    }
    
    @Override
    public String getName() {
        return "PRIM_"+super.getName().toUpperCase();
    }
}
