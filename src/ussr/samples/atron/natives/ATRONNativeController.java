/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.natives;

import ussr.model.NativeController;
import ussr.model.NativeControllerProvider;
import ussr.samples.atron.ATRONController;


/**
 * Native controller that loads the controller functionality from a specific native library
 * 
 * @author ups
 */
public class ATRONNativeController extends NativeController {
	
	protected class Link extends ATRONController implements NativeControllerProvider {
		@Override
		public void activate() {
			ATRONNativeController.this.activate();
		}
		public int getRole() { return ATRONNativeController.this.getRole(); }

		public synchronized void handleMessage(byte[] message, int messageSize, int channel) {
	        if(eventLock!=null) {
	            synchronized(eventLock) {
	                eventLock.notify();
	                eventLock = null;
	            }
	        }
	        nativeHandleMessage(getInitializationContext(), message, messageSize, channel);
	    }

		void delay_internal(int wait) {
		    super.delay(wait);
		}
	}

	public ATRONNativeController(String nativeLibraryName) {
		super(nativeLibraryName);
		this.setInternalControllerHook();
	}

	protected void setInternalControllerHook() {
	    this.setInternalController(new Link());
	}
	
    private native void nativeHandleMessage(int context, byte[] message, int messageSize, int channel);

}
