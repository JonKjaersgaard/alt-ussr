package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Exp;

public class CenterPos extends Exp {

    @Override
    public void visit(Visitor visitor) {
        visitor.visitCenterPos(this);
    }

}
