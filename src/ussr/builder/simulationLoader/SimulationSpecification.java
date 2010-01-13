package ussr.builder.simulationLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import ussr.builder.saveLoadXML.XMLTagsUsed;


/**
 * Is used as container for storing description of simulation and objects in it. 
 * @author Konstantinas
 *
 */
public class SimulationSpecification implements Serializable {

	/**
	 * For storing robot descriptions extracted from XML.
	 */
	private ArrayList<RobotSpecification> robotsInSimulation = new ArrayList<RobotSpecification>();
	
	/**
	 * For storing  values of Simulation world description and physics parameters respectively.
	 */
	private Map<XMLTagsUsed, String> simWorldDecsriptionValues= new Hashtable<XMLTagsUsed, String>(),
	                                 simPhysicsParameters = new Hashtable<XMLTagsUsed, String>();
		
	/**
	 * Returns specifications of robots in simulation.
	 * @return specifications of robots in simulation.
	 */
	public ArrayList<RobotSpecification> getRobotsInSimulation() {
		return robotsInSimulation;
	}

	/**
	 * Returns the map of physics parameters describing simulation. 
	 * @return the map of physics parameters describing simulation. 
	 */
	public Map<XMLTagsUsed, String> getSimPhysicsParameters() {
		return simPhysicsParameters;
	}

	/**
	 * Returns the map of world description parameters describing simulation world. 
	 * @return the map of world description parameters describing simulation world.
	 */
	public Map<XMLTagsUsed, String> getSimWorldDecsriptionValues() {
		return simWorldDecsriptionValues;
	}
	
	
	/**
	 * Returns an instance of converter for converting values of world description and physics parameters extracted from XML.
	 * @return an instance of converter for converting values of world description and physics parameters extracted from XML.
	 */
	public SimulationSpecificationConverter getConverter(){
		return new SimulationSpecificationConverter(this.simWorldDecsriptionValues,this.simPhysicsParameters);
	}	
}
