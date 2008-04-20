package roleselection;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ussr.samples.atron.ATRONController;

public class RoleSelector {
    // Threshold to use for when we can match a specific tilt
    public static final int TILT_THRESHOLD = 50;
    // Neutral role
    public static final int NO_MODULE = 0;
    public static final int ROLE_ANY = 255;
    // Directions (sides of modules)
    public static final int DIR_NONE = 0;
    public static final int DIR_NORTH = 1;
    public static final int DIR_SOUTH = 2;
    public static final int DIR_EAST = 4;
    public static final int DIR_WEST = 8;
    public static final int DIR_UP = 16;
    public static final int DIR_DOWN = 32;
    // Orientations (cental axis of modules)
    public static final int ORI_NONE = 0;
    public static final int ORI_NS = 1;
    public static final int ORI_EW = 2;
    public static final int ORI_UD = 4;
    // Communication
    private static final byte MSG_ORI = 1;
    // Datastructure
    private class Role {
        int id, connection_count, orientation_definition, orientation_constraint;
        Map<Integer,Constraint> connector;
        Role(int id) { this.id = id; connector = new HashMap<Integer,Constraint>(); }
        Constraint lookup(int direction) {
            Constraint defn = connector.get(direction);
            if(defn==null) {
                defn = new Constraint(direction);
                connector.put(direction, defn);
            }
            return defn;
        }
    }
    private class Constraint {
        Constraint(int dir) { this.direction = dir; }
        int direction, count, roles; 
    }
    // Data
    private Map<Integer,Role> role_defns = new HashMap<Integer,Role>();
    private int[] neighbor_roles = new int[8];
    private int neighbor_count;
    private int local_role = ROLE_ANY;
    private int local_orientation = ORI_NONE;
    private ATRONController self;
    private int[] connector_direction = new int[8];
    private byte message_prefix;
    // Constructor
    public RoleSelector(ATRONController controller, byte message_prefix) {
        self = controller;
        this.message_prefix = message_prefix;
    }
    // API
    private Role lookup(int role_id) {
        Role defn = role_defns.get(role_id);
        if(defn==null) {
            defn = new Role(role_id);
            role_defns.put(role_id, defn);
        }
        return defn;
    }
    // Methods for setting up role definitions
    public void set_role_connection_count(int role_id, int count) { 
        lookup(role_id).connection_count = count;
    }
    public void set_role_orientation_definition(int role_id, int orientation) {
        lookup(role_id).orientation_definition = orientation;
    }
    public void set_role_connector_constraint(int role_id, int dir, int count, int roles) {
        lookup(role_id).lookup(dir).count = count;
        lookup(role_id).lookup(dir).roles = roles;
    }
    public void set_role_orientation_constraint(int role_id, int orientation) {
        lookup(role_id).orientation_constraint = orientation;
    }
    // Get the last selected role
    public int get_role() {
        return local_role;
    }
    // Get the orientation of the module
    public int get_orientation() {
        return local_orientation;
    }
    // Get direction of a connector
    public int get_connector_direction(int connector) {
        return this.connector_direction[connector];
    }
    // Get connectors based on criteria
    public BitSet get_matching_connectors(int direction, int role) {
        BitSet result = new BitSet();
        for(int i=0; i<8; i++) {
            //System.out.println("Connector "+i+": ("+this.connector_direction[i]+"&"+direction+"!=0) && (("+(role==ROLE_ANY)+"&&"+(this.neighbor_roles[i]!=NO_MODULE)+")||"+(this.neighbor_roles[i]==role)+")");
            if((this.connector_direction[i]&direction)!=0 && ((role==ROLE_ANY&&this.neighbor_roles[i]!=NO_MODULE)||this.neighbor_roles[i]==role))
                result.set(i);
        }
        return result;
    }
    // Select a role for the module based on the current orientation and context
    int i;
    public boolean select_role() {
        boolean head = self.getModule().getProperty("name").startsWith("driver");
        update_context();
        List<Role> selected_roles = new LinkedList<Role>();
        // Eliminate roles based on count
        for(Role role: this.role_defns.values()) {
            if(role.connection_count==this.neighbor_count) selected_roles.add(role);
        }
        // Eliminate roles based on rotation
        Iterator<Role> rotation_roles_iterator = selected_roles.iterator();
        while(rotation_roles_iterator.hasNext()) {
            Role role = rotation_roles_iterator.next();
            if(role.orientation_constraint!=ORI_NONE)
                if(!match_orientation(role.orientation_constraint)) {
                    rotation_roles_iterator.remove();
                    if(head) System.out.println("Deselected head role based on orientation");
                }
        }
        // Eliminate roles based on connector constraints, only supports ROLE_ANY currently
        Iterator<Role> connector_roles_iterator = selected_roles.iterator();
        while(connector_roles_iterator.hasNext()) {
            Role role = connector_roles_iterator.next();
            for(Constraint constraint: role.connector.values()) {
                int count = 0;
                for(int i=0; i<8; i++)
                    if((this.connector_direction[i]&constraint.direction)!=0) {
                        //System.out.println(" Module "+self.getModule().getProperty("name")+" considering "+role.id+" connector "+i);
                        if(this.neighbor_roles[i]!=NO_MODULE) count++;
                    }
                //System.out.println("Module "+self.getModule().getProperty("name")+" count="+count+", needed "+constraint.count);
                if(constraint.count!=count) connector_roles_iterator.remove(); 
            }
        }
        // Did we match?
        if(selected_roles.size()==1) {
            //if(head) System.out.println("size1:"+i++);
            Role selected = selected_roles.get(0);
            if(this.local_role==selected.id) return false;
            this.local_role = selected.id;
            //System.out.println("*Selected role "+this.local_role+" for module "+self.getModule().getProperty("name"));
            if(selected.orientation_definition!=0) {
                set_local_origo(selected.orientation_definition);
                //System.out.println("*Setting module "+self.getModule().getProperty("name")+" as origo: "+get_orientation());
            }
        }
        else {
            //if(head) System.out.println("sizen:"+i++);
            if(this.local_role==ROLE_ANY) {
                //System.out.println("Already any");
                return false;
            }
            boolean reset_orientation = (this.local_role!=ROLE_ANY && lookup(this.local_role).orientation_definition!=ORI_NONE);
            this.local_role = ROLE_ANY;
            //System.out.println("*Set local origo to none");
            if(reset_orientation) set_local_origo(ORI_NONE);
        }
        return true;
    }

    private boolean match_orientation(int orientation_constraint) {
        // Note: same conditions as set_local_origo, combine
        final int t = TILT_THRESHOLD;
        int x = self.getTiltX(), ax = (int)Math.abs(x);
        int y = self.getTiltY(), ay = (int)Math.abs(y);
        int z = self.getTiltZ(), az = (int)Math.abs(z);
        if(orientation_constraint==ORI_NS) {
            if(ay<t && az<t)
                return (x>t || x<-t);
        }
        return false;

    }
    // Find orientation, trying to match to required origo
    public void set_local_origo(int definition) {
        final int t = TILT_THRESHOLD;
        boolean updated = definition==ORI_NONE;
        this.local_orientation = definition;
        connector_direction  = new int[8];
        if(definition==ORI_NS) {
            int x = self.getTiltX(), ax = (int)Math.abs(x);
            int y = self.getTiltY(), ay = (int)Math.abs(y);
            int z = self.getTiltZ(), az = (int)Math.abs(z);
            if(ay<t && az<t) {
                if(x>t) {
                    System.out.println("Initial orientation for head of small vehicle"); 
                    connector_direction[0] = DIR_UP|DIR_NORTH;
                    connector_direction[1] = DIR_WEST|DIR_NORTH;
                    connector_direction[2] = DIR_DOWN|DIR_NORTH;
                    connector_direction[3] = DIR_EAST|DIR_NORTH;
                    connector_direction[4] = DIR_UP|DIR_SOUTH;
                    connector_direction[5] = DIR_WEST|DIR_SOUTH;
                    connector_direction[6] = DIR_DOWN|DIR_SOUTH;
                    connector_direction[7] = DIR_EAST|DIR_SOUTH;
                    updated = true;
                } else if(x<-t) {
                    System.out.println("Upside-down orientation for head of small vehicle"); 
                    connector_direction[0] = DIR_DOWN|DIR_SOUTH;
                    connector_direction[1] = DIR_EAST|DIR_SOUTH;
                    connector_direction[2] = DIR_UP|DIR_SOUTH;
                    connector_direction[3] = DIR_WEST|DIR_SOUTH;
                    connector_direction[4] = DIR_DOWN|DIR_NORTH;
                    connector_direction[5] = DIR_EAST|DIR_NORTH;
                    connector_direction[6] = DIR_UP|DIR_NORTH;
                    connector_direction[7] = DIR_WEST|DIR_NORTH;
                    updated = true;
                }
            } else if(ax<t && az<t) {
                if(y>t)
                    System.out.println("Right wheel up");
                else if(y<-t)
                    System.out.println("Left wheel up");
            }
        }
        // If updated send message to neighbors
        if(updated) {
            for(byte c=0; c<8; c++) {
                if(this.neighbor_roles[c]==NO_MODULE) continue;
                //System.out.println("Module "+self.getModule().getProperty("name")+" connector "+c+" has orientation "+connector_direction[c]);
                byte[] message = new byte[] { this.message_prefix, MSG_ORI, transform_orientation(connector_direction[c]), reverse_direction(connector_direction[c]) }; 
                self.sendMessage(message, (byte)4, c);
            }
        }
    }

    private byte transform_orientation(int direction) {
        switch(this.local_orientation) {
        case ORI_NONE: return ORI_NONE;
        case ORI_NS:
            if((direction&DIR_WEST)!=0 || (direction&DIR_EAST)!=0) return ORI_EW;
            return ORI_UD;
        default:
            throw new Error("Not implemented: "+local_orientation);
        }
    }
    // Helpers
    private byte reverse_direction(int direction) {
        int result = 0;
        if((direction&DIR_NORTH)!=0) result|=DIR_SOUTH;
        if((direction&DIR_SOUTH)!=0) result|=DIR_NORTH;
        if((direction&DIR_WEST)!=0) result|=DIR_EAST;
        if((direction&DIR_EAST)!=0) result|=DIR_WEST;
        if((direction&DIR_UP)!=0) result|=DIR_DOWN;
        if((direction&DIR_DOWN)!=0) result|=DIR_UP;        
        return (byte)result;
    }

    private void update_context() {
        this.neighbor_count = 0;
        for(int i=0; i<8; i++) {
            if(self.isConnected(i)) {
                this.neighbor_roles[i] = ROLE_ANY;
                this.neighbor_count++;
            }
            else
                this.neighbor_roles[i] = NO_MODULE;
        }
    }

    // Network communication
    public void deliver_message(byte[] message, byte length, byte connector) {
        parse_message: {
        if(message.length<2) break parse_message;
        if(!(message[0]==this.message_prefix)) break parse_message;
        if(!(message[1]==MSG_ORI)) break parse_message;
        byte orientation = message[2];
        byte direction = message[3];
        set_remote_orientation(orientation,connector,direction);
        return;
        }
    System.err.println("Warning: illegal message received by "+self.getModule().getProperty("name")+": "+message);
    }

    private void set_remote_orientation(int orientation, int connector, int direction) {
        this.local_orientation = orientation;
        this.connector_direction = new int[8];
        switch(orientation) {
        case ORI_NONE:
            System.out.println("Reset orientation in "+self.getModule().getProperty("name"));
            break;
        case ORI_EW:
            int ew = direction&(DIR_EAST|DIR_WEST);
            if(ew!=0) label_hemispheres(connector,ew);
            //System.out.println("Updated connector directions in "+self.getModule().getProperty("name")+", 4="+connector_direction[4]);
            break;
        case ORI_UD:
            // Handle; ideas for operations: connector -> neighboring connectors, opposing connector 
        default:
            throw new Error("Not implemented: "+orientation);
        }
    }

    private void label_hemispheres(int designator, int direction) {
        boolean lower = designator<4;
        for(int i=0; i<4; i++) connector_direction[i] |= lower ? direction : reverse_direction(direction);
        for(int i=4; i<8; i++) connector_direction[i] |= !lower ? direction : reverse_direction(direction);
    }

}
