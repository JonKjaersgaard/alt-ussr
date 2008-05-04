package dcd.highlevel.fapl;

import dcd.highlevel.Visitor;

public class LocalName extends Name {

    public LocalName(String name) {
        super(name);
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visitLocalName(this);
    }

}
