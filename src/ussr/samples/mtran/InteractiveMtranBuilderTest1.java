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
import ussr.samples.atron.ATRON;
import ussr.samples.atron.simulations.ATRONSimpleVehicleController1;

/**
 * Simple MTRAN simulation with a snake robot
 * 
 * @author david
 * @author Konstantinas(modified for the project called "Quick Prototyping of Simulation Scenarios")
 * Previous name of the class was MTRANSampleSimulation1.java
 */
//TODO CONSIDER IMPLEMENTING SEPARATE FILE WITH CREATION OF ALL POSSIBLE MR ROBOTS AND EMPTY CONTROLLERS
public class InteractiveMtranBuilderTest1 extends MTRANSimulation { 

	public static void main( String[] args ) {
		new InteractiveMtranBuilderTest1().runSimulation(null,true);
	}
	public Controller getController(String type) {
		return new MTRANSampleController2("MTRAN");
	}

	@Override
	public void changeWorldHook(WorldDescription world) {
	    super.changeWorldHook(world);
	    world.setPlaneTexture(WorldDescription.GRASS_TEXTURE);
	    PhysicsParameters.get().setPhysicsSimulationStepSize(0.004f);
	    
	}
	
	public void physicsTimeStepHook(PhysicsSimulation simulation) {
		
	
	}
	protected void constructRobot() {		
		addModule(6,0,0,ORI2,"leftBackSpline");//ORI1X		
	}
}
