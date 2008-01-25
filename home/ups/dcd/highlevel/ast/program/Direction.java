package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Exp;

public class Direction extends Literal {
    private final String description;
    
    public static final Direction EAST_WEST = new Direction("EAST_WEST");
    public static final Direction NORTH_SOUTH = new Direction("NORTH_SOUTH");
    public static final Direction UP_DOWN = new Direction("UP_DOWN");
    public static final Direction UP = new Direction("UP");
    public static final Direction EAST = new Direction("EAST");
    public static final Direction DOWN = new Direction("DOWN");
    public static final Direction WEST = new Direction("WEST");
    
    public Direction(String description) {
        this.description = description;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visitDirection(this);
    }

    @Override
    public String residualize() {
        return "ARG_"+getDescription();
    }


}