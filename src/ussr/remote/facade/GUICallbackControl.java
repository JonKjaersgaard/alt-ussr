package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ussr.aGui.tabs.controllers.AssignBehaviorsTabController;
import ussr.builder.enumerations.ConstructionTools;

public interface GUICallbackControl extends Remote {

	/**
	 * Adapts Construct Robot tab to the tool chosen by user. To be more precise disables and enables relevant components of the tab. 
	 * @param chosenTool,the tool chosen by the user in Construct Robot tab.
	 */
	public void adaptConstructRobotTabToChosenTool(ConstructionTools chosenTool)throws RemoteException;


	/**
	 * Populates the table in Assign behavior tab with labels of entity(module, connector and so on) selected in simulation environment.
	 * @param labels, the string of labels separated by comma to populate the table with.
	 */
	public void updateTableWithLabels(String labels) throws RemoteException;
}
