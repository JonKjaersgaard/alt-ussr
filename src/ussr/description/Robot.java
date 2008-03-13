/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.description;

import ussr.description.robot.RobotDescription;
import ussr.model.Controller;

/**
 * An abstract description of what a robot is: a description of the hardware and an
 * implementation of a controller. 
 * 
 * @author ups
 *
 */
public interface Robot {
    public Robot NO_DEFAULT = new Robot() {
        public Controller createController() { throw new Error("No default robot definition provided"); }
        public RobotDescription getDescription() { throw new Error("No default robot definition provided"); }
    };
    
    /**
     * Get a description of the hardware (physical characteristics) of the robot
     * @return a description of the robot hardware
     */
    public RobotDescription getDescription();
    
    /**
     * Create a new controller object that can be associated with the robot hardware
     * description by getDescription
     * @return a newly created controller object
     */
    public Controller createController();
}
