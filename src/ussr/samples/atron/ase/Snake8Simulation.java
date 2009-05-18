/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.ase;

import java.util.ArrayList;

import ussr.comm.monitors.StatisticalMonitor;
import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
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
import ussr.util.learning.CMTracker;
import ussr.util.learning.RadioConnection;
import ussr.util.learning.WifiCMBroadcaster;




public class Snake8Simulation extends GenericATRONSimulation implements PhysicsObserver {
	private boolean hasCMTracker = true;
	private boolean hasRadioConnection = true;
	RadioConnection radioConnection;
	static StatisticalMonitor commMonitor;
	
    private ObstacleGenerator.ObstacleType obstacle = ObstacleGenerator.ObstacleType.LINE;
    public static void main( String[] args ) {
    	commMonitor = new StatisticalMonitor(1.0f);
    	PhysicsFactory.getOptions().addCommunicationMonitor(commMonitor);
		PhysicsParameters.get().setPlaneMaterial(Material.CONCRETE);
        PhysicsParameters.get().setPhysicsSimulationStepSize(0.01f);
 		PhysicsParameters.get().setRealisticCollision(true);
		PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
		PhysicsParameters.get().setMaintainRotationalJointPositions(true); 
		new Snake8Simulation().main();
    }
	
	protected void simulationHook(PhysicsSimulation simulation) {
		super.simulationHook(simulation);
		if(hasCMTracker) {
			CMTracker tracker = new CMTracker(simulation);
			WifiCMBroadcaster broadcaster = new WifiCMBroadcaster(simulation, 7.0, tracker);
			simulation.subscribePhysicsTimestep(broadcaster);
		}	
		if(hasRadioConnection) {
			radioConnection = new RadioConnection(simulation, 9899); //allow Modular commander to communicate with USSR 
			radioConnection.setPackToASE(true);
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
        robot.setRadio();

        return robot;
    }
	
	protected ArrayList<ModulePosition> buildSnake(int length) {
    	float Yoffset = 0.4f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	int x=0,y=0,z=0;
    	int portRC = 9900;
    	int portEvent = 9901;
    	String radioStr = "";
    	for(int i=0;i<length;i++) {
    		if(i%2==0) {
    			mPos.add(new ModulePosition(i+"", ";portRC="+portRC+";portEvent="+portEvent+""+radioStr, new VectorDescription(x*ATRON.UNIT,y*ATRON.UNIT-Yoffset,z*ATRON.UNIT), ATRON.ROTATION_EW));
    		}
    		else {
    			mPos.add(new ModulePosition(i+"", ";portRC="+portRC+";portEvent="+portEvent+""+radioStr, new VectorDescription(x*ATRON.UNIT,y*ATRON.UNIT-Yoffset,z*ATRON.UNIT), ATRON.ROTATION_NS));
    		}
    		portRC+=2;portEvent+=2;
    		x++;z++;
    		radioStr = ";radio=disabled";	//disable all but first module in snake
    	}
    	return mPos;
	}

	protected ArrayList<ModulePosition> buildRobot() {
		return buildSnake(8);
	}
    
    protected void changeWorldHook(WorldDescription world) {
    	world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
		world.setHasBackgroundScenery(false);
    	startPaused = false;
    }

    int count = 0;
	public void physicsTimeStepHook(PhysicsSimulation simulation) {
		count ++;
		if(count%100==0) {
			int nModules = 8;
			for(int id=0;id<nModules;id++) {
				for(int channel=0;channel<=8;channel++) {
					int bitpersec = commMonitor.getBitOutWindow(id, channel);
					if(bitpersec>512) {
						//System.out.println("Module "+id+" sends "+bitpersec+" bits/sec on channel "+channel);
					}
				}
			}
		}
	}
}
