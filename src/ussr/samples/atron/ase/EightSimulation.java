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
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.samples.atron.network.ATRONReflectionEventController;
import ussr.util.learning.CMTracker;
import ussr.util.learning.WifiCMBroadcaster;


 

public class EightSimulation extends GenericATRONSimulation {
	
    private ObstacleGenerator.ObstacleType obstacle = ObstacleGenerator.ObstacleType.LINE;
    
	public static void main( String[] args ) {		
		PhysicsParameters.get().setPlaneMaterial(Material.CONCRETE);
        PhysicsParameters.get().setPhysicsSimulationStepSize(0.01f);
 		PhysicsParameters.get().setRealisticCollision(true);
		PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
		PhysicsParameters.get().setMaintainRotationalJointPositions(false); 
        new EightSimulation().main();
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
	
	protected ArrayList<ModulePosition> buildEight() {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>(); 
		mPos.add(new ModulePosition("m1", ";portRC=9900;portEvent=9901", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("m2", ";portRC=9902;portEvent=9903;radio=disabled", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("m3", ";portRC=9904;portEvent=9905;radio=disabled", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("m4", ";portRC=9906;portEvent=9907;radio=disabled", new VectorDescription(2*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
    	//mPos.add(new ModulePosition("m5", ";portRC=9908;portEvent=9909;radio=disabled", new VectorDescription(-1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	//mPos.add(new ModulePosition("m6", ";portRC=9910;portEvent=9911;radio=disabled", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_WE));
    	//mPos.add(new ModulePosition("m7", ";portRC=9912;portEvent=9913;radio=disabled", new VectorDescription(3*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	//mPos.add(new ModulePosition("m8", ";portRC=9914;portEvent=9915;radio=disabled", new VectorDescription(2*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_EW));
    	return mPos;
	}
	protected ArrayList<ModulePosition> buildLoop4(String id) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("x1["+id+"]", ";portRC=9900;portEvent=9901", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("x2["+id+"]", ";portRC=9902;portEvent=9903;radio=disabled",  new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("x3["+id+"]", ";portRC=9904;portEvent=9905;radio=disabled", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("x4["+id+"]", ";portRC=9906;portEvent=9907;radio=disabled", new VectorDescription(2*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildRobot() {
		return buildLoop4("");
	}
    
    protected void changeWorldHook(WorldDescription world) {
        //new ObstacleGenerator().obstacalize(obstacle, world);
    	world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
		world.setHasBackgroundScenery(false);
    	startPaused = false;
    }
}
