package dcd.highlevel.ast;

import dcd.highlevel.generic.IName;

public class Name extends Node implements IName {
    private final String name;

    /**
     * @param name
     */
    public Name(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return name==null ? 0 : name.hashCode();
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
        if(obj instanceof String) { return obj.equals(name); }
        if (getClass() != obj.getClass())
            return false;
        final Name other = (Name) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
    
}
