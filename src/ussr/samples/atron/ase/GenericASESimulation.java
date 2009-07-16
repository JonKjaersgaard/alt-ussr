/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.ase;

import ussr.description.Robot;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.physics.PhysicsParameters.Material;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.samples.atron.network.ATRONReflectionEventController;
import ussr.util.supervision.CMTracker;
import ussr.util.supervision.CommunicationLoadMonitor;
import ussr.util.supervision.CommunicationLostMonitor;
import ussr.util.supervision.RadioConnection;
import ussr.util.supervision.WifiCMBroadcaster;




public abstract class GenericASESimulation extends GenericATRONSimulation implements PhysicsObserver {
	
	
	/*Monitors*/
	protected static boolean hasCMTracker = true; 
	protected static float TIME_PER_CM_UPDATE = 5.0f;
	
	
	protected static boolean hasCommunicationLoadMonitor = false;
	protected static boolean hasCommunicationLostMonitor = false;
	
	/*Communicaiton*/
	protected static boolean hasRadioConnection = true;
	protected static boolean hasHalfDuplex= false;
	
	protected static RadioConnection radioConnection;
    private ObstacleGenerator.ObstacleType obstacle = ObstacleGenerator.ObstacleType.LINE;
    
    public static void initASE( ) { 
    	PhysicsParameters.get().setPlaneMaterial(Material.CONCRETE);
        PhysicsParameters.get().setPhysicsSimulationStepSize(0.01f);
 		PhysicsParameters.get().setRealisticCollision(true);
		PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
		PhysicsParameters.get().setMaintainRotationalJointPositions(true);
		PhysicsFactory.getOptions().setStartPaused(false);
		PhysicsFactory.getOptions().setHeadless(false);
		PhysicsFactory.getOptions().setSaveWindowSettingOnExit(true);
		
		
    }
	
	protected void simulationHook(PhysicsSimulation simulation) {
		super.simulationHook(simulation);
		if(hasCMTracker) {
			CMTracker tracker = new CMTracker(simulation);
			WifiCMBroadcaster broadcaster = new WifiCMBroadcaster(simulation, TIME_PER_CM_UPDATE, tracker);
			simulation.subscribePhysicsTimestep(broadcaster);
		}
		if(hasRadioConnection) {
			radioConnection = new RadioConnection(simulation, 9899); //allow Modular commander to communicate with USSR 
			radioConnection.setPackToASE(true);
		}
		if(hasCommunicationLoadMonitor) {
			CommunicationLoadMonitor commMonitor = new CommunicationLoadMonitor(simulation, 1.0);
			simulation.subscribePhysicsTimestep(commMonitor);
		}
		if(hasCommunicationLostMonitor) {
			CommunicationLostMonitor commMonitor = new CommunicationLostMonitor(simulation, 1.0);
			simulation.subscribePhysicsTimestep(commMonitor);
		}
		simulation.subscribePhysicsTimestep(this);
	}

	
	protected Robot getRobot() {
        ATRON robot = new ATRON() {
            public Controller createController() {
                return new ATRONReflectionEventController();
            }
        };
        
        robot.setRealistic();
        if(hasHalfDuplex) robot.setHalfDuplex();
        if(hasRadioConnection) robot.setRadio();

        return robot;
    }
	
    protected void changeWorldHook(WorldDescription world) {
    	world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
		world.setHasBackgroundScenery(false);
    }

	public void physicsTimeStepHook(PhysicsSimulation simulation) {
		
	}

}
