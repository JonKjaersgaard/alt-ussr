package ussr.aGui;

import ussr.aGui.tabs.controllers.TabsControllers;
import ussr.remote.facade.BuilderControlInter;
import ussr.remote.facade.RemotePhysicsSimulation;

public class GeneralController {
	   /**
	 * Remote version of builder controller object.
	 */
	protected static BuilderControlInter builderControl;
	
	
	/**
	 * The remote(running of separate JVM than GUI) physics simulation.
	 */
	protected static RemotePhysicsSimulation remotePhysicsSimulation; 
	
	/**
	 * Sets builder controller of remote simulation for this controller.
	 * @param builderControl,builder controller of remote simulation.
	 */
	public static void setBuilderController(BuilderControlInter builderControl) {
		GeneralController.builderControl = builderControl;
	}
	
	/**
	 * Sets remote physics simulation for this controller.
	 * @param remotePhysicsSimulation, the remote physics simulation.
	 */
	public static void setRemotePhysicsSimulation(RemotePhysicsSimulation remotePhysicsSimulation) {
		GeneralController.remotePhysicsSimulation = remotePhysicsSimulation;
	}
}
