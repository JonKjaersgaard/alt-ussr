/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.stickybot;

import java.awt.Color;
import java.util.ArrayList;
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
import ussr.physics.jme.connectors.JMEConnector;
import ussr.physics.jme.connectors.JMEMagneticConnector;
import ussr.samples.GenericSimulation;

/**
 * A simple controller for the Sticky Bot, controlling connector stickiness based on
 * user-controlled state stored in the main simulator
 * 
 * @author ups
 *
 */
public class LargeStickyBotSimController extends ActControllerImpl {
    
    private Transmitter transmitter;
    private Receiver receiver;
    
    private static boolean activeToken = true;
    public static synchronized boolean getActiveToken() {
        if(activeToken) {
            activeToken = false;
            return true;
        }
        return false;
    }
    
    public void initializationActStep() {
        transmitter = module.getTransmitters().get(0);
        receiver = module.getReceivers().get(0);
        VectorDescription modulePos = module.getPhysics().get(0).getPosition();
        Vector3f mp = new Vector3f(modulePos.getX(),modulePos.getY(),modulePos.getZ());
        for(Connector connector: module.getConnectors()) {
            connector.setColor(Color.BLUE);
            JMEMagneticConnector temporaryHack = (JMEMagneticConnector)connector.getPhysics().get(0);
            temporaryHack.setAlignmentValues(20f, LargeStickyBotSimulation.connectorSize*2, StickyBot.SCALE*0.1f, 100);
            System.out.println(module.getProperty("name")+" 0Distance="+mp.distance(temporaryHack.getPos())+"; mod="+mp+", rel="+temporaryHack.getPosRel());
        }
    }

    float lastDisTime = 0;
    boolean isActive = getActiveToken();
    public boolean singleActStep() {
        if(!isActive) return false;
        if(module.getSimulation().getTime()-lastDisTime<1) return true;
        for(Connector connector: module.getConnectors()) {
            for(Connector other: module.getConnectors()) {
                if(other!=connector && other.hasProximateConnector()) {
                    System.out.println("dis");
                    lastDisTime = module.getSimulation().getTime();
                    connector.disconnect();
                    connector.setColor(Color.YELLOW);
                    return true;
                }
            }
            /*JMEMagneticConnector temporaryHack = (JMEMagneticConnector)connector.getPhysics().get(0);
            List<JMEConnector> others = temporaryHack.getNearbyConnectors(LargeStickyBotSimulation.connectorSize*2);
            if(others.size()>0) System.out.println("Nearby");*/
        }
/*        if(connectionCount>=4) {
            transmitter.send(new Packet(87));
            module.setColor(Color.RED);
            return false;
        }
        if(receiver.hasData()) {
            Packet data = receiver.getData();
            if(data.get(0)==87) {
                module.setColor(Color.YELLOW);
            }
        }*/
        return true;
    }

}
