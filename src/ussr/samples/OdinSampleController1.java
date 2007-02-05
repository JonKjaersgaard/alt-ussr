/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples;

import ussr.model.Connector;
import ussr.model.ControllerImpl;

/**
 * A simple controller for the ATRON, controlling connector stickiness based on
 * user-controlled state stored in the main simulator
 * 
 * @author ups
 *
 */
public class OdinSampleController1 extends ControllerImpl {

	String type;
    public OdinSampleController1(String type) {
    	this.type =type; 
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
        	try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new Error("unexpected");
            }
            rotate(1);
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
    
	float t=0;
	public void rotate(int dir) {
		module.getActuators().get(0).activate((float)(Math.sin(t)+1)/2f);
		t=t+0.1f;
		//System.out.println("follow = "+(float)(Math.sin(t)+1)/2f);
	}
}
