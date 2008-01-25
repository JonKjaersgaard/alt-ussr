/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.white;

import ussr.model.Controller;
import ussr.physics.PhysicsSimulation;

/**
 * Simple White simulation
 * 
 * @author david
 *
 */
public class WhiteSampleSimulation1 extends WhiteSimulation {

	public static void main( String[] args ) {
		new WhiteSampleSimulation1().runSimulation(null,true);
	}
	public Controller getController(String type) {
		return new WhiteSampleController1("White");
	}

	public void physicsTimeStepHook(PhysicsSimulation simulation) {
		//simulation.setGravity(0.0f);
	}
}
