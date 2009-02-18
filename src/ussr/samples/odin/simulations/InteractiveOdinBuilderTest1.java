/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.odin.simulations;

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
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.GenericSimulation;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.simulations.ATRONSimpleVehicleController1;
import ussr.samples.mtran.MTRAN;
import ussr.samples.mtran.MTRANSampleController2;
import ussr.samples.odin.OdinBuilder;
import ussr.samples.odin.modules.OdinBall;
import ussr.samples.odin.modules.OdinBattery;
import ussr.samples.odin.modules.OdinHinge;
import ussr.samples.odin.modules.OdinMuscle;
import ussr.samples.odin.modules.OdinSpring;
import ussr.samples.odin.modules.OdinTube;
import ussr.samples.odin.modules.OdinWheel;

/**
 * Simple Odin simulation test setup
 * 
 * @author david
 * @author Konstantinas(modified for the project called "Quick Prototyping of Simulation Scenarios")
 * Previous name of the class was OdinSimulation1.java
 */
//TODO CONSIDER IMPLEMENTING SEPARATE FILE WITH CREATION OF ALL POSSIBLE MR ROBOTS AND EMPTY CONTROLLERS  
public class InteractiveOdinBuilderTest1 extends GenericSimulation {
	
    public static void main( String[] args ) {
    	new InteractiveOdinBuilderTest1().runSimulation(null,true);
    }
    
    public void runSimulation(WorldDescription world, boolean startPaused) {
        PhysicsLogger.setDefaultLoggingLevel();
        final PhysicsSimulation simulation = PhysicsFactory.createSimulator();
        PhysicsParameters.get().setResolutionFactor(2); // Needed for large Odin structures 
        
        simulation.setRobot(new OdinMuscle(){
        	public Controller createController() {
        		return new OdinSampleController1("OdinMuscle");
        	}},"OdinMuscle");        
        simulation.setRobot(new OdinBall(){
        	public Controller createController() {
        		return new OdinSampleController1("OdinBall");
        	}},"OdinBall");
        
//FIXME CONSIDER IMPLEMENTING SEPARATE FILE WITH CREATION OF ALL POSSIBLE MR ROBOTS AND EMPTY CONTROLLERS
        /*Added so it is possible to load from XML file all possible types of modular robots*/
        simulation.setRobot(new OdinHinge(){
        	public Controller createController() {
        		return new OdinSampleController1("OdinHinge");
        	}},"OdinHinge");
        
        simulation.setRobot(new OdinBattery(){
        	public Controller createController() {
        		return new OdinSampleController1("OdinBattery");
        	}},"OdinBattery");
        
        simulation.setRobot(new OdinSpring(){
        	public Controller createController() {
        		return new OdinSampleController1("OdinSpring");
        	}},"OdinSpring");
        simulation.setRobot(new OdinTube(){
        	public Controller createController() {
        		return new OdinSampleController1("OdinTube");
        	}},"OdinTube");
        simulation.setRobot(new OdinWheel(){
        	public Controller createController() {
        		return new OdinSampleController1("OdinWheel");
        	}},"OdinWheel");        

        simulation.setRobot(new MTRAN(){
        	public Controller createController() {
        		return new MTRANSampleController2("MTRAN");
        	}},"MTRAN");
        
        simulation.setRobot(new ATRON(){
        	public Controller createController() {
        		return new ATRONSimpleVehicleController1();
        	}},"default");        
        
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
        world.setPlaneSize(5);
        world.setHasBackgroundScenery(false);
        world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
        OdinBuilder builder = new OdinBuilder();     
        int nBalls=1, xMax=4, yMax=4,zMax=4;       
        ArrayList<ModulePosition> modulePos = builder.buildDenseBlob(nBalls,xMax,yMax,zMax);
        world.setModulePositions(modulePos);
        world.setModuleConnections(builder.allConnections());
        System.out.println("#Total         = "+modulePos.size());
        return world;
    }

	@Override
	protected Robot getRobot() {
		return Robot.NO_DEFAULT;
	}
}
