package ussr.builder;

import ussr.description.Robot;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.GenericSimulation;
import ussr.samples.atron.ATRON;
import ussr.samples.mtran.MTRAN;
import ussr.samples.odin.modules.OdinBall;
import ussr.samples.odin.modules.OdinBattery;
import ussr.samples.odin.modules.OdinHinge;
import ussr.samples.odin.modules.OdinMuscle;
import ussr.samples.odin.modules.OdinSpring;
import ussr.samples.odin.modules.OdinTube;
import ussr.samples.odin.modules.OdinWheel;

public class BuilderMultiRobotSimulation extends GenericSimulation {

	@Override
	protected Robot getRobot() {		
				return null;		
	}
	
	 public static void main( String[] args ) {
	        GenericSimulation.setConnectorsAreActive(true);
	        BuilderMultiRobotSimulation simulation = new BuilderMultiRobotSimulation();	        
	        PhysicsParameters.get().setRealisticCollision(true);
	        PhysicsParameters.get().setPhysicsSimulationStepSize(0.0035f);
	        PhysicsParameters.get().setResolutionFactor(1);
	        simulation.runSimulation(null,true);
	    }
	 
	 public void runSimulation(WorldDescription world, boolean startPaused) {
	        PhysicsLogger.setDefaultLoggingLevel();
	        final PhysicsSimulation simulation = PhysicsFactory.createSimulator();
	        
	        simulation.setRobot(new OdinMuscle(){
	            public Controller createController() {
	                return new OdinControllerDefault() {
	                    public void activate() {
	                        delay(10000);
	                        super.activate();
	                    }
	                };
	            }},"OdinMuscle");
	        simulation.setRobot(new OdinWheel(){
	            public Controller createController() {
	                return new OdinControllerDefault();
	            }},"OdinWheel");
	        simulation.setRobot(new OdinHinge(){
	            public Controller createController() {
	                return new OdinControllerDefault();
	            }},"OdinHinge");
	        
	        simulation.setRobot(new OdinBattery(){
	            public Controller createController() {
	                return new OdinControllerDefault();
	            }},"OdinBattery");
	        
	        simulation.setRobot(new OdinBall(){
	            public Controller createController() {
	                return new OdinControllerDefault();
	            }},"OdinBall");
	        
	        simulation.setRobot(new OdinSpring(){
	        	public Controller createController() {
	        		return new OdinControllerDefault();
	        	}},"OdinSpring");
	        
	        simulation.setRobot(new OdinTube(){
	        	public Controller createController() {
	        		return new OdinControllerDefault();
	        	}},"OdinTube");
	        
	        ATRON atron = new ATRON(){
	            public Controller createController() {
	                return new ATRONControllerDefault(simulation) {
	                    public void activate() {
	                        delay(10000);
	                        super.activate();
	                    }
	                };
	            }
	        };
	        atron.setGentle();
	        simulation.setRobot(atron,"ATRON");

	        simulation.setRobot(new MTRAN(){
	            public Controller createController() {
	                return new MTRANControllerDefault() {
	                  public void activate() {
	                      delay(10000);
	                      super.activate();
	                  }
	                };
	            }},"MTRAN");
	        
	        if(world==null) world = createWorld();
	        simulation.setWorld(world);
	        simulation.setPause(startPaused);

	        /* Start the simulation*/
	        simulation.start();
	    }
	 
	 /**
	     * Create a world description for simulation
	     * @return the world description
	     */
	    private static WorldDescription createWorld() {
	        WorldDescription world = new WorldDescription();	        
	        world.setPlaneSize(250);	       
	        return world;
	    }

}
