package ussr.samples.atron.network;

import ussr.network.ReflectionConnection;
import ussr.samples.atron.ATRONController;

public class ATRONReflectionController extends ATRONController {
    ReflectionConnection rcIncomming;
    
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
    }
    
}
