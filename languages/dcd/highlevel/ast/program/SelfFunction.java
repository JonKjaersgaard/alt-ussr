package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Block;
import dcd.highlevel.ast.Exp;
import dcd.highlevel.ast.Name;
import dcd.highlevel.ast.Statement;

public class SelfFunction extends Function {

    public static final SelfFunction CENTER_POSITION = new SelfFunction("CENTER_POSITION");
    public static final SelfFunction TOTAL_CONNECTED = new SelfFunction("TOTAL_CONNECTED");
    public static final SelfFunction Y = new SelfFunction("Y");

    public SelfFunction(String name) {
        super(name);
    }

    public SelfFunction(String name, Exp argument) {
        super(name, argument);
    }

    public SelfFunction(String name, Exp argument, boolean hasInlineArgument) {
        super(name, argument, hasInlineArgument);
    }

    public static SelfFunction CONNECTED(Exp argument) {
        return new SelfFunction("CONNECTED",argument);
    }
    @Override
    public void visit(Visitor visitor) {
        visitor.visitSelfFunction(this);
    }

    public static SelfFunction DISABLE_EVENT(ConstantRef ref) {
        return new SelfFunction("DISABLE_EVENT",ref);
    }

    public static SelfFunction TURN_CONTINUOUSLY(Exp exp) {
        return new SelfFunction("TURN_CONTINUOUSLY",exp);
    }

    public static SelfFunction HAS_ROLE(String roleName) {
        return new SelfFunction("HAS_ROLE",new Predefined("ROLE_"+roleName),true);
    }

}
