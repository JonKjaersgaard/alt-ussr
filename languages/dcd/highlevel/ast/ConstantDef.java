package dcd.highlevel.ast;

import dcd.highlevel.ast.program.Literal;
import dcd.highlevel.ast.program.Numeric;

public class ConstantDef extends Member {
    public static final Numeric EVENT_HANDLER_5 = new Numeric(5);
    
    private Name name;
    private Literal value;
    /**
     * @param name
     * @param value
     * @param isAbstract
     */
    public ConstantDef(Name name, Literal value) {
        this.name = name;
        this.value = value;
    }
    public ConstantDef(String name, Literal value) {
        this(new Name(name),value);
    }
    public ConstantDef(String name) {
        this(name,null);
    }
    /**
     * @return the isAbstract
     */
    public boolean isAbstract() {
        return value==null;
    }
    /**
     * @return the name
     */
    public Name getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(Name name) {
        this.name = name;
    }
    /**
     * @return the value
     */
    public Literal getValue() {
        if(isAbstract()) throw new Error("getValue() on abstract constant "+name);
        return value;
    }
    /**
     * @param value the value to set
     */
    public void setValue(Literal value) {
        if(isAbstract()) throw new Error("setValue() on abstract constant "+name);
        this.value = value;
    }
    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((name == null) ? 0 : name.hashCode());
        result = PRIME * result + ((value == null) ? 0 : value.hashCode());
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
        final ConstantDef other = (ConstantDef) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }
 }
