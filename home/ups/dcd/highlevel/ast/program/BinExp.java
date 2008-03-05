package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Exp;

public class BinExp extends Exp {
    public static BinExp EQUALS(Exp left, Exp right) { return new BinExp("==","EQUALS",left,right); }
    public static BinExp GREATER(Exp left, Exp right) { return new BinExp(">","GREATER",left,right); }
    
    private String syntax, name;
    private Exp left, right;

    /**
     * @param left
     * @param right
     */
    public BinExp(String syntax, String name, Exp left, Exp right) {
        this.syntax = syntax;
        this.name = name;
        this.left = left;
        this.right = right;
    }

    /**
     * @return the left
     */
    public Exp getLeft() {
        return left;
    }

    /**
     * @param left the left to set
     */
    public void setLeft(Exp left) {
        this.left = left;
    }

    /**
     * @return the right
     */
    public Exp getRight() {
        return right;
    }

    /**
     * @param right the right to set
     */
    public void setRight(Exp right) {
        this.right = right;
    }

    public String getName() {
        return name;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visitBinExp(this);
    }
    @Override
    public Exp duplicate() {
        return new BinExp(syntax,name,left.duplicate(),right.duplicate());
    }
}
