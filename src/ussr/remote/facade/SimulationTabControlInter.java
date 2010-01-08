package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import ussr.description.geometry.VectorDescription;

public interface SimulationTabControlInter extends Remote {
	
	
	public void setModulePosition(int moduleID,VectorDescription newModulePosition)throws RemoteException;
	
	public VectorDescription getModulePosition(int moduleID)throws RemoteException;
	
	
	public void deleteModules(List<Integer> ids)throws RemoteException;

}
