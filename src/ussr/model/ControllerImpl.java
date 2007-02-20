/**
 * 
 */
/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.model;

/**
 * Abstract class providing a default implementation of the Controller interface.
 * The class maintain a reference to the module and provides a convenience "wait
 * for an event to be signalled on the module" method.
 * 
 * @author ups
 *
 */
public abstract class ControllerImpl implements Controller {

    /**
     * Reference to the module controlled by this controller 
     */
    protected Module module;
    
    /** 
     * @see ussr.model.Controller#activate()
     */
    public abstract void activate();

    /** 
     * @see ussr.model.Controller#setModule(ussr.model.Module)
     */
    public void setModule(Module module) {
        this.module = module;
    }

    /**
     * Wait for an event to be signalled on the module object, using Java's built-in
     * notify operation.
     * @see java.lang.Object#notify()
     * @see ussr.model.Module#eventNotify()
     */
    protected void waitForEvent() {
        synchronized(module) {
            try {
                module.wait();
            } catch (InterruptedException e) {
                throw new Error("Unexpected interrupt of waiting module");
            }
        }
    }
    protected void delay(long ms) {
    	float stopTime = module.getSimulation().getTime()+ms/1000f;
    	while(stopTime>module.getSimulation().getTime()) {
    		Thread.yield();
    	}
	}
}
