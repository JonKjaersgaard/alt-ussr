/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ckbot;

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
 * Abstract MTRAN simulation
 * 
 * @author david
 *
 */
public abstract class CKBotSimulation extends GenericSimulation implements PhysicsObserver {
	private static float unit = 0.060f;
	private static float pi = (float)Math.PI;
    //public static PhysicsSimulation simulation;
    
    private static ArrayList<ModulePosition> modulePos = new ArrayList<ModulePosition>();
    private static int constructIndex=0;
    private static boolean printContrutionProgram = false;
    
	public void runSimulation(WorldDescription world, boolean startPaused) {
        PhysicsLogger.setDefaultLoggingLevel();
        simulation = PhysicsFactory.createSimulator();
        simulation.setRobot(new CKBotStandard(){
        	public Controller createController() {
        		return getController("CKBotStandard");
        	}},"CKBotStandard");
        constructRobot();
        if(world==null) world = createWorld();
        this.changeWorldHook(world);
        simulation.setWorld(world);
        simulation.setPause(startPaused);
        simulation.subscribePhysicsTimestep(this);
        simulation.start();
    }
	protected void changeWorldHook(WorldDescription world) {
		world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
		
		world.setHasBackgroundScenery(true);
		PhysicsParameters.get().setPhysicsSimulationStepSize(0.005f);
		PhysicsParameters.get().setWorldDampingLinearVelocity(0.9f);
    }
	/**
     * Create a world description for our simulation
     * @return the world description
     */
    private static WorldDescription createWorld() {
    	WorldDescription world = new WorldDescription();
        world.setPlaneSize(100);
		ArrayList<ModuleConnection> connections = allConnections(modulePos);
		System.out.println("#connection found = "+connections.size());
		world.setModuleConnections(connections);
		System.out.println("#Module Placed = "+modulePos.size());
		world.setModulePositions(modulePos);
		System.out.println("#Total         = "+modulePos.size());
		return world;
    }
    
    protected static void addModule(float x, float y, float z, RotationDescription ori) {
    	VectorDescription pos = new VectorDescription(x*unit,y*unit,z*unit);
    	//RotationDescription rot = rotFromBalls(ballPos.get(i),ballPos.get(j));
    	modulePos.add(new ModulePosition(Integer.toString(constructIndex),"CKBotStandard", pos, ori));
    	constructIndex++;
	}
    protected static void addModule(float x, float y, float z, RotationDescription ori, String name) {
    	VectorDescription pos = new VectorDescription(x*unit,y*unit,z*unit);
    	modulePos.add(new ModulePosition(name,"CKBotStandard", pos, ori));
	}
    private static ArrayList<ModuleConnection> allConnections(ArrayList<ModulePosition> modulePos) {
    	ArrayList<ModuleConnection> connections = new ArrayList<ModuleConnection>();
    	System.out.println("modulePos.size()"+modulePos.size());
    	for(int i=0;i<modulePos.size();i++) {
    		for(int j=i+1;j<modulePos.size();j++) {
    			if(isConnectable(modulePos.get(i), modulePos.get(j))) {
    				System.out.println("Found connection from module "+modulePos.get(i).getName()+" to "+modulePos.get(j).getName());
    				connections.add(new ModuleConnection(modulePos.get(i).getName(),modulePos.get(j).getName()));
    			}
    		}
    	}
		return connections;
	}
    
	public static boolean isConnectable(ModulePosition m1, ModulePosition m2) {
    	float dist = m1.getPosition().distance(m2.getPosition());
    	System.out.println(Math.abs(dist-unit));
    	return Math.abs(dist-unit)<0.05f;
    }
    
	protected Robot getRobot() {
		// TODO Auto-generated method stub
		return null;
	}
	public abstract Controller getController(String type);
	protected abstract void constructRobot();
}
