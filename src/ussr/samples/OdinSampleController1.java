/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples;

import java.util.Random;

import ussr.model.ControllerImpl;

/**
 * A simple controller for the ODIN robot, ossilates OdinMuscles with a random start state 
 * 
 * @author david
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
    	while(module.getSimulation().isPaused()) Thread.yield();
    	delay(1000);
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
		//System.out.println(" "+module.getConnectors().get(0).getPhysics().get(0));
	}
}
