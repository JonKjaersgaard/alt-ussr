/**
 * 
 */
package ussr.samples.atron.natives;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONController;
import ussr.samples.atron.ATRONLatticeSimulation;
import ussr.samples.atron.ATRONLatticeSimulation.ModuleSelector;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class ATRONDcdCompassTest extends ATRONLatticeSimulation {

    public static void main(String argv[]) {
        new ATRONDcdCompassTest().main();
    }
    
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ATRONNativeController("dcdController");
            }
        };
    }

    protected ArrayList<ModulePosition> buildRobot() {
        ArrayList<ModulePosition> positions = buildAsNamedLattice(100,3,1,5, new Namer() {
            public String name(int number, VectorDescription pos, RotationDescription rot) {
                if(rot.equals(rotation_NS))
                    return "NS_1";
                else if(rot.equals(rotation_SN))
                    return "SN_1";
                else if(rot.equals(rotation_EW))
                    return "SN_2";
                else if(rot.equals(rotation_UD))
                    return "SN_3";
                else throw new Error("Unrecognized rotation: "+rot);
            }
        },new ModuleSelector() {
            public String select(String name, int index, VectorDescription pos, RotationDescription rot) {
                return null;
            }
        },unit);
        if(false) {
            positions.add(new ModulePosition("shoulder", new VectorDescription(3*unit,1*unit,2*unit), rotation_UD));
            positions.add(new ModulePosition("elbow", new VectorDescription(2*unit,2*unit,2*unit), rotation_EW));
            positions.add(new ModulePosition("hand", new VectorDescription(3*unit,3*unit,2*unit), rotation_UD));
            positions.add(new ModulePosition("finger99", new VectorDescription(4*unit,4*unit,2*unit), rotation_EW));
        }
        return positions;
    }
    
}
