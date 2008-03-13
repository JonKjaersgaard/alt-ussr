/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics;

import java.awt.Color;
import java.util.List;

import ussr.model.Module;

/**
 * A simulated physical module, useable independently of the underlying physics engine.
 * 
 * @author Modular Robots @ MMMI
 *
 */
public interface PhysicsModuleComponent extends PhysicsEntity {
    
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

    /**
     * Set the color of the module component
     * @param color the color to assign to the module component
     */
    public void setModuleComponentColor(Color color);
    
    /**
     * Get the color of the module component
     * @return the color this physics component
     */
    public Color getModuleComponentColor();
    
    /**
     * Obtain a reference to the physics simulation in which this module is located
     * @return the physics simulation of this module
     * @see #getSimulationHelper()
     */
    public PhysicsSimulation getSimulation();

    /**
     * Obtain a reference to the helper (utility class) for the simulation in which
     * the module is being simulated
     * @return the helper for the simulation of the module
     */
    public PhysicsSimulationHelper getSimulationHelper();

}
