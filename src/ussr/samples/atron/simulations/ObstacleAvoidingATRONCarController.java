/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.simulations;

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
 * A simple controller for an ATRON car that reports data from the proximity sensors
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ObstacleAvoidingATRONCarController extends ATRONController {
	
    private static final byte[] MSG_OBSTACLE = new byte[] { 1 };
    private static final byte[] MSG_CLEAR = new byte[] { 2 };
    private static final float REVERSE_TIME = 4;
    
    byte dir = 1;
    float proximity_threshold = 0.1f;
    boolean is_reversing = false;
    String name;
    float reverse_stop_time = Float.MAX_VALUE;

    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	setup(); 
    	this.delay(1000); /* rotateContinuous seem to fail sometimes if we do not wait at first */
        name = module.getProperty("name");

        while(true) {
            if(name.startsWith("wheel1")) rotateContinuous(dir);
            if(name.startsWith("wheel2")) rotateContinuous(-dir);
            if(name.startsWith("wheel3")) rotateContinuous(dir);
            if(name.startsWith("wheel4")) rotateContinuous(-dir);
            if(name.equals("axleOne5")) {
                if(!is_reversing)
                    this.rotateToDegreeInDegrees(10);
                else
                    this.rotateToDegreeInDegrees(-10);
            }

            // Print out proximity information
            for(Sensor s: module.getSensors()) {
                if(s.getName().startsWith("Proximity")) {
                    float v = s.readValue();
                    if(!is_reversing && v>proximity_threshold) trigger_reverse();
                }
            }

            if(is_reversing && this.getModule().getSimulation().getTime()>reverse_stop_time) {
                reverse_stop_time = Float.MAX_VALUE;
                trigger_forwards();
            }
            
            // Always call yield sometimes
        	yield();
        }
    }

    private void trigger_reverse() {
        System.out.println("Triggering reverse for module "+name);
        is_reversing = true;
        if(name.startsWith("wheel")) dir = (byte)-dir;
        for(int c=0; c<8; c++) this.sendMessage(MSG_OBSTACLE, (byte)MSG_OBSTACLE.length, (byte)c);
        reverse_stop_time = this.getModule().getSimulation().getTime()+REVERSE_TIME;
    }
    
    private void trigger_forwards() {
        System.out.println("Triggering forwards for module "+name);
        is_reversing = false;
        if(name.startsWith("wheel")) dir = (byte)-dir;
        for(int c=0; c<8; c++) this.sendMessage(MSG_CLEAR, (byte)MSG_CLEAR.length, (byte)c);
    }
    
    @Override
    public void handleMessage(byte[] message, int messageLength, int connector) {
        if(message.length == 1 && message[0]==MSG_OBSTACLE[0]) {
            is_reversing = true;
            if(name.startsWith("wheel")) dir = (byte)-dir;
            for(int c=0; c<8; c++) if(c!=connector) this.sendMessage(MSG_OBSTACLE, (byte)MSG_OBSTACLE.length, (byte)c);
        } else if(message.length == 1 && message[0]==MSG_CLEAR[0]) {
            is_reversing = false;
            if(name.startsWith("wheel")) dir = (byte)-dir;
            for(int c=0; c<8; c++) if(c!=connector) this.sendMessage(MSG_CLEAR, (byte)MSG_CLEAR.length, (byte)c);
        } else

            System.err.println("Warning: unknown message");
    }
}
