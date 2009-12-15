package ussr.builder;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ussr.aGui.tabs.simulation.SimulationSpecification;
import ussr.aGui.tabs.simulation.SimulationTab;
import ussr.builder.enumerations.UssrXmlFileTypes;
import ussr.builder.enumerations.XMLTagsUsed;
import ussr.builder.helpers.ControllerFactory;
import ussr.builder.helpers.ControllerFactoryImpl;
import ussr.builder.saveLoadXML.PreSimulationXMLSerializer;
import ussr.builder.saveLoadXML.SaveLoadXMLBuilderTemplate;
import ussr.builder.saveLoadXML.SaveLoadXMLFileTemplateInter;
import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.jme.JMESimulation;
import ussr.samples.DefaultSimulationSetup;
import ussr.samples.GenericSimulation;


/**
 *Input format: samples/atron/car.xml ussr.samples.atron.simulations.ATRONCarController1.
 * @author Konstantinas
 */
public class SimulationXMLFileLoader extends GenericSimulation implements SimuliationXMLFileLoaderInter {
	
	
	private static Map<XMLTagsUsed,String> simulationWorldDescription,simulationPhysicsParameters,
                                           robotDescription;
	
	private Map<Integer,ModulePosition> robotModules;
	
	public Map<Integer,ModulePosition> getRobotModules() {
		return robotModules;
	}

	private static SimulationDescriptionConverter descriptionConverter;
	
	private String robotMorphologyLocation;
	
	private SaveLoadXMLFileTemplateInter robotXMLLoader;
	
	private String idsModules;
	
	private SimulationSpecification simulationSpecification;
	
	
	public SimulationSpecification getSimulationSpecification() {
		return simulationSpecification;
	}


	public String getIdsModules() {
		return idsModules;
	}


	public SaveLoadXMLFileTemplateInter getRobotXMLLoader() {
		return robotXMLLoader;
	}


	public String getRobotMorphologyLocation() {
		return robotMorphologyLocation;
	}
	

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
		SaveLoadXMLFileTemplateInter xmlLoaderSimulation = new PreSimulationXMLSerializer(/*new PhysicsParameters()*/);
		xmlLoaderSimulation.loadXMLfile(UssrXmlFileTypes.SIMULATION, simulationXMLfileName);
        
		/*Get values from XML file*/
		simulationWorldDescription = xmlLoaderSimulation.getSimulationWorldDescriptionValues();
        simulationPhysicsParameters = xmlLoaderSimulation.getSimulationPhysicsValues();
        robotDescription =  xmlLoaderSimulation.getRobotDescriptionValues();
        simulationSpecification = xmlLoaderSimulation.getSimulationSpecification();
       
        
        
        /*Converter for converting values from String into corresponding type used in USSR*/
        descriptionConverter =  new SimulationDescriptionConverter(simulationWorldDescription,simulationPhysicsParameters); 
        
        //String controllerLocation = robotDescription.get(XMLTagsUsed.CONTROLLER_LOCATION);
       // String controllerLocation = SimulationSpecification.robotsInSimulation.get(0).getControllerLocation();
        String controllerLocation = simulationSpecification.getRobotsInSimulation().get(0).getControllerLocation();
        
        List<String> controllerNames = new ArrayList<String>();        
        controllerNames.add(controllerLocation);
        ControllerFactory controllerFactory = new ControllerFactoryImpl(controllerNames);
        
        
        WorldDescription world = this.createGenericSimulationWorld(controllerFactory);
        world = createWorld();
        
        /* Load the robot */
       // robotMorphologyLocation = robotDescription.get(XMLTagsUsed.MORPHOLOGY_LOCATION);
     
        robotXMLLoader = new PreSimulationXMLSerializer(world);
       
        for (int robotNr=0;robotNr<simulationSpecification.getRobotsInSimulation().size();robotNr++){
        	 
        	String morphology = simulationSpecification.getRobotsInSimulation().get(robotNr).getMorphologyLocation();
        	 robotXMLLoader.loadXMLfile(UssrXmlFileTypes.ROBOT,morphology);
        }
        
        
        //robotXMLLoader.loadXMLfile(UssrXmlFileTypes.ROBOT,robotMorphologyLocation);
       
        
     
   //     idsModules = robotXMLLoader.getIdsModules();
      
        
      
        simulation.setWorld(world); 
      
	    
        /* Connect modules */
        //world.setModuleConnections(new GenericModuleConnectorHelper().computeAllConnections(world.getModulePositions()));
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
