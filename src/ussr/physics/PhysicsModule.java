/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.physics;

import ussr.model.Module;

/**
 * A simulated physical module, useable independently of the underlying physics engine.
 * 
 * @author ups
 *
 */
public interface PhysicsModule {
    
    /**
     * Reset the physics of the module, including velocity/acceleration, rotation, and
     * position.
     */
    public void reset();
    
    /**
     * Notify the module model associated with this physical module, that a detectable
     * change in the environment has occurred
     */
    public void changeNotify();
    
    /**
     * Get the model module associated with this physics module
     * @return the model module of this physics module
     */
    public Module getModel();
}
