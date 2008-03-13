package ussr.samples.atron.natives.dcd;

import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.model.Controller;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.natives.ATRONNativeController;

public class ATRONDcdCyclicCarSimulation extends ATRONDCDCarSimulation {
    
    public static void main(String argv[]) {
        new ATRONDcdCyclicCarSimulation().main();
    }

    @Override
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ATRONNativeController("dcdController");
            }
        };
    }
    
    protected ArrayList<ModulePosition> buildCar() {
        return new ATRONBuilder().buildCyclicCar(new VectorDescription(0,-0.25f,0));
    }

}
