package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import ussr.description.geometry.VectorDescription;

/**
 * Provides simulation tab(package "ussr.aGui.tabs.simulation" ) with control of remote simulation.
 * (Used on the simulation side.)
 * @author Konstantinas
 */
public interface SimulationTabControlInter extends Remote {
	
	/**
	 * Moves module to new position.
	 * @param moduleID, the unique global ID of the module.
	 * @param newModulePosition, new position for module.
	 */
	public void setModulePosition(int moduleID,VectorDescription newModulePosition)throws RemoteException;
	
	/**
	 * Returns module position
	 * @param moduleID, the unique global ID of the module.
	 * @return module position.
	 */
	public VectorDescription getModulePosition(int moduleID)throws RemoteException;
	
	
	/**
	 * Removes modules from simulation environment.
	 * @param ids, the list of ids of modules to remove.
	 * FIXME SOMETIMES FAILS! REASON IS NOT YET CLEAR.
	 * 	 */
	public void deleteModules(List<Integer> ids)throws RemoteException;

}
