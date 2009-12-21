package ussr.remote.facade;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import ussr.description.setup.WorldDescription;
import ussr.description.setup.WorldDescription.CameraPosition;
import ussr.description.setup.WorldDescription.TextureDescription;
import ussr.physics.jme.JMESimulation;

public class WorldDescriptionControlWrapper extends UnicastRemoteObject implements WorldDescriptionControlInter {


	private WorldDescription worldDescription;


	public WorldDescriptionControlWrapper(WorldDescription worldDescription)throws RemoteException {
		this.worldDescription =worldDescription;
	}

	/**
	 * Returns the size of one edge of the underlying plane.
	 */
	public int getPlaneSize()throws RemoteException{
		return worldDescription.getPlaneSize();
	}

	/**
	 * Returns whether is used a plane or a generated texture
	 */
	public boolean theWorldIsFlat()throws RemoteException{
		return worldDescription.theWorldIsFlat();
	}

	/**
	 *  Returns whenever the background/overhead have clouds etc.
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
	
	
	public CameraPosition getCameraPosition()throws RemoteException{
		return worldDescription.getCameraPosition();
	}

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
