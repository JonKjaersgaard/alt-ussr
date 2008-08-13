package dcd.highlevel.fapl;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.program.NameImpl;

public class GlobalName extends NameImpl {

    public GlobalName(String name) {
        super(name);
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visitGlobalName(this);
    }

}
