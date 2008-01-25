/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package onlineLearning.odin;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsSimulation;
import ussr.robotbuildingblocks.ModuleConnection;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.samples.GenericSimulation;
import ussr.samples.odin.OdinBall;
import ussr.samples.odin.OdinBallController;
import ussr.samples.odin.OdinBattery;
import ussr.samples.odin.OdinHinge;
import ussr.samples.odin.OdinMuscle;
import ussr.samples.odin.OdinSpring;
import ussr.samples.odin.OdinWheel;

/**
 * Simple Odin simulation test setup
 * 
 * @author david
 *
 */
public abstract class OdinSkillSimulation extends GenericSimulation implements PhysicsObserver {
	
    /*USER PARAMETERS START*/
	protected static enum OdinRobots {LATTICE, ONEWHEELER, TWOWHEELER, SURFACE, BALL};
	protected static OdinRobots robotType =  OdinRobots.ONEWHEELER;
    private static boolean printContrutionProgram = false;
    protected static boolean loadSkillsFromFile = false;
    /*USER PARAMETERS END*/
        
    
    private static float unit = (float)Math.sqrt((0.18f*0.18f)/2);
	private static float pi = (float)Math.PI;
    public static PhysicsSimulation simulation;
    private static ArrayList<ModulePosition> ballPos = new ArrayList<ModulePosition>();
    private static ArrayList<ModulePosition> modulePos = new ArrayList<ModulePosition>();
    private static int constructIndex=0;
    
    
	public void runSimulation(WorldDescription world, boolean startPaused) {
        PhysicsLogger.setDefaultLoggingLevel();
        simulation = PhysicsFactory.createSimulator();
        simulation.setRobot(new OdinMuscle(){
        	public Controller createController() {
        		return getController("OdinMuscle");
        	}},"OdinMuscle");
        simulation.setRobot(new OdinWheel(){
        	public Controller createController() {
        		return getController("OdinWheel"); 
        	}},"OdinWheel");
        simulation.setRobot(new OdinHinge(){
        	public Controller createController() {
        		return getController("OdinHinge");
        	}},"OdinHinge");
        
        simulation.setRobot(new OdinBattery(){
        	public Controller createController() {
        		return getController("OdinBattery");
        	}},"OdinBattery");
        
        simulation.setRobot(new OdinBall(){
        	public Controller createController() {
        		return new OdinBallController("OdinBall");
        	}},"OdinBall");
        simulation.setRobot(new OdinSpring(){
        	public Controller createController() {
        		return getController("OdinSpring");
        	}},"OdinSpring");
        if(world==null) world = createWorld();
        this.changeWorldHook(world);
        simulation.setWorld(world);
        simulation.setPause(startPaused);
        simulation.subscribePhysicsTimestep(this);
        simulation.start();
    }
	protected void changeWorldHook(WorldDescription world) {
    }
	/**
     * Create a world description for our simulation
     * @return the world description
     */
    private static WorldDescription createWorld() {
    	WorldDescription world = new WorldDescription();
        world.setPlaneSize(10);
        
        //printConnectorPos();
        if(robotType == OdinRobots.LATTICE) constructLattice();
        if(robotType == OdinRobots.ONEWHEELER) constructOneWheeler();
        if(robotType == OdinRobots.TWOWHEELER) constructTwoWheeler();
        if(robotType == OdinRobots.SURFACE) constructSurface();
        if(robotType == OdinRobots.BALL) constructBall();
        
		ArrayList<ModuleConnection> connections = allConnections(ballPos,modulePos);
		System.out.println("#connection found = "+connections.size());
		world.setModuleConnections(connections);
		System.out.println("#Balls Placed  = "+ballPos.size());
		System.out.println("#Module Placed = "+modulePos.size());
		modulePos.addAll(ballPos);
		world.setModulePositions(modulePos);
		System.out.println("#Total         = "+modulePos.size());
		return world;
    }
	private static void constructSurface() {
    	addBall(2, 0, 0);
    	addBall(1, 0, 1);
    	addBall(3, 0, 1);
    	addBall(0, 0, 2);
    	addBall(2, 0, 2);
    	addBall(4, 0, 2);
    	addBall(1, 0, 3);
    	addBall(3, 0, 3);
    	addBall(2, 0, 4);
    	addModule(0, 2, "OdinWheel");
    	addModule(1, 4, "OdinWheel");
    	addModule(3, 6, "OdinWheel");
    	addModule(2, 5, "OdinWheel");
    	addModule(4, 7, "OdinWheel");
    	addModule(6, 8, "OdinWheel");
    	
    	addModule(0, 1, "OdinBattery");
    	addModule(1, 3, "OdinBattery");
    	addModule(2, 4, "OdinBattery");
    	addModule(4, 6, "OdinBattery");
    	addModule(5, 7, "OdinBattery");
    	addModule(7, 8, "OdinBattery");
	}
    private static void constructBall() {
    	/*Center*/
    	addBall(0, 1, 0);
    	
    	/*Layer 0*/
    	addBall(1, 0, 0);
    	addBall(0, 0, 1);
    	addBall(-1, 0, 0);
    	addBall(0, 0, -1);
    	
    	/*Layer 1*/
    	addBall(1, 1, -1);
    	addBall(1, 1, 1);
    	addBall(-1, 1, 1);
    	addBall(-1, 1, -1);
    	
    	/*Layer 2*/
    	addBall(0, 2, -1);
    	addBall(1, 2, 0);
    	addBall(0, 2, 1);
    	addBall(-1, 2, 0);
    	
    	/*Center module to every ball*/
    	for(int i=1;i<13;i++) {
    		addModule(0, i, "OdinMuscle");
    	}
    	
    	/*Between every other module*/
    	for(int i=1;i<13;i++) {
    		for(int j=i+1;j<13;j++){
    			//addModule(i, j, "OdinBattery");
    			addModule(i, j, "OdinSpring");
    		}
    	}
    	
	}
	private static void constructLattice() {
        //int nBalls=0,xMax=0, yMax=0,zMax=0; 
        //int nBalls=2, xMax=1, yMax=2,zMax=2;
        //int nBalls=3, xMax=3, yMax=2,zMax=2;
                //int nBalls=5, xMax=3, yMax=1,zMax=3;
        //int nBalls=3, xMax=2, yMax=1,zMax=3; //two wheels
       // int nBalls=4, xMax=3, yMax=2,zMax=2;
        //int nBalls=5, xMax=5, yMax=5,zMax=5;
        //int nBalls=4, xMax=3, yMax=2,zMax=2;
    	//int nBalls=9, xMax=5, yMax=1,zMax=5;
        //int nBalls=10, xMax=3, yMax=2,zMax=3;
        //int nBalls=7, xMax=4, yMax=2,zMax=8;
       int nBalls=20, xMax=4, yMax=4,zMax=4;
        for(int x=0;x<xMax;x++) {
        	for(int y=0;y<yMax;y++) {
        		for(int z=0;z<zMax;z++) {
        			if((x+y+z)%2==0) {
        				if(constructIndex<nBalls) {
        					addBall(x, y, z);
        				}
        			}
        		}
        	}
        }
        for(int i=0;i<ballPos.size();i++) {
        	for(int j=i+1;j<ballPos.size();j++) {
        		addModule(i, j, "OdinMuscle");
        	}
        }

    }
	private static void constructOneWheeler() {
    	addBall(0, 1, 1);
    	addBall(1, 0, 1);
    	addBall(0, 0, 2);
    	addModule(0, 1, "OdinMuscle");
    	addModule(0, 2, "OdinMuscle");
    	addModule(1, 2, "OdinWheel");
    }
    private static void constructTwoWheeler() {
    	addBall(0, 0, 0);
    	addBall(0, 0, 2);
    	addBall(1, 0, 1);
    	addModule(0, 2, "OdinWheel");
    	addModule(1, 2, "OdinWheel");
    }
    
    private static void addModule(int i, int j, String type) {
    	if(isNeighorBalls(ballPos.get(i),ballPos.get(j))) {
			VectorDescription pos = posFromBalls(ballPos.get(i),ballPos.get(j));
			RotationDescription rot = rotFromBalls(ballPos.get(i),ballPos.get(j));
			if(type=="OdinBattery") System.out.println("root="+rot);
			/*if(constructIndex%2==0) {
				Quaternion q = new Quaternion();
				q.fromAngles(pi/2, 0, 0);
				rot.setRotation(rot.getRotation().mult(q));
			}*/
			modulePos.add(new ModulePosition(Integer.toString(constructIndex),type, pos, rot));
			if(printContrutionProgram) System.out.println("addModule("+i+", "+j+", \""+type+"\");");
			constructIndex++;
		}
    	
		
	}
	private static void addBall(int x, int y, int z) {
    	VectorDescription pos = new VectorDescription(x*unit,y*unit-0.47f,z*unit);
    	ballPos.add(new ModulePosition(Integer.toString(constructIndex),"OdinBall", pos, new RotationDescription(0,0,0)));
    	if(printContrutionProgram) System.out.println("addBall("+x+", "+y+", "+z+");");
    	constructIndex++;
	}
	private static ArrayList<ModuleConnection> allConnections(ArrayList<ModulePosition> ballPos, ArrayList<ModulePosition> modulePos) {
    	ArrayList<ModuleConnection> connections = new ArrayList<ModuleConnection>();
    	for(int i=0;i<ballPos.size();i++) {
    		for(int j=0;j<modulePos.size();j++) {
    			if(isConnectable(ballPos.get(i), modulePos.get(j))) {
    				connections.add(new ModuleConnection(ballPos.get(i).getName(),modulePos.get(j).getName()));
    			}
    		}
    	}
		return connections;
	}
	private static VectorDescription posFromBalls(ModulePosition p1, ModulePosition p2) {
    	VectorDescription pos = new VectorDescription((p1.getPosition().getX()+p2.getPosition().getX())/2,(p1.getPosition().getY()+p2.getPosition().getY())/2,(p1.getPosition().getZ()+p2.getPosition().getZ())/2);
		return pos;
	}
	private static RotationDescription rotFromBalls(ModulePosition p1, ModulePosition p2) {
		RotationDescription rot = rotFromBallsWithDir(p1, p2);
		if(rot==null) rot = rotFromBallsWithDir(p2, p1);
		if(rot==null) return new RotationDescription(0,0,0);
		else return rot;
	}
	private static RotationDescription rotFromBallsWithDir(ModulePosition p1, ModulePosition p2) {
		float x1 = p1.getPosition().getX();
		float y1 = p1.getPosition().getY();
		float z1 = p1.getPosition().getZ();
		float x2 = p2.getPosition().getX();
		float y2 = p2.getPosition().getY();
		float z2 = p2.getPosition().getZ();
		
		if(x1-x2<0&&z1-z2<0) return new RotationDescription(0,-pi/4,0);
		else if(x1-x2<0&&z1-z2>0) return new RotationDescription(0,pi/4,0);
		else if(x1-x2<0&&y1-y2<0) return new RotationDescription(0,0,pi/4);
		else if(x1-x2<0&&y1-y2>0) return new RotationDescription(0,0,-pi/4);
		else if(y1-y2<0&&z1-z2<0) return new RotationDescription(0,pi/4,-pi/2);
		else if(y1-y2<0&&z1-z2>0) return new RotationDescription(0,-pi/4,-pi/2);
		return null;
	}
	public static boolean isConnectable(ModulePosition ball, ModulePosition module) {
    	float dist = ball.getPosition().distance(module.getPosition());
    	if(dist-Math.abs(Math.sqrt(2*unit*unit))/2<0.0000001) return true;
    	return dist==(float)Math.sqrt(2*unit*unit)/2;
    }
	public static boolean isNeighorBalls(ModulePosition ball1, ModulePosition ball2) {
    	float dist = ball1.getPosition().distance(ball2.getPosition());
    	if(dist-Math.abs(Math.sqrt(2*unit*unit))<0.0000001) return true;
    	return dist==(float)Math.sqrt(2*unit*unit);
    }
    public static void printConnectorPos() {
    	for(int x=-2;x<2;x++) {
        	for(int y=-2;y<2;y++) {
        		for(int z=-2;z<2;z++) {
        			if((x+y+z)%2==0&&(x*x+y*y+z*z)<3&&!(x==0&&y==0&&z==0)) {
        				System.out.println("new VectorDescription("+x+"*unit, "+y+"*unit, "+z+"*unit),");
        			}
        		}
        	}
        }
    }
	protected Robot getRobot() {
		// TODO Auto-generated method stub
		return null;
	}
	public abstract Controller getController(String type);
}
