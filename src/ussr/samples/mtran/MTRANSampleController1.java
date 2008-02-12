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
    	while(module.getSimulation().isPaused()) ussrYield();
    	System.out.println("MTRAN RUNNING "+module.getID());
    	while(true) {
    		double goal0 = Math.sin(4*module.getSimulation().getTime()+module.getID());
    		double cur0 =  2*(getEncoderPosition(0)-0.5);
    		//System.out.println(goal+" "+cur);
    		if(cur0>goal0) {
    			rotate(-1, 0);
    		}else {
    			rotate(1, 0);
    		}
    		
    		double goal1 = -Math.sin(4*module.getSimulation().getTime()+module.getID()+0.5f);
    		double cur1 =  2*(getEncoderPosition(1)-0.5);
    		//System.out.println(goal+" "+cur);
    		if(cur1>goal1) {
    			rotate(-1, 1);
    		}else {
    			rotate(1, 1);
    		}
    		
    		/*if(>0) {
    			if(getAngularPositionDegrees(0)<90||getAngularPositionDegrees(0)>-90) {
    				rotate(-1, 0);	
    			}
    			else {
    				rotate(0, 0);
    			}
    		}
    		else {
    			//rotate(1, 0);
    		}*/
    		
    		ussrYield();
    	/*	rotate(-1, 0);rotate(-1, 1);delay(1000);
    		rotate(-1, 0);rotate(1, 1);delay(1000);
    		rotate(1, 0);rotate(1, 1);delay(1000);
    		rotate(1, 0);rotate(-1, 1);delay(1000);*/
    		/*rotateTo(0.4f, 1);
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
    		
    		delay(500000);*/
    	}
	}
    
    public void handleMessage(byte[] message, int messageSize, int channel) {
   	}
}
