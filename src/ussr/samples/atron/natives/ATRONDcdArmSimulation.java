/**
 * 
 */
package ussr.samples.atron.natives;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.robotbuildingblocks.WorldDescription.ModulePosition;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONController;
import ussr.samples.atron.ATRONLatticeSimulation;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class ATRONDcdArmSimulation extends ATRONLatticeSimulation {

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
        ArrayList<ModulePosition> positions = buildAsLattice(100,3,1,5);
//        ArrayList<ModulePosition> positions = buildAsLattice(10,2,1,3);
        positions.add(new WorldDescription.ModulePosition("shoulder", new VectorDescription(3*unit,1*unit,2*unit), rotation_UD));
        positions.add(new WorldDescription.ModulePosition("elbow", new VectorDescription(2*unit,2*unit,2*unit), rotation_EW));
        positions.add(new WorldDescription.ModulePosition("hand", new VectorDescription(3*unit,3*unit,2*unit), rotation_UD));
        positions.add(new WorldDescription.ModulePosition("finger99", new VectorDescription(4*unit,4*unit,2*unit), rotation_EW));
        return positions;
    }
    
}
