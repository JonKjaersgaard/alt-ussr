package ussr.samples.atron.natives;

import java.util.ArrayList;

import ussr.description.ModulePosition;
import ussr.description.Robot;
import ussr.description.VectorDescription;
import ussr.model.Controller;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;

public class ATRONDcdCyclicCarSimulation extends ATRONNativeCarSimulation {
    
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
