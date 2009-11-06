package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ussr.builder.genericTools.RemoveModule;
import ussr.physics.jme.pickers.PhysicsPicker;
import ussr.physics.jme.pickers.Picker;

public interface BuilderControlInter extends Remote {
	

	/**
	 * Attaches specific picker(left side of the mouse selection) to remote simulation.
	 * @param builderSupportingPicker, the picker supported by builder.	
	 */
	public void setPicker(BuilderSupportingPickers builderSupportingPicker)throws RemoteException;
	
	

}
