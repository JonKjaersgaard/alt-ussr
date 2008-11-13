/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.odin.simulations;

import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.GenericSimulation;
import ussr.samples.odin.OdinBuilder;
import ussr.samples.odin.OdinBuilder.ModuleDesignator;
import ussr.samples.odin.modules.OdinBall;
import ussr.samples.odin.modules.OdinMuscle;
import ussr.samples.odin.modules.OdinTube;

/**
 * Simple Odin simulation test setup
 * 
 * @author david
 *
 */
public class OdinSimulationTubeTest extends GenericSimulation {
	
    public static void main( String[] args ) {
    	new OdinSimulationTubeTest().runSimulation(null,true);
    }
    
    public void runSimulation(WorldDescription world, boolean startPaused) {
        PhysicsLogger.setDefaultLoggingLevel();
        final PhysicsSimulation simulation = PhysicsFactory.createSimulator();
        PhysicsParameters.get().setResolutionFactor(2); // Needed for large Odin structures 
        
        simulation.setRobot(new OdinMuscle(){
        	public Controller createController() {
        		return new OdinSampleController1("OdinMuscle");
        	}},"OdinMuscle");
      
        simulation.setRobot(new OdinTube(){
        	public Controller createController() {
        		return new OdinSampleController1("OdinTube");
        	}},"OdinTube");
        
        simulation.setRobot(new OdinBall(){
        	public Controller createController() {
        		return new OdinSampleController1("OdinBall");
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
        world.setPlaneSize(5);
        world.setHasBackgroundScenery(false);
        world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
        OdinBuilder builder = new OdinBuilder();
        int index = 0;
        builder.addBall(0, 0, 0, index); index++;
        builder.addBall(1, 1, 0, index); index++;
        builder.addModule(0, 1, "OdinMuscle", index);index++;
        world.setModulePositions(builder.getModulePositions());
        world.setModuleConnections(builder.allConnections());
        System.out.println("#Total         = "+builder.getModulePositions().size());
        return world;
    }

	@Override
	protected Robot getRobot() {
		return Robot.NO_DEFAULT;
	}
}
