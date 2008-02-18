package onlineLearning.atron;

import java.util.ArrayList;
import java.util.Collection;

import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsParameters.Material;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.GenericATRONSimulation;

public abstract class AtronSkillSimulation extends GenericATRONSimulation {
	

    /*USER PARAMETERS START*/
	protected static enum ATRONRobots {NONE, ONE, TWOWHEELER, CRAWLER1, CRAWLER2,CAR, SNAKE2, SNAKE3, SNAKE4, SNAKE7, WALKER1, WALKER2, WALKER3, WALKER4, WALKER5,LOOP4, LOOP7, LOOP8};
	protected static ATRONRobots robotType =  ATRONRobots.WALKER1;
	protected static int nRobots =  1;
	static boolean loadSkillsFromFile = true;
	static boolean startPaused = false;
	static boolean realistic = true;
	static boolean realisticCollision = true;
	static float periodeTime = 12;
	static float evalPeriode = 12;
	static float simulationTime = 3000;
	static boolean syncronized = true;
	static float physicsSimulationStepSize = 0.01f;//0.005f;
	static boolean primitiveRoles = true;
	static boolean rubberRing = false;
	protected static String trialID = System.currentTimeMillis()%1000+""; 
    /*USER PARAMETERS END*/
	
	public static enum ObstacleType { NONE, LINE, CIRCLE }
    private ObstacleType obstacle = ObstacleType.CIRCLE;
    
    public void setObstableType(ObstacleType obstacle) { this.obstacle = obstacle; }
	
    
	protected Robot getRobot() {
		ATRON atron = new ATRON() {
            public Controller createController() {
                return getController(); 
            }
        };
        PhysicsParameters.get().setPlaneMaterial(Material.CONCRETE);
        PhysicsParameters.get().setPhysicsSimulationStepSize(physicsSimulationStepSize);
		PhysicsParameters.get().setRealisticCollision(realisticCollision);
		PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
		//PhysicsParameters.get().setPlaneMaterial(Material.GLASS);
        if(realistic) atron.setRealistic();
        else atron.setSuper();
        //if(rubberRing) atron.setRubberRing();
        return atron;
    }
	protected ArrayList<ModulePosition> buildTwoWheeler(VectorDescription offset,String id) {
    	float Yoffset = 0.25f;
    	rubberRing = true;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	//mPos.add(new ModulePosition("axle", new VectorDescription(1*unit,-1*unit-Yoffset,0*unit), rotation_UD));
    	mPos.add(new ModulePosition("axle["+id+"]", new VectorDescription(0*unit+offset.getX(),-2*unit-Yoffset+offset.getY(),0*unit+offset.getZ()), rotation_EW));
    	mPos.add(new ModulePosition("wheel1["+id+"]", new VectorDescription(1*unit+offset.getX(),-2*unit-Yoffset+offset.getY(),1*unit+offset.getZ()), rotation_SN));
    	mPos.add(new ModulePosition("wheel2["+id+"]", new VectorDescription(1*unit+offset.getX(),-2*unit-Yoffset+offset.getY(),-1*unit+offset.getZ()), rotation_NS));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildCar(String id) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("driver0["+id+"]", new VectorDescription(2*0*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	mPos.add(new ModulePosition("axleOne5["+id+"]", new VectorDescription(1*unit,-1*unit-Yoffset,0*unit), rotation_UD));
    	mPos.add(new ModulePosition("axleTwo6["+id+"]", new VectorDescription(-1*unit,-1*unit-Yoffset,0*unit), rotation_UD));
    	mPos.add(new ModulePosition("wheel1["+id+"]", new VectorDescription(-1*unit,-2*unit-Yoffset,1*unit), rotation_SN));
    	mPos.add(new ModulePosition("wheel2["+id+"]", new VectorDescription(-1*unit,-2*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new ModulePosition("wheel3["+id+"]", new VectorDescription(1*unit,-2*unit-Yoffset,1*unit), rotation_SN));
    	mPos.add(new ModulePosition("wheel4["+id+"]", new VectorDescription(1*unit,-2*unit-Yoffset,-1*unit), rotation_NS));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildLoop4(String id) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("x1["+id+"]", new VectorDescription(0*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	mPos.add(new ModulePosition("x2["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new ModulePosition("x3["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,1*unit), rotation_SN));
    	mPos.add(new ModulePosition("x4["+id+"]", new VectorDescription(2*unit,0*unit-Yoffset,0*unit), rotation_WE));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildLoop7(String id) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("x1["+id+"]", new VectorDescription(0*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	mPos.add(new ModulePosition("x2["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new ModulePosition("x3["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,1*unit), rotation_SN));
    	mPos.add(new ModulePosition("x4["+id+"]", new VectorDescription(2*unit,0*unit-Yoffset,0*unit), rotation_WE));
    	
    	mPos.add(new ModulePosition("x5["+id+"]", new VectorDescription(3*unit,0*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new ModulePosition("x6["+id+"]", new VectorDescription(3*unit,0*unit-Yoffset,1*unit), rotation_SN));
    	mPos.add(new ModulePosition("x7["+id+"]", new VectorDescription(4*unit,0*unit-Yoffset,0*unit), rotation_WE));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildLoop8(String id) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("x1["+id+"]", new VectorDescription(0*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	mPos.add(new ModulePosition("x2["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new ModulePosition("x3["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,1*unit), rotation_SN));
    	mPos.add(new ModulePosition("x4["+id+"]", new VectorDescription(2*unit,0*unit-Yoffset,0*unit), rotation_WE));
    	
    	mPos.add(new ModulePosition("y1["+id+"]", new VectorDescription(2*unit,0*unit-Yoffset,2*unit), rotation_EW));
    	mPos.add(new ModulePosition("y2["+id+"]", new VectorDescription(3*unit,0*unit-Yoffset,1*unit), rotation_NS));
    	mPos.add(new ModulePosition("y3["+id+"]", new VectorDescription(3*unit,0*unit-Yoffset,3*unit), rotation_SN));
    	mPos.add(new ModulePosition("y4["+id+"]", new VectorDescription(4*unit,0*unit-Yoffset,2*unit), rotation_WE));
        return mPos;
	}	
	protected ArrayList<ModulePosition> buildWalker1(String id) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("x1["+id+"]", new VectorDescription(0*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	mPos.add(new ModulePosition("x2["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new ModulePosition("x3["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,1*unit), rotation_SN));
    	mPos.add(new ModulePosition("x4["+id+"]", new VectorDescription(2*unit,0*unit-Yoffset,0*unit), rotation_WE));
    	
    	mPos.add(new ModulePosition("y1["+id+"]", new VectorDescription(-1*unit,-1*unit-Yoffset,0*unit), rotation_DU));
    	mPos.add(new ModulePosition("y2["+id+"]", new VectorDescription(1*unit,-1*unit-Yoffset,-2*unit), rotation_DU));
    	mPos.add(new ModulePosition("y3["+id+"]", new VectorDescription(1*unit,-1*unit-Yoffset,2*unit), rotation_DU));
    	mPos.add(new ModulePosition("y4["+id+"]", new VectorDescription(3*unit,-1*unit-Yoffset,0*unit), rotation_DU));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildWalker2(String id) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("x1["+id+"]", new VectorDescription(0*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	mPos.add(new ModulePosition("x2["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new ModulePosition("x3["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,1*unit), rotation_SN));
    	mPos.add(new ModulePosition("x4["+id+"]", new VectorDescription(2*unit,0*unit-Yoffset,0*unit), rotation_WE));
    	
    	mPos.add(new ModulePosition("y1["+id+"]", new VectorDescription(-1*unit,-1*unit-Yoffset,0*unit), rotation_DU));
    	mPos.add(new ModulePosition("y2["+id+"]", new VectorDescription(1*unit,-1*unit-Yoffset,-2*unit), rotation_DU));
    	mPos.add(new ModulePosition("y3["+id+"]", new VectorDescription(1*unit,-1*unit-Yoffset,2*unit), rotation_DU));
    	mPos.add(new ModulePosition("y4["+id+"]", new VectorDescription(3*unit,-1*unit-Yoffset,0*unit), rotation_DU));
    	
    	mPos.add(new ModulePosition("z1["+id+"]", new VectorDescription(-2*unit,-2*unit-Yoffset,0*unit), rotation_EW));
    	mPos.add(new ModulePosition("z2["+id+"]", new VectorDescription(1*unit,-2*unit-Yoffset,-3*unit), rotation_NS));
    	mPos.add(new ModulePosition("z3["+id+"]", new VectorDescription(1*unit,-2*unit-Yoffset,3*unit), rotation_SN));
    	mPos.add(new ModulePosition("z4["+id+"]", new VectorDescription(4*unit,-2*unit-Yoffset,0*unit), rotation_WE));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildWalker3(String id) {
		float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("Spline["+id+"]", new VectorDescription(0*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	
    	mPos.add(new ModulePosition("fa1["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new ModulePosition("fa2["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,1*unit), rotation_SN));

    	mPos.add(new ModulePosition("fl1["+id+"]", new VectorDescription(1*unit,-1*unit-Yoffset,-2*unit), rotation_DU));
    	mPos.add(new ModulePosition("fl2["+id+"]", new VectorDescription(1*unit,-1*unit-Yoffset,2*unit), rotation_DU));

    	mPos.add(new ModulePosition("ba1["+id+"]", new VectorDescription(-1*unit,0*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new ModulePosition("ba2["+id+"]", new VectorDescription(-1*unit,0*unit-Yoffset,1*unit), rotation_SN));

    	mPos.add(new ModulePosition("bl1["+id+"]", new VectorDescription(-1*unit,-1*unit-Yoffset,-2*unit), rotation_DU));
    	mPos.add(new ModulePosition("bl2["+id+"]", new VectorDescription(-1*unit,-1*unit-Yoffset,2*unit), rotation_DU));
        return mPos;
	}
	
	protected ArrayList<ModulePosition> buildWalker4(String id) {
		float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("spline1["+id+"]", new VectorDescription(0*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	mPos.add(new ModulePosition("spline2["+id+"]", new VectorDescription(-2*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	
    	mPos.add(new ModulePosition("fa1["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new ModulePosition("fa2["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,1*unit), rotation_SN));

    	mPos.add(new ModulePosition("fl1["+id+"]", new VectorDescription(1*unit,-1*unit-Yoffset,-2*unit), rotation_DU));
    	mPos.add(new ModulePosition("fl2["+id+"]", new VectorDescription(1*unit,-1*unit-Yoffset,2*unit), rotation_DU));

    	mPos.add(new ModulePosition("ma1["+id+"]", new VectorDescription(-1*unit,0*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new ModulePosition("ma2["+id+"]", new VectorDescription(-1*unit,0*unit-Yoffset,1*unit), rotation_SN));

    	mPos.add(new ModulePosition("ml1["+id+"]", new VectorDescription(-1*unit,-1*unit-Yoffset,-2*unit), rotation_DU));
    	mPos.add(new ModulePosition("ml2["+id+"]", new VectorDescription(-1*unit,-1*unit-Yoffset,2*unit), rotation_DU));

    	mPos.add(new ModulePosition("ba1["+id+"]", new VectorDescription(-3*unit,0*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new ModulePosition("ba2["+id+"]", new VectorDescription(-3*unit,0*unit-Yoffset,1*unit), rotation_SN));

    	mPos.add(new ModulePosition("bl1["+id+"]", new VectorDescription(-3*unit,-1*unit-Yoffset,-2*unit), rotation_DU));
    	mPos.add(new ModulePosition("bl2["+id+"]", new VectorDescription(-3*unit,-1*unit-Yoffset,2*unit), rotation_DU));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildWalker5(String id) {
		float Yoffset = 0.35f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("spline1["+id+"]", new VectorDescription(0*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	mPos.add(new ModulePosition("spline2["+id+"]", new VectorDescription(-2*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	
    	mPos.add(new ModulePosition("fa1["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new ModulePosition("fa2["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,1*unit), rotation_SN));

    	mPos.add(new ModulePosition("fl1["+id+"]", new VectorDescription(1*unit,-1*unit-Yoffset,-2*unit), rotation_DU));
    	mPos.add(new ModulePosition("fl2["+id+"]", new VectorDescription(1*unit,-1*unit-Yoffset,2*unit), rotation_DU));

    	mPos.add(new ModulePosition("ma1["+id+"]", new VectorDescription(-1*unit,0*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new ModulePosition("ma2["+id+"]", new VectorDescription(-1*unit,0*unit-Yoffset,1*unit), rotation_SN));

    	//mPos.add(new ModulePosition("ml1["+id+"]", new VectorDescription(-1*unit,-1*unit-Yoffset,-2*unit), rotation_DU));
    	//mPos.add(new ModulePosition("ml2["+id+"]", new VectorDescription(-1*unit,-1*unit-Yoffset,2*unit), rotation_DU));

    	mPos.add(new ModulePosition("ba1["+id+"]", new VectorDescription(-3*unit,0*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new ModulePosition("ba2["+id+"]", new VectorDescription(-3*unit,0*unit-Yoffset,1*unit), rotation_SN));

    	mPos.add(new ModulePosition("bl1["+id+"]", new VectorDescription(-3*unit,-1*unit-Yoffset,-2*unit), rotation_DU));
    	mPos.add(new ModulePosition("bl2["+id+"]", new VectorDescription(-3*unit,-1*unit-Yoffset,2*unit), rotation_DU));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildCrawler1(String id) {
		float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("Spline["+id+"]", new VectorDescription(0*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	
    	mPos.add(new ModulePosition("fa1["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new ModulePosition("fa2["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,1*unit), rotation_SN));

    	mPos.add(new ModulePosition("fl1["+id+"]", new VectorDescription(1*unit,-1*unit-Yoffset,-2*unit), rotation_DU));
    	mPos.add(new ModulePosition("fl2["+id+"]", new VectorDescription(1*unit,1*unit-Yoffset,2*unit), rotation_DU));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildCrawler2(String id) {
		float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("Spline["+id+"]", new VectorDescription(0*unit,0*unit-Yoffset,0*unit), rotation_EW));
    	
    	mPos.add(new ModulePosition("fa1["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,-1*unit), rotation_NS));
    	mPos.add(new ModulePosition("fa2["+id+"]", new VectorDescription(1*unit,0*unit-Yoffset,1*unit), rotation_SN));

    	mPos.add(new ModulePosition("fl1["+id+"]", new VectorDescription(1*unit,-1*unit-Yoffset,-2*unit), rotation_DU));
    	mPos.add(new ModulePosition("fl2["+id+"]", new VectorDescription(1*unit,1*unit-Yoffset,2*unit), rotation_DU));
    	
    	mPos.add(new ModulePosition("Tail1["+id+"]", new VectorDescription(-1*unit,-1*unit-Yoffset,0*unit), rotation_DU));
    	//mPos.add(new ModulePosition("Tail["+id+"]", new VectorDescription(0*unit,0*unit-Yoffset,0*unit), rotation_EW));
        return mPos;
	}
	protected static ArrayList<ModulePosition> buildSnake(int length, String id) {
    	float Yoffset = 0.4f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	int x=0,y=0,z=0;
    	for(int i=0;i<length;i++) {
    		if(i%2==0) {
    			mPos.add(new ModulePosition("snake "+i+"["+id+"]", new VectorDescription(x*unit,y*unit-Yoffset,z*unit), rotation_EW));
    		}
    		else {
    			mPos.add(new ModulePosition("snake "+i+"["+id+"]", new VectorDescription(x*unit,y*unit-Yoffset,z*unit), rotation_NS));
    		}
    		x++;z++;
    	}
		return mPos;
	}

	protected ArrayList<ModulePosition> buildRobot() {
		ArrayList<ModulePosition> modulePos = new ArrayList<ModulePosition>();
		VectorDescription offset = new VectorDescription(0,0,0);
		int robotCount = 0;
		for(int i=0;i<Math.sqrt(nRobots);i++) {
			for(int j=0;j<Math.sqrt(nRobots);j++) {
				if(robotCount<nRobots) {
					offset.set(i, 0, j);
					if(robotType == ATRONRobots.NONE) modulePos.addAll(new ArrayList<ModulePosition>());
					if(robotType == ATRONRobots.ONE) modulePos.addAll(buildSnake(1,robotCount+""));
					if(robotType == ATRONRobots.TWOWHEELER) modulePos.addAll(buildTwoWheeler(offset,robotCount+""));
					if(robotType == ATRONRobots.CAR) modulePos.addAll(buildCar(robotCount+""));
					if(robotType == ATRONRobots.SNAKE2) modulePos.addAll(buildSnake(2,robotCount+""));
					if(robotType == ATRONRobots.SNAKE3) modulePos.addAll(buildSnake(3,robotCount+""));
					if(robotType == ATRONRobots.SNAKE4) modulePos.addAll(buildSnake(4,robotCount+""));
			        if(robotType == ATRONRobots.SNAKE7) modulePos.addAll(buildSnake(7,robotCount+""));
			        if(robotType == ATRONRobots.WALKER1) modulePos.addAll(buildWalker1(robotCount+""));
			        if(robotType == ATRONRobots.WALKER2) modulePos.addAll(buildWalker2(robotCount+""));
			        if(robotType == ATRONRobots.WALKER3) modulePos.addAll(buildWalker3(robotCount+""));
			        if(robotType == ATRONRobots.WALKER4) modulePos.addAll(buildWalker4(robotCount+""));
			        if(robotType == ATRONRobots.WALKER5) modulePos.addAll(buildWalker5(robotCount+""));
			        if(robotType == ATRONRobots.CRAWLER1) modulePos.addAll(buildCrawler1(robotCount+""));
			        if(robotType == ATRONRobots.CRAWLER2) modulePos.addAll(buildCrawler2(robotCount+""));
			        if(robotType == ATRONRobots.LOOP4) modulePos.addAll(buildLoop4(robotCount+""));
			        if(robotType == ATRONRobots.LOOP7) modulePos.addAll(buildLoop7(robotCount+""));
			        if(robotType == ATRONRobots.LOOP8) modulePos.addAll(buildLoop8(robotCount+""));
			        
			        robotCount++;
				}
			}
		}
        return modulePos;
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
