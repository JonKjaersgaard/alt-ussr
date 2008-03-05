package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Statement;

public class SendCommand extends Statement {
    private final String role, method;
    private Literal argument;
    
    /**
     * @param role
     * @param method
     */
    public SendCommand(String role, String method, Literal argument) {
        this.role = role;
        this.method = method;
        this.argument = argument;
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
    
    public Literal getArgument() {
        return argument;
    }

    @Override
    public void visit(Visitor compiler) {
        compiler.visitSendCommand(this);
    }

}
