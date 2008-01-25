/**
 * 
 */
package meta2d.statements;

import meta2d.MetaController;
import meta2d.Statement;

public class Rotate extends Statement {
    protected int where, degrees;
    public Rotate(int self, int where, int degrees) {
        super(self);
        this.where = where; this.degrees = degrees;
    }

    @Override
    public boolean evaluateImplementation(MetaController metaController) {
        // ?
        metaController.rotateDegrees(degrees);
        return true;
    }

    @Override
    public Statement reverseStatement() {
        return new Rotate(self,where,-degrees);
    }

}