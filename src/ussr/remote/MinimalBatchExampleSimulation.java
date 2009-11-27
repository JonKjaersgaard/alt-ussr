/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.remote;

import java.rmi.RemoteException;
import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.model.Sensor;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.DisplacementPicker;
import ussr.remote.facade.ParameterHolder;
import ussr.samples.GenericSimulation;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.ATRONController;
import ussr.samples.atron.GenericATRONSimulation;

/**
 * Basic car simulation
 * 
 * @author Modular Robots @ MMMI
 */
public class MinimalBatchExampleSimulation extends GenericATRONSimulation {
	
    // Interesting part: simulation run starts in main by extracting parameters, reportResult calls
    // back to the batch process before terminating
    
	public static void main( String[] args ) {
		PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
		PhysicsParameters.get().setRealisticCollision(true);
		MinimalBatchExample.Parameters p = (MinimalBatchExample.Parameters)ParameterHolder.get();
		PhysicsFactory.getOptions().setStartPaused(false);
        System.out.println("Simulation starting for "+p);
		maxRunTime = p.getSecondsToRun();
		new MinimalBatchExampleSimulation().main();
    }
	
    public void reportResult(float result) {
        PhysicsSimulation sim = GenericSimulation.getPhysicsSimulation();
        String experiment = ParameterHolder.get().toString();
        try {
            if(SimulationClient.getReturnHandler()==null) {
                System.err.println("No return handler specified; time taken = "+sim.getTime());
                System.exit(0);
            }
            SimulationClient.getReturnHandler().provideReturnValue(experiment,"success",result);
            System.exit(0);
        } catch(RemoteException exn) {
            throw new Error("Unable to report return value");
        }
    }
    
    // Uninteresting part: runs simulation for a while, calls reportResult when it's done
    
    public static int maxRunTime;

    protected ObstacleGenerator.ObstacleType obstacleType = ObstacleGenerator.ObstacleType.LINE;
	
	protected Robot getRobot() {

        ATRON robot = new ATRON() {
            public Controller createController() {
                return new ATRONCarController1();
            }
        };
        robot.setGentle();
        return robot;
    }
	
	protected ArrayList<ModulePosition> buildRobot() {
		return new ATRONBuilder().buildCar(4, new VectorDescription(0,-0.25f,0));
	}
    
	@Override
    protected void changeWorldHook(WorldDescription world) {
        ObstacleGenerator generator = new ObstacleGenerator();
        generator.obstacalize(obstacleType, world);
        world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
    }
	
	public class ATRONCarController1 extends ATRONController {
	    
	    /**
	     * @see ussr.model.ControllerImpl#activate()
	     */
	    public void activate() {
	        setup(); 
	        this.delay(1000); /* rotateContinuous seem to fail sometimes if we do not wait at first */
	        byte dir = 1;
	        float lastProx = Float.NEGATIVE_INFINITY; /* for printing out proximity data */
	        boolean firstTime = true;
	        while(true) {
	            // Basic control: first time we enter the loop start rotating and turn the axle
	            String name = module.getProperty("name");
	            if(firstTime) {
	                firstTime = false;
	                if(name.startsWith("wheel1")) rotateContinuous(dir);
	                if(name.startsWith("wheel2")) rotateContinuous(-dir);
	                if(name.startsWith("wheel3")) rotateContinuous(dir);
	                if(name.startsWith("wheel4")) rotateContinuous(-dir);
	                if(name.equals("axleOne5")) {
	                    this.rotateDegrees(10);
	                }
	            }

	            // Print out proximity information
	            float max_prox = Float.NEGATIVE_INFINITY;
	            for(Sensor s: module.getSensors()) {
	                if(s.getName().startsWith("Proximity")) {
	                    float v = s.readValue();
	                    max_prox = Math.max(max_prox, v);
	                }
	            }
	            if(name.startsWith("wheel")&&Math.abs(lastProx-max_prox)>0.01) {
	                System.out.println("Proximity "+name+" max = "+max_prox);
	                lastProx = max_prox; 
	            }

                if(name.startsWith("wheel1") && module.getSimulation().getTime()>maxRunTime) {
                    reportResult(lastProx);
                }

	            // Always call yield sometimes
	            yield();
	        }
	    }
	}
}
