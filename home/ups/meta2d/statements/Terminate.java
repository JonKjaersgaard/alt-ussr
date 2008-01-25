/**
 * 
 */
package meta2d.statements;

import meta2d.Meta2DSimulation;
import meta2d.MetaController;
import meta2d.Statement;

public class Terminate extends Statement {
    public Terminate() {
        super(Meta2DSimulation.MetaPosition_NONE);
    }

    public boolean evaluateImplementation(MetaController metaController) {
        throw new Error("Should not be called");
    }

    @Override
    public Statement reverseStatement() {
        throw new Error("Terminate cannot be reversed");
    }
}