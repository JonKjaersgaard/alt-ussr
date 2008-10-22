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
public class CKBotSampleSimulation2 extends CKBotSimulation { 

	public static void main( String[] args ) {
		new CKBotSampleSimulation2().runSimulation(null,true);
	}
	public Controller getController(String type) {
		if (type.equals("CKBotStandard")) return new CKBotL7SampleController1();
		else if (type.equals("CKBotL7")) return new CKBotL7SampleController1();
		else if (type.equals("CKBotSpring")) return new CKBotSpringSampleController1();
		return new CKBotStandardSampleController1();
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
		addModule("CKBotL7",0,0,0,new RotationDescription(0f,0f,(float)Math.PI/2));
		addModule("CKBotStandard",1,0,0,new RotationDescription(0f,(float)Math.PI/2,0f));
		addModule("CKBotStandard",-1,0,0,new RotationDescription(0f,(float)Math.PI/2,0f));
	}
}
