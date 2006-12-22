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

    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {

        while(true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new Error("unexpected");
            }
            if(!ATRONSimulation1.getConnectorsAreActive())
            for(Connector connector: module.getConnectors()) {
                connector.disconnect();
            }
        }
    }

}
