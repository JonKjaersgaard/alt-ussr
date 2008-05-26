package sunTron.samples.simpleVechicle;
import java.util.ArrayList;
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
public class SunTronSimpleCarSimulation extends GenericATRONSimulation {
	
	public static void main( String[] args ) {
        new SunTronSimpleCarSimulation().main();
    }
	
	protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new SunTronSimpleVehicleController();
            }
        };
    }

	protected ArrayList<ModulePosition> buildRobot() {
		return new ATRONBuilder().buildCar(2, new VectorDescription(0,-0.25f,0));
	}
    
    protected void changeWorldHook(WorldDescription world) {
        ObstacleGenerator generator = new ObstacleGenerator();
        generator.obstacalize(ObstacleGenerator.ObstacleType.LINE, world);
    }
}
