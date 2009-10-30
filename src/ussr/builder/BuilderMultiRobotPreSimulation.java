package ussr.builder;

import java.util.Hashtable;
import java.util.Map;

import ussr.builder.saveLoadXML.PreSimulationXMLSerializer;
import ussr.builder.saveLoadXML.SaveLoadXMLFileTemplate;
import ussr.builder.saveLoadXML.TagsUsed;
import ussr.builder.saveLoadXML.UssrXmlFileTypes;
import ussr.description.Robot;
import ussr.description.setup.WorldDescription;
import ussr.description.setup.WorldDescription.CameraPosition;
import ussr.description.setup.WorldDescription.TextureDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.physics.PhysicsParameters.Material;
import ussr.samples.GenericModuleConnectorHelper;
import ussr.samples.GenericSimulation;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;
import ussr.samples.ckbot.CKBotStandard;
import ussr.samples.mtran.MTRAN;
import ussr.samples.odin.modules.OdinBall;
import ussr.samples.odin.modules.OdinBattery;
import ussr.samples.odin.modules.OdinHinge;
import ussr.samples.odin.modules.OdinMuscle;
import ussr.samples.odin.modules.OdinSpring;
import ussr.samples.odin.modules.OdinTube;
import ussr.samples.odin.modules.OdinWheel;

/**
 * The main responsibility of this class is to start default simulation
 * for ATRON,MTRAN and Odin modular robots.
 * @author Konstantinas
 */
public class BuilderMultiRobotPreSimulation extends GenericSimulation {


	/**
	 * The obstacle generator for generating line of box obstacles
	 */
	protected static ObstacleGenerator.ObstacleType obstacleType = ObstacleGenerator.ObstacleType.LINE;


	/**
	 * Returns the robot in the simulation.
	 */
	@Override
	protected Robot getRobot() {		
		return null;
	}

	/*For saving XML file*/ 
	public static final  String robotMorphologyFile = "samples/simulations/atron/morphologies/simpleVehicleMorphology.xml";
	//public static final  String robotMorphologyFile = "samples/simulations/atron/morphologies/simulation1Morphology.xml";
   // public static final  String robotMorphologyFile = "samples/simulations/atron/morphologies/snakeSimulationMorphology.xml";
	
	
	
	/*UNCOMMENT ONE OF US TO LOAD SIMULATION FROM XML FILE*/
	//private  static final String loadableSimulationFile ="samples/simulations/atron/simpleVehicleSim.xml";
	//private  static final String loadableSimulationFile ="samples/simulations/atron/simulation1.xml";
	private  static final String loadableSimulationFile ="samples/simulations/atron/snakeSimulation.xml";
	
	private static Map<TagsUsed,String> simulationWorldDescription;
	private static Map<TagsUsed,String> simulationPhysicsParameters; 
	
	/**
	 * Starts multi-robot simulation for ATRON,MTRAN and Odin.
	 * @param args, passed arguments.
	 */
	public static void main( String[] args ) {
	/*	 Set up home 
		String home = System.getenv("USSRHOME"); 
		if(home!=null) {
			System.out.println("Setting home to "+home);
			PhysicsFactory.getOptions().setResourceDirectory(home+"/");
		}	*/	
		runSimulationFromXMLFile();	
	}
	private static void runSimulationFromXMLFile(){

		/*Activate connectors*/
		BuilderMultiRobotPreSimulation simulation = new BuilderMultiRobotPreSimulation();
				
		/*Load Simulation Configuration file*/
		SaveLoadXMLFileTemplate xmlLoaderSimulation = new PreSimulationXMLSerializer(new PhysicsParameters());
		xmlLoaderSimulation.loadXMLfile(UssrXmlFileTypes.SIMULATION, loadableSimulationFile);
        
		/*Get values from XML file*/
		simulationWorldDescription = xmlLoaderSimulation.getSimulationDescriptionValues();
        simulationPhysicsParameters = xmlLoaderSimulation.getSimulationPhysicsValues();
		
        /*Get values*/
        float worldDampingLinearVelocity = Float.parseFloat(simulationPhysicsParameters.get(TagsUsed.WORLD_DAMPING_LINEAR_VELOCITY)); 
        float worldDampingAngularVelocity = Float.parseFloat(simulationPhysicsParameters.get(TagsUsed.WORLD_DAMPING_ANGULAR_VELOCITY));
        float physicsSimulationStepSize = Float.parseFloat(simulationPhysicsParameters.get(TagsUsed.PHYSICS_SIMULATION_STEP_SIZE));
        boolean realisticCollision = Boolean.parseBoolean(simulationPhysicsParameters.get(TagsUsed.REALISTIC_COLLISION));
        float gravity = Float.parseFloat(simulationPhysicsParameters.get(TagsUsed.GRAVITY));
        String material = simulationPhysicsParameters.get(TagsUsed.PLANE_MATERIAL);
        boolean maintainRotationalJointPositions = Boolean.parseBoolean(simulationPhysicsParameters.get(TagsUsed.MAINTAIN_ROTATIONAL_JOINT_POSITIONS));
        float constraintForceMix = Float.parseFloat(simulationPhysicsParameters.get(TagsUsed.CONSTRAINT_FORCE_MIX));
        float errorReductionParameter = Float.parseFloat(simulationPhysicsParameters.get(TagsUsed.ERROR_REDUCTION_PARAMETER));	
        int resolutionFactor = Integer.parseInt(simulationPhysicsParameters.get(TagsUsed.RESOLUTION_FACTOR));
        boolean useModuleEventQueue = Boolean.parseBoolean(simulationPhysicsParameters.get(TagsUsed.USE_MOUSE_EVENT_QUEUE));
        boolean sync = Boolean.parseBoolean(simulationPhysicsParameters.get(TagsUsed.SYNC_WITH_CONTROLLERS));
        float physicsSimulationControllerStepFactor = Float.parseFloat(simulationPhysicsParameters.get(TagsUsed.PHYSICS_SIMULATION_CONTROLLER_STEP_FACTOR));
        
        /*Set values*/
        PhysicsParameters.get().setWorldDampingLinearVelocity(worldDampingLinearVelocity);
        PhysicsParameters.get().setWorldDampingAngularVelocity(worldDampingAngularVelocity);
        PhysicsParameters.get().setPhysicsSimulationStepSize(physicsSimulationStepSize);
        PhysicsParameters.get().setRealisticCollision(realisticCollision);
        PhysicsParameters.get().setGravity(gravity);
        PhysicsParameters.get().setPlaneMaterial(Material.valueOf(material));
        PhysicsParameters.get().setMaintainRotationalJointPositions(maintainRotationalJointPositions);
//       /*Not supported yet*/ HAS_MECHANICAL_CONNECTOR_SPRINGINESS,
//       /*Not supported yet*/ MECHANICAL_CONNECTOR_CONSTANT,
//       /*Not supported yet*/MECHANICAL_CONNECTOR_DAMPING,
        PhysicsParameters.get().setConstraintForceMix(constraintForceMix);
        PhysicsParameters.get().setErrorReductionParameter(errorReductionParameter);
        PhysicsParameters.get().setResolutionFactor(resolutionFactor);
        PhysicsParameters.get().setUseModuleEventQueue(useModuleEventQueue);
        PhysicsParameters.get().setSyncWithControllers(sync);
        PhysicsParameters.get().setPhysicsSimulationControllerStepFactor(physicsSimulationControllerStepFactor);
       	
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

		/*Set ATRON robot to simulation and assign default controller to it*/
		ATRON atron = new ATRON(){
			public Controller createController() {
				return new ATRONControllerDefault() {
					public void activate() {
						//delay(10000);
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
				return new OdinControllerDefault() {
					public void activate() {
						//delay(10000);
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
		simulation.setRobot(new CKBotStandard(){
			public Controller createController() {
				return new CKBotControllerDefault();
			}},"CKBotStandard");

		
		
		/*Create the world description of simulation and set it to simulation*/
		if(world==null) world = createWorld();
		simulation.setWorld(world);
		/*Simulation should be in paused state (static)in the beginning*/
		simulation.setPause(startPaused);

		/*Load Robot Morphology file*/
		SaveLoadXMLFileTemplate xmlLoaderRodbot = new PreSimulationXMLSerializer(world);
		xmlLoaderRodbot.loadXMLfile(UssrXmlFileTypes.ROBOT, simulationWorldDescription.get(TagsUsed.MORPHOLOGY_LOCATION));

		world.setModuleConnections(new GenericModuleConnectorHelper().computeAllConnections(world.getModulePositions()));

		/* Start the simulation*/
		simulation.start();
	}

	/**
	 * Create  world description for simulation.
	 * @return world, the world description.
	 */
	private static WorldDescription createWorld() {
		/*Extract values*/
		int planeSize =Integer.parseInt(simulationWorldDescription.get(TagsUsed.PLANE_SIZE)) ;
	    String textureFile = simulationWorldDescription.get(TagsUsed.PLANE_TEXTURE);
	    System.out.println("tEXTURE:"+textureFile );
	    
	    /*Local container for all textures*/
	    //FIXME SHOULD I COUPLE YOU WITH WORLD_DESCRIPTION?
	    Map<String,TextureDescription> containerTextureDesc = new Hashtable<String, TextureDescription>(); 
	     
	    containerTextureDesc.put(WorldDescription.GRASS_TEXTURE.getFileName(), WorldDescription.GRASS_TEXTURE);
	    containerTextureDesc.put(WorldDescription.GREY_GRID_TEXTURE.getFileName(), WorldDescription.GREY_GRID_TEXTURE);
	    containerTextureDesc.put(WorldDescription.MARS_TEXTURE.getFileName(), WorldDescription.MARS_TEXTURE);
	    containerTextureDesc.put(WorldDescription.WHITE_GRID_TEXTURE.getFileName(), WorldDescription.WHITE_GRID_TEXTURE);
	    containerTextureDesc.put(WorldDescription.WHITE_TEXTURE.getFileName(), WorldDescription.WHITE_TEXTURE);
	    
	    TextureDescription textureDescription = containerTextureDesc.get(textureFile);
	    
		String cameraPosition = simulationWorldDescription.get(TagsUsed.CAMERA_POSITION);
		boolean theWorldIsFlat = Boolean.parseBoolean(simulationWorldDescription.get(TagsUsed.THE_WORLD_IS_FLAT));
		boolean hasClouds = Boolean.parseBoolean(simulationWorldDescription.get(TagsUsed.HAS_BACKGROUND_SCENERY));
		boolean heavyObstacles = Boolean.parseBoolean(simulationWorldDescription.get(TagsUsed.HAS_HEAVY_OBSTACLES));
		boolean isActive = Boolean.parseBoolean(simulationWorldDescription.get(TagsUsed.IS_FRAME_GRABBING_ACTIVE));
		//boolean heavyObstacles = Boolean.parseBoolean(simulationDescription.get(TagsUsed.HAS_HEAVY_OBSTACLES));
		
		
		/*Assign values to world*/
		WorldDescription world = new WorldDescription();	        
		world.setPlaneSize(planeSize);
		world.setPlaneTexture(textureDescription);
		world.setCameraPosition(CameraPosition.valueOf(cameraPosition));
		world.setFlatWorld(theWorldIsFlat);
		world.setHasBackgroundScenery(hasClouds);
		world.setHeavyObstacles(heavyObstacles);
		world.setIsFrameGrabbingActive(isActive);
		//TODO world.setBigObstacles(bigObstacles);
		
		//ObstacleGenerator generator = new ObstacleGenerator();
		//generator.obstacalize(obstacleType, world); // activate to add obstacles
		 //world.setPlaneTexture(textureDescription);
		 
		return world;
	}
}
