package ussr.samples.mtran;

import ussr.network.EventConnection;
import ussr.network.ReflectionConnection;
import ussr.physics.PhysicsLogger;
import ussr.samples.ReflectionEventController;
import ussr.samples.ReflectionEventHelper;
import ussr.samples.atron.ATRONController;

public class MTRANReflectionEventController extends MTRANController implements ReflectionEventController {
    ReflectionConnection rcConnection;
    EventConnection eventConnection;
    private boolean isActive = false;

    public void activate() {
        ReflectionEventHelper.initializeAndActivate(this);
    }
    
    public void handleMessage(byte[] message, int messageSize, int channel) {
        if(!isActive) return;
    	if(eventConnection.isReady()) {
    		eventConnection.sendEvent("handleMessage", new Object[]{message, messageSize, channel});
    	}
    	else {
    		System.err.println("Module "+module.getProperty("name")+": Event connection not ready, throw away package..");
    	}
    }

    public void setEventConnection(EventConnection eventConnection) {
        this.eventConnection = eventConnection;
    }

    public void setRcConnection(ReflectionConnection rcConnection) {
        this.rcConnection = rcConnection;
    }

    public void setActive() {
        isActive = true;
    }
}
