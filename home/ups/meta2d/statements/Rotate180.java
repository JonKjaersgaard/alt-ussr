/**
 * 
 */
package meta2d.statements;

import meta2d.MetaController;
import meta2d.Statement;

public class Rotate180 extends Statement {
    public Rotate180(int self) { super(self); }

    @Override
    protected boolean evaluateImplementation(MetaController metaController) {
        metaController.rotateDegrees(180);
        return true;
    }

    @Override
    public Statement reverseStatement() {
        return new Rotate180(self);
    }
}