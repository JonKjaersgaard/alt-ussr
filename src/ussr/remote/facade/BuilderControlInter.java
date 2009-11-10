package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;
import ussr.builder.enums.SupportedModularRobots;
import ussr.description.geometry.VectorDescription;

public interface BuilderControlInter extends Remote {
	
	
	
	/**
	 * Attaches specific picker(left side of the mouse selection) to remote simulation.
	 * @param builderSupportingPicker, the picker supported by builder.	
	 */
	public void setPicker(BuilderSupportingUnicastPickers builderSupportingPicker)throws RemoteException;
	
	/**
	 * Removes all modules (robot(s)) from simulation environment.
	 */
	public void removeAllModules() throws RemoteException;
	//public void setConstructionPicker(ConstructionToolSpecification constructionToolSpecification)throws RemoteException;
	
	
	/**
	 * Checks if module is occupying specific position in simulation environment. 
	 * @param prosition, position in simulation environment
	 * @return true, if module exists at this position.
	 */
	public boolean moduleExists(VectorDescription position) throws RemoteException;
	
	/**
	 * Adds default(first) construction module in simulation environment.
	 * @param SupportedModularRobot, the type of module to add. 
	 */
	public void addInitialConstructionModule (SupportedModularRobots SupportedModularRobot)throws RemoteException;
	
	/**
	 * Connects all modules existing in simulation environment.
	 */
	public  void connectAllModules()throws RemoteException;
}
