/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.white;

import java.awt.Color;
import java.util.Random;

/**
 * A simple controller for the ODIN robot, ossilates OdinMuscles with a random start state 
 * 
 * @author david
 *
 */
public class WhiteSampleController1 extends WhiteController {
	
	static Random rand = new Random(System.currentTimeMillis());
    float timeOffset=0;
    byte[] msg = {0};
    int color = 0;
    public WhiteSampleController1(String type) {
    }
	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	while(module.getSimulation().isPaused()) ussrYield();
    	//alternativeHook();
    		
    	while(true) { //put control here
    		delay(new Random().nextInt(1000));
    		
    		if(getDebugID()==0) {
    			for(int i=0;i<4;i++) {
    				/*if(isConnected(i)&&countConnections()>1) {
	    				disconnect(i);
	    				delay(500);
	    			}*/
	    			if(isOtherConnectorNearby(i)&&isConnected((i+1)%4)) {
	    				connect(i);
	    				//System.out.println(getDebugID()+" Connected connector "+i);
	    				int count=0;
	    				while(!isConnected(i)&&count<50) {
	    					delay(100);
	    					count++;
	    				}
	    				if(isConnected(i)) {
	    					disconnect((i+1)%4);
	    					delay(500);
	    				}
	    			}
	    		}
    		}
    		else {
    			module.setColor(Color.BLUE);
    			//System.out.println(getDebugID()+": I have "+countConnections()+" connections and "+countNearbyConnections()+" nearby");
    			delay(1000);
    		}
    		ussrYield(); //must use in main loop
    	}
    }
    private void alternativeHook() {
    	for(int i=0;i<4;i++) {
			if(isConnected(i)) {
				disconnect(i);
				delay(500);
			}		
		}
    	delay(10000);
    	while(true) { //put control here
    		delay(new Random().nextInt(1000));
    		if(getDebugID()==0) {
    			for(int i=0;i<4;i++) {
	    			if(isOtherConnectorNearby(i)) {
	    				connect(i);
	    				int count=0;
	    				while(!isConnected(i)&&count<50) {
	    					delay(100);
	    					count++;
	    				}
	    			}
	    		}
    		}
    		else {
    			module.setColor(Color.BLUE);
    			//System.out.println(getDebugID()+": I have "+countConnections()+" connections and "+countNearbyConnections()+" nearby");
    			delay(1000);
    		}
    		ussrYield(); //must use in main loop
    	}
	}
	private int countConnections() {
    	int connectionCount=0;
		for(int i=0;i<4;i++) {
			if(isConnected(i)) {
				connectionCount++;
			}
		}
		return connectionCount;
	}
    private int countNearbyConnections() {
    	int connectionNearbyCount=0;
		for(int i=0;i<4;i++) {
			//if(isOtherConnectorNearby(i)) {
			if(module.getConnectors().get(i).hasProximateConnector()) {
				connectionNearbyCount++;
			}
		}
		return connectionNearbyCount;
	}
    
    public void handleMessage(byte[] message, int messageSize, int channel) {
   	}
}
