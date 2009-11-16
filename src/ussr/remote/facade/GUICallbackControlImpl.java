package ussr.remote.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ussr.aGui.tabs.controllers.ConstructRobotTabController;
import ussr.aGui.tabs.views.constructionTabs.ConstructRobotTab;
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
	



}
