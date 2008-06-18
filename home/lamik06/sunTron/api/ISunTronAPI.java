package sunTron.api;


import sunTron.futures.Future;





/**
 * Temporary interface wrt. Sun SPOT extensions to the IATRONController interface
 * @author lamik06@student.sdu.dk
 *
 */
public interface ISunTronAPI {
	public byte sendRadioMessage(byte[] message, int destination);
	public void sleep(long delay);
	public void activate();
	
	public void addActiveFuturesTable(String tmpKey,Future f);
	public void removeActiveFuturesTable(String tmpKey);
	public boolean activeFutures();
	public void waitForAllActiveFutures();
	
	public Future extend(int connectNo);
	public Future retract(int connectNo);
	public Future rotateTo(int targetInDegrees);
	public void addDebugInfo(String debugInfo);
	public String getDebugInfo();

	
	public Future moveLoopTo(IControllerLoop controllerLoop, String target);
	public Future receiveLoopFrom(IControllerLoop controllerLoop, String sender);
//	public Future moveLoopFrom(String target);
    /**
     * Move the main joint to the initial
     *  position (zero degrees)
     */
    public void home();

    /**
     * Perform any required initialization of the hardware (not required in simulation)
     */
    public void setup();
    
    /**
     * Get the name of the module
     * @return the name of the module
     */
	public String getName();

	/**
	 * Test whether the main joint of the module is currently rotation
	 * @return true if the module is rotating, false otherwise
	 */
	public boolean isRotating();

	/**
	 * Set whether operations such as rotating and connecting should block until
	 * they have completed
	 * @param blocking true if operations should block, false otherwise
	 */
	public void setBlocking(boolean blocking);

	/**
	 * Get the main joint position in terms of the current hemisphere (0-3 both inclusive)
	 * @return the joint hemisphere position
	 * @see #rotate(int)
	 */
	public int getJointPosition();

	/**
	 * Rotate the main joint to a specific joint position in terms of the hemisphere (0-3 both inclusive)
	 * @param dir the hemisphere position to rotate to
	 * @see #getJointPosition()
	 */
	public void rotate(int dir);

	/**
	 * Rotate the main joint a number of degrees
	 * @param degrees the number of degrees to rotate
	 */
	public void rotateDegrees(int degrees);

	/**
	 * Rotate the main joint to a specific degree position
	 * @param degrees the position in degrees to which to rotate
	 */
	public Future rotateToDegreeInDegrees(int degrees);

	/**
	 * Rotate the main jount to a specific radian position
	 * @param rad the position in radians to which to rotate
	 */
	public void rotateToDegree(float rad);

	/**
	 * Get the current simulation real time in seconds since the simulation started
	 * @return the current simulation time
	 */
	public float getTime();

	/**
	 * Get the position of the main joint in radians
	 * @return position of the main joint in radians
	 */
	public float getAngularPosition();

	/**
	 * Get the position of the main joint in degrees
	 * @return position of the main joint in degrees
	 */
	public int getAngularPositionDegrees();

	/**
	 * Disconnect all connectors
	 */
	public void disconnectAll();

	/**
	 * Connect all connectors
	 */
	public void connectAll();

	/**
	 * Test whether a specific connector is close enough to another connector to enable it to connect.
	 * Only male connectors can be considered able to connect. 
	 * @param connector the number of the connector to test
	 * @return true if there is another connector to which this connector can connect, false otherwise
	 */
	public boolean canConnect(int connector);

	/**
	 * Test whether a specific connector can disconnect, meaning that it is a male connector currently
	 * connected to another module.
	 * @param connector the number of the connector to test
	 * @return true if the connecto can disconnect, false otherwise
	 */
	public boolean canDisconnect(int connector);

	/**
	 * Test if a specific connector is a male connector
	 * @param connector the connector to test
	 * @return true if the connector is a male connector, false otherwise
	 */
	public boolean isMale(int connector);

	/**
	 * Attempt to connect this connector.  Will only perform the connection if another module is available
	 * for connection (unlike the physical module which will actuate the connector regardless of whether
	 * it is possible to connect) 
	 * @param connector the connector to connect
	 */
	public Future connect(int connector);

	/**
	 * Attempt to disconnect this connector, will work if the connector 
	 * @param connector the connector to disconnect
	 */
	public void disconnect(int connector);

	/**
	 * Test if a specific connector is currently connected
	 * @param connector the connector to connect
	 * @return true if the connector is connected, false otherwise
	 */
	public boolean isConnected(int connector);

	/**
	 * Test is a specific connector is currently disconnected
	 * @param connector the connector to disconnect
	 * @return true if the connector is disconnected, false otherwise
	 */
	public boolean isDisconnected(int connector);

	/**
	 * Rotate the main joint continuously in the given direction.  Maximal speed is indicated
	 * by the value 1 (negative meaning rotate in the opposite direction).
	 * @param dir the direction and velocity of the rotation
	 */
	public void rotateContinuous(float dir);

	/**
	 * Cause the main joint to stop rotating and presumably break actively (the latter is not implemented yet)
	 */
	public void centerBrake();

	/**
	 * Causes the main joint to stop rotating
	 */
	public void centerStop();

	/**
	 * Test is another connector is within communication range
	 * @param connector the connector to test
	 * @return true if another connector is nearby, false otherwise
	 */
	public boolean isOtherConnectorNearby(int connector);

	/**
	 * Test if there is an object in the proximity of a specific connector. <strong>Note:</strong> currently only
	 * tests for the proximity of obstacles, not other modules.
	 * @param connector the connector for which to test proximity
	 * @return true if there is an obstacle nearby, false otherwise
	 */
	public boolean isObjectNearby(int connector);

	/**
	 * Send a message on a given connector
	 * @param message the buffer containing the bytes to send
	 * @param messageSize the number of bytes to send from the buffer
	 * @param connector the connector to send the message on
	 * @return the value 1 if the message was transmitted to another module, 0 otherwise
	 */
	public byte sendMessage(byte[] message, byte messageSize, byte connector);

	/**
	 * Controllers must overwrite this method to receive messages 
	 * @param message a buffer holding message delivered to this module
	 * @param messageSize the number of bytes of message in the buffer
	 * @param channel the connector the message was received on
	 */
	public void handleMessage(byte[] message, int messageSize, int channel);

	/**
	 * Read the value of the X tilt sensor. <strong>Note:</strong> does not match physical module perfectly.
	 * @return the current X tilt value of the module 
	 */
	public byte getTiltX();

    /**
     * Read the value of the Y tilt sensor. <strong>Note:</strong> does not match physical module perfectly.
     * @return the current Y tilt value of the module 
     */
	public byte getTiltY();

    /**
     * Read the value of the Z tilt sensor. <strong>Note:</strong> does not match physical module perfectly.
     * @return the current Z tilt value of the module 
     */
	public byte getTiltZ();
	
	/**
	 * Yield control to another module.  Should be called regularly to ensure fair scheduling in the
	 * simulation.
	 */
	public void yield();
	
	
	
}
