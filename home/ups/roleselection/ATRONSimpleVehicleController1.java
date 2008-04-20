/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package roleselection;

import ussr.samples.atron.ATRONController;
import static roleselection.RoleSelector.*;

/**
 * A controller for a two-wheeler ATRON robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONSimpleVehicleController1 extends ATRONController {
	
    // Roles
    public static final int ROLE_LEFTWHEEL = 1;
    public static final int ROLE_RIGHTWHEEL = 2;
    public static final int ROLE_HEAD = 3;
    
    // Communication
    public static final byte HEADER_NORMAL = 1;
    public static final byte MSG_GO = 2;
    public static final byte MSG_STOP = 3;
    public static final byte HEADER_ROLESELECT = 87;
    
    // Control
    public static final int PERIOD = 30;
    
    // Selector helper
    RoleSelector selector = new RoleSelector(this,HEADER_ROLESELECT);

    // State
    int role = ROLE_ANY;
    boolean wheel_moving = true;
    int head_counter = 0;
    
    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        yield();
        // Specify role selection
        selector.set_role_connection_count(ROLE_LEFTWHEEL,1);
        selector.set_role_connection_count(ROLE_RIGHTWHEEL,1);
        selector.set_role_connection_count(ROLE_HEAD,2);
        selector.set_role_orientation_definition(ROLE_HEAD, ORI_NS);
        selector.set_role_orientation_constraint(ROLE_HEAD, ORI_NS);
        selector.set_role_connector_constraint(ROLE_LEFTWHEEL, DIR_WEST, 0, ROLE_ANY);
        selector.set_role_connector_constraint(ROLE_RIGHTWHEEL, DIR_EAST, 0, ROLE_ANY);
        // Start running
        byte dir = 1;
        String self = this.getModule().getProperty("name");
        while(true) {
            int oldRole = role;
            // Find a role
            //do {
                selector.select_role();
                yield();
            //} while(selector.get_role()==ROLE_ANY);
            role = selector.get_role();
            if(role!=oldRole) System.out.println("Selected role "+role+" for "+self);
            // Perform action
            switch(role) {
            case ROLE_RIGHTWHEEL: 
                if(wheel_moving) rotateContinuous(dir); 
                else centerStop();
                break;
            case ROLE_LEFTWHEEL: 
                if(wheel_moving) rotateContinuous(-dir);
                else centerStop();
                break;
            case ROLE_HEAD:
                int left = selector.get_matching_connectors(DIR_WEST, ROLE_ANY).nextSetBit(0);
                int right = selector.get_matching_connectors(DIR_EAST, ROLE_ANY).nextSetBit(0);
                //System.out.println("left="+left+", right="+right);
                if(head_counter++>PERIOD) {
                    head_counter = 0;
                    wheel_moving = !wheel_moving;
                    System.out.println("Sending message: "+(wheel_moving ? "MSG_STOP" : "MSG_GO" ));
                    byte[] message = { HEADER_NORMAL, wheel_moving ? MSG_STOP : MSG_GO };
                    this.sendMessage(message, (byte)2, (byte)left);
                    this.sendMessage(message, (byte)2, (byte)right);
                }
            default:
                centerStop();
            }
            this.delay(1000);
            yield();
        }
    }
    
    @Override
    public void handleMessage(byte[] message, int length, int connector) {
        if(length>0 && message[0]==HEADER_ROLESELECT) selector.deliver_message(message, (byte)length, (byte)connector);
        else if(length>1 && message[0]==HEADER_NORMAL) {
            if(role==ROLE_LEFTWHEEL||role==ROLE_RIGHTWHEEL) {
                if(message[1]==MSG_STOP)
                    wheel_moving = false;
                else if(message[1]==MSG_GO)
                    wheel_moving = true;
                else
                    System.err.println("Incorrect message");
            }
        }
    }
}
