package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Exp;
import dcd.highlevel.ast.Statement;

public class Label extends Exp {
    private String label;
    public Label(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
    @Override
    public void visit(Visitor compiler) {
        compiler.visitLabel(this);
    }
}
