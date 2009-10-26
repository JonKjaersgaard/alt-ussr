package ussr.samples.odin.natives;

import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.robot.RobotDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.GenericSimulation;
import ussr.samples.odin.OdinBuilder;
import ussr.samples.odin.modules.Odin;
import ussr.samples.odin.modules.OdinBall;
import ussr.samples.odin.modules.OdinMuscle;
import ussr.samples.odin.modules.OdinSSRlinearActuator;
import ussr.samples.odin.simulations.OdinSandboxController;
import ussr.visualization.VisualizationParameters;

public class OdinNativeTinyOSSimulation extends GenericSimulation {

	public static void main(String argv[]) {
		
		VisualizationParameters.get().setUseDataDumper(true);
		
		//PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
		PhysicsParameters.get().setRealisticCollision(true);
		PhysicsParameters.get().setUseModuleEventQueue(true);
		PhysicsParameters.get().setSyncWithControllers(true);
		PhysicsParameters.get().setPhysicsSimulationStepSize(0.001f);
		PhysicsParameters.get().setPhysicsSimulationControllerStepFactor(0.01f);
		new OdinNativeTinyOSSimulation().runSimulation(null,true);
	}

    public void runSimulation(WorldDescription world, boolean startPaused) {
        PhysicsLogger.setDefaultLoggingLevel();
        final PhysicsSimulation simulation = PhysicsFactory.createSimulator();
        PhysicsParameters.get().setResolutionFactor(2); // Needed for large Odin structures 
        
        simulation.setRobot(new OdinSSRlinearActuator(){
        	public Controller createController() {
        		//return new OdinSandboxController("OdinSSRlinearActuator");
        		return new OdinNativeTinyOSController("OdinSSRlinearActuator", "odinTinyos");
        	}},"OdinSSRlinearActuator");
        
        simulation.setRobot(new OdinBall(){
        	public Controller createController() {
        		//return new OdinSandboxController("OdinBall");
        		return new OdinNativeTinyOSController("OdinBall", "odinTinyos");
        	}},"OdinBall");
        
        simulation.setRobot(new OdinMuscle(){
        	public Controller createController() {
        		//return new OdinSandboxController("OdinMuscle");
        		return new OdinNativeTinyOSController("OdinMuscle", "odinTinyos");
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
