package dcd.highlevel.ast;

import dcd.highlevel.ByteCodeCompiler;
import dcd.highlevel.Visitor;

public abstract class Statement extends ASTNode {

    public abstract void visit(Visitor compiler);
    public Statement duplicate() { return this; }

}
