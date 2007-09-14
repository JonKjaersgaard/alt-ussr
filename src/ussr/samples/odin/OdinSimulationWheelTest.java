/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.odin;

import java.util.ArrayList;
import java.util.Random;

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
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONCarController1;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

/**
 * Simple Odin simulation test setup
 * 
 * @author david
 *
 */
public class OdinSimulationWheelTest extends GenericSimulation {
	private static float unit = (float)Math.sqrt((0.18f*0.18f)/2);
	private static float pi = (float)Math.PI;
	private static float[] gene = new float[1];
	
    public static void main( String[] args ) {
    	Random rand = new Random();
    	for(int i=0;i<1;i++) {
    		gene[0]  = rand.nextFloat();
	    	OdinSimulationWheelTest simulation = new OdinSimulationWheelTest();
	    	simulation.runSimulation(null,true,gene);
    	}
    }
    
	public void runSimulation(WorldDescription world, boolean startPaused, final float[] gene) {
        PhysicsLogger.setDefaultLoggingLevel();
        final PhysicsSimulation simulation = PhysicsFactory.createSimulator();
        
        simulation.setRobot(new OdinMuscle(){
        	public Controller createController() {
        		return new OdinSampleWheelController("OdinMuscle", gene);
        	}},"OdinMuscle");
        simulation.setRobot(new OdinWheel(){
        	public Controller createController() {
        		return new OdinSampleWheelController("OdinWheel", gene);
        	}},"OdinWheel");
        simulation.setRobot(new OdinHinge(){
        	public Controller createController() {
        		return new OdinSampleWheelController("OdinHinge", gene);
        	}},"OdinHinge");
        
        simulation.setRobot(new OdinBattery(){
        	public Controller createController() {
        		return new OdinSampleWheelController("OdinBattery", gene);
        	}},"OdinBattery");
        
        simulation.setRobot(new OdinBall(){
        	public Controller createController() {
        		return new OdinSampleWheelController("OdinBall", gene);
        	}},"OdinBall");
        
        if(world==null) world = createWorld();
        simulation.setWorld(world);
        simulation.setPause(startPaused);
        simulation.start();
    }
    /**
     * Create a world description for our simulation
     * @return the world description
     */
    private static WorldDescription createWorld() {
    	WorldDescription world = new WorldDescription();
        world.setPlaneSize(1);
        ArrayList<WorldDescription.ModulePosition> ballPos = new ArrayList<WorldDescription.ModulePosition>();
        ArrayList<WorldDescription.ModulePosition> modulePos = new ArrayList<WorldDescription.ModulePosition>();
        //printConnectorPos();
        int index=0;
        int nBalls=3, xMax=3, yMax=2,zMax=2;
        for(int x=0;x<xMax;x++) {
        	for(int y=0;y<yMax;y++) {
        		for(int z=0;z<zMax;z++) {
        			if((x+y+z)%2==0) {
        				VectorDescription pos = new VectorDescription(x*unit,y*unit-0.48f,z*unit);
        				if(index<nBalls) {
       						ballPos.add(new WorldDescription.ModulePosition(Integer.toString(index),"OdinBall", pos, new RotationDescription(0,0,0)));
        				}
    	        		index++;
        			}
        		}
        	}
        }
        for(int i=0;i<ballPos.size();i++) {
        	for(int j=i+1;j<ballPos.size();j++) {
        		if(isNeighorBalls(ballPos.get(i),ballPos.get(j))) {
        			VectorDescription pos = posFromBalls(ballPos.get(i),ballPos.get(j));
        			RotationDescription rot = rotFromBalls(ballPos.get(i),ballPos.get(j));
        			if(index%2==0) {
        				Quaternion q = new Quaternion();
        				q.fromAngles(pi/2, 0, 0);
        				rot.setRotation(rot.getRotation().mult(q));
        				
        			}
        			if(index%2==0) modulePos.add(new WorldDescription.ModulePosition(Integer.toString(index),"OdinBattery", pos, rot));
        			else modulePos.add(new WorldDescription.ModulePosition(Integer.toString(index),"OdinWheel", pos, rot));
        			index++;
        		}
        	}
        }
		ArrayList<Connection> connections = allConnections(ballPos,modulePos);
		System.out.println("#connection found = "+connections.size());
		world.setModuleConnections(connections);
		System.out.println("#Balls Placed  = "+ballPos.size());
		System.out.println("#Module Placed = "+modulePos.size());
		modulePos.addAll(ballPos);
        world.setModulePositions(modulePos);
        System.out.println("#Total         = "+modulePos.size());
        return world;
    }
    private static ArrayList<Connection> allConnections(ArrayList<ModulePosition> ballPos, ArrayList<ModulePosition> modulePos) {
    	ArrayList<Connection> connections = new ArrayList<Connection>();
    	for(int i=0;i<ballPos.size();i++) {
    		for(int j=0;j<modulePos.size();j++) {
    			if(isConnectable(ballPos.get(i), modulePos.get(j))) {
    				connections.add(new Connection(ballPos.get(i).getName(),modulePos.get(j).getName()));
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
		System.out.println("("+(x1-x2)+","+(y1-y2)+","+(z1-z2)+")");
    	return new RotationDescription(0,0,0);
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
		return null;
	}
}
