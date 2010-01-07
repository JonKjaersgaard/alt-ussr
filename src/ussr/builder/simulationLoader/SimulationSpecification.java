package ussr.builder.simulationLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import ussr.builder.saveLoadXML.XMLTagsUsed;


/**
 * Is used as container for storing description of simulation. 
 * @author Konstantinas
 *
 */
public class SimulationSpecification implements Serializable {

	/**
	 * For storing robot descriptions extracted from XML and newly defined in GUI.
	 */
	private ArrayList<RobotSpecification> robotsInSimulation = new ArrayList<RobotSpecification>();
	
	/**
	 * 
	 */
	private Map<XMLTagsUsed, String> simWorldDecsriptionValues= new Hashtable<XMLTagsUsed, String>(),
	                                 simPhysicsParameters = new Hashtable<XMLTagsUsed, String>();
		
	



	/**
	 * Returns the robot descriptions extracted from XML and newly defined in GUI.
	 * @return the robot descriptions extracted from XML and newly defined in GUI.
	 */
	public ArrayList<RobotSpecification> getRobotsInSimulation() {
		return robotsInSimulation;
	}

	

	public Map<XMLTagsUsed, String> getSimPhysicsParameters() {
		return simPhysicsParameters;
	}

	public Map<XMLTagsUsed, String> getSimWorldDecsriptionValues() {
		return simWorldDecsriptionValues;
	}
	
	
	
	
	public void setSimPhysicsParameters(
			Map<XMLTagsUsed, String> simPhysicsParameters) {
		this.simPhysicsParameters = simPhysicsParameters;
	}



	public void setSimWorldDecsriptionValues(
			Map<XMLTagsUsed, String> simWorldDecsriptionValues) {
		this.simWorldDecsriptionValues = simWorldDecsriptionValues;
	}
	
	public SimulationSpecificationConverter getConverter(){
		return new SimulationSpecificationConverter(this.simWorldDecsriptionValues,this.simPhysicsParameters);
	}
	
	
	
}
