/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.ase;

import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.physics.PhysicsParameters.Material;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.samples.atron.network.ATRONReflectionEventController;
import ussr.util.learning.CMTracker;
import ussr.util.learning.WifiCMBroadcaster;


 

public class WalkerSimulation2 extends GenericATRONSimulation {
	
    private ObstacleGenerator.ObstacleType obstacle = ObstacleGenerator.ObstacleType.LINE;
    
	public static void main( String[] args ) {		
		PhysicsParameters.get().setPlaneMaterial(Material.CONCRETE);
        PhysicsParameters.get().setPhysicsSimulationStepSize(0.01f);
 		PhysicsParameters.get().setRealisticCollision(true);
		PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
		PhysicsParameters.get().setMaintainRotationalJointPositions(false); 
        new WalkerSimulation().main();
    }
	protected void simulationHook(PhysicsSimulation simulation) {
		super.simulationHook(simulation);
		CMTracker tracker = new CMTracker(simulation);
		WifiCMBroadcaster broadcaster = new WifiCMBroadcaster(simulation, 7.0, tracker);
		simulation.subscribePhysicsTimestep(broadcaster);
	}
	protected Robot getRobot() {
        ATRON robot = new ATRON() {
            public Controller createController() {
                return new ATRONReflectionEventController();
            }
        };
        
        robot.setRealistic();
        robot.setRadio();
        return robot;
    }
	
	protected ArrayList<ModulePosition> buildWalker1(String id) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>(); 
    	mPos.add(new ModulePosition("x1["+id+"]", ";portRC=9900;portEvent=9901", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("x2["+id+"]", ";portRC=9902;portEvent=9903", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("x3["+id+"]", ";portRC=9904;portEvent=9905", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("x4["+id+"]", ";portRC=9906;portEvent=9907", new VectorDescription(2*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
    	mPos.add(new ModulePosition("y1["+id+"]", ";portRC=9908;portEvent=9909", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("y2["+id+"]", ";portRC=9910;portEvent=9911", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("y3["+id+"]", ";portRC=9912;portEvent=9913", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("y4["+id+"]", ";portRC=9914;portEvent=9915", new VectorDescription(3*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_DU));
        return mPos;
	}
	
	protected ArrayList<ModulePosition> buildRobot() {
		return buildWalker1("");
	}
    
    protected void changeWorldHook(WorldDescription world) {
        //new ObstacleGenerator().obstacalize(obstacle, world);
    	world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
		world.setHasBackgroundScenery(false);
    	startPaused = false;
    }
}
