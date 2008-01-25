package dcd.highlevel.ast;

public class Invariant extends Member {
    private Exp condition;

    /**
     * @param condition
     */
    public Invariant(Exp condition) {
        this.condition = condition;
    }

    /**
     * @return the condition
     */
    public Exp getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(Exp condition) {
        this.condition = condition;
    }

    @Override
    public boolean isAbstract() {
        return false;
    }

    @Override
    public Member duplicate() {
        return new Invariant(condition.duplicate());
    }
    
}
