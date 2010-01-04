package ussr.builder.controllerAdjustmentTool;

import ussr.model.Module;
import ussr.samples.atron.ATRONController;
import ussr.samples.ckbot.CKBotController;


public class CKBOT_STANDARDControllerNOT_SUPPORTED_YET extends ControllerStrategy {

	
	/**
	 * The controller class providing the ATRON API
	 */
	private CKBotController controller;	
	
	/**
	 * The method which will is activated when this class is instantiated by the tool called "AssignControllerTool"
	 * @param selectedModule, the module selected in simulation environment
	 */
	public void activate (Module selectedModule){		
		//controller = (CKBotController)selectedModule.getController();
		//TODO
	}	
}
