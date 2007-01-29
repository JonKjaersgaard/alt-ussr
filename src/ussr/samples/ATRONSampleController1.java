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
        	if(!module.getSimulation().isPaused()) {
	            if(!ATRONSimulation1.getConnectorsAreActive()) {
		            for(Connector connector: module.getConnectors()) {
		                connector.disconnect();
		            }
	            }
	            //module.setColor(Color.blue);
	          //  module.getConnectors().get(0).setColor(Color.red);
	            rotate(1);
        	}
        }
    }
    float t=0;
    public void rotate(int dir) {
    	module.getActuators().get(0).activate((float)(Math.sin(t)+1)/2f);
    	t=t+0.00001f;
    	//System.out.println("follow = "+(float)(Math.sin(t)+1)/2f);
    	
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
