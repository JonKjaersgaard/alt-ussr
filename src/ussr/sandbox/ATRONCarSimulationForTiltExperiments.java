package ussr.sandbox;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.GenericATRONSimulation;

public class ATRONCarSimulationForTiltExperiments extends GenericATRONSimulation {
	
	
	public static void main( String[] args ) {
        new ATRONCarSimulationForTiltExperiments().main();
    }
	
	protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ATRONCarControllerForTiltSensorExperiments();
            }
        };
    }
	private static ArrayList<ModulePosition> buildCar() {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("driver", new VectorDescription(2*0*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	mPos.add(new ModulePosition("axleOne", new VectorDescription(1*unit,-1*unit-Yoffset,0*unit), rotation_UD));
    	mPos.add(new ModulePosition("axleTwo", new VectorDescription(-1*unit,-1*unit-Yoffset,0*unit), rotation_UD));
    	mPos.add(new ModulePosition("wheel1", new VectorDescription(-1*unit,-2*unit-Yoffset,1*unit), rotation_SN));
    	mPos.add(new ModulePosition("wheel2", new VectorDescription(-1*unit,-2*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new ModulePosition("wheel3", new VectorDescription(1*unit,-2*unit-Yoffset,1*unit), rotation_SN));
    	mPos.add(new ModulePosition("wheel4", new VectorDescription(1*unit,-2*unit-Yoffset,-1*unit), rotation_NS));
        return mPos;
	}

	protected ArrayList<ModulePosition> buildRobot() {
		return buildCar();
	}
}
