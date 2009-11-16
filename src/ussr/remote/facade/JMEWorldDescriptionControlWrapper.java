package ussr.remote.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ussr.description.setup.WorldDescription;
import ussr.physics.jme.JMESimulation;

public class JMEWorldDescriptionControlWrapper extends UnicastRemoteObject implements WorldDescriptionControlInter {


	private WorldDescription worldDescription;


	public JMEWorldDescriptionControlWrapper(WorldDescription worldDescription)throws RemoteException {
		this.worldDescription =worldDescription;
	}

	/**
	 * Returns the size of one edge of the underlying plane.
	 */
	public int getPlaneSize()throws RemoteException{
		return worldDescription.getPlaneSize();
	}


	public void setPlaneSize(int size)throws RemoteException{
		worldDescription.setPlaneSize(size);
	}

	/**
	 * Returns whether is used a plane or a generated texture
	 */
	public boolean theWorldIsFlat()throws RemoteException{
		return worldDescription.theWorldIsFlat();
	}

	/**
	 *  Returns if the background/overhead have clouds etc.
	 */
	public boolean hasBackgroundScenery()throws RemoteException{
		return worldDescription.hasBackgroundScenery();
	}
	
	/**
     * Returns if light or heavy obstacles are set in simulation environment.
     */
	public boolean hasHeavyObstacles()throws RemoteException{
		return worldDescription.hasHeavyObstacles();
	}



}
