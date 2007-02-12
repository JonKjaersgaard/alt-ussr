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
import ussr.physics.jme.JMEModuleComponent;

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
        	if(!module.getSimulation().isPaused()) {
        		String name = module.getProperty("name");
    			float time = module.getSimulation().getTime();
        		//rotate((float)(Math.sin(time)+1)/2f);
    			if(name=="wheel1") rotate(1);
    			if(name=="wheel2") rotate(-1);
    			if(name=="wheel3") rotate(1);
    			if(name=="wheel4") rotate(-1);
        	}
        	Thread.yield();
        }
    }
    float t=0;
    public void rotate(float pos) {
    	module.getActuators().get(0).activate(pos);
    }
    public boolean isOtherConnectorNearby(int connector) {
    	if(module.getConnectors().get(connector).isConnected()) {
    		return true;
    	}
    	else  {
    		return false;
    	}
    }
}
