package dcd.highlevel;

import java.util.List;

import dcd.highlevel.ast.ConstantDef;
import dcd.highlevel.ast.Modifier;
import dcd.highlevel.ast.Name;

public interface GlobalSource {

    public IName getName();
    public List<? extends InvariantSource> getInvariants();
    public boolean hasModifier(Modifier modifier);
    public ConstantDef getConstant(IName name);

}
