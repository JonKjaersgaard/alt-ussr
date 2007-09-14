/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.sandbox;

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
import ussr.samples.atron.ATRONController;

/**
 * A simple controller for the ATRON, controlling connector stickiness based on
 * user-controlled state stored in the main simulator
 * 
 * @author ups
 *
 */
public class ATRONCarControllerForTiltSensorExperiments extends ATRONController {
	
    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        float last[] = new float[] { Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY };
        float tsal = Float.NEGATIVE_INFINITY;
        while(true) {
        	if(!module.getSimulation().isPaused()) {
        		String name = module.getProperty("name");
    			/*if(name=="wheel1") rotateContinuous(1);
    			if(name=="wheel2") rotateContinuous(-1);
    			if(name=="wheel3") rotateContinuous(1);
    			if(name=="wheel4") rotateContinuous(-1);
    			if(!GenericSimulation.getConnectorsAreActive()) {
    				disconnectAll();
    			}*/
                int i=0;
                /*List ps = (List)module.getPhysics();
                JMEModuleComponent c1 = (JMEModuleComponent)ps.get(0);
                DynamicPhysicsNode n = c1.getNodes().get(0);
                Matrix3f m = n.getLocalRotation().toRotationMatrix();
                float tmp = m.m00+m.m01+m.m02+m.m10+m.m11+m.m12+m.m20+m.m21+m.m22;
                if(Math.abs(tmp-tsal)>0.05) {
                    System.out.println(m.toString());
                    tsal = tmp;
                }*/
                for(Sensor s_ensor: module.getSensors())
                    if(Math.abs(s_ensor.readValue()-last[i++])>0.05f) {
                        i = 0;
                        synchronized(ATRONCarControllerForTiltSensorExperiments.class) {
                            System.out.print(name+"=(");
                            for(Sensor sensor: module.getSensors()) {
                                last[i] = sensor.readValue();
                                System.out.print(((int)(last[i]/Math.PI*180))+",");
                                i++;
                            }
                            System.out.println(')');
                            break;
                        }
                    }
        	}
        	Thread.yield();
        }
    }
}
