/**
 * 
 */
package ussr.model;

import ussr.physics.PhysicsModule;

/**
 * @author ups
 *
 * A module is the basic unit from which the modular robot is built.  Specifically,
 * each module is associated with a controller, and is thus the unit of autonomous
 * behavior.
 * 
 */
public class Module extends Entity {
    /**
     * The controller of the module
     */
    private Controller controller;
    
    /**
     * The physics model for the module
     */
    private PhysicsModule physics;
    
}
