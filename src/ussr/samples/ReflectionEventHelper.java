package ussr.samples;

import ussr.model.Module;
import ussr.network.EventConnection;
import ussr.network.ReflectionConnection;

public class ReflectionEventHelper {
    public static void initializeAndActivate(ReflectionEventController controller) {
        final ReflectionConnection rcConnection;
        final EventConnection eventConnection;
        Module module = controller.getModule();
        int portRC, portEvent;
        String portRCDescription = module.getProperty("portRC");
        String portEventDescription = module.getProperty("portEvent");
        String aseAuto = module.getProperty("ase.auto");
        if(portRCDescription!=null&&portEventDescription!=null) {
            try {
                portRC = Integer.parseInt(portRCDescription);
                portEvent = Integer.parseInt(portEventDescription);
            } catch(NumberFormatException exn) {
                throw new Error("Illegal port number, cannot parse: "+portRCDescription+" or "+portEventDescription);
            }
        } else if(aseAuto!=null) {
            String idmaybe = module.getProperty("roleid");
            if(idmaybe==null) throw new Error("ase.auto only legal when roleid attribute also set");
            try {
                int basePort = Integer.parseInt(aseAuto);
                int id = Integer.parseInt(idmaybe);
                portRC = basePort+id*2;
                portEvent = basePort+id*2+1;
            } catch(NumberFormatException exn) {
                throw new Error("Illegal number, cannot parse one of: "+aseAuto+" or "+idmaybe);
            }
        } else {
            System.out.println("Module "+module.getProperty("name")+" is passive ["+module+"]");
            return;
        }
        System.out.println("Remote connection sockets "+portRC+","+portEvent+" open for module "+module);
        eventConnection = new EventConnection(portEvent);
        controller.setEventConnection(eventConnection);
        (new Thread() {
            public void run() { 
                eventConnection.activate();            
            }
         }).start();
        rcConnection = new ReflectionConnection(portRC,controller);
        controller.setRcConnection(rcConnection);
        controller.setActive();
        rcConnection.activate();
    }

}
