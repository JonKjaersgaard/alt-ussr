package ussr.samples.atron.natives;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;

public class ATRONDcdLongCarSimulation extends ATRONNativeCarSimulation {
    
    public static void main(String argv[]) {
        ATRONDcdLongCarSimulation simulation = new ATRONDcdLongCarSimulation();
        simulation.obstacleType = ObstacleGenerator.ObstacleType.CIRCLE;
        simulation.main();
    }

    static VectorDescription pos(float x, float y, float z) {
        final float Yoffset = 0.25f;
        return new VectorDescription(x*ATRON.UNIT, y*ATRON.UNIT-Yoffset, z*ATRON.UNIT);
    }
    
    protected ArrayList<ModulePosition> buildCar() {
        return new ATRONBuilder().buildCar(6,new VectorDescription(0,-0.25f,0));
    }

}
