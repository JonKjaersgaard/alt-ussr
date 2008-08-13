package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Exp;
import dcd.highlevel.ast.Statement;

public class SendCommand extends Statement {
    private final String role, method;
    private Exp argument;
    
    /**
     * @param role
     * @param method
     */
    public SendCommand(String role, String method, Exp argument) {
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

    public boolean hasLiteralArgument() {
        return argument instanceof Literal;
    }
    
    public Literal getLiteralArgument() {
        if(!(argument instanceof Literal)) throw new Error("Argument is not literal");
        return (Literal)argument;
    }

    public Exp getArgument() {
        return argument;
    }

    @Override
    public void visit(Visitor compiler) {
        compiler.visitSendCommand(this);
    }

    public Statement duplicate() {
        return new SendCommand(role,method,argument.duplicate());
    }
}
