package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import ussr.builder.enumerations.SupportedModularRobots;
import ussr.builder.enumerations.tools.ConstructionTools;
import ussr.builder.enumerations.tools.LabeledEntities;
import ussr.builder.enumerations.tools.LabelingTools;

import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;

import ussr.model.Module;


/**
 * Provides builder(package "ussr.builder" ) with control of remote simulation.
 * @author Konstantinas
 */
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
	 * Sets the picker for reading labels of entities in simulation environment.
	 * @param entityName, supported entity name,
	 * @param toolName, supported tool name. 
	 */
	public void setLabelingToolReadLabels(LabeledEntities entityName,LabelingTools toolName)throws RemoteException;
	
	/**
	 * Sets the picker for assigning labels to entities in simulation environment.
	 * @param entityName, supported entity name.
	 * @param toolName,supported tool name.
	 * @param labels, the labels to be assigned.
	 * @throws RemoteException
	 */
	public void setLabelingToolAssignLabels(LabeledEntities entityName,LabelingTools toolName, String labels)throws RemoteException;
	
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
	
	/**
	 * Returns the type of the module according to its number sequence in the list of modules.
	 * @param moduleNr, number of the module in the list of modules.
	 * @return type, the type of the module.
	 */
	public String getModuleType(int moduleNr) throws RemoteException;
	
	/**
	 * Returns the list of IDs of all modules in simulation environment.
	 * @return the list of IDs of all modules in simulation environment.
	 */
	public List<Integer> getIDsModules()throws RemoteException;
	
	/**
	 * Loads robot morphology from robot xml file.
	 * @param fileDirectoryName, the directory of robot xml file.
	 */
	public void loadRobotXML(String fileDirectoryName) throws RemoteException;
	
	/**
	 * Colors connectors of all modules with color coding.
	 */
	public void  colorConnectorsModules()throws RemoteException;

	/**
	 * Restores module colors of connectors to original ones. 
	 */
	public void restoreOriginalColorsConnectors()throws RemoteException;
	
}
