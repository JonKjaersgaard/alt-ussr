/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.mtran;

import java.util.Random;

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
    	setBlocking(true);
    }
	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	while(module.getSimulation().isPaused()) controlYield();
    	controlYield();
    	System.out.println("MTRAN RUNNING "+module.getID());
    	while(true) {
    		double goal0 = 0.5f*Math.sin(3*module.getSimulation().getTime()+module.getID()+0.5f);
    		double cur0 =  2*(getEncoderPosition(0)-0.5);
    		if(cur0>goal0) {
    			rotate(-1, 0);
    		}else {
    			rotate(1, 0);
    		}
    		
    		double goal1 = -0.5f*Math.sin(3*module.getSimulation().getTime()+module.getID());
    		double cur1 =  2*(getEncoderPosition(1)-0.5);
    		if(cur1>goal1) {
    			rotate(-1, 1);
    		}else {
    			rotate(1, 1);
    		}
    		controlYield();
    	}
	}
    
    public void handleMessage(byte[] message, int messageSize, int channel) {
   	}
}
