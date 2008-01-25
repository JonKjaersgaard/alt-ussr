/**
 * 
 */
package meta2d.statements;

import java.util.Collection;

import meta2d.MetaController;
import meta2d.Statement;


public class DisconnectForeigners extends Statement {

    public DisconnectForeigners(int self) { super(self); }

    @Override
    protected boolean evaluateImplementation(MetaController metaController) {
        Collection<Integer> metaNeighbors = metaController.getMetaNeighbors();
        for(int c=0; c<8; c++)
            if(!metaNeighbors.contains(c) && metaController.isConnected(c)) metaController.symmetricDisconnect(c);
        return true;
    }

    @Override
    public Statement reverseStatement() {
        return new ConnectForeigners(self);
    }

}