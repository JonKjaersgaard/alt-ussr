package ussr.remote.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ussr.description.setup.WorldDescription;
import ussr.description.setup.WorldDescription.CameraPosition;
import ussr.description.setup.WorldDescription.TextureDescription;


/**
 * Wrapper for a world description control allowing it to be used as a remote object.
 * (Used on the simulation side.)
 */
public class WorldDescriptionControlWrapper extends UnicastRemoteObject implements WorldDescriptionControlInter {

	/**
	 * World description object
	 */
	private WorldDescription worldDescription;

	/**
	 * Wrapper for a world description control allowing it to be used as a remote object.
      * (Used on the simulation side.)
	 * @param worldDescription, world description object.
	 */
	public WorldDescriptionControlWrapper(WorldDescription worldDescription)throws RemoteException {
		this.worldDescription =worldDescription;
	}

	/**
	 * Returns the size of one edge of the underlying plane.
	 * @return the size of one edge of the underlying plane.
	 */
	public int getPlaneSize()throws RemoteException{
		return worldDescription.getPlaneSize();
	}
	
	/**
	 * Returns whether is used a plane or a generated texture.
	 * @return whether is used a plane or a generated texture.
	 */
	public boolean theWorldIsFlat()throws RemoteException{
		return worldDescription.theWorldIsFlat();
	}

	/**
	 * Returns if the background/overhead have clouds etc.
	 * @return if the background/overhead have clouds etc.
	 * @throws RemoteException
	 */
	public boolean hasBackgroundScenery()throws RemoteException{
		return worldDescription.hasBackgroundScenery();
	}
	
	/**
     * Returns whenever light or heavy obstacles are set in simulation environment.
     */
	public boolean hasHeavyObstacles()throws RemoteException{
		return worldDescription.hasHeavyObstacles();
	}
	
	/**
	 * Returns the camera position in simulation environment.
	 * @return the camera position in simulation environment.
	 */
	public CameraPosition getCameraPosition()throws RemoteException{
		return worldDescription.getCameraPosition();
	}

	/**
	 * Returns the plane texture.
	 * @return the plane texture.
	 */
	public TextureDescription getPlaneTexture()throws RemoteException{
		return worldDescription.getPlaneTexture();
	}
	
	public String getPlaneTextureFileName()throws RemoteException{
		return worldDescription.getPlaneTexture().getFileName();
	}
	public boolean getIsFrameGrabbingActive()throws RemoteException{
		return worldDescription.getIsFrameGrabbingActive();
	}
	
	
	
	
}
