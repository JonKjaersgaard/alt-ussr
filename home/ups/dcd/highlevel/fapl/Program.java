package dcd.highlevel.fapl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import dcd.highlevel.GlobalSource;
import dcd.highlevel.IName;
import dcd.highlevel.InvariantSource;
import dcd.highlevel.ast.ConstantDef;
import dcd.highlevel.ast.Modifier;

public class Program implements GlobalSource {
    private List<Unit> declarations;
    public Program(Unit[] declarations) {
        this.declarations = new ArrayList<Unit>(Arrays.asList(declarations)); 
    }
    public List<Unit> getUnits() {
        return declarations;
    }
    public ConstantDef getConstant(IName name) {
        // TODO Auto-generated method stub
        // return null;
        throw new Error("Method not implemented");
    }
    public List<? extends InvariantSource> getInvariants() {
        // TODO Auto-generated method stub
        // return null;
        throw new Error("Method not implemented");
    }
    public IName getName() {
        // TODO Auto-generated method stub
        // return null;
        throw new Error("Method not implemented");
    }
    public boolean hasModifier(Modifier modifier) {
        // TODO Auto-generated method stub
        // return false;
        throw new Error("Method not implemented");
    }
}
