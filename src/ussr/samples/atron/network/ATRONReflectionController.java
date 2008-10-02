package ussr.samples.atron.network;

import ussr.network.ReflectionConnection;
import ussr.samples.atron.ATRONController;

public class ATRONReflectionController extends ATRONController {
    ReflectionConnection rcIncomming;
    ReflectionConnection rcOutgoing;
    
    @Override
    public void activate() {
        String portDescription = super.getModule().getProperty("port");
        if(portDescription==null) {
            System.out.println("Module "+getModule().getProperty("name")+" is passive");
            return;
        }
        int port;
        try {
            port = Integer.parseInt(portDescription);
        } catch(NumberFormatException exn) {
            throw new Error("Illegal port number, cannot parse: "+portDescription);
        }
        rcIncomming = new ReflectionConnection(port,this);
        rcIncomming.activate();
        
        rcOutgoing = new ReflectionConnection(port+1,this);
        rcOutgoing.activate();
    }
    public void handleMessage(byte[] message, int messageSize, int channel) {
    	System.out.println("Should reroute handlemessage(...) to socket");
    }
}
