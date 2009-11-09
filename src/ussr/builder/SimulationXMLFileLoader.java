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
import ussr.samples.DefaultSimulationSetup;
import ussr.samples.GenericModuleConnectorHelper;
import ussr.samples.GenericSimulation;
import ussr.samples.ObstacleGenerator;

/**
 *Input format: samples/atron/car.xml ussr.samples.atron.simulations.ATRONCarController1.
 * @author Konstantinas
 */
public class SimulationXMLFileLoader extends GenericSimulation {
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
		
	    WorldDescription world = this.createGenericSimulationWorld(null);
        //WorldDescription world = createWorld();
        /* Load the robot */
        SaveLoadXMLBuilderTemplate xmlLoader = new PreSimulationXMLSerializer(world);
        xmlLoader.loadXMLfile(UssrXmlFileTypes.ROBOT,simulationXMLfileName);
        /* Connect modules */
        world.setModuleConnections(new GenericModuleConnectorHelper().computeAllConnections(world.getModulePositions()));
	}
	
	private static WorldDescription createWorld() {
		WorldDescription world = new WorldDescription();	        
		world.setPlaneSize(100);		
	  	//ObstacleGenerator generator = new ObstacleGenerator();
	    //generator.obstacalize(obstacleType, world); // activate to add obstacles
	    // world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
		return world;
	}

}
