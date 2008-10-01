package socketTest;

import ussr.model.Controller;
import ussr.network.ModularCommanderConnection;
import ussr.network.ModularCommanderConnection.Mapper;
import ussr.samples.atron.ATRONController;

public class ATRONNativeViaSocketController extends ATRONController implements Mapper {
    ModularCommanderConnection mc;
    
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
        mc = new ModularCommanderConnection(port,this);
        mc.activate();
    }

    public void mapPacketToAPI(byte[] packet) {
        String name = getModule().getProperty("name");
        System.out.println("Module "+name+" got: "+packet.length+" bytes");
    }

}
