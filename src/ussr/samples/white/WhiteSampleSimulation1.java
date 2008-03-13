/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
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
