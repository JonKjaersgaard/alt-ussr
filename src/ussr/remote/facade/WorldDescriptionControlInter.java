package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface WorldDescriptionControlInter extends Remote {

	/**
     * Returns the size of one edge of the underlying plane.
     */
	public int getPlaneSize()throws RemoteException;
	
	public void setPlaneSize(int size)throws RemoteException;
	
	/**
     * Returns whether is used a plane or a generated texture
     */
	public boolean theWorldIsFlat()throws RemoteException;
	
	/**
     *  Returns if the background/overhead have clouds etc.
     */
	public boolean hasBackgroundScenery()throws RemoteException;
	
	/**
     * Returns if light or heavy obstacles are set in simulation environment.
     */
	public boolean hasHeavyObstacles()throws RemoteException;
}
