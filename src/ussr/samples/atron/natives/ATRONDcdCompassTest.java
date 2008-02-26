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
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.ATRONController;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.samples.atron.ATRONBuilder.ModuleSelector;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class ATRONDcdCompassTest extends GenericATRONSimulation {

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
        ArrayList<ModulePosition> positions = new ATRONBuilder().buildAsNamedLattice(100,3,1,5, new ATRONBuilder.Namer() {
            public String name(int number, VectorDescription pos, RotationDescription rot) {
                if(rot.equals(ATRON.ROTATION_NS))
                    return "NS_1";
                else if(rot.equals(ATRON.ROTATION_SN))
                    return "SN_1";
                else if(rot.equals(ATRON.ROTATION_EW))
                    return "SN_2";
                else if(rot.equals(ATRON.ROTATION_UD))
                    return "SN_3";
                else throw new Error("Unrecognized rotation: "+rot);
            }
        },new ATRONBuilder.ModuleSelector() {
            public String select(String name, int index, VectorDescription pos, RotationDescription rot) {
                return null;
            }
        },ATRON.UNIT);
        if(false) {
            positions.add(new ModulePosition("shoulder", new VectorDescription(3*ATRON.UNIT,1*ATRON.UNIT,2*ATRON.UNIT), ATRON.ROTATION_UD));
            positions.add(new ModulePosition("elbow", new VectorDescription(2*ATRON.UNIT,2*ATRON.UNIT,2*ATRON.UNIT), ATRON.ROTATION_EW));
            positions.add(new ModulePosition("hand", new VectorDescription(3*ATRON.UNIT,3*ATRON.UNIT,2*ATRON.UNIT), ATRON.ROTATION_UD));
            positions.add(new ModulePosition("finger99", new VectorDescription(4*ATRON.UNIT,4*ATRON.UNIT,2*ATRON.UNIT), ATRON.ROTATION_EW));
        }
        return positions;
    }
    
}
