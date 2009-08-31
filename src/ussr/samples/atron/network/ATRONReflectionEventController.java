package ussr.samples.atron.network;

import ussr.model.Module;
import ussr.network.EventConnection;
import ussr.network.ReflectionConnection;
import ussr.physics.PhysicsLogger;
import ussr.samples.ReflectionEventController;
import ussr.samples.ReflectionEventHelper;
import ussr.samples.atron.ATRONController;

public class ATRONReflectionEventController extends ATRONController implements ReflectionEventController {
    private ReflectionConnection rcConnection;
    private EventConnection eventConnection;
    private boolean isActive = false;

    private void printTopology(){
    	for(int i=0;i<8;i++) {
    		if(isConnected(i)) {
    			System.out.println("Module "+getName()+" is connected on connector "+i);
    		}
    	}
    }
    
    public void activate() {
    	super.setup();
    	printTopology();
        ReflectionEventHelper.initializeAndActivate(this);
    }
    
    public void handleMessage(byte[] message, int messageSize, int channel) {
        if(!isActive) return;
    	if(eventConnection.isReady()) {
    		eventConnection.sendEvent("handleMessage", new Object[]{message, messageSize, channel});
    	}
    	else {
    		System.err.println(getName()+": Event connection not ready, throw away package..");
    	}
    }
    public void setEventConnection(EventConnection eventConnection) {
        this.eventConnection = eventConnection;
    }
    public void setRcConnection(ReflectionConnection rcConnection) {
        this.rcConnection = rcConnection;
    }

    public void setActive() {
        this.isActive = true;
    }
}
