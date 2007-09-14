package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Statement;

public class Goto extends Statement {
    private String label;
    public Goto(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
    @Override
    public void visit(Visitor compiler) {
        compiler.visitGoto(this);
    }
}
