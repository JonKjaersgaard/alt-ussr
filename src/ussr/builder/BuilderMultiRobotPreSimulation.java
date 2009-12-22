package ussr.builder;

import java.util.Map;

import ussr.builder.enumerations.UssrXmlFileTypes;
import ussr.builder.enumerations.XMLTagsUsed;
import ussr.builder.saveLoadXML.PreSimulationXMLSerializer;
import ussr.builder.saveLoadXML.SaveLoadXMLFileTemplateInter;
import ussr.builder.simulationLoader.SimulationSpecificationConverter;
import ussr.builder.simulationLoader.SimulationSpecification;
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
	private  static final String xmlSimulationFile ="samples/simulations/atron/simpleVehicleSim.xml";
	//private  static final String loadableSimulationFile ="samples/simulations/atron/simulation1.xml";
	//private  static final String loadableSimulationFile ="samples/simulations/atron/snakeSimulation.xml";
	
	private static Map<XMLTagsUsed,String> simulationWorldDescription;
	private static Map<XMLTagsUsed,String> simulationPhysicsParameters; 
	
	/**
	 * Starts multi-robot simulation for ATRON,MTRAN and Odin.
	 * @param args, passed arguments.
	 */
	public static void main( String[] args ) {

	
		runSimulationFromXMLFile();	
	}
	
	private static SimulationSpecificationConverter descriptionConverter ;
	private static SimulationSpecification  simulationSpecification;
	
	private static void runSimulationFromXMLFile(){

		/*Activate connectors*/
		BuilderMultiRobotPreSimulation simulation = new BuilderMultiRobotPreSimulation();
				
		/*Load Simulation Configuration file*/
		SaveLoadXMLFileTemplateInter xmlLoaderSimulation = new PreSimulationXMLSerializer(/*new PhysicsParameters()*/);
		xmlLoaderSimulation.loadXMLfile(UssrXmlFileTypes.SIMULATION, xmlSimulationFile);
		//xmlLoaderSimulation.loadXMLfile(UssrXmlFileTypes.SIMULATION, loadableSimulationFile);
        
		/*Get values from XML file*/
		simulationSpecification = xmlLoaderSimulation.getSimulationSpecification();
		
        /*Converter for converting values from String into corresponding type used in USSR*/
         descriptionConverter =  new SimulationSpecificationConverter(simulationSpecification.getSimWorldDecsriptionValues(),simulationSpecification.getSimPhysicsParameters()); 
        
        /*Get and set values*/
        setPhysicsParatemeters(descriptionConverter);
       	
		simulation.runSimulation(null,true);
	}
	
	private static void setPhysicsParatemeters(SimulationSpecificationConverter descriptionConverter){
	      PhysicsParameters.get().setWorldDampingLinearVelocity(descriptionConverter.convertWorldDamping(true));
	        PhysicsParameters.get().setWorldDampingAngularVelocity(descriptionConverter.convertWorldDamping(false));
	        PhysicsParameters.get().setPhysicsSimulationStepSize(descriptionConverter.convertPhysicsSimulationStepSize());
	        PhysicsParameters.get().setRealisticCollision(descriptionConverter.covertRealisticCollision());
	        PhysicsParameters.get().setGravity(descriptionConverter.covertGravity());
	       // PhysicsParameters.get().setPlaneMaterial(descriptionConverter.covertPlaneMaterial());
	        PhysicsParameters.get().setMaintainRotationalJointPositions(descriptionConverter.convertMaintainRotationalJointPositions());
//	       /*Not supported yet*/ HAS_MECHANICAL_CONNECTOR_SPRINGINESS,
//	       /*Not supported yet*/ MECHANICAL_CONNECTOR_CONSTANT,
//	       /*Not supported yet*/MECHANICAL_CONNECTOR_DAMPING,
	        PhysicsParameters.get().setConstraintForceMix(descriptionConverter.convertConstraintForceMix());
	        PhysicsParameters.get().setErrorReductionParameter(descriptionConverter.convertErrorReductionParameter());
	        PhysicsParameters.get().setResolutionFactor(descriptionConverter.convertResolutionFactor());
	        PhysicsParameters.get().setUseModuleEventQueue(descriptionConverter.convertUseModuleEventQueue());
	        PhysicsParameters.get().setSyncWithControllers(descriptionConverter.convertSyncWithControllers());
	        PhysicsParameters.get().setPhysicsSimulationControllerStepFactor(descriptionConverter.convertPhysicsSimulationControllerStepFactor());
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
		SaveLoadXMLFileTemplateInter xmlLoaderRodbot = new PreSimulationXMLSerializer(world);
		xmlLoaderRodbot.loadXMLfile(UssrXmlFileTypes.ROBOT, simulationWorldDescription.get(XMLTagsUsed.MORPHOLOGY_LOCATION));

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
		//int planeSize =Integer.parseInt(simulationWorldDescription.get(TagsUsed.PLANE_SIZE)) ;
	    //String textureFile = simulationWorldDescription.get(TagsUsed.PLANE_TEXTURE);
	    
	   // System.out.println("tEXTURE:"+textureFile );
	    
	    /*Local container for all textures*/
	    //FIXME SHOULD I COUPLE YOU WITH WORLD_DESCRIPTION?
	  /*  Map<String,TextureDescription> containerTextureDesc = new Hashtable<String, TextureDescription>(); 
	     
	    containerTextureDesc.put(WorldDescription.GRASS_TEXTURE.getFileName(), WorldDescription.GRASS_TEXTURE);
	    containerTextureDesc.put(WorldDescription.GREY_GRID_TEXTURE.getFileName(), WorldDescription.GREY_GRID_TEXTURE);
	    containerTextureDesc.put(WorldDescription.MARS_TEXTURE.getFileName(), WorldDescription.MARS_TEXTURE);
	    containerTextureDesc.put(WorldDescription.WHITE_GRID_TEXTURE.getFileName(), WorldDescription.WHITE_GRID_TEXTURE);
	    containerTextureDesc.put(WorldDescription.WHITE_TEXTURE.getFileName(), WorldDescription.WHITE_TEXTURE);*/
	    
	    //TextureDescription textureDescription = containerTextureDesc.get(textureFile);
	    
		//String cameraPosition = simulationWorldDescription.get(TagsUsed.CAMERA_POSITION);
		//boolean theWorldIsFlat = Boolean.parseBoolean(simulationWorldDescription.get(TagsUsed.THE_WORLD_IS_FLAT));
		//boolean hasClouds = Boolean.parseBoolean(simulationWorldDescription.get(TagsUsed.HAS_BACKGROUND_SCENERY));
		//boolean heavyObstacles = Boolean.parseBoolean(simulationWorldDescription.get(TagsUsed.HAS_HEAVY_OBSTACLES));
		//boolean isActive = Boolean.parseBoolean(simulationWorldDescription.get(TagsUsed.IS_FRAME_GRABBING_ACTIVE));
		//boolean heavyObstacles = Boolean.parseBoolean(simulationDescription.get(TagsUsed.HAS_HEAVY_OBSTACLES));
		
		
		/*Assign values to world*/
		WorldDescription world = new WorldDescription();	        
		world.setPlaneSize(descriptionConverter.convertPlaneSize());
		world.setPlaneTexture(descriptionConverter.covertPlaneTexture());
		world.setCameraPosition(descriptionConverter.convertCameraPosition());
		world.setFlatWorld(descriptionConverter.convertTheWorldIsFlat());
		world.setHasBackgroundScenery(descriptionConverter.convertHasClouds());
		world.setHeavyObstacles(descriptionConverter.convertHasHeavyObstacles());
		world.setIsFrameGrabbingActive(descriptionConverter.covertIsFrameGrabbingActive());
		//TODO world.setBigObstacles(bigObstacles);
		
		//ObstacleGenerator generator = new ObstacleGenerator();
		//generator.obstacalize(obstacleType, world); // activate to add obstacles
		 //world.setPlaneTexture(textureDescription);
		 
		return world;
	}
}
