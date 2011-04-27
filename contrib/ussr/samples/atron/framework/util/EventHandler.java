package ussr.samples.atron.framework.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Generic event handler encapsulating a target object and a parameterless method to invoke on that object
 * @author ups
 *
 */
public class EventHandler {
    /**
     * Target object to invoke a method on
     */
    private Object target;
    /**
     * The method to invoke on the target object
     */
    private Method handler;
    
    /**
     * Create a new event handler object for the target object and handler method
     * @param target the object to invoke the handler method on
     * @param handler the handler method to invoke on the object
     */
    public EventHandler(Object _target, Method _handler) {
        target = _target; handler = _handler;
    }

    /**
     * Invoke the handler method on the target object
     */
    public void invoke() {
        try {
            handler.invoke(target);
        } catch (IllegalArgumentException e) {
            throw new Error("Internal error: "+e);
        } catch (IllegalAccessException e) {
            throw new Error("Internal error: "+e);
        } catch (InvocationTargetException e) {
            System.err.print("Caught exception: ");
            e.printStackTrace();
            throw new Error("Invocation error: "+e.getTargetException());
        }
    }

}
