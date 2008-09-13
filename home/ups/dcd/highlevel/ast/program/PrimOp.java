package dcd.highlevel.ast.program;

import java.util.List;

import dcd.highlevel.IName;
import dcd.highlevel.Visitor;
import dcd.highlevel.ast.Block;
import dcd.highlevel.ast.Exp;
import dcd.highlevel.ast.Name;
import dcd.highlevel.ast.Statement;
import dcd.highlevel.rdcd.RDCDCodeGenerator;

public class PrimOp extends Statement {
    public static final PrimOp MIGRATE_CONTINUE = new PrimOp("MIGRATE_CONTINUE");
    public static final PrimOp DEBUG = new PrimOp("ACTIVATE_DEBUG");
    private String name;
    private Exp arguments[];
    private Block blockArgument;
    private boolean needsMapping = false;
    
    public PrimOp(String name, Exp[] arguments, Block block) {
        this(name,arguments,block,false);
    }
    public PrimOp(String name, Exp[] arguments, Block block, boolean needsMapping) {
        this.name = name;
        this.arguments = arguments;
        this.blockArgument = block;
        this.needsMapping = needsMapping;
    }
    
    public PrimOp(String name) {
        this.name = name;
        arguments = new Exp[0];
    }
    public PrimOp(String name, Exp[] arguments) {
        this(name,arguments,null,false);
    }
    public PrimOp(String name, Exp argument) {
        this(name,new Exp[] { argument });
    }
    public String toString() {
        StringBuffer result = new StringBuffer(name);
        for(int i=0; i<arguments.length; i++) {
            result.append(", ");
            result.append(arguments[i]);
        }
        return result.toString();
    }
    
    public boolean needsMapping() { return this.needsMapping; }
    
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
                if(i+1==arguments.length || !(arguments[i+1]==Numeric.NaN)) 
                    throw new Error("Malformed block argument: must be followed by size");
                return;
            }
        throw new Error("Empty slot for block argument not found in "+this);
    }

    public static String roleName(IName roleName) {
        return "ROLE_"+roleName;
    }

    public static PrimOp SET_ROLE_NOTIFY(IName roleName) {
        return new PrimOp("SET_ROLE_NOTIFY",new Predefined(roleName(roleName)));
    }

    public static PrimOp HANDLE_EVENT(Exp vector, Block block) {
        block.addStatement(PrimOp.TERMINATE());
        return new PrimOp("HANDLE_EVENT",new Exp[] { vector, null, Numeric.NaN }, block); 
    }

    public static Statement HANDLE_EVENT_MULTI(Exp[] vectors, Block block) {
        block.addStatement(PrimOp.TERMINATE());
        Statement[] statements = new Statement[vectors.length];
        for(int i=0; i<vectors.length; i++)
            statements[i] = new PrimOp("HANDLE_EVENT",new Exp[] { vectors[i], null, Numeric.NaN }, block); 
        Block result = new Block(statements);
        return result;
    }

    public static PrimOp TERMINATE() {
        return new PrimOp("END_TERMINATE");
    }

    @Override
    public void visit(Visitor compiler) {
        compiler.visitPrimOp(this);
    }

    public static PrimOp INSTALL_COMMAND(IName roleName, int methodIndex, Block body, boolean isBehavior) {
        if(isBehavior)
            body.addStatement(PrimOp.END_REPEAT());
        else
            body.addStatement(PrimOp.TERMINATE());
        return new PrimOp("INSTALL_COMMAND",new Exp[] { new Predefined("ROLE_"+roleName.getName()), new Numeric(methodIndex), null, Numeric.NaN }, body);
    }

    private static PrimOp END_REPEAT() {
        return new PrimOp("END_REPEAT");
    }

    public PrimOp duplicate() {
        Exp[] fresh = new Exp[arguments.length];
        for(int i=0; i<arguments.length; i++) fresh[i] = arguments[i]==null ? null : arguments[i].duplicate();
        return new PrimOp(name,fresh,blockArgument==null ? null : blockArgument.duplicate(),needsMapping);
    }

    public static Statement EVAL_COMMAND(int index, Exp argument) {
        return new PrimOp("EVAL_COMMAND", new Exp[] { new Numeric(index), argument });
    }

    public static Statement EVAL_NAMED_COMMAND(String name, Exp argument) {
        return new PrimOp("EVAL_COMMAND", new Exp[] { new Predefined(name), argument },null,true);
    }
}
