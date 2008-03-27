/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics;

import java.awt.Color;

import ussr.model.Module;

/**
 * A helper class for physics simulations, containing various utility methods that can
 * be applied to the <tt>PhysicsSimulation</tt> object associated with this helper.
 * 
 * @author Modular Robots @ MMMI
 */
public interface PhysicsSimulationHelper {

    /**
     * Obtain the simulated physics position of a module 
     * @param module the module to obtain the position of
     * @return the position of the module
     */
    PhysicsVectorHolder getModulePos(Module module);
    
    /**
     * Obtain the simulated physics orientation of a module
     * @param module the module to obtain the position of
     * @return the orientation of the module
     */
    PhysicsQuaternionHolder getModuleOri(Module module);
    
    /**
     * Set the color of a simulated physics object
     * @param object the object to assign a color to
     * @param color the color to assign to the object
     */
    void setColor(Object object, Color color);

    /**
     * Get the color of a simulated physics object
     * @return the color of the object
     */
    Color getColor(Object object);
}
