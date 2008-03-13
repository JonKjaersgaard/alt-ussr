/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.odin.simulations;

import java.util.ArrayList;

import ussr.description.ModuleConnection;
import ussr.description.ModulePosition;
import ussr.description.Robot;
import ussr.description.RotationDescription;
import ussr.description.VectorDescription;
import ussr.description.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.GenericSimulation;
import ussr.samples.odin.OdinBuilder;
import ussr.samples.odin.modules.OdinBall;
import ussr.samples.odin.modules.OdinMuscle;

/**
 * Simple Odin simulation test setup
 * 
 * @author david
 *
 */
public class OdinSimulation1 extends GenericSimulation {
	
    public static void main( String[] args ) {
    	new OdinSimulation1().runSimulation(null,true);
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
        world.setPlaneTexture(WorldDescription.GRID_TEXTURE);
        OdinBuilder builder = new OdinBuilder();
        //int nBalls=0,xMax=0, yMax=0,zMax=0; = Arrays.asList(new WorldDescription.ModulePosition[] { new WorldDescription.ModulePosition("0","OdinMuscle", new VectorDescription(0,0,0), new RotationDescription(0,0,0))});
        //int nBalls=2, xMax=1, yMax=2,zMax=2;
       // int nBalls=3, xMax=3, yMax=2,zMax=2;
       // int nBalls=4, xMax=3, yMax=2,zMax=2;
       //int nBalls=8, xMax=3, yMax=2,zMax=2;
       //int nBalls=14, xMax=3, yMax=3,zMax=3;
        int nBalls=20, xMax=4, yMax=4,zMax=4;
        //int nBalls=80, xMax=5, yMax=5,zMax=5; // Max on Ulrik's machine
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
