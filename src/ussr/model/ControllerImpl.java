/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
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
     * Reference to the remoteControllerConnection  
     */
    protected RemoteControllerConnection remoteControllerConnection;
    
    
    
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

    public Module getModule() {
    	return module;
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
    
    /**
     * Wait for a given amount of time, in terms of the simulation.  Yields the
     * current thread of control using {@link #controlYield()} while busy-waiting.
     * @param ms amount of time to wait, in simulation milliseconds
     */
    protected void delay(long ms) {
    	float stopTime = module.getSimulation().getTime()+ms/1000f;
    	while(stopTime>module.getSimulation().getTime()) {
    		controlYield();
    	}
	}

    /**
     * Yield the control to some other thread, or pause if the simulation is paused.
     * Yielding is done by waiting for the simulator to take a physics step.
     */
    public void controlYield() {
    	while(module.getSimulation().isPaused()) Thread.yield();
    	module.getSimulation().waitForPhysicsStep(false);	
	}


}
