package ussr.builder.controllerReassignmentTool;

import ussr.model.Module;
import ussr.samples.odin.OdinController;

public class OdinMuscleWheelControllerPositive {

	private OdinController controller;
	
	/*private Module selectedModule;
	public WheelController(Module selectedModule){
		this.selectedModule =selectedModule;
	}*/
	
	
	
	
	
	public void activate (Module selectedModule){
		System.out.println("INNNNN");
		controller = (OdinController)selectedModule.getController();
		
			controller.actuateContinuous(0.5f);// to the right			
		
		
	}
}
