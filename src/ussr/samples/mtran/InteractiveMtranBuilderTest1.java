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
		//simulation.setGravity(0.0f);
	}
	protected void constructRobot() {
		addModule(2.5f,0,1.5f,ORI3X,"center");		
		addModule(-1,0,0,ORI2,"leftBackFoot");
		addModule(1,0,0,ORI2,"leftBackSpline");
		addModule(3,0,0,ORI2,"leftFrontSpline");
		addModule(5,0,0,ORI2,"leftFrontFoot");
		addModule(0,0,3,ORI2,"rightBackFoot");
		addModule(2,0,3,ORI2,"rightBackSpline");
		addModule(4,0,3,ORI2,"rightFrontSpline");
		addModule(6,0,3,ORI2,"rightFrontFoot");		
		addModule(10,0,3,ORI2,"rightFrontFoot1");		
		addModule(14,0,5,ORI2,"rightFrontFoot2");		
		addModule(16,0,8,ORI2,"rightFrontFoot3");		
        addModule(19,0,3,ORI2,"rightFrontFoot4");		
		addModule(23,0,5,ORI2,"rightFrontFoot5");		
		addModule(25,0,8,ORI2,"rightFrontFoot6");				
        addModule(30,0,3,ORI2,"rightFrontFoot7");		
		addModule(35,0,5,ORI2,"rightFrontFoot8");		
		addModule(40,0,8,ORI2,"rightFrontFoot9");
		addModule(42f,0,1.5f,ORI3X,"center");		
		addModule(44,0,0,ORI2,"leftBackFoot");
		addModule(46,0,0,ORI2,"leftBackSpline");
		addModule(48,0,0,ORI2,"leftFrontSpline");
		addModule(50,0,0,ORI2,"leftFrontFoot");
		addModule(54,0,3,ORI2,"rightBackFoot");
		addModule(58,0,3,ORI2,"rightBackSpline");
		addModule(60,0,3,ORI2,"rightFrontSpline");
		addModule(64,0,3,ORI2,"rightFrontFoot");		
		addModule(67,0,3,ORI2,"rightFrontFoot1");		
		addModule(70,0,5,ORI2,"rightFrontFoot2");		
		addModule(75,0,8,ORI2,"rightFrontFoot3");		
        addModule(80,0,3,ORI2,"rightFrontFoot4");		
		addModule(82,0,5,ORI2,"rightFrontFoot5");		
		addModule(86,0,8,ORI2,"rightFrontFoot6");				
        addModule(90,0,3,ORI2,"rightFrontFoot7");		
		addModule(95,0,5,ORI2,"rightFrontFoot8");		
		addModule(100,0,8,ORI2,"rightFrontFoot9");
		
	}
}
