package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

import ussr.description.setup.WorldDescription.CameraPosition;
import ussr.description.setup.WorldDescription.TextureDescription;

/**
 * Provides with remote access to world description of simulation.  
 * (Used on the simulation side.)
 * @author Konstantinas
 *
 */
public interface WorldDescriptionControlInter extends Remote {

	/**
	 * Returns the size of one edge of the underlying plane.
	 * @return the size of one edge of the underlying plane.
	 */
	public int getPlaneSize()throws RemoteException;
		
	/**
	 * Returns whether is used a plane or a generated texture.
	 * @return whether is used a plane or a generated texture.
	 */
	public boolean theWorldIsFlat()throws RemoteException;
	
	/**
	 * Returns if the background/overhead have clouds etc.
	 * @return if the background/overhead have clouds etc.
	 * @throws RemoteException
	 */
	public boolean hasBackgroundScenery()throws RemoteException;
	
	/**
	 * Returns if light or heavy obstacles are set in simulation environment.
	 * @return if light or heavy obstacles are set in simulation environment.
	 * @throws RemoteException
	 */
	public boolean hasHeavyObstacles()throws RemoteException;
	
	/**
	 * Returns the camera position in simulation environment.
	 * @return the camera position in simulation environment.
	 */
	public CameraPosition getCameraPosition()throws RemoteException;
	
	
	/**
	 * Returns the plane texture.
	 * @return the plane texture.
	 */
	public TextureDescription getPlaneTexture()throws RemoteException;
	
	/**
	 * Returns if frame grabbing is active or not.
	 * @return if frame grabbing is active or not.
	 */
	public boolean getIsFrameGrabbingActive()throws RemoteException;
	
	/**
	 * Returns the file name of texture used for ground plane.
	 * @return the file name of texture used for ground plane.
	 */
	public String getPlaneTextureFileName()throws RemoteException;
}
