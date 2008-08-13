package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;

public class LocalName extends NameImpl {

    public LocalName(String name) {
        super(name);
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visitLocalName(this);
    }

}
