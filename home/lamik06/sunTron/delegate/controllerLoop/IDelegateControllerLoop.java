package sunTron.delegate.controllerLoop;

import sunTron.API.SunTronDelegateAPIImpl;

public interface IDelegateControllerLoop {

	public abstract void setATRONDelegateAPI(SunTronDelegateAPIImpl atronDelegateAPI);
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
