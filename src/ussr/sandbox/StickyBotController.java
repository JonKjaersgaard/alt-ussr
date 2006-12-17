/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.sandbox;

import java.util.List;

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

    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        while(true) {
            this.waitForEvent();
            if(!StickyBotSimulation.getConnectorsAreActive()) continue; 
            for(Connector connector: module.getConnectors()) {
                List<Connector> proximates = connector.getAvailableConnectors();
                for(Connector proximate: proximates) {
                    if(proximate==null||connector.isConnected()) continue;
                    connector.connectTo(proximate);
                }
            }
        }
    }

}
