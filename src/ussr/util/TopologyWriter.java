package ussr.util;

import java.util.List;

import ussr.model.Module;

public interface TopologyWriter {

    void addConnection(Module m1, Module m2);

    void finish();

}
