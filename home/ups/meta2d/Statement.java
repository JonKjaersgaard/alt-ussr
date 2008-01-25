/**
 * 
 */
package meta2d;


public abstract class Statement {
    protected int self;

    public Statement(int self) {
        this.self = self;
    }

    public boolean evaluate(MetaController metaController) {
        System.out.println("? Module "+metaController.getModule().getProperty("name")
                +"<"+metaController.getPosition()+"> considering "+this);
        if((metaController.getPosition()&self)==0) return false;
        System.out.println("! Module "+metaController.getModule().getProperty("name")
                +"<"+metaController.getPosition()+"> executing "+this);
        return evaluateImplementation(metaController);
    }

    protected abstract boolean evaluateImplementation(MetaController metaController);

    public String toString() {
        return this.getClass().getName()+"<"+self+">";
    }

    public abstract Statement reverseStatement();
    
    public boolean isGroupStatement() { return false; }

}