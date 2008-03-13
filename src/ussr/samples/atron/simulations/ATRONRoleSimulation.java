package ussr.samples.atron.simulations;

import java.util.ArrayList;
import java.util.Random;

import ussr.model.Controller;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.GenericATRONSimulation;

/**
 * A sample simulation based on the ATRON robot
 * 
 * @author Modular Robots @ MMMI
 */
public class ATRONRoleSimulation extends GenericATRONSimulation {
	
	
	public static void main( String[] args ) {
        new ATRONRoleSimulation().main();
    }
	
	protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ATRONRoleController1();
            }
        };
    }
	protected ArrayList<ModulePosition> buildRobot() {
		return new ATRONBuilder().randomStructure(4);
	}
}
