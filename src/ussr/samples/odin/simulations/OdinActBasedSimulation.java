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

/**
 * Simple Odin simulation test setup
 * 
 * @author Modular Robots @ MMMI
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
