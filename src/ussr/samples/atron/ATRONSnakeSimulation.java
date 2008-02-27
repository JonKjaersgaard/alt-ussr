package ussr.samples.atron;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.VectorDescription;

/**
 * A simulation of an ATRON snake robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONSnakeSimulation extends GenericATRONSimulation {
	
	
	public static void main( String[] args ) {
        new ATRONSnakeSimulation().main();
    }
	
	protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                //return new ATRONSnakeController1();
            	return new ATRONSnakeController2();
            }
        };
    }

	protected ArrayList<ModulePosition> buildRobot() {
		return new ATRONBuilder().buildSnake(4);
	}
}
