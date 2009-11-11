package ussr.builder;

import java.util.ArrayList;
import java.util.List;

import ussr.builder.enumerations.UssrXmlFileTypes;
import ussr.builder.helpers.ControllerFactory;
import ussr.builder.helpers.ControllerFactoryImpl;
import ussr.builder.saveLoadXML.PreSimulationXMLSerializer;
import ussr.builder.saveLoadXML.SaveLoadXMLBuilderTemplate;
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
public class Loader extends GenericSimulation {
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
	    String fileName=args[0];
	    List<String> controllerNames = new ArrayList<String>();
	    for(int i=1; i<args.length; i++)
	        controllerNames.add(args[i]);
	    new Loader(fileName,controllerNames).start(true);
	}
	
	public Loader(String fileName, List<String> controllerNames) {
	    DefaultSimulationSetup.setUSSRHome();
		/*Activate connectors*/
		// Start simulation
        ControllerFactory controllerFactory = new ControllerFactoryImpl(controllerNames);
        WorldDescription world = this.createGenericSimulationWorld(controllerFactory);
        /* Load the robot */
        SaveLoadXMLBuilderTemplate xmlLoader = new PreSimulationXMLSerializer(world);
        xmlLoader.loadXMLfile(UssrXmlFileTypes.ROBOT,fileName);
        /* Connect modules */
        world.setModuleConnections(new GenericModuleConnectorHelper().computeAllConnections(world.getModulePositions()));
	}

}
