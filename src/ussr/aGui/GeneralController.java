package ussr.aGui;


import ussr.remote.facade.BuilderControlInter;
import ussr.remote.facade.RemotePhysicsSimulation;

/**
 * Supports controllers with common remote objects for controlling remote simulation. 
 * @author Konstantinas
 */
public abstract class GeneralController {
	 
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
	public static void setBuilderControl(BuilderControlInter builderControl) {
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
