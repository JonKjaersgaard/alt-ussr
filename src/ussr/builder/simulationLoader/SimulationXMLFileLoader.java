package ussr.builder.simulationLoader;

import java.util.ArrayList;
import java.util.List;
import ussr.builder.enumerations.ATRONTypesModules;
import ussr.builder.helpers.ControllerFactory;
import ussr.builder.helpers.ControllerFactoryImpl;
import ussr.builder.saveLoadXML.PreSimulationXMLSerializer;
import ussr.builder.saveLoadXML.SaveLoadXMLFileTemplateInter;
import ussr.builder.saveLoadXML.UssrXmlFileTypes;
import ussr.builder.saveLoadXML.XMLTagsUsed;
import ussr.description.Robot;
import ussr.description.setup.WorldDescription;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.samples.DefaultSimulationSetup;
import ussr.samples.GenericSimulation;



/**
 *Input format: samples/atron/car.xml ussr.samples.atron.simulations.ATRONCarController1.
 * @author Konstantinas
 */
public class SimulationXMLFileLoader extends GenericSimulation {
	
	

	private static SimulationSpecificationConverter descriptionConverter;
	
	
	private SimulationSpecification simulationSpecification;
	


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
	 
	     

        /*Load Simulation Configuration file*/
		SaveLoadXMLFileTemplateInter xmlLoaderSimulation = new PreSimulationXMLSerializer();
		xmlLoaderSimulation.loadXMLfile(UssrXmlFileTypes.SIMULATION, simulationXMLfileName);
        
		/*Get all values from XML file*/
        simulationSpecification = xmlLoaderSimulation.getSimulationSpecification();
        
        /*Check if SIMULATION xml file was loaded or some different XML file*/
        if (simulationSpecification.getSimWorldDecsriptionValues().containsKey(XMLTagsUsed.SIMULATION)){
        	 /* Create the simulation*/
            simulation = PhysicsFactory.createSimulator();
            
        	/*Converter for converting values from String into corresponding type used in USSR*/      
            descriptionConverter =  simulationSpecification.getConverter();
          
            
            setPhysicsParameters();
            
            String controllerLocation = null;
            if (simulationSpecification.getRobotsInSimulation().isEmpty()){// new (default) simulation is started
            	controllerLocation = "ussr.builder.simulationLoader.DefaultAtronController";
            }else{
            controllerLocation = simulationSpecification.getRobotsInSimulation().get(0).getControllerLocation();
            }
            
            List<String> controllerNames = new ArrayList<String>();        
            controllerNames.add(controllerLocation);
            ControllerFactory controllerFactory = new ControllerFactoryImpl(controllerNames);
            
            WorldDescription world = this.createGenericSimulationWorld(controllerFactory);
            world = createWorld();
            
            /* Load the robot */ 
            SaveLoadXMLFileTemplateInter robotXMLLoader = new PreSimulationXMLSerializer(world);
           
            for (int robotNr=0;robotNr<simulationSpecification.getRobotsInSimulation().size();robotNr++){
            	 
            	String morphology = simulationSpecification.getRobotsInSimulation().get(robotNr).getMorphologyLocation();
            	 robotXMLLoader.loadXMLfile(UssrXmlFileTypes.ROBOT,morphology);
            }
          
            ATRONTypesModules.setAllModuleTypesOnSimulation(simulation);
            simulation.setWorld(world); 
        }  else{
        	//do nothing.
        }   
        
	}
	
	public SimulationSpecification getSimulationSpecification() {
		return simulationSpecification;
	}
	
    /**
     * Sets physics simulation parameters on current simulation.
     */
    private void setPhysicsParameters(){
    	PhysicsParameters.get().setPhysicsSimulationStepSize(descriptionConverter.convertPhysicsSimulationStepSize());
    	PhysicsParameters.get().setResolutionFactor(descriptionConverter.convertResolutionFactor());
    	PhysicsParameters.get().setWorldDampingLinearVelocity(descriptionConverter.convertWorldDamping(true));
    	PhysicsParameters.get().setWorldDampingAngularVelocity(descriptionConverter.convertWorldDamping(false));
    	PhysicsParameters.get().setRealisticCollision(descriptionConverter.covertRealisticCollision());
    	PhysicsParameters.get().setGravity(descriptionConverter.covertGravity());
    	PhysicsParameters.get().setPlaneMaterial(descriptionConverter.covertPlaneMaterial());
    	PhysicsParameters.get().setMaintainRotationalJointPositions(descriptionConverter.convertMaintainRotationalJointPositions());
    	PhysicsParameters.get().setConstraintForceMix(descriptionConverter.convertConstraintForceMix());
    	PhysicsParameters.get().setErrorReductionParameter(descriptionConverter.convertErrorReductionParameter());
    	PhysicsParameters.get().setUseModuleEventQueue(descriptionConverter.convertUseModuleEventQueue());
    	PhysicsParameters.get().setSyncWithControllers(descriptionConverter.convertSyncWithControllers());
    	PhysicsParameters.get().setPhysicsSimulationControllerStepFactor(descriptionConverter.convertPhysicsSimulationControllerStepFactor());
    	
    } 

	/**
	 * Creates the simulation world from values extracted from xml file.
	 * @return the simulation world created from values extracted from xml file.
	 */
	private  WorldDescription createWorld() {
		
		WorldDescription world = new WorldDescription();
		/*Assign values to world*/
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
