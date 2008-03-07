/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.stickybot;

import java.awt.Color;
import java.util.List;

import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.model.ActControllerImpl;
import ussr.model.Connector;
import ussr.model.ControllerImpl;
import ussr.physics.jme.connectors.JMEMagneticConnector;
import ussr.samples.GenericSimulation;

/**
 * A simple controller for the Sticky Bot, controlling connector stickiness based on
 * user-controlled state stored in the main simulator
 * 
 * @author ups
 *
 */
public class StickyBotController1 extends ActControllerImpl {
    
    private Transmitter transmitter;
    private Receiver receiver;
    
    public void initializationActStep() {
        transmitter = module.getTransmitters().get(0);
        receiver = module.getReceivers().get(0);
        for(Connector connector: module.getConnectors()) {
            connector.setColor(Color.BLUE);
            JMEMagneticConnector temporaryHack = (JMEMagneticConnector)connector.getPhysics().get(0);
            temporaryHack.setIsActive(true);
        }
    }

    public boolean singleActStep() {
        if(this.moduleIsConnected()) {
            transmitter.send(new Packet(87));
            module.setColor(Color.RED);
            return false;
        }
        if(receiver.hasData()) {
            Packet data = receiver.getData();
            if(data.get(0)==87) {
                module.setColor(Color.RED);
            }
        }
        return true;
    }

    private boolean moduleIsConnected() {
        for(Connector connector: module.getConnectors())
            if(connector.isConnected())
                return true;
        return false;
    }
    
}
