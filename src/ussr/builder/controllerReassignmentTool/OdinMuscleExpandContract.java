package ussr.builder.controllerReassignmentTool;

import ussr.builder.OdinControllerExpandContract;
import ussr.model.Module;
import ussr.samples.odin.OdinController;

public class OdinMuscleExpandContract extends ControllerStrategy {

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
		/*1*///controller.exractContract(0.3f);
controller.activate();
		
		//OdinControllerExpandContract  expandContract = new OdinControllerExpandContract();		
		/*1*///selectedModule.setController(expandContract);		
		/*2*///expandContract.setModule(selectedModule);
	}
}
