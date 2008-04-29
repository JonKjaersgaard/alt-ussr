package dcd.highlevel.fapl;

import dcd.highlevel.Visitor;

public class PrimName extends Name {
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
