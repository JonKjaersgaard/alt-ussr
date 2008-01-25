package dcd.highlevel.ast.program;

import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Block;
import dcd.highlevel.ast.Statement;

public class AssumeRole extends Statement {
    private String role;
    private Block behavior;

    @Override
    public void visit(Visitor compiler) {
        compiler.visitAssumeRole(this);
    }

    /**
     * @param role
     */
    public AssumeRole(String role, Block behavior) {
        this.role = role;
        this.behavior = behavior;
    }

    /**
     * @return the behavior
     */
    public Block getBehavior() {
        return behavior;
    }

    /**
     * @param behavior the behavior to set
     */
    public void setBehavior(Block behavior) {
        this.behavior = behavior;
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
    public Statement duplicate() {
        return new AssumeRole(role,behavior.duplicate());
    }

    
}
