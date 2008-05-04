package dcd.highlevel;

import dcd.highlevel.ast.Block;

public interface MethodSpec {
    public IName getName();
    public Block getBody();
}
