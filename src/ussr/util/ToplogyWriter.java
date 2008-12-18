package ussr.util;

import java.util.List;

import ussr.model.Module;

public interface ToplogyWriter {

    void addConnection(Module m1, Module m2);

    void finish();

}
