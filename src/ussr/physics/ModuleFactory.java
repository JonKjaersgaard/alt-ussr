/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics;

import ussr.description.Robot;
import ussr.model.Module;

/**
 * A module factory creates robot modules in a simulation instance.  The factory is called
 * during setup of the simulation, based on the names of the modules that have been placed
 * in the simulation.  The prefix associated with the module is used to decide what factory
 * is invoked.
 * 
 * Factories are created in ussr.physics.PhysicsFactory.createSimulator
 * 
 * @see ussr.physics.PhysicsFactory#createSimulator()
 * @author Modular Robots @ MMMI
 *
 */
public interface ModuleFactory {

    /**
     * Create a module in the simulation instance. 
     * @param module_id the internal ID of the module, must be globally unique 
     * @param module the module abstraction to associate with the physically simulated module
     * @param robot describes the module to create
     * @param module_name name associated with the module inside the simulation
     */
    public void createModule(int module_id, Module module, Robot robot, String module_name);

    /**
     * Obtain the module naming prefix that should match this factory
     * @return the module prefix
     */
    public String getModulePrefix();
    
    /**
     * Specify in what simulation to create module
     * @param simulation
     */
    public void setSimulation(PhysicsSimulation simulation);
}
