package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ussr.builder.enumerations.ConstructionTools;

public interface GUICallbackControl extends Remote {

	/**
	 * Adapts Construct Robot tab to the tool chosen by user. To be more precise disables and enables relevant components of the tab. 
	 * @param chosenTool,the tool chosen by the user in Construct Robot tab.
	 */
  public void adaptConstructRobotTabToChosenTool(ConstructionTools chosenTool)throws RemoteException;
  
}
