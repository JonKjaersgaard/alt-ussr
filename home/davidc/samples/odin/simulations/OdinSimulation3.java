package samples.odin.simulations;


import java.util.ArrayList;
import java.util.Random;

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
import ussr.physics.PhysicsSimulation;
import ussr.samples.GenericSimulation;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.simulations.ATRONCarController1;
import ussr.samples.odin.OdinBuilder;
import ussr.samples.odin.modules.OdinBall;
import ussr.samples.odin.modules.OdinBattery;
import ussr.samples.odin.modules.OdinHinge;
import ussr.samples.odin.modules.OdinMuscle;
import ussr.samples.odin.modules.OdinWheel;

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
        OdinBuilder builder = new OdinBuilder();
        //printConnectorPos();
        //int nBalls=0,xMax=0, yMax=0,zMax=0; modulePos.add(new WorldDescription.ModulePosition("0","OdinMuscle", new VectorDescription(0,0,0), new RotationDescription(0,0,0)));
        //int nBalls=2, xMax=1, yMax=2,zMax=2;
        //int nBalls=3, xMax=3, yMax=2,zMax=2;
        //int nBalls=5, xMax=5, yMax=5,zMax=5;
        int nBalls=4, xMax=3, yMax=2,zMax=2;
        //int nBalls=10, xMax=3, yMax=2,zMax=3;
        //int nBalls=7, xMax=4, yMax=2,zMax=8;
       //int nBalls=20, xMax=4, yMax=4,zMax=4;
        world.setModulePositions(builder.buildHingeBlob(nBalls,xMax,yMax,zMax));
        world.setModuleConnections(builder.allConnections());
        builder.report(System.out);
        return world;
    }

    @Override
	protected Robot getRobot() {
        return Robot.NO_DEFAULT;
	}
}
