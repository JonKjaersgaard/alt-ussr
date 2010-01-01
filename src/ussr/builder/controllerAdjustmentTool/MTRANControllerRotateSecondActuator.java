package ussr.builder.controllerAdjustmentTool;

import ussr.model.Module;
import ussr.samples.atron.ATRONController;
import ussr.samples.mtran.MTRANController;


public class MTRANControllerRotateSecondActuator extends ControllerStrategy {

	/**
	 * The controller class providing ATRON API
	 */
	private MTRANController controller;	
	
	/**
	 * The method which will is activated when this class is instantiated by the tool called "AssignControllerTool"
	 * @param selectedModule, the module selected in simulation environment
	 */
	public void activate (Module selectedModule){		
		controller = (MTRANController)selectedModule.getController();
		controller.rotateContinuous(1, 0);
	}	
}
