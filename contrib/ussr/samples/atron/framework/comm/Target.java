package ussr.samples.atron.framework.comm;

import java.lang.reflect.Method;

/**
 * Value object representing an RPC target: a qualifying type and a method
 * @author ups
 */
public class Target {
    /**
     * The qualifying type of the target
     */
    private Class<?> targetClass;
    /**
     * The method to call on the target
     */
    private Method command;
    
    /**
     * Create a value object representing an RPC target
     * @param _targetClass the qualifying type of the target
     * @param _command the method to call on the target
     */
    public Target(Class<?> _targetClass, Method _command) {
        targetClass = _targetClass; command = _command;
    }
    
    /**
     * Get the qualifying class of the target
     * @return the qualifying class
     */
    public Class<?> getTargetClass() { return targetClass; }
    
    /**
     * Get the method to call on the target
     * @return the method to call
     */
    public Method getCommand() { return command; }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((command == null) ? 0 : command.hashCode());
        result = prime * result
                + ((targetClass == null) ? 0 : targetClass.hashCode());
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
        Target other = (Target) obj;
        if (command == null) {
            if (other.command != null)
                return false;
        } else if (!command.equals(other.command))
            return false;
        if (targetClass == null) {
            if (other.targetClass != null)
                return false;
        } else if (!targetClass.equals(other.targetClass))
            return false;
        return true;
    }

}
