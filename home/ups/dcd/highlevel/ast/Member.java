package dcd.highlevel.ast;

public abstract class Member extends ASTNode {
    public abstract boolean isAbstract();
    public Member duplicate() { return this; }
}
