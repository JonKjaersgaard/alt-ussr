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
        // TODO Auto-generated method stub
        // 
        throw new Error("Method not implemented");
    }
    
}
