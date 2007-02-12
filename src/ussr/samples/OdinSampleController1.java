/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples;

import java.util.Random;

import sun.awt.windows.ThemeReader;
import ussr.model.ControllerImpl;

/**
 * A simple controller for the ATRON, controlling connector stickiness based on
 * user-controlled state stored in the main simulator
 * 
 * @author ups
 *
 */
public class OdinSampleController1 extends ControllerImpl {
	static Random rand = new Random(System.currentTimeMillis());
	String type;
    float timeOffset=0;
    public OdinSampleController1(String type) {
    	this.type =type;
    	timeOffset = 100*rand.nextFloat();
    }
	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	if(type=="OdinMuscle") muscleControl();
    	if(type=="OdinBall") ballControl();
	}
    public void muscleControl() {
    	while(true) {
    		float time = module.getSimulation().getTime()+timeOffset;
			actuate((float)(Math.sin(time)+1)/2f);
			Thread.yield();
        }
    }
    public void ballControl() {
    	while(true) {
        	try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new Error("unexpected");
            }
    	}
    }
	public void actuate(float pos) {
		module.getActuators().get(0).activate(pos);
	}
}
