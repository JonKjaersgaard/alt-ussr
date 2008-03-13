package ussr.samples.atron.simulations;

import java.util.ArrayList;

import ussr.description.ModulePosition;
import ussr.description.Robot;
import ussr.description.VectorDescription;
import ussr.description.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.GenericATRONSimulation;

/**
 * Basic car simulation
 * 
 * @author Modular Robots @ MMMI
 */
public class ATRONCarSimulation extends GenericATRONSimulation {
	
	public static void main( String[] args ) {
		PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
		PhysicsParameters.get().setRealisticCollision(true);
		new ATRONCarSimulation().main();
    }
	
	protected ObstacleGenerator.ObstacleType obstacleType = ObstacleGenerator.ObstacleType.LINE;
	
	protected Robot getRobot() {

        ATRON robot = new ATRON() {
            public Controller createController() {
                return new ATRONCarController1();
            }
        };
        robot.setGentle();
        return robot;
    }
	
	protected ArrayList<ModulePosition> buildRobot() {
		return new ATRONBuilder().buildCar(4, new VectorDescription(0,-0.25f,0));
	}
    
    protected void changeWorldHook(WorldDescription world) {
        ObstacleGenerator generator = new ObstacleGenerator();
        generator.obstacalize(obstacleType, world);
    }
}
