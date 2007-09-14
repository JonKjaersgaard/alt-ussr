package dcd.highlevel;

import dcd.highlevel.ast.Method;
import dcd.highlevel.ast.Role;

public interface VTableResolver {
    public int getMethodIndex(String role, String method);
}
