/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.stickybot;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import com.jme.math.Vector3f;

import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.description.geometry.VectorDescription;
import ussr.model.ActControllerImpl;
import ussr.model.Connector;
import ussr.model.ControllerImpl;
import ussr.physics.ConnectorBehaviorHandler;
import ussr.physics.jme.connectors.JMEMagneticConnector;
import ussr.samples.GenericSimulation;

/**
 * A simple controller for the Sticky Bot, controlling connector stickiness based on
 * user-controlled state stored in the main simulator
 * 
 * @author ups
 *
 */
public class InteractiveStickyBotSimController extends ActControllerImpl {
    
    private Transmitter transmitter;
    private Receiver receiver;
    
    public void initializationActStep() {
        Random randomizer = new Random();
        transmitter = module.getTransmitters().get(0);
        receiver = module.getReceivers().get(0);
        for(Connector connector: module.getConnectors()) {
            if(randomizer.nextBoolean())
                connector.setColor(Color.BLUE);
            else
                connector.setColor(Color.GREEN);
            connector.setConnectorBehaviorHandler(new ConnectorBehaviorHandler() {
                public boolean connectToProximateConnector(Connector target, Connector proximate) {
                    return target.getColor().equals(proximate.getColor());
                }
            });
            JMEMagneticConnector temporaryHack = (JMEMagneticConnector)connector.getPhysics().get(0);
            temporaryHack.setAlignmentValues(20f, StickyBot.SCALE, StickyBot.SCALE*0.1f, 10);
        }
    }

    // When a magnetic connector disconnects it stops actively connecting, to reactivate use
    // JMEMagneticConnector temporaryHack = (JMEMagneticConnector)connector.getPhysics().get(0);
    // temporaryHack.setIsActivelyConnecting(true);
    
    public boolean singleActStep() {
        int connectionCount = 0;
        for(Connector connector: module.getConnectors())
            if(connector.isConnected()) connectionCount++;
        if(connectionCount>=4) {
            for(Connector connector: module.getConnectors())
                if(connector.isConnected()) connector.disconnect();
            transmitter.send(new Packet(87));
            module.setColor(Color.RED);
            return false;
        }
        if(receiver.hasData()) {
            Packet data = receiver.getData();
            if(data.get(0)==87) {
                module.setColor(Color.YELLOW);
            }
        }
        return true;
    }

}
