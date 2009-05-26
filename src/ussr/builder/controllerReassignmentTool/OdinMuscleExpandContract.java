package ussr.builder.controllerReassignmentTool;

import ussr.builder.OdinControllerDefault;
import ussr.builder.OdinControllerExpandContract;
import ussr.model.Controller;
import ussr.model.Module;
import ussr.samples.odin.OdinController;
import ussr.samples.odin.simulations.OdinSampleController1;

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
		/*1*/controller.exractContract(0.3f);
		System.out.println("INNN");	
		
	    //selectedModule.setController(new OdinControllerExpandContract() );
	    //selectedModule.getController().activate();
	
	}
}
