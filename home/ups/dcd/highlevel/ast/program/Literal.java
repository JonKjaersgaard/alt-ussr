package dcd.highlevel.ast.program;

import dcd.highlevel.ast.Exp;

public abstract class Literal extends Exp {

    public abstract String residualize();
    public Literal duplicate() { return this; }

}
