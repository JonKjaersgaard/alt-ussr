/**
 * 
 */
package ussr.samples.odin;

import java.util.HashSet;
import java.util.Set;

import ussr.model.NativeController;
import ussr.model.NativeControllerProvider;
import ussr.samples.atron.ATRONController;


/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class OdinNativeController extends NativeController {
	
	private class Link extends OdinController implements NativeControllerProvider {
		@Override
		public void activate() {
			OdinNativeController.this.activate();
		}
		public int getRole() { return OdinNativeController.this.getRole(); }

		public synchronized void handleMessage(byte[] message, int messageSize, int channel) {
	        if(eventLock!=null) {
	            synchronized(eventLock) {
	                eventLock.notify();
	                eventLock = null;
	            }
	        }
	        nativeHandleMessage(getInitializationContext(), message, messageSize, channel);
	    }

	}

	public OdinNativeController(String nativeLibraryName) {
		super(nativeLibraryName);
		this.setInternalController(new Link());
	}

    private native void nativeHandleMessage(int context, byte[] message, int messageSize, int channel);

	

}