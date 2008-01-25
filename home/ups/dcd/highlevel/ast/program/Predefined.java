package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;

public class Predefined extends Literal {
    private final String residualName;
    
    public Predefined(String residualName) {
        this.residualName =  residualName;
    }
    
    
    @Override
    public String residualize() {
        return residualName;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visitPredefined(this);
    }


    public String getName() {
        return residualName;
    }

}
