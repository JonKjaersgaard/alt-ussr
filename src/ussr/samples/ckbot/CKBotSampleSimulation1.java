/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.ckbot;

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
		addModule("CKBotStandard",0,0,0,new RotationDescription(0f,0f,(float)Math.PI/2));		
		addModule("CKBotStandard",0,0,1,new RotationDescription(0f,0f,(float)Math.PI/2));
		addModule("CKBotStandard",0,0,2,new RotationDescription(0f,0f,(float)Math.PI/2));
		addModule("CKBotStandard",0,0,3,new RotationDescription(0f,0f,(float)Math.PI/2));
		addModule("CKBotStandard",0,0,4,new RotationDescription(0f,0f,(float)Math.PI/2));
		addModule("CKBotStandard",0,0,5,new RotationDescription(0f,0f,(float)Math.PI/2));
		addModule("CKBotStandard",0,0,6,new RotationDescription(0f,0f,(float)Math.PI/2));
		addModule("CKBotStandard",0,0,7,new RotationDescription(0f,0f,(float)Math.PI/2));
		addModule("CKBotStandard",0,0,8,new RotationDescription(0f,0f,(float)Math.PI/2));
		addModule("CKBotStandard",0,0,9,new RotationDescription(0f,0f,(float)Math.PI/2));
	}
}
