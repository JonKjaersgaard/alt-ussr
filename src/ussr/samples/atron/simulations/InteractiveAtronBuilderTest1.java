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
		mPos.add(new ModulePosition("RearRightWheel", new VectorDescription(-1*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
		mPos.add(new ModulePosition("RearLeftWheel", new VectorDescription(-1*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
		mPos.add(new ModulePosition("driver0NEW", new VectorDescription(-4f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
		mPos.add(new ModulePosition("driver0NEW1", new VectorDescription(-6f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
		mPos.add(new ModulePosition("driver0NEW2", new VectorDescription(-9f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
		mPos.add(new ModulePosition("driver0NEW3", new VectorDescription(-12f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_DU));
		mPos.add(new ModulePosition("driver0NEW4", new VectorDescription(-15f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_UD));
		mPos.add(new ModulePosition("driver0NEW5", new VectorDescription(-18f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_SN));
		mPos.add(new ModulePosition("driver0NEW6", new VectorDescription(-21f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_NS));
		mPos.add(new ModulePosition("driver0NEW7", new VectorDescription(1f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
		mPos.add(new ModulePosition("driver0NEW8", new VectorDescription(4f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
		mPos.add(new ModulePosition("driver0NEW9", new VectorDescription(7f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
		mPos.add(new ModulePosition("driver0NEW10", new VectorDescription(10f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
		mPos.add(new ModulePosition("driver0NEW11", new VectorDescription(13f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
		mPos.add(new ModulePosition("driver0NEW12", new VectorDescription(17f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
		mPos.add(new ModulePosition("driver0NEW13", new VectorDescription(21f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
		mPos.add(new ModulePosition("driver0NEW14", new VectorDescription(24f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
		mPos.add(new ModulePosition("driver0NEW15", new VectorDescription(27f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
		mPos.add(new ModulePosition("driver0NEW16", new VectorDescription(30f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
		mPos.add(new ModulePosition("driver0NEW17", new VectorDescription(34f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
		mPos.add(new ModulePosition("driver0NEW18", new VectorDescription(38f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
		mPos.add(new ModulePosition("driver0NEW19", new VectorDescription(42f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
		mPos.add(new ModulePosition("driver0NEW21", new VectorDescription(46f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
		mPos.add(new ModulePosition("driver0NEW22", new VectorDescription(40f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
		mPos.add(new ModulePosition("driver0NEW23", new VectorDescription(44f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
		mPos.add(new ModulePosition("driver0NEW24", new VectorDescription(48f*ATRON.UNIT+Xoffset,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
		return mPos ; 

	}

	protected void changeWorldHook(WorldDescription world) {
		ObstacleGenerator generator = new ObstacleGenerator();
		generator.obstacalize(ObstacleGenerator.ObstacleType.LINE, world);
	}
}
