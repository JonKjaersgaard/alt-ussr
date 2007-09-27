package dcd.highlevel.ast.program;

import dcd.highlevel.CFileGenerator;
import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Block;
import dcd.highlevel.ast.Exp;
import dcd.highlevel.ast.Name;
import dcd.highlevel.ast.Statement;

public class PrimOp extends Statement {
    public static final PrimOp MIGRATE_CONTINUE = new PrimOp("MIGRATE_CONTINUE");
    private String name;
    private Exp arguments[];
    private Block blockArgument;
    
    public PrimOp(String name, Exp[] arguments, Block block) {
        this.name = name;
        this.arguments = arguments;
        this.blockArgument = block;
    }
    
    public PrimOp(String name) {
        this.name = name;
        arguments = new Exp[0];
    }
    public PrimOp(String name, Exp[] arguments) {
        this(name,arguments,null);
    }
    public PrimOp(String name, Exp argument) {
        this(name,new Exp[] { argument });
    }
    public String getName() {
        return name;
    }
    public Exp[] getArguments() {
        for(int i=0; i<arguments.length; i++)
            if(arguments[i]==null) throw new Error("getArguments called before block address resolved");
        return arguments;
    }
    public boolean hasArguments() {
        return arguments.length>0;
    }
    public boolean hasBlockArgument() {
        return blockArgument!=null;
    }
    public Block getBlockArgument() {
        if(blockArgument==null) throw new Error("Null block argument");
        return blockArgument;
    }
    public void setBlockResidualAddress(String label) {
        for(int i=0; i<arguments.length; i++)
            if(arguments[i]==null) {
                arguments[i] = new Label(label);
                return;
            }
        throw new Error("Empty slot for block argument not found");
    }
    public static PrimOp SET_ROLE_NOTIFY(Name roleName) {
        return new PrimOp("SET_ROLE_NOTIFY",new Predefined(CFileGenerator.roleName(roleName)));
    }

    public static PrimOp HANDLE_EVENT(Exp vector, Block block) {
        block.addStatement(PrimOp.TERMINATE());
        return new PrimOp("HANDLE_EVENT",new Exp[] { vector, null }, block); 
    }

    public static PrimOp TERMINATE() {
        return new PrimOp("TERMINATE");
    }

    @Override
    public void visit(Visitor compiler) {
        compiler.visitPrimOp(this);
    }

    public static PrimOp INSTALL_COMMAND(Name roleName, int methodIndex, Block body, boolean isBehavior) {
        if(isBehavior)
            body.addStatement(PrimOp.END_REPEAT());
        else
            body.addStatement(PrimOp.TERMINATE());
        return new PrimOp("INSTALL_COMMAND",new Exp[] { new Predefined("ROLE_"+roleName.getName()), new Numeric(methodIndex), null, new Numeric(body.getStatements().size()) }, body);
    }

    private static PrimOp END_REPEAT() {
        return new PrimOp("END_REPEAT");
    }

}
