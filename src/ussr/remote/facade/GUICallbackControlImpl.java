package ussr.remote.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ussr.aGui.tabs.controllers.AssignBehaviorsTabController;
import ussr.aGui.tabs.controllers.ConstructRobotTabController;
import ussr.builder.enumerations.ConstructionTools;

public class GUICallbackControlImpl extends UnicastRemoteObject implements GUICallbackControl {

	public GUICallbackControlImpl() throws RemoteException {
		super();
	}

	/**
	 * Adapts Construct Robot tab to the tool chosen by user. To be more precise disables and enables relevant components of the tab. 
	 * @param chosenTool,the tool chosen by the user in Construct Robot tab.
	 */
	public void adaptConstructRobotTabToChosenTool(ConstructionTools chosenTool)throws RemoteException{
		ConstructRobotTabController.adaptConstructRobotTabToChosenTool(chosenTool);
	};
	
	/**
	 * Populates the table in Assign behavior tab with labels of entity(module, connector and so on) selected in simulation environment.
	 * @param labels, the string of labels separated by comma to populate the table with.
	 */
	public void updateTableWithLabels(String labels) throws RemoteException{
		AssignBehaviorsTabController.updateTableLabels(labels);
	}
	
	/**
	 * Sets the global ID of the module selected in simulation environment.
	 * @param selectedModuleID, the global ID of the module selected in simulation environment.
	 */
	public void setSelectedModuleID(int selectedModuleID){
		ConstructRobotTabController.setSelectedModuleID(selectedModuleID);
	}
}
