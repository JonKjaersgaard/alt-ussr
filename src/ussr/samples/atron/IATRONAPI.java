package ussr.samples.atron;
/**
 * ATRON Controller interface that provides the ATRON API
 * 
 * @author Modular Robots @ MMMI
 */
public interface IATRONAPI {

	public abstract void home();

	public abstract String getName();

	public abstract boolean isRotating();

	public abstract void setBlocking(boolean blocking);

	public abstract int getJointPosition();

	public abstract void rotate(int dir);

	public abstract void rotateDegrees(int degrees);

	public abstract void rotateToDegreeInDegrees(int degrees);

	public abstract void rotateToDegree(float rad);

	public abstract float getTime();

	public abstract float getAngularPosition();

	public abstract int getAngularPositionDegrees();

	public abstract void disconnectAll();

	public abstract void connectAll();

	public abstract boolean canConnect(int i);

	public abstract boolean canDisconnect(int i);

	public abstract boolean isMale(int i);

	public abstract void connect(int i);

	public abstract void disconnect(int i);

	public abstract boolean isConnected(int i);

	public abstract boolean isDisconnected(int i);

	public abstract void rotateContinuous(float dir);

	public abstract void centerBrake();

	public abstract void centerStop();

	public abstract boolean isOtherConnectorNearby(int connector);

	public abstract boolean isObjectNearby(int connector);

	public abstract byte sendMessage(byte[] message, byte messageSize,
			byte connector);

	/**
	 * Controllers should generally overwrite this method to receieve messages 
	 * @param message
	 * @param messageSize
	 * @param channel
	 */
	public abstract void handleMessage(byte[] message, int messageSize,
			int channel);

	// Note: hard-coded to do forward-backward tilt sensoring for axles
	public abstract byte getTiltX();

	// Note: hard-coded to do side-to-side tilt sensoring for driver and axles
	public abstract byte getTiltY();

	public abstract byte getTiltZ();

}