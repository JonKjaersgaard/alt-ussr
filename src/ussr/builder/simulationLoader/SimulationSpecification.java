package ussr.builder.simulationLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import ussr.builder.enumerations.XMLTagsUsed;
import ussr.description.setup.WorldDescription;

public class SimulationSpecification implements Serializable {

	private ArrayList<RobotSpecification> robotsInSimulation = new ArrayList<RobotSpecification>();
	
	
	public ArrayList<RobotSpecification> getRobotsInSimulation() {
		return robotsInSimulation;
	}

	private Map<XMLTagsUsed, String> simWorldDecsriptionValues= new Hashtable<XMLTagsUsed, String>(),
	                                 simPhysicsParameters = new Hashtable<XMLTagsUsed, String>();

	public Map<XMLTagsUsed, String> getSimPhysicsParameters() {
		return simPhysicsParameters;
	}

	public Map<XMLTagsUsed, String> getSimWorldDecsriptionValues() {
		return simWorldDecsriptionValues;
	}
	
	
	
}
