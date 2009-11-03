package ussr.remote.facade;

import java.io.Serializable;

public abstract class ParameterHolder implements Serializable {
    private static ParameterHolder instance;
    
    public static void set(ParameterHolder in) {
        instance = in;
    }
    
    public static ParameterHolder get() {
        return instance;
    }
}
