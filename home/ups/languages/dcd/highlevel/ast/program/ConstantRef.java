package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Exp;
import dcd.highlevel.ast.Name;

public class ConstantRef extends Exp {
    private Name name;

    /**
     * @param name
     */
    public ConstantRef(Name name) {
        this.name = name;
    }

    public ConstantRef(String name) {
        this(new Name(name));
    }

    /**
     * @return the name
     */
    public Name getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public void visit(Visitor visitor) {
        visitor.visitConstantRef(this);
    }
    
}
