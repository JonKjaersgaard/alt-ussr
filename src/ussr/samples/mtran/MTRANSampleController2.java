/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.mtran;

import java.util.Random;

/**
 * A simple controller for the ODIN robot, ossilates OdinMuscles with a random start state 
 * 
 * @author david
 *
 */
public class MTRANSampleController2 extends MTRANController {
	
	static Random rand = new Random(System.currentTimeMillis());
    float timeOffset=0;
    byte[] msg = {0};
    int color = 0;
    public MTRANSampleController2(String type) {
    	setBlocking(false);
    }
	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	while(module.getSimulation().isPaused()) yield();
    	yield();
    	//"leftBack","leftSpline","leftFront","rightBack","rightSpline","rightFront"
    	while(true) {
    		double goal0=0,goal1=0;
    		if(module.getProperty("name").equals("center")) {
    			goal0 = -1; goal1 = -1;
    			//goal0 = 0; goal1 = 0;
    		}
    		else if(module.getProperty("name").equals("leftBackFoot")) {
    			goal0 = 0; goal1 = 0.5f;
    		}
    		else if(module.getProperty("name").equals("leftBackSpline")) {
    			goal0 = 0; goal1 = 0.5f;
    		}
    		else if(module.getProperty("name").equals("leftFrontSpline")) {
    			goal0 = -0.5f; goal1 = 0.5f;
    		}
    		else if(module.getProperty("name").equals("leftFrontFoot")) {
    			goal0 = 0; goal1 = 0.5f;
    		}
    		else if(module.getProperty("name").equals("rightBackFoot")) {
    			goal0 = 0; goal1 = 0.5f;
    		}
    		else if(module.getProperty("name").equals("rightBackSpline")) {
    			goal0 = -0.5f; goal1 = 0.5f;
    		}
    		else if(module.getProperty("name").equals("rightFrontSpline")) {
    			goal0 = -0.5f; goal1 = 0.5f;
    		}
    		else if(module.getProperty("name").equals("rightFrontFoot")) {
    			goal0 = 0; goal1 = 0.5f;
    		}
    		if(!module.getProperty("name").equals("center")) {
	    		goal0 += 0.5f*Math.sin(3*module.getSimulation().getTime()+module.getID()+0.5f);
		    	goal1 += -0.5f*Math.sin(3*module.getSimulation().getTime()+module.getID());
    		}
    		
    		goToAngles(goal0, goal1);
    		yield();
    	}
	}
    private void goToAngles(double goal0, double goal1) {
    	double cur0 =  2*(getEncoderPosition(0)-0.5);
		if(cur0>goal0) {
			rotateContinuous(-1, 0);
		}else {
			rotateContinuous(1, 0);
		}
		double cur1 =  2*(getEncoderPosition(1)-0.5);
		if(cur1>goal1) {
			rotateContinuous(-1, 1);
		}else {
			rotateContinuous(1, 1);
		}
    }
    
    public void handleMessage(byte[] message, int messageSize, int channel) {
   	}
}
