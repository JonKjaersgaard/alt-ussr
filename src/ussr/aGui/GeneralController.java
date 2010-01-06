package ussr.aGui;


import java.io.IOException;
import java.rmi.RemoteException;

import ussr.remote.GUIRemoteSimulationAdapter;
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
	 * XML file directory of current simulation.
	 */
	protected static String simulationXMLFileDirectory;
	
	/**
	 * Starts new remote simulation from XML file in separate thread.
	 * @param simulationXMLFileDirectory, XML file directory of current simulation.
	 */
	public static void startSimulation(final String simulationXMLFileDirectory ){
		new Thread() {
			public void run() {
				try {
					GUIRemoteSimulationAdapter.runSimulation(simulationXMLFileDirectory);
				} catch (IOException e) {
					//throw new Error("Failed to run simulation file located at "+ simulationXMLFileDirectory+ " , due to remote exception");
				}
			}
		}.start();
		MainFrameSeparateController.setSimulationRunning(false);

	}
	
	/**
	 * Terminates current remote simulation.
	 */
	public static void terminateSimulation(){
		try {
			if (remotePhysicsSimulation!=null){
			remotePhysicsSimulation.stop();
			}
		} catch (RemoteException e) {
			//Do not throw anything, because this means only GUI is closed and simulation was not even started			
		}
	}
	
	
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
	
	/**
	 * Sets XML file directory of current simulation.
	 * @param simulationXMLFileDirectory, XML file directory of current simulation.
	 */
	public static void setSimulationXMLFileDirectory(String simulationXMLFileDirectory) {
		GeneralController.simulationXMLFileDirectory = simulationXMLFileDirectory;
	}
	
}
