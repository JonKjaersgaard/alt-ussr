package atron.delegate;

public interface IDelegateControllerLoop {

	public abstract void setATRONDelegateAPI(ATRONDelegateAPI atronDelegateAPI);
	public abstract void controllerLoop();
	/**
	 * 
	 * @param irMessage
	 */
	public abstract void handleIRCommunication(byte[] irMessage);
	/**
	 * 
	 * @param wlanMessage
	 */
	public abstract void handleWireLessCommunication(byte[] wlanMessage);
}
