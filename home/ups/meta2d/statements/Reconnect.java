/**
 * 
 */
package meta2d.statements;

import meta2d.MetaController;
import meta2d.Statement;

public class Reconnect extends Statement {
    private int where;
    public Reconnect(int self, int where) {
        super(self);
        this.where = where;
    }

    @Override
    protected boolean evaluateImplementation(MetaController metaController) {
        int connector = metaController.role2channel(where);
        metaController.symmetricConnect(connector);
        return true;
    }

    @Override
    public Statement reverseStatement() {
        return new Disconnect(self,where);
    }
}