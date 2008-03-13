package dcd.ussr.samples.atron.natives.dcd;

import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;

public class ATRONDcdLongCarSimulation extends ATRONDCDCarSimulation {
    
    public static void main(String argv[]) {
        ATRONDcdLongCarSimulation simulation = new ATRONDcdLongCarSimulation();
        simulation.obstacleType = ObstacleGenerator.ObstacleType.CIRCLE;
        simulation.main();
    }

    static VectorDescription pos(float x, float y, float z) {
        final float Yoffset = 0.25f;
        return new VectorDescription(x*ATRON.UNIT, y*ATRON.UNIT-Yoffset, z*ATRON.UNIT);
    }
    
    @Override
    protected ArrayList<ModulePosition> buildRobot() {
        return new ATRONBuilder().buildCar(6,new VectorDescription(0,-0.25f,0));
    }

}
