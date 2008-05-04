package dcd.highlevel;

import dcd.highlevel.ast.Method;
import dcd.highlevel.ast.Role;
import dcd.highlevel.fapl.GlobalName;

public interface Resolver {
    public static final int METHOD_OFFSET = 128;

    public int getMethodIndex(String role, String method);

    public int getGlobalIndex(String name);
}
