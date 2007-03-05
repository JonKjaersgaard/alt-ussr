/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.atron;

import java.awt.Color;

import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.samples.GenericSimulation;

/**
 * A simple controller for the ATRON, controlling connector stickiness based on
 * user-controlled state stored in the main simulator
 * 
 * @author ups
 *
 */
public class ATRONCarController1 extends ATRONController {
	
    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        while(true) {
        	if(!module.getSimulation().isPaused()) {
        		String name = module.getProperty("name");
    			if(name=="wheel1") rotateContinuous(1);
    			if(name=="wheel2") rotateContinuous(-1);
    			if(name=="wheel3") rotateContinuous(1);
    			if(name=="wheel4") rotateContinuous(-1);
    			if(!GenericSimulation.getConnectorsAreActive()) {
    				disconnectAll();
    			}
        	}
        	Thread.yield();
        }
    }
}
