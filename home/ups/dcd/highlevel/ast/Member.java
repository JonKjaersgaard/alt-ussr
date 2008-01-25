package dcd.highlevel.ast;

public abstract class Member extends Node {
    public abstract boolean isAbstract();
    public Member duplicate() { return this; }
}
