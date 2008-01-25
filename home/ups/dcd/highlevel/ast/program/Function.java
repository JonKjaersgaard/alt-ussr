package dcd.highlevel.ast.program;

import java.util.Arrays;
import java.util.List;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Exp;

public class Function extends Exp {
    private String name;
    private Exp[] arguments;
    private boolean inlineArguments;
    public Function(String name, Exp argument, boolean inlineArgument) {
        this(name, new Exp[] { argument }, inlineArgument);
    }
    public Function(String name, Exp argument) {
        this(name,argument,false);
    }
    public Function(String name) {
        this(name,new Exp[0],false);
    }
    public Function(String name, Exp[] arguments, boolean areInline) {
        this.name = name;
        this.arguments = arguments;
        this.inlineArguments = areInline;
    }
    /**
     * @return the argument
     */
    public List<Exp> getArguments() {
        return Arrays.asList(arguments);
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
        return arguments.length>0;
    }
    public boolean hasInlineArgument() {
        return inlineArguments;
    }
    public static Function SIZEOF(Exp exp) {
        return new Function("SIZEOF",exp);
    }
    @Override
    public void visit(Visitor visitor) {
        visitor.visitFunction(this);
    }
    public Function duplicate() { 
        Exp[] fresh = new Exp[arguments.length];
        for(int i=0; i<arguments.length; i++) fresh[i] = arguments[i].duplicate();
        return new Function(name, fresh, inlineArguments);
    }
}
