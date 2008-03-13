/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.odin.simulations;

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
public class OdinSimulationWheelTest extends GenericSimulation {
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
        OdinBuilder builder = new OdinBuilder();
        int nBalls=3, xMax=3, yMax=2,zMax=2;
        VectorDescription offset = new VectorDescription(0,-0.46f,0);
        world.setModulePositions(builder.buildWheelBlob(offset, nBalls, xMax, yMax, zMax));
        world.setModuleConnections(builder.allConnections());
        builder.report(System.out);
        return world;
    }
	protected Robot getRobot() {
		return Robot.NO_DEFAULT;
	}
}
