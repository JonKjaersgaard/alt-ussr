package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ussr.builder.constructionTools.ConstructionToolSpecification;
import ussr.builder.controllerAdjustmentTool.AssignControllerTool;
import ussr.builder.enumerations.ConstructionTools;
import ussr.builder.enumerations.LabeledEntities;
import ussr.builder.enumerations.LabelingTools;
import ussr.builder.enumerations.SupportedModularRobots;
import ussr.description.geometry.VectorDescription;
import ussr.model.Module;
import ussr.physics.jme.pickers.Picker;

public interface BuilderControlInter extends Remote {
	
	/**
	 * Sets picker for moving modular robots (left side of the mouse selection) during running state of simulation.
	 */
	public void setDefaultPicker()throws RemoteException;
	
	/**
	 * Sets picker for removing(deleting) module, selected in simulation environment.
	 */
	public void setRemoveModulePicker()throws RemoteException;
	
	/**
	 * Sets picker for moving module(left side of the mouse selection), selected in simulation environment(in paused state).
	 */
	public void setMoveModulePicker()throws RemoteException;
	
	/**
	 * Sets picker for coloring module connectors with color coding, selected in simulation environment.
	 */
	public void setColorModuleConnectorsPicker()throws RemoteException;
	
	/**
	 * Sets a number of pickers called by specific name. For example: ConstructionTools.AVAILABLE_ROTATIONS,
		ConstructionTools.MODULE_OPPOSITE_ROTATION, ConstructionTools.NEW_MODULE_ON_SELECTED_CONNECTOR and so on.
	 * @param toolName, the name of the picker.
	 */
	public void setConstructionToolSpecPicker(ConstructionTools toolName)throws RemoteException;
	
	/**
	 * Sets a number of pickers called by specific name with String parameter. For now it is: ConstructionTools.STANDARD_ROTATIONS
	 * @param toolName, the name of the picker.
	 * @param parameter, String parameter.
	 */
	public void setConstructionToolSpecPicker(ConstructionTools toolName, String parameter)throws RemoteException;
	
	/**
	 * Sets a number of pickers called by specific name with Integer parameter. For now it is: ConstructionTools.ON_CHOSEN_CONNECTOR_NR
	 * @param toolName, the name of the picker.
	 * @param parameter, Integer parameter.
	 */
	public void setConstructionToolSpecPicker(ConstructionTools toolName, int parameter)throws RemoteException;
	
	/**
	 * Sets a picker for adjusting controller of module(s). 
	 * @param controllerLocationDirectory, the directory where the controller can be located.
	 */
	public void setAdjustControllerPicker(String controllerLocationDirectory)throws RemoteException;
	
	
	
	/**
	 * @param toolName
	 */
	public void setLabelingToolSpecPicker(LabeledEntities entityName,LabelingTools toolName)throws RemoteException;
	
	/**
	 * Removes all modules (robot(s)) from simulation environment.
	 */
	public void removeAllModules() throws RemoteException;
	
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
	
	public Module getModuleCountingFromEnd(int amountFromLastMode ) throws RemoteException;
	
	public void moveToNextConnector(SupportedModularRobots supportedMRmoduleType,int connectorNr) throws RemoteException;
	
	public String getModuleCountingFromEndType(int amountFromLastMode ) throws RemoteException;
}
