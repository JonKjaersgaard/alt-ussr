package dcd.highlevel.fapl;

import dcd.highlevel.ast.Exp;
import dcd.highlevel.generic.IName;

public abstract class Name extends Exp implements IName {
    private String name;
    public Name(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}
