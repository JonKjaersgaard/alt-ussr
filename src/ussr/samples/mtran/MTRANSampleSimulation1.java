/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.mtran;

import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsSimulation;

/**
 * Simple MTRAN simulation with a snake robot
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

	@Override
	public void changeWorldHook(WorldDescription world) {
        world.setHasBackgroundScenery(false);
        world.setPlaneTexture(WorldDescription.GRID_TEXTURE);
	}
	
	public void physicsTimeStepHook(PhysicsSimulation simulation) {
		//simulation.setGravity(0.0f);
	}
}
