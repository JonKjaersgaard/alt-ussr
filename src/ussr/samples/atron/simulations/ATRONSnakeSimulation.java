package ussr.samples.atron.simulations;

import java.util.ArrayList;

import ussr.description.ModulePosition;
import ussr.description.Robot;
import ussr.description.VectorDescription;
import ussr.model.Controller;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.GenericATRONSimulation;

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
