/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.physics;

import ussr.comm.Packet;
import ussr.comm.TransmissionType;
import ussr.model.Entity;
import ussr.model.Module;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.WorldDescription;

/**
 * A physics simulation, defined independently of the underlying physics engine.  Instances
 * of this interface are created using the PhysicsFactory class.  The methods allow 
 * simulation parameters to be defined, and to start the simulation running.
 * 
 * @author ups
 *
 */
public interface PhysicsSimulation {

    /**
     * Define a user input handler for the running simulation, allowing the user to
     * interact with the simulation in a dedicated way.  Note that the underlying
     * simulation engine may already define certain keys to certain functions, which
     * may or may not be overridden by this method (the behavior is undefined).
     * @param keyName the name of the key (or key sequence) for which to create an action
     * @param handler an object encapsulating the user-defined action
     */
    public void addInputHandler(String keyName, Handler handler);

    /**
     * Define the robot to be used in this simulation, including visual appearance,
     * physical characteristics, and controller functionality.
     * @param bot the description of the robot used in this simulation
     */
    public void setRobot(Robot bot);

    /**
     * Define the world in which the robots are simulation, including starting configuration
     * of the robot and physical obstacles.
     * @param world the descriptoin of the world used for the simulation
     */
    public void setWorld(WorldDescription world);

    /**
     * An ultra-simple user-input handler
     * @author ups
     */
    public static interface Handler {
        /**
         * Handle an input from the user
         */
        public void handle();
    }
    
    /**
     * Start the simulation
     */
    public void start();

    public void sendMessage(TransmissionType type, Entity emitter, float range, Packet data);
    
}