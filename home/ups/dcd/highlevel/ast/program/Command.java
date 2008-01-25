package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Statement;

public class Command extends Statement {
    private final String role, method;
    
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
     * @return the role
     */
    public String getRole() {
        return role;
    }

    @Override
    public void visit(Visitor compiler) {
        compiler.visitCommand(this);
    }

}
