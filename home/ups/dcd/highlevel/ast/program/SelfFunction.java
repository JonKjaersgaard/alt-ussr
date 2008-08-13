package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Block;
import dcd.highlevel.ast.Exp;
import dcd.highlevel.ast.Name;
import dcd.highlevel.ast.Statement;

public class SelfFunction extends Function {

    public static final SelfFunction CENTER_POSITION = new SelfFunction("CENTER_POSITION");
    public static final SelfFunction TOTAL_CONNECTED = new SelfFunction("CONNECTED_SIZEOF");
    public static final SelfFunction Y = new SelfFunction("COORD_Y");
    public static final SelfFunction Z = new SelfFunction("COORD_Z");

    public SelfFunction(String name) {
        super(name);
    }

    public SelfFunction(String name, Exp argument) {
        super(name, argument);
    }

    public SelfFunction(String name, Exp argument, boolean hasInlineArgument) {
        super(name, argument, hasInlineArgument);
    }

    public SelfFunction(String name, Exp[] arguments, boolean areInline) {
        super(name, arguments, areInline);
    }

    public static SelfFunction CONNECTED(Exp argument) {
        return new SelfFunction("CONNECTED_DIR",argument,true);
    }
    @Override
    public void visit(Visitor visitor) {
        visitor.visitSelfFunction(this);
    }

    public static SelfFunction DISABLE_EVENT(ConstantRef ref) {
        return new SelfFunction("DISABLE_EVENT",ref,true);
    }

    public static SelfFunction TURN_CONTINUOUSLY(Exp exp) {
        return new SelfFunction("TURN_CONTINUOUSLY",exp);
    }

    public static SelfFunction HAS_ROLE(String roleName) {
        return new SelfFunction("HAS_ROLE",makeRoleName(roleName),true);
    }

    public static Exp SLEEP_WHILE_TURNING(Exp exp) {
        return new SelfFunction("SLEEP_ROTATIONS",exp,true);
    }

    public static Exp ENABLE_EVENT(ConstantRef ref) {
        return new SelfFunction("ENABLE_EVENT",ref,true);
    }
    
    public static Exp TURN_TOWARDS(Literal lit) {
        return new SelfFunction("TURN_TOWARDS",lit,true);
    }

    public static Exp CONNECTED_ROLE(Direction down, String roleName) {
        return new SelfFunction("CONNECTED_ROLE", new Exp[] { down, makeRoleName(roleName) }, true);
    }

    private static Predefined makeRoleName(String roleName) {
        return new Predefined("ROLE_"+roleName);
    }
    
    public SelfFunction duplicate() { 
        Exp[] fresh = new Exp[getArguments().size()];
        for(int i=0; i<getArguments().size(); i++) fresh[i] = getArguments().get(i).duplicate();
        return new SelfFunction(getName(), fresh, hasInlineArgument());
    }

    public static Exp INHIBIT_ROLE() {
        return new SelfFunction("INHIBIT_ROLE");
    }

}
