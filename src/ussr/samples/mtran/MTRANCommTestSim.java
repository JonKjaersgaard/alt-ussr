/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.mtran;

import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;

/**
 * Simple MTRAN simulation with a snake robot
 * 
 * @author david
 *
 */
public class MTRANCommTestSim extends MTRANSimulation { 

	public static void main( String[] args ) {
		new MTRANCommTestSim().runSimulation(null,true);
	}
	public Controller getController(String type) {
		return new MTRANCommTestController("MTRAN");
	}

	@Override
	public void changeWorldHook(WorldDescription world) {
	    super.changeWorldHook(world);
	    world.setPlaneTexture(WorldDescription.GRASS_TEXTURE);
	    PhysicsParameters.get().setPhysicsSimulationStepSize(0.004f);
	}
	
	public void physicsTimeStepHook(PhysicsSimulation simulation) {
		//simulation.setGravity(0.0f);
	}
	protected void constructRobot() {
		addModule(0,0,0,ORI2);
		addModule(2,0,0,ORI2);
		addModule(4,0,0,ORI2);
		addModule(6,0,0,ORI2);
		addModule(8,0,0,ORI2);
		addModule(10,0,0,ORI2);
		addModule(12,0,0,ORI2);
		addModule(14,0,0,ORI2);
	}
}
