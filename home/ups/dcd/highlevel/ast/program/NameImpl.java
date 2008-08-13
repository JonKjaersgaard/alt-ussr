package dcd.highlevel.ast.program;

import dcd.highlevel.IName;
import dcd.highlevel.ast.Exp;

public abstract class NameImpl extends Exp implements IName {
    private String name;
    public NameImpl(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
