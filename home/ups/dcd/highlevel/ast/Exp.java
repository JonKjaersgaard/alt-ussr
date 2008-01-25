package dcd.highlevel.ast;

import dcd.highlevel.Visitor;

public abstract class Exp extends Node {

    public abstract void visit(Visitor visitor);

    public Exp duplicate() { return this; }

}
