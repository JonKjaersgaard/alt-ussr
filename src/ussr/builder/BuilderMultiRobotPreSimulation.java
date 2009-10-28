package ussr.builder;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import ussr.builder.saveLoadXML.PreSimulationXMLSerializer;
import ussr.builder.saveLoadXML.SaveLoadXMLBuilderTemplate;
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



	public static final  String loadableRobotMorphologyFile = "samples/simulations/atron/morphologies/simpleVehicleMophology.xml";

	private  static final String loadableSimulationFile ="samples/simulations/atron/simpleVehicleSim.xml";
	
	private static Map<TagsUsed,String> simulationDescription; 
	
	/**
	 * Starts multi-robot simulation for ATRON,MTRAN and Odin.
	 * @param args, passed arguments.
	 */
	public static void main( String[] args ) {
		/* Set up home */
		String home = System.getenv("USSRHOME"); 
		if(home!=null) {
			System.out.println("Setting home to "+home);
			PhysicsFactory.getOptions().setResourceDirectory(home+"/");
		}		
		runSimulationFromXMLFile();	
	}
	private static void runSimulationFromXMLFile(){

		/*Activate connectors*/
		GenericSimulation.setConnectorsAreActive(true);
		BuilderMultiRobotPreSimulation simulation = new BuilderMultiRobotPreSimulation();		

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

		SaveLoadXMLFileTemplate xmlLoaderSimulation = new PreSimulationXMLSerializer(/*world*/);
		xmlLoaderSimulation.loadXMLfile(UssrXmlFileTypes.SIMULATION, loadableSimulationFile);
        
		simulationDescription = xmlLoaderSimulation.getSimulationDescriptionValues();
		
		/*Create the world description of simulation and set it to simulation*/
		if(world==null) world = createWorld();
		simulation.setWorld(world);
		/*Simulation should be in paused state (static)in the beginning*/
		simulation.setPause(startPaused);


		SaveLoadXMLFileTemplate xmlLoaderRodbot = new PreSimulationXMLSerializer(world);
		xmlLoaderRodbot.loadXMLfile(UssrXmlFileTypes.ROBOT, simulationDescription.get(TagsUsed.MORPHOLOGY_LOCATION));

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
		int planeSize =Integer.parseInt(simulationDescription.get(TagsUsed.PLANE_SIZE)) ;
	    String textureFile = simulationDescription.get(TagsUsed.PLANE_TEXTURE);
	    System.out.println("tEXTURE:"+textureFile );
	    
	    /*Local container for all textures*/
	    Map<String,TextureDescription> containerTextureDesc = new Hashtable<String, TextureDescription>(); 
	    containerTextureDesc.put(WorldDescription.GRASS_TEXTURE.getFileName(), WorldDescription.GRASS_TEXTURE);
	    containerTextureDesc.put(WorldDescription.GREY_GRID_TEXTURE.getFileName(), WorldDescription.GREY_GRID_TEXTURE);
	    containerTextureDesc.put(WorldDescription.MARS_TEXTURE.getFileName(), WorldDescription.MARS_TEXTURE);
	    containerTextureDesc.put(WorldDescription.WHITE_GRID_TEXTURE.getFileName(), WorldDescription.WHITE_GRID_TEXTURE);
	    containerTextureDesc.put(WorldDescription.WHITE_TEXTURE.getFileName(), WorldDescription.WHITE_TEXTURE);
	    
	    TextureDescription textureDescription = containerTextureDesc.get(textureFile);
	    
		String cameraPosition = simulationDescription.get(TagsUsed.CAMERA_POSITION);
		boolean theWorldIsFlat = Boolean.parseBoolean(simulationDescription.get(TagsUsed.THE_WORLD_IS_FLAT));
		boolean hasClouds = Boolean.parseBoolean(simulationDescription.get(TagsUsed.HAS_BACKGROUND_SCENERY));
		boolean heavyObstacles = Boolean.parseBoolean(simulationDescription.get(TagsUsed.HAS_HEAVY_OBSTACLES));
		boolean isActive = Boolean.parseBoolean(simulationDescription.get(TagsUsed.IS_FRAME_GRABBING_ACTIVE));
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
