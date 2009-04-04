package ussr.builder.controllerReassignmentTool;

import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public class ATRONAxlelController {

	private ATRONController controller;
	
	/*private Module selectedModule;
	public WheelController(Module selectedModule){
		this.selectedModule =selectedModule;
	}*/
	
	
	
	
	
	public void activate (Module selectedModule){
		System.out.println("INNNNN");
		controller = (ATRONController)selectedModule.getController();
			controller.rotateDegrees(20);		
	}
}
