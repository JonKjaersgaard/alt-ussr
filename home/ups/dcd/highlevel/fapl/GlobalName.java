package dcd.highlevel.fapl;

import dcd.highlevel.Visitor;

public class GlobalName extends Name {

    public GlobalName(String name) {
        super(name);
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visitGlobalName(this);
    }

}
