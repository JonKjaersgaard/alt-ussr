/**
 * 
 */
package dcd.ussr.samples.atron.natives.dcd;

import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.model.Controller;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.ATRONController;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.samples.atron.natives.ATRONNativeController;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class ATRONDcdArmSimulation extends GenericATRONSimulation {

    public static void main(String argv[]) {
        new ATRONDcdArmSimulation().main();
    }
    
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ATRONNativeController("dcdController");
            }
        };
    }

    protected ArrayList<ModulePosition> buildRobot() {
        ArrayList<ModulePosition> positions = new ATRONBuilder().buildAsLattice(100,3,1,5);
        positions.add(new ModulePosition("shoulder", new VectorDescription(3*ATRON.UNIT,1*ATRON.UNIT,2*ATRON.UNIT), ATRON.ROTATION_UD));
        positions.add(new ModulePosition("elbow", new VectorDescription(2*ATRON.UNIT,2*ATRON.UNIT,2*ATRON.UNIT), ATRON.ROTATION_EW));
        positions.add(new ModulePosition("hand", new VectorDescription(3*ATRON.UNIT,3*ATRON.UNIT,2*ATRON.UNIT), ATRON.ROTATION_UD));
        positions.add(new ModulePosition("finger99", new VectorDescription(4*ATRON.UNIT,4*ATRON.UNIT,2*ATRON.UNIT), ATRON.ROTATION_EW));
        return positions;
    }
    
}
