package ussr.samples.atron.natives;

import java.awt.Color;
import java.util.ArrayList;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jmex.subdivision.Vector;

import ussr.description.Robot;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.GenericATRONSimulation;


/* before running this compile the tinyos controller from a shell with
 * 'make atronSouth ussr_mod,x', where x is the number of simulated modules */

public class ATRONNativeTinyOSSimulation extends GenericATRONSimulation {

    private static final float eigth = (float)(0.25*Math.PI);
    private static final float quart = (float)(0.5*Math.PI);
    private static final float half = (float)(Math.PI);
	
	public static void main(String argv[]) {
		//PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
		PhysicsParameters.get().setRealisticCollision(true);
		PhysicsParameters.get().setUseModuleEventQueue(true);
		PhysicsParameters.get().setSyncWithControllers(true);
		PhysicsParameters.get().setPhysicsSimulationStepSize(0.001f);
		PhysicsParameters.get().setPhysicsSimulationControllerStepFactor(0.01f);
		new ATRONNativeTinyOSSimulation().main();	
	}

	@Override
	protected Robot getRobot() {
		System.out.println("getRobot");
		return new ATRON() {
			public Controller createController() {
				return new ATRONNativeTinyOSController("tinyos");
			}
		};
	}

	@Override
	protected ArrayList<ModulePosition> buildRobot() {
		//return new ATRONBuilder().buildCar(2, new VectorDescription(0,-0.25f,0));
		return new ATRONBuilder().buildSnake(1);
		//return new ATRONBuilder().buildCar(4, new VectorDescription(0,-0.25f,0));
		
/*
		//eight to car as in the IROS paper
		//we need a slightly different rotation description than ATRON.ROTATION_NS to put the connector as we need
		ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
		mPos.add(new ModulePosition("custom 0", new VectorDescription(0*ATRON.UNIT, -5*ATRON.UNIT, 0*ATRON.UNIT), new RotationDescription(0, 0, -eigth)));
		mPos.add(new ModulePosition("custom 1", new VectorDescription(1*ATRON.UNIT, -5*ATRON.UNIT, 1*ATRON.UNIT), ATRON.ROTATION_EW));
		mPos.add(new ModulePosition("custom 2", new VectorDescription(1*ATRON.UNIT, -5*ATRON.UNIT, -1*ATRON.UNIT), ATRON.ROTATION_EW));
		mPos.add(new ModulePosition("custom 3", new VectorDescription(2*ATRON.UNIT, -5*ATRON.UNIT, 0*ATRON.UNIT), new RotationDescription(0, 0, -eigth)));
		mPos.add(new ModulePosition("custom 4", new VectorDescription(3*ATRON.UNIT, -5*ATRON.UNIT, 1*ATRON.UNIT), ATRON.ROTATION_EW));
		mPos.add(new ModulePosition("custom 5", new VectorDescription(3*ATRON.UNIT, -5*ATRON.UNIT, -1*ATRON.UNIT), ATRON.ROTATION_EW));
		mPos.add(new ModulePosition("custom 6", new VectorDescription(4*ATRON.UNIT, -5*ATRON.UNIT, 0*ATRON.UNIT), new RotationDescription(0, 0, -eigth)));
		return mPos;	
*/
		/*
		ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
		double x0 = 1;
		double y0 = 0;
		double z0 = 0;
		double angle = Math.PI/2 + Math.PI;
		Vector3f moduleOrientationVector = new Vector3f(0, 0, 1);
		Double moduleOrientationAngle = 0d;
		Quaternion moduleOrientationQuaternion = new Quaternion( (float)(moduleOrientationVector.x*Math.sin((moduleOrientationAngle)/2)), (float)(moduleOrientationVector.y*Math.sin((moduleOrientationAngle)/2)), (float)(moduleOrientationVector.z*Math.sin((moduleOrientationAngle)/2)), (float)(Math.cos((moduleOrientationAngle)/2)));
		//System.out.println(moduleOrientation.x + " " + moduleOrientation.y + " " + moduleOrientation.z);
		Quaternion rotation = new Quaternion( (float)(x0*Math.sin((angle)/2)), (float)(y0*Math.sin((angle)/2)), (float)(z0*Math.sin((angle)/2)), (float)(Math.cos((angle)/2)));
		//Vector3f newModuleOrientation = rotation.mult(moduleOrientation);
		//System.out.println(newModuleOrientation.x + " " + newModuleOrientation.y + " " + newModuleOrientation.z);
		//mPos.add(new ModulePosition("custom 0", new VectorDescription(0*ATRON.UNIT, 0*ATRON.UNIT, 0*ATRON.UNIT), new RotationDescription(rotation)));
		mPos.add(new ModulePosition("custom 0", new VectorDescription(0*ATRON.UNIT, 0*ATRON.UNIT, 0*ATRON.UNIT), new RotationDescription(new Quaternion( (float)(0), (float)(0), (float)(0), (float)(1)))));
		//x0 = 1; y0 = 0; z0 = 0; angle = 0;
		//mPos.add(new ModulePosition("custom 1", new VectorDescription(1*ATRON.UNIT, 1*ATRON.UNIT, 0*ATRON.UNIT), new RotationDescription(new Quaternion( (float)(x0*Math.sin(angle/2)), (float)(y0*Math.sin(angle/2)), (float)(z0*Math.sin(angle/2)), (float)(Math.cos(angle/2))))));
		return mPos;
*/
	}

	
}
