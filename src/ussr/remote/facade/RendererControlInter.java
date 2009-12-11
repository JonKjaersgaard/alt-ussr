package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;
import com.jme.scene.state.LightState;
import com.jme.scene.state.WireframeState;

/**
 * Remote version of rendering control.
 * @author Konstantinas
 */
public interface RendererControlInter extends Remote {

	/**
	 * Sets the state of showing physics.
	 * @param showPhysics, the state of showing physics.
	 */
	public void setShowPhysics(boolean showPhysics) throws RemoteException;
	
	/**
	 * Returns the state of showing physics. 
	 * @return showPhysics, the state of showing physics.
	 */
	public boolean isShowingPhysics()throws RemoteException; 
	
	
	/**
	 *  Sets whenever wire state is enabled(shown).
	 * @return enabled, the state of showing wireFrame.
	 */
	public void setShowWireFrame(boolean enabled) throws RemoteException;
	
	/**
	 *  Returns the wireFrame.
	 * @return wireState, the wireFrame.
	 */
	public WireframeState getWireFrame()throws RemoteException;
	
	/**
	 *  Returns the state of showing wireFrame.
	 * @return wireState,  the state of showing wireFrame.
	 */
	public boolean isShowingWireFrame()throws RemoteException;
	
	/**
	 * Returns the state of showing bounds. 
	 * @return showBounds, the state of showing bounds.
	 */
	public boolean isShowingBounds()throws RemoteException;

	/**
	 * Sets the state of showing bounds.
	 * @param showBounds, the state of showing bounds.
	 */
	public void setShowBounds(boolean showBounds)throws RemoteException;
	
	/**
	 * Returns the state of showing normals. 
	 * @return showNormals, the state of showing normals. 
	 */
	public boolean isShowingNormals()throws RemoteException; 

	/**
	 * Sets the state of  showing normals.
	 * @param showNormals, the state of  showing normals.
	 */
	public void setShowNormals(boolean showNormals) throws RemoteException;
	
	/**
	 * Returns the state for showing lights. 
	 * @return lightState, the state for showing lights.  
	 */
	public LightState getLightState() throws RemoteException;
	
	
	/**
	 * Checks whenever lights are shown. 
	 * @return boolean, true for shown.  
	 */
	public boolean isLightStateShowing() throws RemoteException;
	
	/**
	 * Sets whenever lights are shown.
	 * @param enabled, true for showing lights.
	 */
	public void setShowLights(boolean enabled) throws RemoteException;
	
	/**
	 * Sets the state of showing lights.
	 * @param lightState, the state of showing lights.
	 */
	public void setLightState(LightState lightState)throws RemoteException;
	
	/**
	 * Returns the state of showing the depth of the buffer.
	 * @return showDepth, the state of showing the depth of the buffer.
	 */
	public boolean isShowingDepth()throws RemoteException;

	/**
	 *  Sets the state of showing the depth of the buffer.
	 * @param showDepth, the state of showing the depth of the buffer.
	 */
	public void setShowDepth(boolean showDepth)throws RemoteException;

}
