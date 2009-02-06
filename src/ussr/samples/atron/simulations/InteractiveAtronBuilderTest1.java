/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.simulations;

import java.util.ArrayList;
/* Essentially a stripped down car */

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.GenericATRONSimulation;

/**
 * A simulation for a two-wheeler ATRON robot
 * @author Modular Robots @ MMMI
 *
 */
public class InteractiveAtronBuilderTest1 extends GenericATRONSimulation {

	public static void main( String[] args ) {
		new InteractiveAtronBuilderTest1().main();
	}

	protected Robot getRobot() {
		return new ATRON() {
			public Controller createController() {
				return new ATRONSimpleVehicleController1();
			}
		};
	}

	protected ArrayList<ModulePosition> buildRobot() {
		VectorDescription position = new  VectorDescription(0,-0.25f,0);
		float Xoffset = position.getX();
		float Yoffset = position.getY();
		ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
		mPos.add(new ModulePosition("driver0", new VectorDescription(-2*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));		
		return mPos ; 

	}

	protected void changeWorldHook(WorldDescription world) {
	}
}
