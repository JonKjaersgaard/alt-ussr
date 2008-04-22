package dcd.highlevel.generic;

import java.util.List;

import dcd.highlevel.ast.Modifier;

public interface GlobalSource {

    public IName getName();
    public List<? extends InvariantSource> getInvariants();
    public boolean hasModifier(Modifier modifier);

}
