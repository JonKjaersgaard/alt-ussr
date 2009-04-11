package ussr.builder.controllerReassignmentTool;

import ussr.model.Module;

/**
 * The responsibility of this class is to provide the method called "activate()" for the clidren classes.
 * It follows Strategy pattern and indicates that in order to use any specific class class
 * as a controller, the class should be extended from this class and implement the method
 * called "activate()".  
 * @author Konstantinas 
 */
public abstract class ControllerStrategy {

	/**
	 * The method which will be activated when specific class is instantiated by the tool called "AssignController"
	 * @param selectedModule, the module selected in simulation environment
	 */
	public abstract void activate (Module selectedModule);
}
