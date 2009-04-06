package ussr.samples.atron.network;

import ussr.network.EventConnection;
import ussr.network.ReflectionConnection;
import ussr.physics.PhysicsLogger;
import ussr.samples.atron.ATRONController;

public class ATRONReflectionEventController extends ATRONController {
    ReflectionConnection rcConnection;
    EventConnection eventConnection;

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
        String portRCDescription = super.getModule().getProperty("portRC");
        String portEventDescription = super.getModule().getProperty("portEvent");
        if(portRCDescription==null||portEventDescription==null) {
            System.out.println("Module "+getModule().getProperty("name")+" is passive");
            return;
        }
        int portRC, portEvent;
        try {
        	portRC = Integer.parseInt(portRCDescription);
        	portEvent = Integer.parseInt(portEventDescription);
        } catch(NumberFormatException exn) {
            throw new Error("Illegal port number, cannot parse: "+portRCDescription+" or "+portEventDescription);
        }
        eventConnection = new EventConnection(portEvent);
        (new Thread() {
    	    public void run() { 
    	    	eventConnection.activate(); 	       
    	    }
    	 }).start();
        rcConnection = new ReflectionConnection(portRC,this);
        rcConnection.activate();
    }
    public void handleMessage(byte[] message, int messageSize, int channel) {
    	if(eventConnection.isReady()) {
    		eventConnection.sendEvent("handleMessage", new Object[]{message, messageSize, channel});
    	}
    	else {
    		System.err.println(getName()+": Event connection not ready, throw away package..");
    	}
    }
}
