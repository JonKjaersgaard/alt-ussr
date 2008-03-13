/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.white;

import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModuleConnection;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.GenericSimulation;

/**
 * Abstract white simulation
 * 
 * @author david
 *
 */
public abstract class WhiteSimulation extends GenericSimulation implements PhysicsObserver {
	private static float unit = 0.1f;
	private static float pi = (float)Math.PI;
    public static PhysicsSimulation simulation;
    
    private static ArrayList<ModulePosition> modulePos = new ArrayList<ModulePosition>();
    private static int constructIndex=0;
    private static boolean printContrutionProgram = true;
    
	public void runSimulation(WorldDescription world, boolean startPaused) {
        PhysicsLogger.setDefaultLoggingLevel();
        PhysicsFactory.addFactory(new JMEWhiteFactory());
        simulation = PhysicsFactory.createSimulator();
        simulation.setRobot(new White(){
        	public Controller createController() {
        		return getController("White");
        	}},"White");
        if(world==null) world = createWorld();
        this.changeWorldHook(world);
        simulation.setWorld(world);
        simulation.setPause(startPaused);
        
        simulation.subscribePhysicsTimestep(this);
        simulation.start();
    }
	protected void changeWorldHook(WorldDescription world) {
		world.setPlaneTexture(WorldDescription.GRID_TEXTURE);
		world.setHasBackgroundScenery(false);
		PhysicsParameters.get().setPhysicsSimulationStepSize(0.01f);
		PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
    }
	/**
     * Create a world description for our simulation
     * @return the world description
     */
    private static WorldDescription createWorld() {
    	WorldDescription world = new WorldDescription();
        world.setPlaneSize(100);
        constructRobot();
		ArrayList<ModuleConnection> connections = allConnections(modulePos);
		System.out.println("#connection found = "+connections.size());
		world.setModuleConnections(connections);
		System.out.println("#Module Placed = "+modulePos.size());
		world.setModulePositions(modulePos);
		System.out.println("#Total         = "+modulePos.size());
		return world;
    }
	private static void constructRobot() {
		addModule(0,0,0);
		addModule(1,0,0);
		//addModule(0,0,-1);
		//addModule(1,0,1);
    }
    private static void addModule(int x, int y, int z) {
    	VectorDescription pos = new VectorDescription(2f*x*unit,2f*y*unit-0.4f,2f*z*unit);
    	//RotationDescription rot = rotFromBalls(ballPos.get(i),ballPos.get(j));
    	modulePos.add(new ModulePosition(Integer.toString(constructIndex),"White", pos, new RotationDescription(0,0,0)));
    	constructIndex++;
	}
	private static ArrayList<ModuleConnection> allConnections(ArrayList<ModulePosition> modulePos) {
    	ArrayList<ModuleConnection> connections = new ArrayList<ModuleConnection>();
    	for(int i=0;i<modulePos.size();i++) {
    		for(int j=i+1;j<modulePos.size();j++) {
    			if(isConnectable(modulePos.get(i), modulePos.get(j))) {
    				System.out.println("Found connection from "+i+" to "+j);
    				addConnections(connections, modulePos.get(i),modulePos.get(j));
    			}
    		}
    	}
		return connections;
	}

	private static void addConnections(ArrayList<ModuleConnection> connections, ModulePosition mp1,ModulePosition mp2) {
		if(mp1.getPosition().getX()-mp2.getPosition().getX()==-2*unit) {
			System.out.println("Type 1");
			connections.add(new ModuleConnection(mp1.getName(),1,mp2.getName(),2));
			connections.add(new ModuleConnection(mp1.getName(),0,mp2.getName(),3));
		}
		else if(mp1.getPosition().getX()-mp2.getPosition().getX()==2*unit) {
			System.out.println("Type 2");
			connections.add(new ModuleConnection(mp1.getName(),2,mp2.getName(),1));
			connections.add(new ModuleConnection(mp1.getName(),3,mp2.getName(),0));
		}
		else if(mp1.getPosition().getZ()-mp2.getPosition().getZ()==-2*unit) {
			System.out.println("Type 3");
			connections.add(new ModuleConnection(mp1.getName(),3,mp2.getName(),2));
			connections.add(new ModuleConnection(mp1.getName(),0,mp2.getName(),1));
		}
		else if(mp1.getPosition().getZ()-mp2.getPosition().getZ()==2*unit) {
			System.out.println("Type 4");
			connections.add(new ModuleConnection(mp1.getName(),2,mp2.getName(),3));
			connections.add(new ModuleConnection(mp1.getName(),1,mp2.getName(),0));
		}
		else {
			throw new RuntimeException("Only simple module setup supported");
		}
	}
	public static boolean isConnectable(ModulePosition mPos1, ModulePosition mPos2) {
    	float dist = mPos1.getPosition().distance(mPos2.getPosition());
    	if(dist-2*unit<0.0000001) return true;
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
		return null;
	}
	public abstract Controller getController(String type);
}
