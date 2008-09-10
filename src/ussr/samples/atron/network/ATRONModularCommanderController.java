package ussr.samples.atron.network;

import network.ModularCommanderController;
import network.ModularCommanderController.Mapper;
import ussr.model.Controller;
import ussr.samples.atron.ATRONController;

public class ATRONModularCommanderController extends ATRONController implements Mapper {
    ModularCommanderController mc;
    
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
        mc = new ModularCommanderController(port,this);
        mc.activate();
    }

    public void mapPacketToAPI(byte[] packet) {
        String name = getModule().getProperty("name");
        System.out.println("Module "+name+" got: "+packet.length+" bytes");
    }

}
