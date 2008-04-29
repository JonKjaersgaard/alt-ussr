package dcd.highlevel.fapl;

import dcd.highlevel.ast.Exp;

public class Evaluate extends Unit {
    private Exp exp;
    public Evaluate(Exp exp) {
        this.exp = exp;
    }
    public Exp getExp() {
        return exp;
    }
}
