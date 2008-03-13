/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.odin.simulations;

import java.util.ArrayList;
import java.util.Random;

import ussr.description.ModuleConnection;
import ussr.description.ModulePosition;
import ussr.description.Robot;
import ussr.description.RotationDescription;
import ussr.description.VectorDescription;
import ussr.description.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsSimulation;
import ussr.samples.GenericSimulation;
import ussr.samples.odin.OdinBuilder;
import ussr.samples.odin.modules.OdinBall;
import ussr.samples.odin.modules.OdinBattery;
import ussr.samples.odin.modules.OdinHinge;
import ussr.samples.odin.modules.OdinMuscle;
import ussr.samples.odin.modules.OdinWheel;

import com.jme.math.Quaternion;

/**
 * Simple Odin simulation test setup
 * 
 * @author david
 *
 */
public class OdinSimulationHingeTest extends GenericSimulation {
	private static float unit = (float)Math.sqrt((0.18f*0.18f)/2);
	private static float pi = (float)Math.PI;
	private static float[] gene = new float[1];
	
    public static void main( String[] args ) {
    	Random rand = new Random();
    	for(int i=0;i<1;i++) {
    		gene[0]  = rand.nextFloat();
	    	OdinSimulationHingeTest simulation = new OdinSimulationHingeTest();
	    	simulation.runSimulation(null,true,gene);
    	}
    }
    
	public void runSimulation(WorldDescription world, boolean startPaused, final float[] gene) {
        PhysicsLogger.setDefaultLoggingLevel();
        final PhysicsSimulation simulation = PhysicsFactory.createSimulator();
        
        simulation.setRobot(new OdinMuscle(){
        	public Controller createController() {
        		return new OdinSampleHingeController("OdinMuscle", gene);
        	}},"OdinMuscle");
        simulation.setRobot(new OdinWheel(){
        	public Controller createController() {
        		return new OdinSampleHingeController("OdinWheel", gene);
        	}},"OdinWheel");
        simulation.setRobot(new OdinHinge(){
        	public Controller createController() {
        		return new OdinSampleHingeController("OdinHinge", gene);
        	}},"OdinHinge");
        
        simulation.setRobot(new OdinBattery(){
        	public Controller createController() {
        		return new OdinSampleHingeController("OdinBattery", gene);
        	}},"OdinBattery");
        
        simulation.setRobot(new OdinBall(){
        	public Controller createController() {
        		return new OdinSampleHingeController("OdinBall", gene);
        	}},"OdinBall");
        
        if(world==null) world = createWorld();
        simulation.setWorld(world);
        simulation.setPause(startPaused);

        // Start
        simulation.start();
    }
    /**
     * Create a world description for our simulation
     * @return the world description
     */
    private static WorldDescription createWorld() {
    	WorldDescription world = new WorldDescription();
        world.setPlaneSize(3);
        OdinBuilder builder = new OdinBuilder();
		world.setModulePositions(builder.buildHingePyramid());
		world.setModuleConnections(builder.allConnections());
		System.out.println("#Total         = "+builder.getModulePositions().size());
		return world;
    }
    
  	protected Robot getRobot() {
		return Robot.NO_DEFAULT;
	}
}
