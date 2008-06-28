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
//		return new ATRONBuilder().buildCar(2, new VectorDescription(0,-0.25f,0));
		ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
		VectorDescription position = new VectorDescription(0,-0.25f,0);
        float Yoffset = position.getY();
        mPos.add(new ModulePosition("driver0:5432", new VectorDescription(-2*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
        mPos.add(new ModulePosition("RearRightWheel", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
        mPos.add(new ModulePosition("RearLeftWheel", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT+Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));

		
		return mPos;
	}
    
    protected void changeWorldHook(WorldDescription world) {
        ObstacleGenerator generator = new ObstacleGenerator();
        generator.obstacalize(ObstacleGenerator.ObstacleType.LINE, world);
    }
}
