package ussr.samples.atron.framework;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import ussr.samples.atron.framework.ATRONFramework.Role;

public class EventHandler {
    private Role role;
    private Method handler;
    
    public EventHandler(Role _role, Method _handler) {
        role = _role; handler = _handler;
    }

    public void invoke() {
        try {
            handler.invoke(role);
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
