package dcd.ussr.samples.atron.natives.dcd;

import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.model.Controller;
import ussr.physics.PhysicsSimulation;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.natives.ATRONNativeController;

public class ATRONDcdWideCarSimulation extends ATRONDCDCarSimulation {
    
    public static void main(String argv[]) {
        new ATRONDcdWideCarSimulation().main();
    }

    /*@Override
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ATRONNativeController("dcdController");
            }
        };
    }*/
    
    protected void simulationHook(PhysicsSimulation simulation) {
        ATRON zuper = new ATRON() {
            public Controller createController() {
                return new ATRONNativeController("dcdController");
            }
        };
        zuper.setSuper();
        simulation.setRobot(zuper, "ATRON super");
    }
    
    static VectorDescription pos(float x, float y, float z) {
        final float Yoffset = 0.25f;
        return new VectorDescription(x*ATRON.UNIT, y*ATRON.UNIT-Yoffset, z*ATRON.UNIT);
    }
    
    @Override
    protected ArrayList<ModulePosition> buildRobot() {
        return new ATRONBuilder().buildWideCar(new VectorDescription(0,-0.25f,0));
    }

}
