package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;

public class Numeric extends Literal {
    private final int value;
    public Numeric(int value) {
        this.value = value;
    }
    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + value;
        return result;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Numeric other = (Numeric) obj;
        if (value != other.value)
            return false;
        return true;
    }
    @Override
    public void visit(Visitor visitor) {
        visitor.visitNumeric(this);
    }
    @Override
    public String residualize() {
        return Integer.toString(this.getValue());
    }
    
}
