package dcd.highlevel.fapl;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Exp;

public class Apply extends Exp {
    private Exp fun, arg;

    /**
     * @param fun
     * @param arg
     */
    public Apply(Exp fun, Exp arg) {
        this.fun = fun;
        this.arg = arg;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visitApply(this);
    }

    /**
     * @return the fun
     */
    public Exp getFun() {
        return fun;
    }

    /**
     * @return the arg
     */
    public Exp getArg() {
        return arg;
    }
    
}
