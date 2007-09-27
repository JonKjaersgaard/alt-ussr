package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Statement;

public class Command extends Statement {
    private String role, method;
    
    /**
     * @param role
     * @param method
     */
    public Command(String role, String method) {
        this.role = role;
        this.method = method;
    }

    /**
     * @return the method
     */
    public String getMethod() {
        return method;
    }

    /**
     * @param method the method to set
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * @param role the role to set
     */
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public void visit(Visitor compiler) {
        compiler.visitCommand(this);
    }

}
