package ussr.builder.controllerReassignmentTool;

import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public class ATRONWheelController {

	private ATRONController controller;
	
	/*private Module selectedModule;
	public WheelController(Module selectedModule){
		this.selectedModule =selectedModule;
	}*/
	
	
	
	
	
	public void activate (Module selectedModule){
		System.out.println("INNNNN");
		controller = (ATRONController)selectedModule.getController();
		
			controller.rotateContinuous(1);// to the right			
		//controller.rotateContinuous(-1);// to the left		
			//controller.rotate(45);// specific amount of degrees
		
	}
}
