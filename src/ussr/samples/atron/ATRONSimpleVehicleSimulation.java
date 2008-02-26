package ussr.samples.atron;

import java.util.ArrayList;
/* Essentially a stripped down car */

import ussr.model.Controller;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.samples.ObstacleGenerator;

/**
 * A simulation for a two-wheeler ATRON robot
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONSimpleVehicleSimulation extends GenericATRONSimulation {
	
	public static void main( String[] args ) {
        new ATRONSimpleVehicleSimulation().main();
    }
	
	protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ATRONSimpleVehicleController1();
            }
        };
    }
	protected ArrayList<ModulePosition> buildCar() {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("driver0", new VectorDescription(-2*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("RearRightWheel", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("RearLeftWheel", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
        return mPos;
	}

	protected ArrayList<ModulePosition> buildRobot() {
		return buildCar();
	}
    
    protected void changeWorldHook(WorldDescription world) {
        ObstacleGenerator generator = new ObstacleGenerator();
        generator.obstacalize(ObstacleGenerator.ObstacleType.LINE, world);
    }
}
