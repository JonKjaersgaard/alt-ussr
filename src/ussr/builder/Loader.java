package ussr.builder;

import java.util.ArrayList;
import java.util.List;

import ussr.builder.helpers.ControllerFactory;
import ussr.builder.saveLoadXML.PreSimulationXMLSerializer;
import ussr.builder.saveLoadXML.SaveLoadXMLBuilderTemplate;
import ussr.builder.saveLoadXML.UssrXmlFileTypes;
import ussr.description.Robot;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.GenericModuleConnectorHelper;
import ussr.samples.GenericSimulation;
import ussr.samples.ObstacleGenerator;
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
 *Input format: samples/atron/car.xml ussr.samples.atron.simulations.ATRONCarController1.
 * @author Konstantinas
 */
public class Loader extends GenericSimulation {
    private static String fileName;
    private static List<String> controllerNames = new ArrayList<String>();
	
	/**
	 * Returns the robot in the simulation.
	 */
	@Override
	protected Robot getRobot() {		
		return null;
	}

	/**
	 * Starts multi-robot simulation for ATRON,MTRAN and Odin.
	 * @param args, passed arguments.
	 */
	public static void main( String[] args ) {
	    if(args.length!=2) throw new Error("Usage: provide robot definition xml file and name of the Java controller class(es)");
	    fileName=args[0];
	    for(int i=1; i<args.length; i++)
	        controllerNames.add(args[i]);
	    runSimulationFromFile();
	}
	
	public static void runSimulationFromFile() {
	    /* Set up home */
	    String home = System.getenv("USSRHOME"); 
	    if(home!=null) {
	        System.out.println("Setting home to "+home);
	        PhysicsFactory.getOptions().setResourceDirectory(home+"/");
	    }
		/*Activate connectors*/
		GenericSimulation.setConnectorsAreActive(true);
		Loader simulation = new Loader();
		/* Specify realistic collision*/
		PhysicsParameters.get().setRealisticCollision(true);
		/* Specify simulation step*/
		PhysicsParameters.get().setPhysicsSimulationStepSize(0.0035f);
		/* Specify smallest resolution factor so that simulation can handle more modules.
		 * This is done in order to "in a way" avoid stack overflow */
		PhysicsParameters.get().setResolutionFactor(1);
		simulation.runSimulation(null,true);
	}
	/**
	 * Define the simulation to run.
	 * @param world, the world description.
	 * @param startPaused, the state of simulation. True if paused and false if running.
	 */
	public void runSimulation(WorldDescription world, boolean startPaused) {
		PhysicsLogger.setDefaultLoggingLevel();
		/* Create the simulation*/
		final PhysicsSimulation simulation = PhysicsFactory.createSimulator();
		
		/**
		 * Find the controller(s)
		 */
		final ControllerFactory controllerFactory = new ControllerFactory(controllerNames);
		
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
                return getDefaultOdinController(controllerFactory);
			}},"OdinMuscle");
		simulation.setRobot(new OdinWheel(){
			public Controller createController() {
				return getDefaultOdinController(controllerFactory);
			}},"OdinWheel");
		simulation.setRobot(new OdinHinge(){
			public Controller createController() {
				return getDefaultOdinController(controllerFactory);
			}},"OdinHinge");

		simulation.setRobot(new OdinBattery(){
			public Controller createController() {
				return getDefaultOdinController(controllerFactory);
			}},"OdinBattery");

		simulation.setRobot(new OdinBall(){
			public Controller createController() {
				return getDefaultOdinController(controllerFactory);
			}},"OdinBall");

		simulation.setRobot(new OdinSpring(){
			public Controller createController() {
				return getDefaultOdinController(controllerFactory);
			}},"OdinSpring");

		simulation.setRobot(new OdinTube(){
			public Controller createController() {
				return getDefaultOdinController(controllerFactory);
			}},"OdinTube");	
		 simulation.setRobot(new CKBotStandard(){
	        	public Controller createController() {
	        	    if(controllerFactory.has(CKBotController.class))
	                    return controllerFactory.create(CKBotController.class);
	        	    return new CKBotControllerDefault();
	        	}},"CKBotStandard");
		
        /*Create the world description of simulation and set it to simulation*/
		if(world==null) world = createWorld();
		simulation.setWorld(world);
		/*Simulation should be in paused state (static)in the beginning*/
		simulation.setPause(startPaused);
		/* Load the robot */
		SaveLoadXMLBuilderTemplate xmlLoader = new PreSimulationXMLSerializer(world);
		xmlLoader.loadXMLfile(UssrXmlFileTypes.ROBOT,fileName);
		/* Connect modules */
		world.setModuleConnections(new GenericModuleConnectorHelper().computeAllConnections(world.getModulePositions()));
		/* Start the simulation*/
		simulation.start();
	}

	private Controller getDefaultOdinController(final ControllerFactory controllerFactory) {
        if(controllerFactory.has(OdinController.class))
            return controllerFactory.create(OdinController.class);
        return new OdinControllerDefault();
    }

    /**
	 * Create a world description for simulation.
	 * @return world, the world description.
	 */
	private static WorldDescription createWorld() {
		WorldDescription world = new WorldDescription();	        
		world.setPlaneSize(100);		
	  	//ObstacleGenerator generator = new ObstacleGenerator();
	    //generator.obstacalize(obstacleType, world); // activate to add obstacles
	    // world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
		return world;
	}
}
