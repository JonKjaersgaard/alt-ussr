package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Statement;

public class Nop extends Statement {

    @Override
    public void visit(Visitor compiler) {
        compiler.visitNop(this);
    }

}
