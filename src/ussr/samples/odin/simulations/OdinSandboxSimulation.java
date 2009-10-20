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
import ussr.samples.odin.OdinBuilder;
import ussr.samples.odin.modules.OdinBall;
import ussr.samples.odin.modules.OdinMuscle;
import ussr.samples.odin.modules.OdinSSRlinearActuator;

/**
 * Simple Odin simulation test setup
 * 
 * @author david
 *
 */
public class OdinSandboxSimulation extends GenericSimulation {
	
    public static void main( String[] args ) {
    	new OdinSandboxSimulation().runSimulation(null,true);
    }
    
    public void runSimulation(WorldDescription world, boolean startPaused) {
        PhysicsLogger.setDefaultLoggingLevel();
        final PhysicsSimulation simulation = PhysicsFactory.createSimulator();
        PhysicsParameters.get().setResolutionFactor(2); // Needed for large Odin structures 
        
        simulation.setRobot(new OdinSSRlinearActuator(){
        	public Controller createController() {
        		return new OdinSandboxController("OdinSSRlinearActuator");
        	}},"OdinSSRlinearActuator");
        
        simulation.setRobot(new OdinBall(){
        	public Controller createController() {
        		return new OdinSandboxController("OdinBall");
        	}},"OdinBall");
        
        simulation.setRobot(new OdinMuscle(){
        	public Controller createController() {
        		return new OdinSandboxController("OdinMuscle");
        	}},"OdinMuscle");
        
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
        int nBalls=4, xMax=3, yMax=2,zMax=2;
        //ArrayList<ModulePosition> modulePos = builder.buildDenseBlob(nBalls,xMax,yMax,zMax);
        ArrayList<ModulePosition> modulePos = builder.buildDenseBlobSSRlinearActuator(nBalls,xMax,yMax,zMax);
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
