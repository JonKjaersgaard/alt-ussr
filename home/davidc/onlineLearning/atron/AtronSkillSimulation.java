package onlineLearning.atron;

import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModuleConnection;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsParameters.Material;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.GenericATRONSimulation;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

public abstract class AtronSkillSimulation extends GenericATRONSimulation {
		

    /*USER PARAMETERS START*/
	protected static enum ATRONRobots {NONE, ONE, TWOWHEELER, CRAWLER1, CRAWLER2, CAR, SNAKE2, SNAKE3, SNAKE4, SNAKE7, WALKER1, WALKER2, WALKER3, WALKER4, WALKER5, WALKER6,LOOP4, LOOP7, LOOP8,MILLIPEDE1,MILLIPEDE2,MILLIPEDE3,MILLIPEDE4,MILLIPEDE5,MILLIPEDE6,MILLIPEDE7,MILLIPEDE8,MILLIPEDE9,MILLIPEDE10,MILLIPEDE12,MILLIPEDE14,MILLIPEDE16,MILLIPEDE18,MILLIPEDE20};
	protected static ATRONRobots robotType =  ATRONRobots.MILLIPEDE10;
	protected static int nRobots =  1;
	static boolean loadSkillsFromFile = false;
	static boolean startPaused = false;
	static boolean realistic = true;
	static boolean realisticCollision = true;	
	static float periodeTime = 7;//7f;
	static float evalPeriode = 7;//7f;
	static float simulationTime = evalPeriode*1030;//1030;//520; //2000
	static boolean syncronized = true;
	static float physicsSimulationStepSize = 0.01f;//0.01f;
	static boolean primitiveRoles = true;
	static boolean rubberRing = false;
	public static boolean SRFAULT = false;
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
        //PhysicsParameters.get().setConstraintForceMix(0.01f);//10E-5f
        //PhysicsParameters.get().setErrorReductionParameter(0.8f); //0.8f;
		PhysicsParameters.get().setRealisticCollision(realisticCollision);
		PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
		PhysicsParameters.get().setMaintainRotationalJointPositions(false); 
		//PhysicsParameters.get().setPlaneMaterial(Material.GLASS);
        if(realistic) atron.setRealistic();
        else atron.setSuper();
        //if(rubberRing) atron.setRubberRing();
        return atron;
    }
	protected ArrayList<ModulePosition> buildTwoWheeler(VectorDescription offset,String id) {
    	float Yoffset = 0.25f;
    	rubberRing = true; 
    	final float eigth = (float)(0.25*Math.PI);
        final float quart = (float)(0.5*Math.PI);
        final float half = (float)(Math.PI);
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	//mPos.add(new ModulePosition("axle", new VectorDescription(1*unit,-1*unit-Yoffset,0*unit), rotation_UD));
    	mPos.add(new ModulePosition("axle["+id+"]", new VectorDescription(0*ATRON.UNIT+offset.getX(),-2*ATRON.UNIT-Yoffset+offset.getY(),0*ATRON.UNIT+offset.getZ()), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("wheel1["+id+"]", new VectorDescription(1*ATRON.UNIT+offset.getX(),-2*ATRON.UNIT-Yoffset+offset.getY(),1*ATRON.UNIT+offset.getZ()), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("wheel2["+id+"]", new VectorDescription(1*ATRON.UNIT+offset.getX(),-2*ATRON.UNIT-Yoffset+offset.getY(),-1*ATRON.UNIT+offset.getZ()), ATRON.ROTATION_NS));
    	
        return mPos;
	}
	protected ArrayList<ModulePosition> buildCar(String id) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("driver0["+id+"]", new VectorDescription(2*0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("axleOne5["+id+"]", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_UD));
    	mPos.add(new ModulePosition("axleTwo6["+id+"]", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_UD));
    	mPos.add(new ModulePosition("wheel1["+id+"]", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("wheel2["+id+"]", new VectorDescription(-1*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("wheel3["+id+"]", new VectorDescription(1*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("wheel4["+id+"]", new VectorDescription(1*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildLoop4(String id) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("x1["+id+"]", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("x2["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("x3["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("x4["+id+"]", new VectorDescription(2*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildLoop7(String id) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("x1["+id+"]", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("x2["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("x3["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("x4["+id+"]", new VectorDescription(2*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
    	
    	mPos.add(new ModulePosition("x5["+id+"]", new VectorDescription(3*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("x6["+id+"]", new VectorDescription(3*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("x7["+id+"]", new VectorDescription(4*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildLoop8(String id) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("x1["+id+"]", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("x2["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("x3["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("x4["+id+"]", new VectorDescription(2*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
    	
    	mPos.add(new ModulePosition("y1["+id+"]", new VectorDescription(2*ATRON.UNIT,0*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("y2["+id+"]", new VectorDescription(3*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("y3["+id+"]", new VectorDescription(3*ATRON.UNIT,0*ATRON.UNIT-Yoffset,3*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("y4["+id+"]", new VectorDescription(4*ATRON.UNIT,0*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_WE));
        return mPos;
	}	
	protected ArrayList<ModulePosition> buildWalker1(String id) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("x1["+id+"]", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("x2["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("x3["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("x4["+id+"]", new VectorDescription(2*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
    	
    	mPos.add(new ModulePosition("y1["+id+"]", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("y2["+id+"]", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("y3["+id+"]", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("y4["+id+"]", new VectorDescription(3*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_DU));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildWalker2(String id) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("x1["+id+"]", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("x2["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("x3["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("x4["+id+"]", new VectorDescription(2*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
    	
    	mPos.add(new ModulePosition("y1["+id+"]", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("y2["+id+"]", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("y3["+id+"]", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("y4["+id+"]", new VectorDescription(3*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_DU));
    	
    	mPos.add(new ModulePosition("z1["+id+"]", new VectorDescription(-2*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("z2["+id+"]", new VectorDescription(1*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,-3*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("z3["+id+"]", new VectorDescription(1*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,3*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("z4["+id+"]", new VectorDescription(4*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildWalker3(String id) {
		float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("Spline["+id+"]", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	
    	mPos.add(new ModulePosition("fa1["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("fa2["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));

    	mPos.add(new ModulePosition("fl1["+id+"]", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("fl2["+id+"]", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_DU));

    	mPos.add(new ModulePosition("ba1["+id+"]", new VectorDescription(-1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("ba2["+id+"]", new VectorDescription(-1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));

    	mPos.add(new ModulePosition("bl1["+id+"]", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("bl2["+id+"]", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_DU));
        return mPos;
	}
	
	protected ArrayList<ModulePosition> buildWalker4(String id) {
		float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("spline1["+id+"]", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("spline2["+id+"]", new VectorDescription(-2*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	
    	mPos.add(new ModulePosition("fa1["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("fa2["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));

    	mPos.add(new ModulePosition("fl1["+id+"]", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("fl2["+id+"]", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_DU));

    	mPos.add(new ModulePosition("ma1["+id+"]", new VectorDescription(-1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("ma2["+id+"]", new VectorDescription(-1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));

    	mPos.add(new ModulePosition("ml1["+id+"]", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("ml2["+id+"]", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_DU));

    	mPos.add(new ModulePosition("ba1["+id+"]", new VectorDescription(-3*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("ba2["+id+"]", new VectorDescription(-3*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));

    	mPos.add(new ModulePosition("bl1["+id+"]", new VectorDescription(-3*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("bl2["+id+"]", new VectorDescription(-3*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_DU));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildWalker5(String id) {
		float Yoffset = 0.35f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("spline1["+id+"]", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("spline2["+id+"]", new VectorDescription(-2*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	
    	mPos.add(new ModulePosition("fa1["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("fa2["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));

    	mPos.add(new ModulePosition("fl1["+id+"]", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("fl2["+id+"]", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_DU));

    	mPos.add(new ModulePosition("ma1["+id+"]", new VectorDescription(-1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("ma2["+id+"]", new VectorDescription(-1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));

    	//mPos.add(new ModulePosition("ml1["+id+"]", new VectorDescription(-1*unit,-1*unit-Yoffset,-2*unit), rotation_DU));
    	//mPos.add(new ModulePosition("ml2["+id+"]", new VectorDescription(-1*unit,-1*unit-Yoffset,2*unit), rotation_DU));

    	mPos.add(new ModulePosition("ba1["+id+"]", new VectorDescription(-3*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("ba2["+id+"]", new VectorDescription(-3*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));

    	mPos.add(new ModulePosition("bl1["+id+"]", new VectorDescription(-3*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("bl2["+id+"]", new VectorDescription(-3*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_DU));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildWalker6(String id) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("x1["+id+"]", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	mPos.add(new ModulePosition("x2["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("x3["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("x4["+id+"]", new VectorDescription(2*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_WE));
    	
    	mPos.add(new ModulePosition("y1["+id+"]", new VectorDescription(-1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));
    	//mPos.add(new ModulePosition("y2["+id+"]", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_DU));
    	//mPos.add(new ModulePosition("y3["+id+"]", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("y4["+id+"]", new VectorDescription(3*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	
    	mPos.add(new ModulePosition("z1["+id+"]", new VectorDescription(-2*ATRON.UNIT,0*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_EW));
    	//mPos.add(new ModulePosition("z2["+id+"]", new VectorDescription(1*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,-3*ATRON.UNIT), ATRON.ROTATION_NS));
    	//mPos.add(new ModulePosition("z3["+id+"]", new VectorDescription(1*ATRON.UNIT,-2*ATRON.UNIT-Yoffset,3*ATRON.UNIT), ATRON.ROTATION_SN));
    	mPos.add(new ModulePosition("z4["+id+"]", new VectorDescription(4*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_WE));
        
        return mPos;
	}
	protected ArrayList<ModulePosition> buildMillipede(String id, int nLegPairs) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	final float eigth = (float)(0.25*Math.PI);
        final float quart = (float)(0.5*Math.PI);
        final float half = (float)(Math.PI);
        
        for(int i=0;i<nLegPairs;i++) {
        	mPos.add(new ModulePosition("spline"+(i)+"["+id+"]", new VectorDescription(0*ATRON.UNIT,2*i*ATRON.UNIT-Yoffset,0*ATRON.UNIT),ATRON.ROTATION_UD.clone()));
        	mPos.add(new ModulePosition("xLeg"+(i)+"["+id+"]", new VectorDescription(1*ATRON.UNIT,(1+2*i)*ATRON.UNIT-Yoffset,0*ATRON.UNIT),ATRON.ROTATION_WE.clone()));
        	mPos.add(new ModulePosition("yLeg"+(i)+"["+id+"]", new VectorDescription(-1*ATRON.UNIT,(1+2*i)*ATRON.UNIT-Yoffset,0*ATRON.UNIT),ATRON.ROTATION_EW.clone()));
        	if(i%1==0) {
	        	mPos.add(new ModulePosition("xFeet"+(i)+"["+id+"]", new VectorDescription(2*ATRON.UNIT,(1+2*i)*ATRON.UNIT-Yoffset,1*ATRON.UNIT),ATRON.ROTATION_NS.clone()));
	        	mPos.add(new ModulePosition("yFeet"+(i)+"["+id+"]", new VectorDescription(-2*ATRON.UNIT,(1+2*i)*ATRON.UNIT-Yoffset,1*ATRON.UNIT),ATRON.ROTATION_SN.clone()));
        	}
        }
    	
    	for(ModulePosition m: mPos) {
        	Quaternion q = new Quaternion();
        	q.fromAngles(quart, 0, 0);
        	m.rotate(new Vector3f(0,0,0),q);
        	m.translate(new Vector3f(0,-Yoffset,0));
    	}
    	
    	return mPos;
	}
	ArrayList<ModuleConnection> ignoreConnections = new ArrayList<ModuleConnection>();
	protected ArrayList<ModulePosition> buildMillipede2(String id) {
    	float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	final float eigth = (float)(0.25*Math.PI);
        final float quart = (float)(0.5*Math.PI);
        final float half = (float)(Math.PI);
        
        for(int i=0;i<5;i++) {
        	mPos.add(new ModulePosition("spline"+(i)+"["+id+"]", new VectorDescription(0*ATRON.UNIT,2*i*ATRON.UNIT-Yoffset,0*ATRON.UNIT),ATRON.ROTATION_UD.clone()));
        	if(i%1==0) {
        //		mPos.add(new ModulePosition("xHip"+(i)+"["+id+"]", new VectorDescription(1*ATRON.UNIT,(1+2*i)*ATRON.UNIT-Yoffset,0*ATRON.UNIT),ATRON.ROTATION_WE.clone()));
    		//	mPos.add(new ModulePosition("yHip"+(i)+"["+id+"]", new VectorDescription(0*ATRON.UNIT,(1+2*i)*ATRON.UNIT-Yoffset,1*ATRON.UNIT),ATRON.ROTATION_NS.clone()));
        		if(i%2==0) {
        			mPos.add(new ModulePosition("xHip"+(i)+"["+id+"]", new VectorDescription(1*ATRON.UNIT,(1+2*i)*ATRON.UNIT-Yoffset,0*ATRON.UNIT),ATRON.ROTATION_WE.clone()));
        			mPos.add(new ModulePosition("xFeet"+(i)+"["+id+"]", new VectorDescription(2*ATRON.UNIT,(1+2*i)*ATRON.UNIT-Yoffset,1*ATRON.UNIT),ATRON.ROTATION_NS.clone()));
        			
        		}
        		else {
        			mPos.add(new ModulePosition("yHip"+(i)+"["+id+"]", new VectorDescription(0*ATRON.UNIT,(1+2*i)*ATRON.UNIT-Yoffset,1*ATRON.UNIT),ATRON.ROTATION_NS.clone()));
        			mPos.add(new ModulePosition("yFeet"+(i)+"["+id+"]", new VectorDescription(1*ATRON.UNIT,(1+2*i)*ATRON.UNIT-Yoffset,2*ATRON.UNIT),ATRON.ROTATION_WE.clone()));
                	
        		}

            	//mPos.add(new ModulePosition("xHip2"+(i)+"["+id+"]", new VectorDescription(-1*ATRON.UNIT,(1+2*i)*ATRON.UNIT-Yoffset,0*ATRON.UNIT),ATRON.ROTATION_EW.clone()));
            	//mPos.add(new ModulePosition("yHip2"+(i)+"["+id+"]", new VectorDescription(0*ATRON.UNIT,(1+2*i)*ATRON.UNIT-Yoffset,-1*ATRON.UNIT),ATRON.ROTATION_SN.clone()));
            	
	        	//mPos.add(new ModulePosition("xKnee"+(i)+"["+id+"]", new VectorDescription(2*ATRON.UNIT,(0+2*i)*ATRON.UNIT-Yoffset,0*ATRON.UNIT),ATRON.ROTATION_UD.clone()));
	        	//mPos.add(new ModulePosition("yKnee"+(i)+"["+id+"]", new VectorDescription(0*ATRON.UNIT,(0+2*i)*ATRON.UNIT-Yoffset,2*ATRON.UNIT),ATRON.ROTATION_UD.clone()));
	        	
            	if(i%2==0) {
            	

            	ignoreConnections.add(new ModuleConnection("xFeet"+(i)+"["+id+"]","yFeet"+(i)+"["+id+"]"));
	        	ignoreConnections.add(new ModuleConnection("yFeet"+(i)+"["+id+"]","xFeet"+(i)+"["+id+"]"));
            	}
	        	
	        	
            	//mPos.add(new ModulePosition("xKnee"+(i)+"["+id+"]", new VectorDescription(0*ATRON.UNIT,(1+2*i)*ATRON.UNIT-Yoffset,-1*ATRON.UNIT),ATRON.ROTATION_UD.clone()));
	        	//mPos.add(new ModulePosition("yKnee"+(i)+"["+id+"]", new VectorDescription(0*ATRON.UNIT,(0+2*i)*ATRON.UNIT-Yoffset,2*ATRON.UNIT),ATRON.ROTATION_UD.clone()));
	        	
	        	
	        	/*ignoreConnections.add(new ModuleConnection("xKnee"+(i+1)+"["+id+"]","xHip"+(i)+"["+id+"]"));
	        	ignoreConnections.add(new ModuleConnection("xHip"+(i)+"["+id+"]","xKnee"+(i+1)+"["+id+"]"));
	        	
	        	ignoreConnections.add(new ModuleConnection("yKnee"+(i+1)+"["+id+"]","yHip"+(i)+"["+id+"]"));
	        	ignoreConnections.add(new ModuleConnection("yHip"+(i)+"["+id+"]","yKnee"+(i+1)+"["+id+"]"));*/
	        	
	        	
	        	//ignoreConnections.add(new ModuleConnection(mPos.get(mPos.size()-1).getName(),mPos.get(mPos.size()-2).getName()));
        	}
        	else {
        		mPos.add(new ModulePosition("xBack"+(i)+"["+id+"]", new VectorDescription(-1*ATRON.UNIT,(1+2*i)*ATRON.UNIT-Yoffset,0*ATRON.UNIT),ATRON.ROTATION_WE.clone()));
            	mPos.add(new ModulePosition("yBack"+(i)+"["+id+"]", new VectorDescription(0*ATRON.UNIT,(1+2*i)*ATRON.UNIT-Yoffset,-1*ATRON.UNIT),ATRON.ROTATION_NS.clone()));
        	}
        }
      
    	for(ModulePosition m: mPos) {
        	Quaternion q = new Quaternion();
        	q.fromAngles(quart, 0, -eigth);
        	m.rotate(new Vector3f(0,0,0),q);
    	}
    	
    	return mPos;
	}
	protected ArrayList<ModulePosition> buildCrawler1(String id) {
		float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("Spline["+id+"]", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	
    	mPos.add(new ModulePosition("fa1["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("fa2["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));

    	mPos.add(new ModulePosition("fl1["+id+"]", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("fl2["+id+"]", new VectorDescription(1*ATRON.UNIT,1*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_DU));
        return mPos;
	}
	protected ArrayList<ModulePosition> buildCrawler2(String id) {
		float Yoffset = 0.25f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	mPos.add(new ModulePosition("Spline["+id+"]", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_EW));
    	
    	mPos.add(new ModulePosition("fa1["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,-1*ATRON.UNIT), ATRON.ROTATION_NS));
    	mPos.add(new ModulePosition("fa2["+id+"]", new VectorDescription(1*ATRON.UNIT,0*ATRON.UNIT-Yoffset,1*ATRON.UNIT), ATRON.ROTATION_SN));

    	mPos.add(new ModulePosition("fl1["+id+"]", new VectorDescription(1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,-2*ATRON.UNIT), ATRON.ROTATION_DU));
    	mPos.add(new ModulePosition("fl2["+id+"]", new VectorDescription(1*ATRON.UNIT,1*ATRON.UNIT-Yoffset,2*ATRON.UNIT), ATRON.ROTATION_DU));
    	
    	mPos.add(new ModulePosition("Tail1["+id+"]", new VectorDescription(-1*ATRON.UNIT,-1*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_DU));
    	//mPos.add(new ModulePosition("Tail["+id+"]", new VectorDescription(0*unit,0*unit-Yoffset,0*unit), rotation_EW));
        return mPos;
	}
	protected static ArrayList<ModulePosition> buildSnake(int length, String id) {
    	float Yoffset = 0.4f;
    	ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
    	int x=0,y=0,z=0;
    	for(int i=0;i<length;i++) {
    		if(i%2==0) {
    			mPos.add(new ModulePosition("snake "+i+"["+id+"]", new VectorDescription(x*ATRON.UNIT,y*ATRON.UNIT-Yoffset,z*ATRON.UNIT), ATRON.ROTATION_EW));
    		}
    		else {
    			mPos.add(new ModulePosition("snake "+i+"["+id+"]", new VectorDescription(x*ATRON.UNIT,y*ATRON.UNIT-Yoffset,z*ATRON.UNIT), ATRON.ROTATION_NS));
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
			        if(robotType == ATRONRobots.WALKER6) modulePos.addAll(buildWalker6(robotCount+""));
			        if(robotType == ATRONRobots.MILLIPEDE1) modulePos.addAll(buildMillipede(robotCount+"",1));
			        if(robotType == ATRONRobots.MILLIPEDE2) modulePos.addAll(buildMillipede(robotCount+"",2));
			        if(robotType == ATRONRobots.MILLIPEDE3) modulePos.addAll(buildMillipede(robotCount+"",3));
			        if(robotType == ATRONRobots.MILLIPEDE4) modulePos.addAll(buildMillipede(robotCount+"",4));
			        if(robotType == ATRONRobots.MILLIPEDE5) modulePos.addAll(buildMillipede(robotCount+"",5));
			        if(robotType == ATRONRobots.MILLIPEDE6) modulePos.addAll(buildMillipede(robotCount+"",6));
			        if(robotType == ATRONRobots.MILLIPEDE7) modulePos.addAll(buildMillipede(robotCount+"",7));
			        if(robotType == ATRONRobots.MILLIPEDE8) modulePos.addAll(buildMillipede(robotCount+"",8));
			        if(robotType == ATRONRobots.MILLIPEDE9) modulePos.addAll(buildMillipede(robotCount+"",9));
			        if(robotType == ATRONRobots.MILLIPEDE10) modulePos.addAll(buildMillipede(robotCount+"",10));
			        if(robotType == ATRONRobots.MILLIPEDE12) modulePos.addAll(buildMillipede(robotCount+"",12));
			        if(robotType == ATRONRobots.MILLIPEDE14) modulePos.addAll(buildMillipede(robotCount+"",14));
			        if(robotType == ATRONRobots.MILLIPEDE16) modulePos.addAll(buildMillipede(robotCount+"",16));
			        if(robotType == ATRONRobots.MILLIPEDE18) modulePos.addAll(buildMillipede(robotCount+"",18));
			        if(robotType == ATRONRobots.MILLIPEDE20) modulePos.addAll(buildMillipede(robotCount+"",20));
			        
			        
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
