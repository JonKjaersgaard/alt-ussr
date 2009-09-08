package ussr.samples.atron.natives;

import com.jme.scene.shape.Arrow;

import ussr.model.NativeController;
import ussr.model.NativeControllerProvider;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.atron.ATRONTinyOSController;

public class ATRONNativeTinyOSController extends NativeController {

	protected class Link extends ATRONTinyOSController implements NativeControllerProvider {
		@Override
		public synchronized void activate() {
			ATRONNativeTinyOSController.this.activate();
		}
	    public synchronized void physicsTimeStepHook(PhysicsSimulation simulation) {
	    	//we issue a callback to the controller every 1ms for the timer/counter
	    	//for the moment, this implies a timestepSize == 1ms
	    	if(PhysicsParameters.get().getPhysicsSimulationStepSize() != 0.001f)
	    		PhysicsLogger.log("PhysicsStepSize does not allow for accurate simulation!");
	    	nativeMillisecondElapsed(getInitializationContext());
	    	
	    	//this retrieves the rotation
	    	//if(this.getName().contains("0"))
	    		//System.out.println(this.module.referenceNode.getLocalRotation().w + " " + this.module.referenceNode.getLocalRotation().x + " " + this.module.referenceNode.getLocalRotation().y + " " + this.module.referenceNode.getLocalRotation().z);
	    	//this retrieves the translation
	    	//if(this.getName().contains("5"))
	    		//System.out.println(this.module.referenceNode.getLocalTranslation().x + " " + this.module.referenceNode.getLocalTranslation().y + " " + this.module.referenceNode.getLocalTranslation().z);
	    	
	    }
		public int getRole() { return ATRONNativeTinyOSController.this.getRole(); }
		
		//making the callbacks/events synchronized is equivalent
		//to running each interrupt with the other interrupts disabled,
		//so it's ok to use atomics in TinyOS to reassure the nesC race analyzer
		//(should it be a tunable?).
		//(actually since we now have only one event-delivering thread
		// it should never happen anyway that a callback is interrupted
		// by the execution of another one)
		public synchronized void sendDone(byte[] msg, int error, int connector) {
	        if(eventLock!=null) {
	            synchronized(eventLock) {
	                eventLock.notify();
	                eventLock = null;
	            }
	        }
	        this.sendBusy = false;	        
	        //uncomment once implemented!
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
	
	public ATRONNativeTinyOSController(String nativeLibraryName) {
		super(nativeLibraryName);
		this.setInternalControllerHook();
		//maybe set here the PhysicsParameters so to use events?
	}

	protected void setInternalControllerHook() {
	    this.setInternalController(new Link());
	}
	
	// in the future we might want to map the original pointer
	// from the byte[] object, for now we assume the user can
	// just send one at a time
//	private native void nativeSendDone(byte[] msg, int error, int connector);
	private native void nativeSendDone(int context, int error, int connector);
	
    private native void nativeHandleMessage(int context, byte[] message, int messageSize, int channel);

    private native void nativeMillisecondElapsed(int context);
 
}
