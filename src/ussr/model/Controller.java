/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.model;

/**
 * Interface the simulator expects user-defined controllers to implement, allowing
 * hardware to be associated with the controller and the controller to be started
 * in its own thread.
 *
 * @see ussr.model.ControllerImpl
 * @author ups
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
}
