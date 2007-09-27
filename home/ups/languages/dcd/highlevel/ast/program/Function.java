package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Exp;

public class Function extends Exp {
    private String name;
    private Exp argument;
    private boolean inlineArgument;
    public Function(String name, Exp argument, boolean inlineArgument) {
        this.name = name;
        this.argument = argument;
        this.inlineArgument = inlineArgument;
    }
    public Function(String name, Exp argument) {
        this(name,argument,false);
    }
    public Function(String name) {
        this(name,null);
    }
    /**
     * @return the argument
     */
    public Exp getArgument() {
        if(argument==null) throw new Error("Function has no argument: "+name);
        return argument;
    }
    /**
     * @param argument the argument to set
     */
    public void setArgument(Exp argument) {
        this.argument = argument;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    public boolean hasArgument() {
        return argument!=null;
    }
    public boolean hasInlineArgument() {
        return inlineArgument;
    }
    public static Function SIZEOF(Exp exp) {
        return new Function("SIZEOF",exp);
    }
    @Override
    public void visit(Visitor visitor) {
        visitor.visitFunction(this);
    }
    
}
