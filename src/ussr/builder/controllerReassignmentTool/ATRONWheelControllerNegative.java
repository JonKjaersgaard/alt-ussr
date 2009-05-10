package ussr.builder.controllerReassignmentTool;

import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public class ATRONWheelControllerNegative extends ControllerStrategy {

	/**
	 * The controller class providing the ATRON API
	 */
	private ATRONController controller;	
	
	/**
	 * The method which will is activated when this class is instantiated by the tool called "AssignControllerTool"
	 * @param selectedModule, the module selected in simulation environment
	 */
	public void activate (Module selectedModule){	
		controller = (ATRONController)selectedModule.getController();
			controller.rotateContinuous(-1);// to the right			
			
	}
}