package ussr.builder;

import java.util.ArrayList;
import java.util.List;

import ussr.builder.helpers.ControllerFactory;
import ussr.builder.helpers.ControllerFactoryImpl;
import ussr.builder.saveLoadXML.PreSimulationXMLSerializer;
import ussr.builder.saveLoadXML.SaveLoadXMLBuilderTemplate;
import ussr.builder.saveLoadXML.UssrXmlFileTypes;
import ussr.description.Robot;
import ussr.description.setup.WorldDescription;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.GenericModuleConnectorHelper;
import ussr.samples.GenericSimulation;
import ussr.samples.ObstacleGenerator;

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
	    DefaultSimulationSetup.setUSSRHome();
		/*Activate connectors*/
		Loader simulation = new Loader();
		/* Specify realistic collision*/
		// PhysicsParameters.get().setRealisticCollision(true);// already default
		/* Specify simulation step*/
		// PhysicsParameters.get().setPhysicsSimulationStepSize(0.0035f);// Redundant
		/* Specify smallest resolution factor so that simulation can handle more modules.
		 * This is done in order to "in a way" avoid stack overflow */
		//PhysicsParameters.get().setResolutionFactor(1); // Default is 2, redundant default
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
		PhysicsSimulation simulation = PhysicsFactory.createSimulator();
		
		/* Assign controller to selection of robots */
		ControllerFactory controllerFactory = new ControllerFactoryImpl(controllerNames);
		DefaultSimulationSetup.addDefaultRobotSelection(simulation, controllerFactory);
		
        /*Create the world description of simulation and set it to simulation*/
		if(world==null) world = DefaultSimulationSetup.createWorld();
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
}
