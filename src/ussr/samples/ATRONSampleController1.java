/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples;

import java.awt.Color;
import java.util.List;

import ussr.model.Connector;
import ussr.model.ControllerImpl;

/**
 * A simple controller for the ATRON, controlling connector stickiness based on
 * user-controlled state stored in the main simulator
 * 
 * @author ups
 *
 */
public class ATRONSampleController1 extends ControllerImpl {

	private int nConnections = 0;
	   
	
    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {

        if(module.getID()%2==0) module.setColor(Color.RED);
        while(true) {
            this.waitForEvent();
            if(!ATRONSimulation1.getConnectorsAreActive()||nConnections>=2) continue;
            for(Connector connector: module.getConnectors()) {
                if(!connector.isConnected()&&connector.hasProximateConnector()) {
                    if(connector.connect()) nConnections++;
                }
            }
        }
    }

}
