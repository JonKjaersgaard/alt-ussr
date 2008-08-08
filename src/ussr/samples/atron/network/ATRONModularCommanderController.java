package ussr.samples.atron.network;

import ussr.model.Controller;
import ussr.samples.atron.ATRONController;
import ussr.samples.atron.network.ModularCommanderController.Mapper;

public class ATRONModularCommanderController extends ATRONController implements Mapper {
    ModularCommanderController mc;
    
    @Override
    public void activate() {
        super.getModule().waitForPropertyToExist("name");
        String moduleName = super.getModule().getProperty("name");
        int index = moduleName.indexOf(':');
        if(index==-1) {
            System.out.println("Module "+moduleName+" is passive");
            return;
        }
        String[] components = moduleName.split(":");
        String name = components[0];
        int port;
        try {
            port = Integer.parseInt(components[1]);
        } catch(NumberFormatException exn) {
            throw new Error("Illegal port number, cannot parse: "+components[1]);
        }
        super.getModule().setProperty("name", name);
        mc = new ModularCommanderController(name,port,this);
        mc.activate();
    }

    public void mapPacketToAPI(byte[] packet) {
        String name = getModule().getProperty("name");
        System.out.println("Module "+name+" got: "+packet.length+" bytes");
    }

}
