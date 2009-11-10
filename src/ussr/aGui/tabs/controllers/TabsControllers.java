package ussr.aGui.tabs.controllers;

import ussr.remote.facade.BuilderControlInter;

public abstract class TabsControllers implements TabsControllerInter{

	
     /**
	 * Remote version of builder controller object.
	 */
	protected static BuilderControlInter builderControl;
	
	/**
	 * Sets builder controller of remote simulation for this controller.
	 * @param builderControl,builder controller of remote simulation.
	 */
	public static void setBuilderController(BuilderControlInter builderControl) {
		TabsControllers.builderControl = builderControl;
	}
}
