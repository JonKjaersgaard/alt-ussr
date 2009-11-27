package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface XMLSimulationProviderInter extends Remote {

	
	public String getRobotMorphologyLocation()throws RemoteException;
	
	
}
