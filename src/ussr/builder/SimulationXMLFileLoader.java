package ussr.builder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ussr.aGui.tabs.controllers.SimulationTabController;
import ussr.aGui.tabs.views.SimulationTab;
import ussr.builder.enumerations.UssrXmlFileTypes;
import ussr.builder.enumerations.XMLTagsUsed;
import ussr.builder.helpers.ControllerFactory;
import ussr.builder.helpers.ControllerFactoryImpl;
import ussr.builder.saveLoadXML.PreSimulationXMLSerializer;
import ussr.builder.saveLoadXML.SaveLoadXMLBuilderTemplate;
import ussr.builder.saveLoadXML.SaveLoadXMLFileTemplate;
import ussr.description.Robot;
import ussr.description.setup.WorldDescription;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.DefaultSimulationSetup;
import ussr.samples.GenericModuleConnectorHelper;
import ussr.samples.GenericSimulation;
import ussr.samples.ObstacleGenerator;

/**
 *Input format: samples/atron/car.xml ussr.samples.atron.simulations.ATRONCarController1.
 * @author Konstantinas
 */
public class SimulationXMLFileLoader extends GenericSimulation implements Serializable {
	
	
	private static Map<XMLTagsUsed,String> simulationWorldDescription;
	private static Map<XMLTagsUsed,String> simulationPhysicsParameters; 
	private static SimulationDescriptionConverter descriptionConverter;
	
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
	    if(args.length>1) throw new Error("Usage: provide simulation definition xml file");
	    String simulationXMLfileName=args[0];
	   
	    new SimulationXMLFileLoader(simulationXMLfileName).start(true);
	}
	
	public SimulationXMLFileLoader(String simulationXMLfileName) {
	    DefaultSimulationSetup.setUSSRHome();	    
	    
	    PhysicsLogger.setDefaultLoggingLevel();
        /* Create the simulation*/
        simulation = PhysicsFactory.createSimulator();
        
        
        /*Load Simulation Configuration file*/
		SaveLoadXMLFileTemplate xmlLoaderSimulation = new PreSimulationXMLSerializer(/*new PhysicsParameters()*/);
		xmlLoaderSimulation.loadXMLfile(UssrXmlFileTypes.SIMULATION, simulationXMLfileName);
        
		/*Get values from XML file*/
		simulationWorldDescription = xmlLoaderSimulation.getSimulationWorldDescriptionValues();
        simulationPhysicsParameters = xmlLoaderSimulation.getSimulationPhysicsValues();
        
        /*Converter for converting values from String into corresponding type used in USSR*/
        descriptionConverter =  new SimulationDescriptionConverter(simulationWorldDescription,simulationPhysicsParameters); 
        
        String controllerLocation = simulationWorldDescription.get(XMLTagsUsed.CONTROLLER_LOCATION);
        
        //String controllerLocation  = "";
        List<String> controllerNames = new ArrayList<String>();        
        controllerNames.add(controllerLocation);
        ControllerFactory controllerFactory = new ControllerFactoryImpl(controllerNames);
        
        
        WorldDescription world = this.createGenericSimulationWorld(controllerFactory);
        world = createWorld();
        
        /* Load the robot */
        String robotMorphologyLocation = simulationWorldDescription.get(XMLTagsUsed.MORPHOLOGY_LOCATION);
        
        SaveLoadXMLBuilderTemplate xmlLoader = new PreSimulationXMLSerializer(world);       
        xmlLoader.loadXMLfile(UssrXmlFileTypes.ROBOT,robotMorphologyLocation);
      
        simulation.setWorld(world); 
	    
		
	    //WorldDescription world = this.createGenericSimulationWorld(null);
       // world = createWorld();
        
        
        
        /* Load the robot */
        //SaveLoadXMLBuilderTemplate xmlLoader = new PreSimulationXMLSerializer(world);
        //xmlLoader.loadXMLfile(UssrXmlFileTypes.ROBOT,simulationXMLfileName);
        /* Connect modules */
        world.setModuleConnections(new GenericModuleConnectorHelper().computeAllConnections(world.getModulePositions()));
	}
	
	public static SimulationDescriptionConverter getDescriptionConverter() {
		return descriptionConverter;
	}

	private static WorldDescription createWorld() {
		/*Assign values to world*/
		WorldDescription world = new WorldDescription();	        
		world.setPlaneSize(descriptionConverter.convertPlaneSize());
		world.setPlaneTexture(descriptionConverter.covertPlaneTexture());
		world.setCameraPosition(descriptionConverter.convertCameraPosition());
		world.setFlatWorld(descriptionConverter.convertTheWorldIsFlat());
		world.setHasBackgroundScenery(descriptionConverter.convertHasClouds());
		world.setHeavyObstacles(descriptionConverter.convertHasHeavyObstacles());
		world.setIsFrameGrabbingActive(descriptionConverter.covertIsFrameGrabbingActive());
		return world;
	}

}
