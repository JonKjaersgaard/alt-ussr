/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.mtran;

import java.util.Random;

import ussr.home.davidc.onlineLearning.tools.RNN;
import ussr.model.ControllerImpl;

/**
 * A simple controller for the ODIN robot, ossilates OdinMuscles with a random start state 
 * 
 * @author david
 *
 */
public class MTRANSampleController1 extends MTRANController {
	
	static Random rand = new Random(System.currentTimeMillis());
    float timeOffset=0;
    byte[] msg = {0};
    int color = 0;
    public MTRANSampleController1(String type) {
    }
	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	while(module.getSimulation().isPaused()) Thread.yield();
    	while(true) {
    		rotateTo(0.4f, 1);
    		rotateTo(0f, 1); delay(1000);
    		rotateTo(0.4f, 1);
    		rotateTo(0.6f, 1);
    		rotateTo(1f, 1);delay(1000);
    		rotateTo(0.6f, 1);
    		rotateTo(0.5f, 1);delay(1000);
    		
    		rotateTo(0.4f, 0);
    		rotateTo(0f, 0); delay(1000);
    		rotateTo(0.4f, 0);
    		rotateTo(0.6f, 0);
    		rotateTo(1f, 0);delay(1000);
    		rotateTo(0.6f, 0);
    		rotateTo(0.5f, 0);delay(1000);
    		
    		rotateTo(1f, 0);
    		rotateTo(1f, 1);
    		rotateTo(0.6f, 1);
    		rotateTo(0.4f, 1);
    		rotateTo(0f, 1);
    		
    		delay(500000);
    	}
	}
    
    public void handleMessage(byte[] message, int messageSize, int channel) {
   	}
}
