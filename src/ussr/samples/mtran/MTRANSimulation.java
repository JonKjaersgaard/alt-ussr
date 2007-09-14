/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.mtran;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsSimulation;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.robotbuildingblocks.WorldDescription.Connection;
import ussr.robotbuildingblocks.WorldDescription.ModulePosition;
import ussr.samples.GenericSimulation;

/**
 * Simple MTRAN simulation
 * 
 * @author david
 *
 */
public abstract class MTRANSimulation extends GenericSimulation implements PhysicsObserver {
	private static float unit = (float)Math.sqrt((0.18f*0.18f)/2);
	private static float pi = (float)Math.PI;
    public static PhysicsSimulation simulation;
    
    private static ArrayList<WorldDescription.ModulePosition> modulePos = new ArrayList<WorldDescription.ModulePosition>();
    private static int constructIndex=0;
    private static boolean printContrutionProgram = true;
    
	public void runSimulation(WorldDescription world, boolean startPaused) {
        PhysicsLogger.setDefaultLoggingLevel();
        simulation = PhysicsFactory.createSimulator();
        simulation.setRobot(new MTRAN(){
        	public Controller createController() {
        		return getController("MTRAN");
        	}},"MTRAN");
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
        world.setPlaneSize(100);
        constructRobot();
		ArrayList<Connection> connections = allConnections(modulePos);
		System.out.println("#connection found = "+connections.size());
		world.setModuleConnections(connections);
		System.out.println("#Module Placed = "+modulePos.size());
		world.setModulePositions(modulePos);
		System.out.println("#Total         = "+modulePos.size());
		return world;
    }
	private static void constructRobot() {
		addModule(0,0,0);
    }
    private static void addModule(int x, int y, int z) {
    	VectorDescription pos = new VectorDescription(x*unit,y*unit-0.47f,z*unit);
    	//RotationDescription rot = rotFromBalls(ballPos.get(i),ballPos.get(j));
    	modulePos.add(new WorldDescription.ModulePosition(Integer.toString(constructIndex),"MTRAN", pos, new RotationDescription(0,0,0)));
    	if(printContrutionProgram) System.out.println("addBall("+x+", "+y+", "+z+");");
    	constructIndex++;
	}
	private static ArrayList<Connection> allConnections(ArrayList<ModulePosition> modulePos) {
    	ArrayList<Connection> connections = new ArrayList<Connection>();
    	for(int i=0;i<modulePos.size();i++) {
    		for(int j=0;j<modulePos.size();j++) {
    			if(false&&isConnectable(modulePos.get(i), modulePos.get(j))) {
    				connections.add(new Connection(modulePos.get(i).getName(),modulePos.get(j).getName()));
    			}
    		}
    	}
		return connections;
	}

	public static boolean isConnectable(ModulePosition ball, ModulePosition module) {
    	float dist = ball.getPosition().distance(module.getPosition());
    	if(dist-Math.abs(Math.sqrt(2*unit*unit))/2<0.0000001) return true;
    	return dist==(float)Math.sqrt(2*unit*unit)/2;
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
