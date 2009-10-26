package ussr.samples.odin.natives;

import ussr.model.NativeController;
import ussr.model.NativeControllerProvider;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.odin.OdinTinyOSController;

public class OdinNativeTinyOSController extends NativeController {
	
	protected class Link extends OdinTinyOSController implements NativeControllerProvider {

		public Link(String type) {
			this.type = type;
		}

		public void stubCall() {
			/* need to get something from the simulation? */
		}
		
		@Override
		public synchronized void activate() {
			OdinNativeTinyOSController.this.activate();
		}
		
	    public synchronized void physicsTimeStepHook(PhysicsSimulation simulation) {
	    	//we issue a callback to the controller every 1ms for the timer/counter
	    	//for the moment, this implies a timestepSize == 1ms
	    	if(PhysicsParameters.get().getPhysicsSimulationStepSize() != 0.001f)
	    		PhysicsLogger.log("PhysicsStepSize does not allow for accurate simulation!");
	    	nativeMillisecondElapsed(getInitializationContext());
	    }

		public int getRole() {
			return OdinNativeTinyOSController.this.getRole();
		}
		
		public synchronized void sendDone(byte[] msg, int error, int connector) {
	        if(eventLock!=null) {
	            synchronized(eventLock) {
	                eventLock.notify();
	                eventLock = null;
	            }
	        }
	        this.sendBusy = false;	        
			nativeSendDone(getInitializationContext(), error, connector);
		}
		
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

  public OdinNativeTinyOSController(String type, String nativeLibraryName) {
	  super(nativeLibraryName);
	  this.setInternalControllerHook(type);
	  //maybe set here the PhysicsParameters so to use events?
  }

  protected void setInternalControllerHook(String type) {
	  this.setInternalController(new Link(type));
  }
  
	// in the future we might want to map the original pointer
	// from the byte[] object, for now we assume the user can
	// just send one at a time
//	private native void nativeSendDone(byte[] msg, int error, int connector);
  private native void nativeSendDone(int context, int error, int connector);
	
  private native void nativeHandleMessage(int context, byte[] message, int messageSize, int channel);

  private native void nativeMillisecondElapsed(int context);
   
  // as ussr_stub called from within tinyos code, this can be used for general sync purposes
  public native void native_stub(int context);

}