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
        selector.set_role_connection_count(ROLE_HEAD,2);
        selector.set_role_orientation_definition(ROLE_HEAD, ORI_NS);
        selector.set_role_connector_constraint(ROLE_LEFTWHEEL, DIR_WEST, 0, ROLE_ANY);
        selector.set_role_connector_constraint(ROLE_RIGHTWHEEL, DIR_EAST, 0, ROLE_ANY);
        // Start running
        byte dir = 1;
        int role = ROLE_ANY;
        String self = this.getModule().getProperty("name");
        while(true) {
            int oldRole = role;
            // Find a role
            do {
                selector.select_role();
                yield();
            } while(selector.get_role()==ROLE_ANY);
            role = selector.get_role();
            if(role!=oldRole) System.out.println("Selected role "+role+" for "+self);
            // Perform action
            if(role==ROLE_RIGHTWHEEL) rotateContinuous(dir);
            if(role==ROLE_LEFTWHEEL) rotateContinuous(-dir);
            if(role==ROLE_HEAD) {
                
            }
            this.delay(1000);
            yield();
        }
    }
    
    @Override
    public void handleMessage(byte[] message, int length, int connector) {
        if(length>0 && message[0]==(byte)87) selector.deliver_message(message, (byte)length, (byte)connector);
    }
}
