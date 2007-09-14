package ussr.physics;

import java.awt.Color;

import ussr.model.Module;

public interface PhysicsSimulationHelper {

    PhysicsVectorHolder getModulePos(Module module);
    PhysicsQuaternionHolder getModuleOri(Module m);
    void setColor(Object elm, Color color);

}
