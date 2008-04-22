package dcd.highlevel.generic;

import dcd.highlevel.ast.Method;
import dcd.highlevel.ast.Role;

public interface Resolver {
    public int getMethodIndex(String role, String method);
}
