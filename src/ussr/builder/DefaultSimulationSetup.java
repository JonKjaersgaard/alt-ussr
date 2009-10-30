package ussr.builder;

import ussr.builder.helpers.ControllerFactory;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsSimulation;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONController;
import ussr.samples.ckbot.CKBotController;
import ussr.samples.ckbot.CKBotStandard;
import ussr.samples.mtran.MTRAN;
import ussr.samples.mtran.MTRANController;
import ussr.samples.odin.OdinController;
import ussr.samples.odin.modules.OdinBall;
import ussr.samples.odin.modules.OdinBattery;
import ussr.samples.odin.modules.OdinHinge;
import ussr.samples.odin.modules.OdinMuscle;
import ussr.samples.odin.modules.OdinSpring;
import ussr.samples.odin.modules.OdinTube;
import ussr.samples.odin.modules.OdinWheel;

/**
 * This class provides utility methods for setting up "sensible" parameters for simulations
 * @author ups
 *
 */
public class DefaultSimulationSetup {

    public static void setUSSRHome() {
        /* Set up home */
        String home = System.getenv("USSRHOME"); 
        if(home!=null) {
            System.out.println("Setting home to "+home);
            PhysicsFactory.getOptions().setResourceDirectory(home+"/");
        }
    }

    public static void addDefaultRobotSelection(final PhysicsSimulation simulation,
            final ControllerFactory controllerFactory) {
        /*Set ATRON robot to simulation and assign default controller to it*/
    	ATRON atron = new ATRON(){
    		public Controller createController() {
    		    if(controllerFactory.has(ATRONController.class))
    		        return controllerFactory.create(ATRONController.class);
    			return new ATRONControllerDefault() {
    				public void activate() {
    					super.activate();
    				}
    			};
    		}
    	};
    	atron.setGentle();// Currently builder supports only this type of ATRON
    	//atron.setRubberRing();
    	simulation.setRobot(atron,"ATRON");
    	simulation.setRobot(atron,"ATRON rubberRing gentle");
    	simulation.setRobot(atron,"default");
    	
    	/*Set MTRAN robot to simulation and assign default controller to it*/
    	simulation.setRobot(new MTRAN(){
    		public Controller createController() {
                if(controllerFactory.has(MTRANController.class))
                    return controllerFactory.create(MTRANController.class);
    			return new MTRANControllerDefault() {
    				public void activate() {
    					//delay(10000);
    					super.activate();
    				}
    			};
    		}},"MTRAN");
    	
    	/*Set different Odin modules  to simulation and assign default controllers to them */
    	simulation.setRobot(new OdinMuscle(){
    		public Controller createController() {
                return DefaultSimulationSetup.getDefaultOdinController(controllerFactory);
    		}},"OdinMuscle");
    	simulation.setRobot(new OdinWheel(){
    		public Controller createController() {
    			return DefaultSimulationSetup.getDefaultOdinController(controllerFactory);
    		}},"OdinWheel");
    	simulation.setRobot(new OdinHinge(){
    		public Controller createController() {
    			return DefaultSimulationSetup.getDefaultOdinController(controllerFactory);
    		}},"OdinHinge");
    
    	simulation.setRobot(new OdinBattery(){
    		public Controller createController() {
    			return DefaultSimulationSetup.getDefaultOdinController(controllerFactory);
    		}},"OdinBattery");
    
    	simulation.setRobot(new OdinBall(){
    		public Controller createController() {
    			return DefaultSimulationSetup.getDefaultOdinController(controllerFactory);
    		}},"OdinBall");
    
    	simulation.setRobot(new OdinSpring(){
    		public Controller createController() {
    			return DefaultSimulationSetup.getDefaultOdinController(controllerFactory);
    		}},"OdinSpring");
    
    	simulation.setRobot(new OdinTube(){
    		public Controller createController() {
    			return DefaultSimulationSetup.getDefaultOdinController(controllerFactory);
    		}},"OdinTube");	
    	 simulation.setRobot(new CKBotStandard(){
            	public Controller createController() {
            	    if(controllerFactory.has(CKBotController.class))
                        return controllerFactory.create(CKBotController.class);
            	    return new CKBotControllerDefault();
            	}},"CKBotStandard");
    }

    static Controller getDefaultOdinController(final ControllerFactory controllerFactory) {
        if(controllerFactory.has(OdinController.class))
            return controllerFactory.create(OdinController.class);
        return new OdinControllerDefault();
    }

    /**
     * Create a world description for simulation.
     * @return world, the world description.
     */
    static WorldDescription createWorld() {
    	WorldDescription world = new WorldDescription();	        
    	world.setPlaneSize(100);		
      	//ObstacleGenerator generator = new ObstacleGenerator();
        //generator.obstacalize(obstacleType, world); // activate to add obstacles
        // world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
    	return world;
    }

}
