/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ASESocketTest;

import java.util.ArrayList;

import com.jme.math.Vector3f;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.GenericSimulation;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.samples.atron.network.ATRONReflectionController;
import ussr.samples.atron.network.ATRONReflectionEventController;




public class SocTwoWheelerSimulation extends GenericATRONSimulation {
	
    private ObstacleGenerator.ObstacleType obstacle = ObstacleGenerator.ObstacleType.LINE;
    
	public static void main( String[] args ) {
		PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
		PhysicsParameters.get().setRealisticCollision(true);
		new SocTwoWheelerSimulation().main();
    }
	protected void simulationHook(PhysicsSimulation simulation) {
		super.simulationHook(simulation);
		CMTracker tracker = new CMTracker(simulation);
		WifiBroadcaster broadcaster = new WifiBroadcaster(simulation, 7.0, tracker);
		simulation.subscribePhysicsTimestep(broadcaster);
	}
	protected Robot getRobot() {
        ATRON robot = new ATRON() {
            public Controller createController() {
                return new ATRONReflectionEventController();
            }
        };
        
        robot.setGentle();
        robot.setRadio();
        return robot;
    }
	
	protected ArrayList<ModulePosition> buildTwoWheeler(VectorDescription offset,String id) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("axle["+id+"]", ";portRC=9900;portEvent=9901", new VectorDescription(0*ATRON.UNIT+offset.getX(),-2*ATRON.UNIT-Yoffset+offset.getY(),0*ATRON.UNIT+offset.getZ()), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("wheel1["+id+"]", ";portRC=9902;portEvent=9903", new VectorDescription(1*ATRON.UNIT+offset.getX(),-2*ATRON.UNIT-Yoffset+offset.getY(),1*ATRON.UNIT+offset.getZ()), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("wheel2["+id+"]", ";portRC=9904;portEvent=9905", new VectorDescription(1*ATRON.UNIT+offset.getX(),-2*ATRON.UNIT-Yoffset+offset.getY(),-1*ATRON.UNIT+offset.getZ()), ATRON.ROTATION_NS));
        return mPos;
	}
	
	protected ArrayList<ModulePosition> buildCar() {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("driver0", ";port=9900", new VectorDescription(2*0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("axleOne5", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_UD));
    	mPos.add(new ModulePosition("axleTwo6", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_UD));
    	mPos.add(new ModulePosition("wheel1", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("wheel2", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("wheel3", new VectorDescription(1*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("wheel4", new VectorDescription(1*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
        return mPos;
	}
	
	protected ArrayList<ModulePosition> buildRobot() {
		//return buildCar();
		return buildTwoWheeler(new VectorDescription(),"TW1");
	}
    
    protected void changeWorldHook(WorldDescription world) {
        new ObstacleGenerator().obstacalize(obstacle, world);
        startPaused = false;
    }
}
