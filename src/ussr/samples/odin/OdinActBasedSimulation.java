/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.odin;

import java.util.ArrayList;

import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.robotbuildingblocks.ModuleConnection;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.samples.GenericSimulation;

/**
 * Simple Odin simulation test setup
 * 
 * @author david
 *
 */
public class OdinActBasedSimulation extends GenericSimulation {
	
    public static void main( String[] args ) {
    	new OdinActBasedSimulation().runSimulation(null,true);
    }
    public void runSimulation(WorldDescription world, boolean startPaused) {
        PhysicsLogger.setDefaultLoggingLevel();
        final PhysicsSimulation simulation = PhysicsFactory.createSimulator();
        PhysicsParameters.get().setResolutionFactor(1); // Needed for *really* large Odin structures 
        
        simulation.setRobot(new OdinMuscle(){
        	public Controller createController() {
        		return new OdinActBasedController("OdinMuscle");
        	}},"OdinMuscle");
        
        simulation.setRobot(new OdinBall(){
        	public Controller createController() {
        		return new OdinActBasedController("OdinBall");
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
        //printConnectorPos();
        int index=0;
        //int nBalls=0,xMax=0, yMax=0,zMax=0; modulePos.add(new WorldDescription.ModulePosition("0","OdinMuscle", new VectorDescription(0,0,0), new RotationDescription(0,0,0)));
        //int nBalls=2, xMax=1, yMax=2,zMax=2;
       // int nBalls=3, xMax=3, yMax=2,zMax=2;
       // int nBalls=4, xMax=3, yMax=2,zMax=2;
       //int nBalls=8, xMax=3, yMax=2,zMax=2;
       //int nBalls=14, xMax=3, yMax=3,zMax=3;
        //int nBalls=20, xMax=4, yMax=4,zMax=4;
        int nBalls=250, xMax=8, yMax=8,zMax=8; // Runs on Ulrik's machine but structure falls apart; nBalls=300 causes some weird overflow
        ArrayList<ModulePosition> modulePos = builder.buildDenseBlob(nBalls,xMax,yMax,zMax);
        world.setModulePositions(modulePos);
        world.setModuleConnections(builder.allConnections());
        builder.report(System.out);
        return world;
    }

	@Override
	protected Robot getRobot() {
		return Robot.NO_DEFAULT;
	}
}
