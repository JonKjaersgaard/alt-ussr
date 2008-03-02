package atron.delegate;

public interface IDelegateController {
	public void setATRONDelegateAPI(ATRONDelegateAPI atronDelegateAPI);
	public void controllerLoop();
	public void handleIRCommunication(byte[] irMessage);
	public void handleWireLessCommunication(byte[] wlanMessage);
}
