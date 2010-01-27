package ussr.builder.simulationLoader;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Is used as container for storing description robot. 
 * @author Konstantinas
 *
 */
public class RobotSpecification implements Serializable {

	/**
	 * The directory of controller location.
	 */
	private String controllerLocation;
	
	/**
	 * The directory of robot morphology location 
	 */
	private String morphologyLocation;
	
	/**
	 * The IDs of modules constituting the robot. 
	 */
	private List<Integer> idsModules = new ArrayList<Integer>();
	
	/**
	 * Returns the list of modules constituting the robot.
	 * @return the list of modules constituting the robot.
	 */
	public List<Integer> getIdsModules() {
		return idsModules;
	}

	/**
	 * Sets the list of modules constituting the robot.
	 * @param idsModules, the list of modules constituting the robot.
	 */
	public void setIdsModules(List<Integer> idsModules) {
		this.idsModules.clear();
		this.idsModules.addAll(idsModules);
	}

	/**
	 * Nr. of modules the robot consists of.
	 */
	private  int amountModules;
	
	/**
	 * Sets nr. of modules the robot consists of.
	 * @param amountModules
	 */
	public void setAmountModules(int amountModules) {
		this.amountModules = amountModules;
	}

	public RobotSpecification(){
	}
	
	/**
	 * Returns the directory of controller location.
	 * @return the directory of controller location.
	 */
	public String getControllerLocation() {
		return controllerLocation;
	}

	/**
	 * Sets the directory of controller location.
	 * @param controllerLocation, the directory of controller location.
	 */
	public void setControllerLocation(String controllerLocation) {
		this.controllerLocation = controllerLocation;
	}
	
	
	/**
	 * Returns Nr. of modules the robot consists of.
	 * @return Nr. of modules the robot consists of.
	 */
	public  int getAmountModules() {
		return amountModules;
	}

	/**
	 * Returns the directory of robot morphology location.
	 * @return, the directory of robot morphology location. 
	 */
	public  String getMorphologyLocation() {
		return morphologyLocation;
	}
	
	/**
	 * Sets the directory of robot morphology location.
	 * @param morphologyLocation,  the directory of robot morphology location. 
	 */
	public void setMorphologyLocation(String morphologyLocation) {
		this.morphologyLocation = morphologyLocation;
	}
}
