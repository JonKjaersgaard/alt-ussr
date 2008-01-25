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
import ussr.robotbuildingblocks.ModuleConnection;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
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
public class OdinSimulation3 extends GenericSimulation implements PhysicsObserver {
	private static float unit = (float)Math.sqrt((0.18f*0.18f)/2);
	private static float pi = (float)Math.PI;
	private static float[] gene = new float[1];
	
    public static void main( String[] args ) {
    	Random rand = new Random();
    	for(int i=0;i<1;i++) {
    		gene[0]  = rand.nextFloat();
	    	OdinSimulation3 simulation = new OdinSimulation3();
	    	simulation.runSimulation(null,true,gene);
    	}
    }
	float oldDist = 0;
    float oldPrintTime=0;
    public void physicsTimeStepHook(PhysicsSimulation simulation) {
    	//simulation.setGravity(0.0f);
    	Vector3f cm = new Vector3f();
       	for(int i=0;i<simulation.getModules().size();i++) {
       		cm = cm.addLocal((Vector3f)simulation.getHelper().getModulePos(simulation.getModules().get(i)).get());
       	}
       	//cm = cm.multLocal(1.0f/modules.size());
       	//System.out.println("{"+simulation.getTime()+", "+cm.x+", "+cm.y+", "+cm.z+"},");
       	
       	if(oldPrintTime+10<simulation.getTime()) {
       		/*int[] rc = OdinSampleController3.roleCount;
       		System.out.print("Role: [");
       		for(int i=0;i<rc.length-1;i++) {
       			System.out.print(rc[i]+", ");
       			OdinSampleController3.roleCount[i]=0;
       		}
       		System.out.println(rc[rc.length-1]+"]");
       		OdinSampleController3.roleCount[rc.length-1]=0;*/
       		
           	System.out.println("RoboReward = "+OdinSampleController3.roboReward+"("+cm.x+", "+cm.y+", "+cm.z+")");
           	OdinSampleController3.roboReward = 0;
           	
       		/*float dist = cm.distance(new Vector3f(10,0,0));
           	System.out.println("Fitness = "+(oldDist-dist));
           	oldDist = dist;*/
           	
       		oldPrintTime = simulation.getTime();
       	}
       	if(simulation.getTime()>500) {
   			simulation.stop();
   			//System.out.println("Fitness = "+((cm.x-0)*(cm.x-0)+(cm.z-0)*(cm.z-0)));
   			System.out.println("Fitness = "+(-cm.distance(new Vector3f(10,0,0))));
   			System.out.println("Gene    = {"+gene[0]+"}");
       	}
    }
    
	public void runSimulation(WorldDescription world, boolean startPaused, final float[] gene) {
        PhysicsLogger.setDefaultLoggingLevel();
        final PhysicsSimulation simulation = PhysicsFactory.createSimulator();
        
        simulation.setRobot(new OdinMuscle(){
        	public Controller createController() {
        		return new OdinSampleController3("OdinMuscle", gene);
        	}},"OdinMuscle");
        simulation.setRobot(new OdinWheel(){
        	public Controller createController() {
        		return new OdinSampleController3("OdinWheel", gene);
        	}},"OdinWheel");
        simulation.setRobot(new OdinHinge(){
        	public Controller createController() {
        		return new OdinSampleController3("OdinHinge", gene);
        	}},"OdinHinge");
        
        simulation.setRobot(new OdinBattery(){
        	public Controller createController() {
        		return new OdinSampleController3("OdinBattery", gene);
        	}},"OdinBattery");
        
        simulation.setRobot(new OdinBall(){
        	public Controller createController() {
        		return new OdinSampleController3("OdinBall", gene);
        	}},"OdinBall");
        
        simulation.setRobot(new ATRON(){
        	public Controller createController() {
        		return new ATRONCarController1();
        	}},"ATRON");

        if(world==null) world = createWorld();
        simulation.setWorld(world);
        simulation.setPause(startPaused);
        simulation.subscribePhysicsTimestep(this);

        // Start
        simulation.start();
    }
    /**
     * Create a world description for our simulation
     * @return the world description
     */
    private static WorldDescription createWorld() {
    	WorldDescription world = new WorldDescription();
        world.setPlaneSize(5);
        ArrayList<ModulePosition> ballPos = new ArrayList<ModulePosition>();
        ArrayList<ModulePosition> modulePos = new ArrayList<ModulePosition>();
        //printConnectorPos();
        int index=0;
        //int nBalls=0,xMax=0, yMax=0,zMax=0; modulePos.add(new WorldDescription.ModulePosition("0","OdinMuscle", new VectorDescription(0,0,0), new RotationDescription(0,0,0)));
        //int nBalls=2, xMax=1, yMax=2,zMax=2;
        //int nBalls=3, xMax=3, yMax=2,zMax=2;
        //int nBalls=5, xMax=5, yMax=5,zMax=5;
        int nBalls=4, xMax=3, yMax=2,zMax=2;
        //int nBalls=10, xMax=3, yMax=2,zMax=3;
        //int nBalls=7, xMax=4, yMax=2,zMax=8;
       //int nBalls=20, xMax=4, yMax=4,zMax=4;
        for(int x=0;x<xMax;x++) {
        	for(int y=0;y<yMax;y++) {
        		for(int z=0;z<zMax;z++) {
        			if((x+y+z)%2==0) {
        				VectorDescription pos = new VectorDescription(x*unit,y*unit-0.48f,z*unit);
        				if(index<nBalls) {
       						ballPos.add(new ModulePosition(Integer.toString(index),"OdinBall", pos, new RotationDescription(0,0,0)));
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
        			modulePos.add(new ModulePosition(Integer.toString(index),"OdinHinge", pos, rot));
        			//if(index%2==0) modulePos.add(new WorldDescription.ModulePosition(Integer.toString(index),"OdinMuscle", pos, rot));
        			//if(index%2==0) modulePos.add(new WorldDescription.ModulePosition(Integer.toString(index),"OdinBattery", pos, rot));
        			//else modulePos.add(new WorldDescription.ModulePosition(Integer.toString(index),"OdinWheel", pos, rot));
        			index++;
        			//System.out.println("Ball "+i+" and ball "+j+" are neighbors");
        		}
        	}
        }
       ArrayList<ModuleConnection> connections = allConnections(ballPos,modulePos);
       System.out.println("#connection found = "+connections.size());
       world.setModuleConnections(connections);
        System.out.println("#Balls Placed  = "+ballPos.size());
        System.out.println("#Module Placed = "+modulePos.size());
        
        /*
        float half = (float)(Math.PI);
    	float quart = (float)(0.5*Math.PI);
    	float eigth = (float)(0.25*Math.PI);
    	
    	float unit = 0.08f;//8 cm between two lattice positions on physical atrons
    	RotationDescription rotation_NS = new RotationDescription(0,0,eigth+quart);//(0,0,eigth);
    	RotationDescription rotation_SN = new RotationDescription(0,half,eigth);
    	RotationDescription rotation_UD = new RotationDescription(quart,eigth,0);
    	RotationDescription rotation_EW = new RotationDescription(new VectorDescription(eigth,0,0),new VectorDescription(0,quart,0));
    	
    	float Yoffset = 0.25f-0.5f;
        
        modulePos.add(new WorldDescription.ModulePosition("wheel4", "ATRON", new VectorDescription(1*unit,-2*unit-Yoffset,-1*unit), rotation_NS));*/
        
       modulePos.addAll(ballPos);
        world.setModulePositions(modulePos);
        System.out.println("#Total         = "+modulePos.size());
        /*world.setModuleConnections(new WorldDescription.Connection[] {
              //  new WorldDescription.Connection("leftleg",4,"middle",6)
                //,new WorldDescription.Connection("rightleg",2,"middle",4)
        });*/
        return world;
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
   // protected Robot getRobot() {
   //     return new OdinMuscle();
   // }
	@Override
	protected Robot getRobot() {
		// TODO Auto-generated method stub
		return null;
	}
}
