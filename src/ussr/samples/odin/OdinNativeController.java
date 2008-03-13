/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.odin;

import ussr.model.NativeController;
import ussr.model.NativeControllerProvider;


/**
 * Odin controller for loading the controller implementation from a native library
 * 
 * @author ups
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
