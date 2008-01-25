/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.mtran;

import ussr.model.Controller;
import ussr.physics.PhysicsSimulation;

/**
 * Simple MTRAN simulation
 * 
 * @author david
 *
 */
public class MTRANSampleSimulation1 extends MTRANSimulation {

	public static void main( String[] args ) {
		new MTRANSampleSimulation1().runSimulation(null,true);
	}
	public Controller getController(String type) {
		return new MTRANSampleController1("MTRAN");
	}

	public void physicsTimeStepHook(PhysicsSimulation simulation) {
		//simulation.setGravity(0.0f);
	}
}
