/**
 * 
 */
package meta2d.statements;

import meta2d.MetaController;
import meta2d.Statement;


public class Disconnect extends Statement {
    private int where;
    public Disconnect(int self, int where) {
        super(self);
        this.where = where;
    }

    @Override
    public boolean evaluateImplementation(MetaController metaController) {
        // ?
        int connector = metaController.role2channel(where);
        metaController.symmetricDisconnect(connector);
        return true;
    }

    @Override
    public Statement reverseStatement() {
        return new Reconnect(self,where);
    }
}