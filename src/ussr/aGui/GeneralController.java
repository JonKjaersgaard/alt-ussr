package ussr.aGui;

import ussr.aGui.tabs.controllers.TabsControllers;
import ussr.remote.facade.BuilderControlInter;

public class GeneralController {
	   /**
	 * Remote version of builder controller object.
	 */
	protected static BuilderControlInter builderControl;
	
	/**
	 * Sets builder controller of remote simulation for this controller.
	 * @param builderControl,builder controller of remote simulation.
	 */
	public static void setBuilderController(BuilderControlInter builderControl) {
		GeneralController.builderControl = builderControl;
	}
}
