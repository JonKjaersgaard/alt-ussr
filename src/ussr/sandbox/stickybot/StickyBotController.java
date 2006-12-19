/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.sandbox.stickybot;

import java.awt.Color;
import java.util.List;

import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.model.Connector;
import ussr.model.ControllerImpl;

/**
 * A simple controller for the Sticky Bot, controlling connector stickiness based on
 * user-controlled state stored in the main simulator
 * 
 * @author ups
 *
 */
public class StickyBotController extends ControllerImpl {
    
    private static int c = 0;
    private static synchronized int getc() { return c++; } 

    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        Transmitter transmitter = module.getTransmitters().get(0);
        Receiver receiver = module.getReceivers().get(0);
        while(true) {
            this.waitForEvent();
            if(receiver.hasData()) {
                Packet data = receiver.getData();
                if(data.get(0)==87) module.setColor(Color.RED);
            }
            if(!StickyBotSimulation.getConnectorsAreActive()) continue;
            for(Connector connector: module.getConnectors()) {
                if(!connector.isConnected()&&connector.hasProximateConnector()) {
                    if(connector.connect()) transmitter.send(new Packet(87));
                }
            }
        }
    }

}
