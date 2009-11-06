package ussr.remote.facade;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.jme.scene.state.LightState;
import com.jme.scene.state.WireframeState;

import ussr.physics.jme.JMESimulation;

public class JMERendererControlWrapper extends UnicastRemoteObject implements SimulationRendererControlInter {

	private JMESimulation jmeSimulation;	

	public JMERendererControlWrapper(JMESimulation jmeSimulation)throws RemoteException {
		this.jmeSimulation = jmeSimulation;
	}

	/**
	 * Sets the state of showing physics.
	 * @param showPhysics, the state of showing physics.
	 */
	public void setShowPhysics(boolean showPhysics)throws RemoteException{
		jmeSimulation.setShowPhysics(showPhysics);
	}

	/**
	 * Returns the state of showing physics. 
	 * @return showPhysics, the state of showing physics.
	 */
	public boolean isShowingPhysics()throws RemoteException {
		return jmeSimulation.isShowingPhysics();
	}

	/**
	 *  Returns the wireFrame.
	 * @return wireState, the wireFrame.
	 */
	public WireframeState getWireFrame() throws RemoteException {		
		return jmeSimulation.getWireFrame();
	}

	/**
	 *  Returns the state of showing wireFrame.
	 * @return wireState,  the state of showing wireFrame.
	 */
	public boolean isShowingWireFrame()throws RemoteException {		
		return jmeSimulation.isShowingWireFrame();
	}

	/**
	 * Returns the state of showing bounds. 
	 * @return showBounds, the state of showing bounds.
	 */
	public void setShowWireFrame(boolean showWireframe)throws RemoteException {
		jmeSimulation.setShowWireFrame(showWireframe);	
	}

	/**
	 * Returns the state of showing bounds. 
	 * @return showBounds, the state of showing bounds.
	 */
	public boolean isShowingBounds() throws RemoteException {		
		return jmeSimulation.isShowingBounds();
	}

	/**
	 * Sets the state of showing bounds.
	 * @param showBounds, the state of showing bounds.
	 */
	public void setShowBounds(boolean showBounds) throws RemoteException {
		jmeSimulation.setShowBounds(showBounds);		
	}


	/**
	 * Returns the state of showing normals. 
	 * @return showNormals, the state of showing normals. 
	 */
	public boolean isShowingNormals()throws RemoteException{
		return jmeSimulation.isShowingNormals();
	} 

	/**
	 * Sets the state of  showing normals.
	 * @param showNormals, the state of  showing normals.
	 */
	public void setShowNormals(boolean showNormals) throws RemoteException{
		jmeSimulation.setShowNormals(showNormals);
	}

	/**
	 * Returns the state for showing lights. 
	 * @return lightState, the state for showing lights.  
	 */
	public LightState getLightState() throws RemoteException{
		return jmeSimulation.getLightState();
	}	

	/**
	 * Checks whenever lights are shown. 
	 * @return boolean, true for shown.  
	 */
	public boolean isLightStateShowing() throws RemoteException{
		return jmeSimulation.isLightStateShowing();
	}

	/**
	 * Sets whenever lights are shown.
	 * @param enabled, true for showing lights.
	 */
	public void setShowLights(boolean showLigths) throws RemoteException{
		jmeSimulation.setShowLights(showLigths);
	}

	/**
	 * Sets the state of showing lights.
	 * @param lightState, the state of showing lights.
	 */
	public void setLightState(LightState lightState)throws RemoteException {
		jmeSimulation.setLightState(lightState);
	}
	
	/**
	 * Returns the state of showing the depth of the buffer.
	 * @return showDepth, the state of showing the depth of the buffer.
	 */
	public boolean isShowingDepth()throws RemoteException{
		return jmeSimulation.isShowingDepth();
	}

	/**
	 *  Sets the state of showing the depth of the buffer.
	 * @param showDepth, the state of showing the depth of the buffer.
	 */
	public void setShowDepth(boolean showDepth)throws RemoteException{
		jmeSimulation.setShowDepth(showDepth);
	}



}
