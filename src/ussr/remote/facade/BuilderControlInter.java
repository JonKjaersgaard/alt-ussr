package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ussr.aGui.tabs.controllers.ConstructRobotTabController;
import ussr.builder.constructionTools.ConstructionToolSpecification;
import ussr.builder.enums.ConstructionTools;
import ussr.builder.enums.SupportedModularRobots;
import ussr.builder.genericTools.RemoveModule;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.model.Module;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.PhysicsPicker;
import ussr.physics.jme.pickers.Picker;

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
	
	
	public boolean moduleExists(VectorDescription currentPosition) throws RemoteException;
	
	public void addInitialConstructionModule (SupportedModularRobots SupportedModularRobot)throws RemoteException;
}
