package ussr.builder.controllerAdjustmentTool;

import ussr.model.Module;
import ussr.samples.odin.OdinController;

public class OdinMuscleWheelControllerNegative extends ControllerStrategy {

	/**
	 * Controller implementation for Odin modular robot.
	 */
	private OdinController controller;	
	
	/**
	 * The method which will is activated when this class is instantiated by the tool called "AssignControllerTool"
	 * @param selectedModule, the module selected in simulation environment
	 */
	public void activate (Module selectedModule){		
		controller = (OdinController)selectedModule.getController();		
			controller.actuateContinuous(-0.5f);// to the right				
	}	
}
