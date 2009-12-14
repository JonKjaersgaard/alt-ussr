package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import ussr.description.geometry.VectorDescription;

public interface XMLSimulationProviderInter extends Remote {

	
	public String getRobotMorphologyLocation()throws RemoteException;
	
	public Map<String, VectorDescription> getRobotModules() throws RemoteException;
}
