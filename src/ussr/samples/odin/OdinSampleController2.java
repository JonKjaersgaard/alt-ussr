/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.odin;

import java.util.Random;

import ussr.model.ControllerImpl;

import com.jme.math.Vector3f;

/**
 * A simple controller for the ODIN robot, uses tilt sensor to stretch upwards
 * 
 * @author david
 *
 */
public class OdinSampleController2 extends ControllerImpl {
	static Random rand = new Random(System.currentTimeMillis());
	String type;
    float timeOffset=0;
    public OdinSampleController2(String type) {
    	this.type =type;
    	timeOffset = 100*rand.nextFloat();
    }
	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	while(module.getSimulation().isPaused()) Thread.yield();
    	//delay(1000);
    	if(type=="OdinMuscle") muscleControl();
    	if(type=="OdinBall") ballControl();
	}
    public void muscleControl() {
    	while(true) {
    		/*if(getTiltSensor().y<Math.PI/4||getTiltSensor().y>3*Math.PI/4) { //more vertical than horizontal
    			actuate(1); //expand
    		}
    		else {
    			actuate(0); //contract
    		}*/
    		System.out.println("getTiltSensor() = "+getTiltSensor());
    		delay(100);
			Thread.yield();
        }
    }
    public Vector3f getTiltSensor() {
    	Vector3f tilt = new Vector3f();
    	tilt.x = module.getSensors().get(0).readValue();
    	tilt.y = module.getSensors().get(1).readValue();
    	tilt.z = module.getSensors().get(2).readValue();
    	return tilt;
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
