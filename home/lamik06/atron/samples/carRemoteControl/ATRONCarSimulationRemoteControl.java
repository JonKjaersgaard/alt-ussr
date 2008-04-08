package atron.samples.carRemoteControl;

import java.util.ArrayList;

import atron.samples.xmlController.ATRONSimpleXmlController;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.model.Controller;
import ussr.physics.PhysicsParameters;

import ussr.samples.atron.ATRON;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.samples.atron.network.ATRONCarControllerRemoteControl;





public class ATRONCarSimulationRemoteControl extends GenericATRONSimulation {
	
	public static enum ObstacleType { NONE, LINE, CIRCLE }
    private ObstacleType obstacle = ObstacleType.LINE;
    
    public void setObstableType(ObstacleType obstacle) { this.obstacle = obstacle; }
    
	public static void main( String[] args ) {
		PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
		PhysicsParameters.get().setRealisticCollision(true);
		new ATRONCarSimulationRemoteControl().main();
    }
	
	protected Robot getRobot() {
		
        return new ATRON() {
            public Controller createController() {
                return new ATRONCarControllerRemoteControl();
            }
        };
    }
	protected ArrayList<ModulePosition> buildCar() {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
//    	mPos.add(new ModulePosition("driver0:1612", new VectorDescription(2*0*unit,0*unit-Yoffset,0*unit), rotation_EW));
//    	mPos.add(new ModulePosition("axleOne5", new VectorDescription(1*unit,-1*unit-Yoffset,0*unit), rotation_UD));
//    	mPos.add(new ModulePosition("axleTwo6", new VectorDescription(-1*unit,-1*unit-Yoffset,0*unit), rotation_UD));
//    	mPos.add(new ModulePosition("wheel1:10D2", new VectorDescription(-1*unit,-2*unit-Yoffset,1*unit), rotation_SN));
//    	mPos.add(new ModulePosition("wheel2", new VectorDescription(-1*unit,-2*unit-Yoffset,-1*unit), rotation_NS));
//    	mPos.add(new ModulePosition("wheel3", new VectorDescription(1*unit,-2*unit-Yoffset,1*unit), rotation_SN));
//    	mPos.add(new ModulePosition("wheel4", new VectorDescription(1*unit,-2*unit-Yoffset,-1*unit), rotation_NS));
        mPos.add(new ModulePosition("driver0", new VectorDescription(2*0*ATRON.UNIT,0*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("axleOne5", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_UD));
        mPos.add(new ModulePosition("axleTwo6", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_UD));
        mPos.add(new ModulePosition("wheel1", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("wheel2", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
        mPos.add(new ModulePosition("wheel3", new VectorDescription(1*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("wheel4", new VectorDescription(1*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));

    	return mPos;
	}

	protected ArrayList<ModulePosition> buildRobot() {
		return buildCar();
	}
    

    
}
