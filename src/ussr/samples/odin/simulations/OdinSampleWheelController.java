/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.odin.simulations;

import java.util.Random;

import ussr.samples.odin.OdinController;

/**
 * A simple controller for the ODIN robot, uses tilt sensor to stretch upwards
 * 
 * @author david
 *
 */
public class OdinSampleWheelController extends OdinController {
	static Random rand = new Random(System.currentTimeMillis());
	
    public OdinSampleWheelController(String type, float[] gene) {
    	this.type =type;
    }
	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	while(module.getSimulation().isPaused()) Thread.yield();
    	if(type=="OdinBall") while(true) ballControl();
    	if(type=="OdinWheel") while(true) wheelTestControl();
    	while(true) {
    		module.getSimulation().waitForPhysicsStep(false);
    	}
	}
    public void ballControl() {
    	while(true) {
    		try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                throw new Error("unexpected");
            }
    	}
    }
    public void wheelTestControl() {
    	while(true) {
    		//actuate(0.9f);
    		while(getTime()<5) {
    			actuate(0f);
    			module.getSimulation().waitForPhysicsStep(false);
    		}
    		while(getTime()<15) {
    			actuateContinuous(1f);
    			module.getSimulation().waitForPhysicsStep(false);
    		}
    		while(getTime()<30) {
    			float x = (float)(Math.sin(getTime()));
    			
    			actuateContinuous((x>0)?1:-1);
    			module.getSimulation().waitForPhysicsStep(false);
    		}
    		while(getTime()<45) {
    			actuateContinuous(-1f);
    			module.getSimulation().waitForPhysicsStep(false);
    		}
    		while(true) {
    			actuate(0f);
    			module.getSimulation().waitForPhysicsStep(false);
    		}
    	}
    }
    
}
