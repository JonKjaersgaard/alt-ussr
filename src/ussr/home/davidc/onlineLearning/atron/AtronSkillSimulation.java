package ussr.home.davidc.onlineLearning.atron;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.physics.PhysicsSimulation;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.robotbuildingblocks.WorldDescription.ModulePosition;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.GenericATRONSimulation;

public abstract class AtronSkillSimulation extends GenericATRONSimulation {
	 public static PhysicsSimulation simulation;
	public static enum ObstacleType { NONE, LINE, CIRCLE }
    private ObstacleType obstacle = ObstacleType.CIRCLE;
    protected static String robotType = "NaN";
    
    public void setObstableType(ObstacleType obstacle) { this.obstacle = obstacle; }
	
    
	protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return getController();
            }
        };
    }
	protected ArrayList<ModulePosition> buildCar() {
		robotType = "atron-car[7]";
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new WorldDescription.ModulePosition("driver0", new VectorDescription(2*0*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	mPos.add(new WorldDescription.ModulePosition("axleOne5", new VectorDescription(1*unit,-1*unit-Yoffset,0*unit), rotation_UD));
    	mPos.add(new WorldDescription.ModulePosition("axleTwo6", new VectorDescription(-1*unit,-1*unit-Yoffset,0*unit), rotation_UD));
    	mPos.add(new WorldDescription.ModulePosition("wheel1", new VectorDescription(-1*unit,-2*unit-Yoffset,1*unit), rotation_SN));
    	mPos.add(new WorldDescription.ModulePosition("wheel2", new VectorDescription(-1*unit,-2*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new WorldDescription.ModulePosition("wheel3", new VectorDescription(1*unit,-2*unit-Yoffset,1*unit), rotation_SN));
    	mPos.add(new WorldDescription.ModulePosition("wheel4", new VectorDescription(1*unit,-2*unit-Yoffset,-1*unit), rotation_NS));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildWalker1() {
		robotType = "atron-walker1[8]";
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new WorldDescription.ModulePosition("x1", new VectorDescription(0*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	mPos.add(new WorldDescription.ModulePosition("x2", new VectorDescription(1*unit,0*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new WorldDescription.ModulePosition("x3", new VectorDescription(1*unit,0*unit-Yoffset,1*unit), rotation_SN));
    	mPos.add(new WorldDescription.ModulePosition("x4", new VectorDescription(2*unit,0*unit-Yoffset,0*unit), rotation_WE));
    	
    	mPos.add(new WorldDescription.ModulePosition("y1", new VectorDescription(-1*unit,-1*unit-Yoffset,0*unit), rotation_DU));
    	mPos.add(new WorldDescription.ModulePosition("y2", new VectorDescription(1*unit,-1*unit-Yoffset,-2*unit), rotation_DU));
    	mPos.add(new WorldDescription.ModulePosition("y3", new VectorDescription(1*unit,-1*unit-Yoffset,2*unit), rotation_DU));
    	mPos.add(new WorldDescription.ModulePosition("y4", new VectorDescription(3*unit,-1*unit-Yoffset,0*unit), rotation_DU));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildWalker2() {
		robotType = "atron-walker2[12]";
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new WorldDescription.ModulePosition("x1", new VectorDescription(0*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	mPos.add(new WorldDescription.ModulePosition("x2", new VectorDescription(1*unit,0*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new WorldDescription.ModulePosition("x3", new VectorDescription(1*unit,0*unit-Yoffset,1*unit), rotation_SN));
    	mPos.add(new WorldDescription.ModulePosition("x4", new VectorDescription(2*unit,0*unit-Yoffset,0*unit), rotation_WE));
    	
    	mPos.add(new WorldDescription.ModulePosition("y1", new VectorDescription(-1*unit,-1*unit-Yoffset,0*unit), rotation_DU));
    	mPos.add(new WorldDescription.ModulePosition("y2", new VectorDescription(1*unit,-1*unit-Yoffset,-2*unit), rotation_DU));
    	mPos.add(new WorldDescription.ModulePosition("y3", new VectorDescription(1*unit,-1*unit-Yoffset,2*unit), rotation_DU));
    	mPos.add(new WorldDescription.ModulePosition("y4", new VectorDescription(3*unit,-1*unit-Yoffset,0*unit), rotation_DU));
    	
    	mPos.add(new WorldDescription.ModulePosition("z1", new VectorDescription(-2*unit,-2*unit-Yoffset,0*unit), rotation_EW));
    	mPos.add(new WorldDescription.ModulePosition("z2", new VectorDescription(1*unit,-2*unit-Yoffset,-3*unit), rotation_NS));
    	mPos.add(new WorldDescription.ModulePosition("z3", new VectorDescription(1*unit,-2*unit-Yoffset,3*unit), rotation_SN));
    	mPos.add(new WorldDescription.ModulePosition("z4", new VectorDescription(4*unit,-2*unit-Yoffset,0*unit), rotation_WE));
        return mPos;
	}
	protected static ArrayList<ModulePosition> buildSnake(int length) {
		robotType = "atron-snake["+length+"]";
    	float Yoffset = 0.4f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	int x=0,y=0,z=0;
    	for(int i=0;i<length;i++) {
    		if(i%2==0) {
    			mPos.add(new WorldDescription.ModulePosition("snake "+i, new VectorDescription(x*unit,y*unit-Yoffset,z*unit), rotation_EW));
    		}
    		else {
    			mPos.add(new WorldDescription.ModulePosition("snake "+i, new VectorDescription(x*unit,y*unit-Yoffset,z*unit), rotation_NS));
    		}
    		x++;z++;
    	}
		return mPos;
	}

	protected ArrayList<ModulePosition> buildRobot() {
		//return buildCar();
		//return buildSnake(7);
		return buildWalker1();
		//return buildWalker2();
	}
    
    private static final int N_LINE_OBSTACLES = 20;
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
    public abstract Controller getController();
}
