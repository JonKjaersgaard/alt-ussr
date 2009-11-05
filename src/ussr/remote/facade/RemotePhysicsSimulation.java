package ussr.remote.facade;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.jme.scene.state.LightState;
import com.jme.scene.state.WireframeState;

import ussr.description.Robot;
import ussr.description.setup.WorldDescription;

/**
 * Remote version of the standard PhysicsSimulation interface.
 * 
 * Note to developers: this interface can be extended beyond the standard simulation interface
 * to provide whatever capabilities are required for remote control of simulation; the 
 * RemotePhysicsSimulationImpl class must simply implement the corresponding method. 
 * 
 * @author ups
 *
 */
public interface RemotePhysicsSimulation extends Remote {

    /**
     * Define the default robot to be used in this simulation, including visual appearance,
     * physical characteristics, and controller functionality.
     * @param bot the one description of the default robot used in this simulation
     */
    public void setRobot(Robot bot) throws RemoteException;

    /**
     * Define the robots to be used in this simulation, including visual appearance,
     * physical characteristics, and controller functionality.
     * @param bot the description of one or more robots used in this simulation
     */
    public void setRobot(Robot bot, String type) throws RemoteException;
    
    /**
     * Define the world in which the robots are simulation, including starting configuration
     * of the robot and physical obstacles.
     * @param world the descriptoin of the world used for the simulation
     */
    public void setWorld(WorldDescription world) throws RemoteException;

    /**
     * Start the simulation
     */
    public void start() throws RemoteException;
    /**
     * Stop the simulation
     *
     */
    public void stop() throws RemoteException;

    /**
     * Test whether the simulation is currently paused
     * @return true is the simulation is paused, false otherwise
     */
    public boolean isPaused() throws RemoteException;
    
    /**
     * Set whether the simulation is paused.
     * @param paused, true if simulation should be paused. 
     */
    public void setPause(boolean paused) throws RemoteException;
    
    /**
     * Test whether the simulation has been stopped
     * @return true if the simulation has been stopped, false otherwise
     */
    public boolean isStopped() throws RemoteException;

    /**
	 * Set whether the simulation is running in real time.
	 * @param realtime, true if the simulation should running in real time, false - fast mode.
	 */
    public void setRealtime(boolean realtime) throws RemoteException;
    
    /**
	 * Set whether the simulation is single step.
	 * @param singleStep,the state of simulation step.
	 */
    public void setSingleStep(boolean singleStep)throws RemoteException;
    
	
	/**
	 * Returns the state of showing physics. 
	 * @return showPhysics, the state of showing physics.
	 */
	public boolean isShowPhysics()throws RemoteException; 
    
    /**
	 * Sets the state of showing physics.
	 * @param showPhysics, the state of showing physics.
	 */
	public void setShowPhysics(boolean showPhysics)throws RemoteException;
	
	
	/**
	 *  Returns the wireFrame.
	 * @return wireState, the wireFrame.
	 */
	public WireframeState getWireFrame() throws RemoteException; 
	
	/**
	 *  Returns the state of showing wireFrame.
	 * @return wireState,  the state of showing wireFrame.
	 */
	public boolean isWireFrameEnabled()throws RemoteException;
	
	
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
	public void setShowNormals(boolean showNormals)throws RemoteException;
	
	/**
	 * Returns the state of showing lights. 
	 * @return lightState, the state of showing lights.  
	 */
	public LightState getLightState() throws RemoteException; 
	
	
	/**
	 * Checks whenever lights are enabled. 
	 * @return boolean, true for shown.  
	 */
	public boolean isLightStateShowing() throws RemoteException;
	
	/**
	 * Sets whenever lights are shown.
	 * @param enabled, true for showing lights.
	 */
	public void setLightStateShowing(boolean enabled) throws RemoteException;
		
	/**
	 * Sets the state of showing lights.
	 * @param lightState, the state of showing lights.
	 */
	public void setLightState(LightState lightState) throws RemoteException;
	
	
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
	
	
	
		
    /**
     * Get the current simulation time
     * @return the simulation time
     */
    public float getTime() throws RemoteException;
    
    /**
	 *  Sets whenever wire state is enabled(shown).
	 * @return enabled, the state of showing wireFrame.
	 */
	public void setWireFrameEnabled(boolean enabled)throws RemoteException; 
	
}
