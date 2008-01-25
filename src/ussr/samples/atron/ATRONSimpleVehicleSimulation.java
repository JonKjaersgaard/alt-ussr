package ussr.samples.atron;

import java.util.ArrayList;
/* Essentially a stripped down car */

import ussr.model.Controller;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;

/**
 * A simulation for a two-wheeler ATRON robot
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONSimpleVehicleSimulation extends GenericATRONSimulation {
	
	public static enum ObstacleType { NONE, LINE, CIRCLE }
    private ObstacleType obstacle = ObstacleType.LINE;
    
    public void setObstableType(ObstacleType obstacle) { this.obstacle = obstacle; }
    
	public static void main( String[] args ) {
        new ATRONSimpleVehicleSimulation().main();
    }
	
	protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ATRONSimpleVehicleController1();
            }
        };
    }
	protected ArrayList<ModulePosition> buildCar() {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	//mPos.add(new WorldDescription.ModulePosition("driver0", new VectorDescription(2*0*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	//mPos.add(new WorldDescription.ModulePosition("FrontAxle", new VectorDescription(1*unit,-1*unit-Yoffset,0*unit), rotation_UD));
    	//mPos.add(new WorldDescription.ModulePosition("RearAxle", new VectorDescription(-1*unit,-1*unit-Yoffset,0*unit), rotation_UD));
    	mPos.add(new ModulePosition("driver0", new VectorDescription(-2*unit,-2*unit-Yoffset,0*unit), rotation_EW));
    	mPos.add(new ModulePosition("RearRightWheel", new VectorDescription(-1*unit,-2*unit-Yoffset,1*unit), rotation_SN));
    	mPos.add(new ModulePosition("RearLeftWheel", new VectorDescription(-1*unit,-2*unit-Yoffset,-1*unit), rotation_NS));
    	//mPos.add(new WorldDescription.ModulePosition("FrontRightWheel", new VectorDescription(1*unit,-2*unit-Yoffset,1*unit), rotation_SN));
    	//mPos.add(new WorldDescription.ModulePosition("FrontLeftWheel", new VectorDescription(1*unit,-2*unit-Yoffset,-1*unit), rotation_NS));
        return mPos;
	}

	protected ArrayList<ModulePosition> buildRobot() {
		return buildCar();
	}
    
    private static final int N_LINE_OBSTACLES = 0;
    private static final float OBSTACLE_DIST = 0.2f;
    private static final float LINE_OBSTACLE_CENTER = 0;
    private static final int N_CIRCLE_OBSTACLES = 0*33;
    private static final float CIRCLE_OBSTACLE_RADIUS = 1.5f;
    protected void changeWorldHook(WorldDescription world) {
        if(obstacle==ObstacleType.LINE) {
            VectorDescription[] obstacles = new VectorDescription[N_LINE_OBSTACLES];
            for(int i=0; i<N_LINE_OBSTACLES; i++) {
                obstacles[i] = new VectorDescription(1.0f, -0.5f, LINE_OBSTACLE_CENTER-(N_LINE_OBSTACLES/2*OBSTACLE_DIST)+i*OBSTACLE_DIST);
            }
            world.setObstacles(obstacles);
        } else if(obstacle==ObstacleType.CIRCLE) {
            VectorDescription[] obstacles = new VectorDescription[N_CIRCLE_OBSTACLES];
            for(int i=0; i<N_CIRCLE_OBSTACLES; i++) {
                obstacles[i] = new VectorDescription(
                        ((float)(CIRCLE_OBSTACLE_RADIUS*Math.cos(((double)i)/N_CIRCLE_OBSTACLES*Math.PI*2))),
                        -0.3f,
                        ((float)(CIRCLE_OBSTACLE_RADIUS*Math.sin(((double)i)/N_CIRCLE_OBSTACLES*Math.PI*2)))
                );
            }
            world.setObstacles(obstacles);
        }
    }
}
