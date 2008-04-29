package dcd.highlevel.fapl;

import dcd.highlevel.IName;
import dcd.highlevel.ast.Exp;

public abstract class Name extends Exp implements IName {
    private String name;
    public Name(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
