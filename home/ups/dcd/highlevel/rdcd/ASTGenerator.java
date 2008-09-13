package dcd.highlevel.rdcd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import dcd.highlevel.ast.ASTNode;
import dcd.highlevel.ast.ConstantDef;
import dcd.highlevel.ast.Exp;
import dcd.highlevel.ast.Invariant;
import dcd.highlevel.ast.Method;
import dcd.highlevel.ast.Modifier;
import dcd.highlevel.ast.Name;
import dcd.highlevel.ast.Program;
import dcd.highlevel.ast.Role;
import dcd.highlevel.ast.program.BinExp;
import dcd.highlevel.ast.program.ConstantRef;
import dcd.highlevel.ast.program.Literal;
import dcd.highlevel.ast.program.Numeric;
import dcd.highlevel.rdcd.parser.analysis.DepthFirstAdapter;
import dcd.highlevel.rdcd.parser.node.AAbstractConstant;
import dcd.highlevel.rdcd.parser.node.AAdditionBinaryExp;
import dcd.highlevel.rdcd.parser.node.ABinexpExp;
import dcd.highlevel.rdcd.parser.node.AConcreteIntConstant;
import dcd.highlevel.rdcd.parser.node.ADecimalIntegerLiteral;
import dcd.highlevel.rdcd.parser.node.ADeploymentSpec;
import dcd.highlevel.rdcd.parser.node.AEqualityBinaryExp;
import dcd.highlevel.rdcd.parser.node.AGreaterThanBinaryExp;
import dcd.highlevel.rdcd.parser.node.AInvariant;
import dcd.highlevel.rdcd.parser.node.ARoleDeclaration;
import dcd.highlevel.rdcd.parser.node.AVariableExp;
import dcd.highlevel.rdcd.parser.node.Node;
import dcd.highlevel.rdcd.parser.node.PName;
import dcd.highlevel.rdcd.parser.node.TIdentifier;

public class ASTGenerator extends DepthFirstAdapter {
    private List<Name> deployment;
    private List<Role> roles = new LinkedList<Role>();
    private List<ConstantDef> constants;
    private List<Invariant> invariants;
    private Map<Node,ASTNode> returnMap = new HashMap<Node,ASTNode>();

    public ASTNode get(Node node) { 
        ASTNode result = returnMap.get(node);
        if(result==null) throw new Error("Return value not defined for "+node.getClass()+" = "+node);
        return result;
    }
    
    public void set(Node node, ASTNode value) {
        returnMap.put(node, value);
    }
    
    public Program getAST() {
        return new Program(roles,deployment);
    }

    @Override
    public void inARoleDeclaration(ARoleDeclaration node) {
        constants = new LinkedList<ConstantDef>();
        invariants = new LinkedList<Invariant>();
    }
    
    @Override
    public void outARoleDeclaration(ARoleDeclaration node) {
        Name name = new Name(node.getName().getText());
        Name zuper = new Name(node.getSuper().getText());
        Role role = new Role(name, zuper, new LinkedList<Modifier>(), constants, invariants, new LinkedList<Method>());
        roles.add(role);
    }

    @Override
    public void outADeploymentSpec(ADeploymentSpec spec) {
        deployment = new LinkedList<Name>();
        for(TIdentifier name: spec.getIdentifier())
            deployment.add(new Name(name.getText()));
    }
    
    @Override
    public void outAAbstractConstant(AAbstractConstant constant) {
        constants.add(new ConstantDef(constant.getName().getText()));
    }
    
    @Override
    public void outAConcreteIntConstant(AConcreteIntConstant constant) {
        constants.add(new ConstantDef(constant.getName().getText(),(Literal)get(constant.getValue())));
    }

    @Override
    public void outADecimalIntegerLiteral(ADecimalIntegerLiteral i) {
        set(i,new Numeric(Integer.parseInt(i.getDecimalIntegerLiteral().getText())));
    }
    
    @Override
    public void outAInvariant(AInvariant invar) {
        invariants.add(new Invariant((Exp)get(invar.getExp())));
    }

    @Override
    public void outAVariableExp(AVariableExp var) {
        set(var,new ConstantRef(var.getIdentifier().getText()));
    }
    
    @Override
    public void outABinexpExp(ABinexpExp exp) {
        set(exp,get(exp.getBinaryExp()));
    }
    
    @Override
    public void outAGreaterThanBinaryExp(AGreaterThanBinaryExp bin) {
        set(bin,BinExp.GREATER((Exp)get(bin.getLeft()), (Exp)get(bin.getRight())));
    }

    @Override
    public void outAEqualityBinaryExp(AEqualityBinaryExp bin) {
        set(bin,BinExp.EQUALS((Exp)get(bin.getLeft()), (Exp)get(bin.getRight())));
    }
}
