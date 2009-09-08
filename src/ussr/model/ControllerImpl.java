/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.model;

import java.util.Random;

/**
 * Abstract class providing a default implementation of the Controller interface.
 * The class maintain a reference to the module and provides a convenience "wait
 * for an event to be signaled on the module" method.
 * 
 * @author ups
 *
 */

public abstract class ControllerImpl implements Controller {

    private static int randomSeed = 0;
    private static Random rand;
    private float localTimeOffset;
    
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
    
    public int getModuleID() {
        if(getModuleRole()!=-1)
            return getModuleRole();
    	return module.getID();
    }
    
    public int getModuleRole() {
        String roleMaybe = this.getModule().getProperty("roleid");
        if(roleMaybe!=null) try {
            return Integer.parseInt(roleMaybe);
        } catch(NumberFormatException exn) {
            return -1;
        }
        return -1;
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
     * current thread of control using {@link #yield()} while busy-waiting.
     * @param ms amount of time to wait, in simulation milliseconds
     */
    public void delay(int ms) {
    	float stopTime = module.getSimulation().getTime()+ms/1000f;
    	while(stopTime>module.getSimulation().getTime()) {
    		yield();
    	}
	}

    /**
     * Yield the control to some other thread, or pause if the simulation is paused.
     * Yielding is done by waiting for the simulator to take a physics step.
     */
    public void yield() {
    	while(module.getSimulation().isPaused()) Thread.yield();
    	module.getSimulation().waitForPhysicsStep(false);	
	}

    private static synchronized int internalGetRandomSeed() {
    	randomSeed++;
    	return new Random(randomSeed).nextInt();
    }
    
    protected static Random getRandom() {
    	if(rand==null) rand = new Random(internalGetRandomSeed());
    	return rand;
    }
    
    public int getRandomSeed() {
        return internalGetRandomSeed();
    }

    /**
     * @see ussr.samples.atron.IATRONAPI#getName()
     */
    public String getName() {
        return module.getProperty("name");
    }

    /**
     * @see ussr.samples.atron.IATRONAPI#getTime()
     */
    public float getTime() {
    	//TODO local version of this instead of global and syncronized
    	return getModule().getSimulation().getTime() + localTimeOffset;
    }
    
    /**
     * Set offset to something random to simulate that the modules do not have a shared timer
     */
    public void setLocalTimeOffset(float offset) {
    	localTimeOffset = offset;
    }
}
