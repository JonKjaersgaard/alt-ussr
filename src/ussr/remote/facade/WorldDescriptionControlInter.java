package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WorldDescriptionControlInter extends Remote {


	public int getPlaneSize()throws RemoteException;
	
	public void setPlaneSize(int size)throws RemoteException;
}
