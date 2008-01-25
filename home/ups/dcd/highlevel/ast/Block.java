package dcd.highlevel.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dcd.highlevel.Visitor;

public class Block extends Statement {
    private List<Statement> statements;

    /**
     * @param statements
     */
    public Block(List<Statement> statements) {
        this.statements = statements;
    }

    public Block(Statement[] statements) {
        this.statements = new ArrayList<Statement>(Arrays.asList(statements));
    }

    /**
     * @return the statements
     */
    public List<Statement> getStatements() {
        return statements;
    }

    /**
     * @param statements the statements to set
     */
    public void setStatements(List<Statement> statements) {
        this.statements = statements;
    }

    public void addStatement(Statement statement) {
        statements.add(statement);
    }

    @Override
    public void visit(Visitor compiler) {
        compiler.visitBlock(this);
    }

    public Block duplicate() {
        ArrayList<Statement> fresh = new ArrayList<Statement>();
        for(Statement st: statements) fresh.add(st.duplicate());
        return new Block(fresh);
    }
    
}
