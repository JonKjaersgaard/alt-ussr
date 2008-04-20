/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package roleselection;

import java.util.ArrayList;
/* Essentially a stripped down car */

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsSimulation;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.GenericATRONSimulation;

/**
 * A simulation for a two-wheeler ATRON robot
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONSimpleVehicleSimulation extends GenericATRONSimulation {
	
	public static void main( String[] args ) {
        new ATRONSimpleVehicleSimulation().main();
    }
	
	protected Robot getRobot() {
	    return Robot.NO_DEFAULT;
	}
	
	@Override
    protected void simulationHook(PhysicsSimulation simulation) {
        ATRON normal = new ATRON() {
            public Controller createController() {
                return new ATRONSimpleVehicleController1();
            }
        };
        ATRON rubber = new ATRON() {
            public Controller createController() {
                return new ATRONSimpleVehicleController1();
            }
        }; 
        rubber.setRubberRing();
        rubber.setGentle();
        simulation.setRobot(normal, "plain");
        simulation.setRobot(rubber, "rubber");
    }

	protected ArrayList<ModulePosition> buildRobot() {
	    float Yoffset = -0.25f;
	    ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
        mPos.add(new ModulePosition("driver0", "plain", new VectorDescription(-2*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("RearLeftWheel", "rubber", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("RearRightWheel", "rubber", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
		return mPos;
	}
    
    protected void changeWorldHook(WorldDescription world) {
        ObstacleGenerator generator = new ObstacleGenerator();
        generator.obstacalize(ObstacleGenerator.ObstacleType.LINE, world);
    }
}
