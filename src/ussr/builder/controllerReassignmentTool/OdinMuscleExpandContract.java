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
	
	private Controller controller1;	
	/**
	 * The method which will is activated when this class is instantiated by the tool called "AssignControllerTool"
	 * @param selectedModule, the module selected in simulation environment
	 */
	public void activate (Module selectedModule){
		 //controller = (OdinController)selectedModule.getController();	
		/*1*///controller.exractContract(0.3f);
		System.out.println("INNN");	
		
		OdinController currentController = (OdinController)selectedModule.getController();
		currentController.disactivate();
		
		controller = new OdinControllerExpandContract(); 
		controller.setModule(selectedModule);
		controller.yield();
		
		
	
	/*	controller = new OdinControllerExpandContract();        
        selectedModule.setController(controller);
        selectedModule.getController().activate();*/
		
		//controller1 = new OdinSampleController1("OdinMuscle");
		//selectedModule.setController(controller1);
		//controller1.activate();
		
		//OdinControllerExpandContract  expandContract = new OdinControllerExpandContract();		
		/*1*///selectedModule.setController(expandContract);		
		/*2*///expandContract.setModule(selectedModule);
	}
}
