/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ckbot;

import ussr.description.geometry.RotationDescription;
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
public class CKBotSampleSimulation1 extends CKBotSimulation { 

	public static void main( String[] args ) {
		new CKBotSampleSimulation1().runSimulation(null,true);
	}
	public Controller getController(String type) {
		return new CKBotStandardController();
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
		addModule(0,0,0,new RotationDescription(0f,0f,0f));
		addModule(0,0,1,new RotationDescription(0f,0f,0f));
	}
}
