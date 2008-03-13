/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.model;

/**
 * Interface the simulator expects user-defined controllers to implement, allowing
 * hardware to be associated with the controller and the controller to be started
 * in its own thread.
 *
 * @see ussr.model.ControllerImpl
 * @author Modular Robots @ MMMI
 *
 */
public interface Controller {
    
    /**
     * This method is called by the simulator in a separate thread, and should define
     * the computations performed by the module.  Activate will only be called once
     * the hardware has been set up.
     * 
     * @see ussr.model.Controller#setModule(Module)
     */
    public void activate();
    
    /**
     * This method is called by the simulator to provide the controller code with a
     * reference to the module.
     * @param module the hardware to associate with the controller
     */
    public void setModule(Module module);

    /**
     * Get the module associated with the controller
     * @return the representation of the module hardware
     */
	public Module getModule();
}
