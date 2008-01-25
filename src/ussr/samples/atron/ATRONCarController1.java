/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.atron;

import java.awt.Color;
import java.util.List;

import com.jme.math.Matrix3f;
import com.jmex.physics.DynamicPhysicsNode;

import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.model.Sensor;
import ussr.physics.jme.JMEModuleComponent;
import ussr.samples.GenericSimulation;

/**
 * A simple controller for an ATRON car that reports data from the proximity sensors
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONCarController1 extends ATRONController {
	
    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	ussrYield();
        byte lastew = -127, lastns = -127;
        byte dir = 1;
        float lastProx = Float.NEGATIVE_INFINITY;
        boolean firstTime = true;
        while(true) {
        	if(!module.getSimulation().isPaused()) {
        		String name = module.getProperty("name");
        		if(firstTime) {
                    firstTime = false;
        		    if(name=="wheel1") rotateContinuous(dir);
        		    if(name=="wheel2") rotateContinuous(-dir);
        		    if(name=="wheel3") rotateContinuous(dir);
        		    if(name=="wheel4") rotateContinuous(-dir);
        		    if(name=="axleOne5" && firstTime) {
        		        this.rotateDegrees(10);
        		    }
        		}
    			if(!GenericSimulation.getConnectorsAreActive()) {
    				disconnectAll();
    			}
                float max_prox = Float.NEGATIVE_INFINITY;
                for(Sensor s: module.getSensors()) {
                    if(s.getName().startsWith("Proximity")) {
                        float v = s.readValue();
                        max_prox = Math.max(max_prox, v);
                    }
                }
                if(name.startsWith("wheel")&&Math.abs(lastProx-max_prox)>0.01) {
                    System.out.println("Proximity "+name+" max = "+max_prox);
                    lastProx = max_prox; 
                }
                //if(name.startsWith("wheel") && this.getTiltX()>5) dir = -1;
                //if(name.startsWith("wheel") && this.getTiltX()<-5) dir = 1;
        		/*if(name.startsWith("axle")&&(Math.abs(this.getTiltY()-lastew)>1)) {
        		    System.out.println("axle tilty="+this.getTiltY());
                    lastew = this.getTiltY();
                } else if(name.startsWith("wheel")&&(Math.abs(this.getTiltX()-lastns)>1)) {
                    System.out.println("wheel tiltx="+this.getTiltX());
                    lastns = this.getTiltX();
                }*/
        	}
        	ussrYield();
        }
    }
}
