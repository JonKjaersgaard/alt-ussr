/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package roleselection;

import static roleselection.RoleSelector.DIR_EAST;
import static roleselection.RoleSelector.DIR_WEST;
import static roleselection.RoleSelector.ORI_NS;
import static roleselection.RoleSelector.ROLE_ANY;

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
public class ATRONCarController1 extends ATRONController {
	
    // Roles
    public static final int ROLE_LEFTWHEEL = 1;
    public static final int ROLE_RIGHTWHEEL = 2;
    public static final int ROLE_AXLE = 3;
    public static final int ROLE_DRIVER = 4;
    
    // Selector helper
    RoleSelector selector = new RoleSelector(this,(byte)87);

    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	yield();
        // Specify role selection
        selector.set_role_connection_count(ROLE_LEFTWHEEL,1);
        selector.set_role_connection_count(ROLE_RIGHTWHEEL,1);
        selector.set_role_connection_count(ROLE_AXLE,3);
        selector.set_role_connection_count(ROLE_DRIVER,2);
        selector.set_role_orientation_definition(ROLE_DRIVER, ORI_NS);
        selector.set_role_connector_constraint(ROLE_LEFTWHEEL, DIR_WEST, 0, ROLE_ANY);
        selector.set_role_connector_constraint(ROLE_RIGHTWHEEL, DIR_EAST, 0, ROLE_ANY);
        // Start running
    	this.delay(1000); /* rotateContinuous seem to fail sometimes if we do not wait at first */
        byte dir = 1;
        float lastProx = Float.NEGATIVE_INFINITY; /* for printing out proximity data */
        boolean firstTime = true;
        // Select role
        do {
            selector.select_role();
            yield();
        } while(selector.get_role()==ROLE_ANY);
        int role = selector.get_role();
        String name = this.getModule().getProperty("name");
        System.out.println("Role "+role+" for "+name);
        while(true) {
        	
            // Enable stopping the car interactively:
            if(!GenericSimulation.getActuatorsAreActive()) { yield(); firstTime = true; continue; }
            
            // Basic control: first time we enter the loop start rotating and turn the axle
            if(firstTime) {
                firstTime = false;
                if(name.equals("wheel1")) rotateContinuous(dir);
                if(name.equals("wheel2")) rotateContinuous(-dir);
                if(name.equals("wheel3")) rotateContinuous(dir);
                if(name.equals("wheel4")) rotateContinuous(-dir);
                if(name.equals("axleOne5")) {
                    this.rotateDegrees(10);
                }
            }

            // Print out proximity information
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

            // Always call yield sometimes
        	yield();
        }
    }
    
    @Override
    public void handleMessage(byte[] message, int length, int connector) {
        if(length>0 && message[0]==(byte)87) selector.deliver_message(message, (byte)length, (byte)connector);
    }
}
