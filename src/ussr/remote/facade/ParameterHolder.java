package ussr.remote.facade;

import java.io.Serializable;

public abstract class ParameterHolder implements Serializable {

    private static ParameterHolder instance;
    protected Class<?> mainClass;

    public ParameterHolder(Class<?> mainClass) {
        this.mainClass = mainClass;
    }
    
    public static void set(ParameterHolder in) {
        instance = in;
    }
    
    public static ParameterHolder get() {
        return instance;
    }

    public Class<?> getMainClass() {
        return mainClass;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((mainClass == null) ? 0 : mainClass.hashCode());
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
        ParameterHolder other = (ParameterHolder) obj;
        if (mainClass == null) {
            if (other.mainClass != null)
                return false;
        } else if (!mainClass.equals(other.mainClass))
            return false;
        return true;
    }
}
