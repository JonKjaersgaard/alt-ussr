/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.framework.examples;

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
public class SimpleVehicle_IndividualRoles_Simulation extends GenericATRONSimulation {
	
	public static void main( String[] args ) {
        new SimpleVehicle_IndividualRoles_Simulation().main();
    }
	
	protected Robot getRobot() {
        ATRON a = new ATRON() {
            public Controller createController() {
                return new SimpleVehicle_IndividualRoles_Controller();
            }
        };
        a.setRubberRing();
        a.setGentle();
        return a;
    }

	protected ArrayList<ModulePosition> buildRobot() {
		return new ATRONBuilder().buildCar(2, new VectorDescription(2f,-0.25f,0f));
	}
    
    protected void changeWorldHook(WorldDescription world) {
        ObstacleGenerator generator = new ObstacleGenerator();
        generator.obstacalize(ObstacleGenerator.ObstacleType.LINE, world);
    }
}
