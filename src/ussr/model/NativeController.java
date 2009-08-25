/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.model;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;

import ussr.physics.PhysicsParameters;
import ussr.physics.jme.JMESimulation;

/**
 * A controller with a native implementation, bridged to a controller implementing
 * the NativeControllerProvider interface.
 * 
 * @author Modular Robots @ MMMI
 */
public class NativeController implements Controller {

	public String getClassName() { return this.getClass().getName(); }
	
    protected Object eventLock;
    private final static int sleepThreshold = 100;
    private int idleLevel = 0;
    private static Set<String> libraryRegistry = new HashSet<String>();
    private Controller controller;
    private final int initializationContext;

    private ThreadMXBean threadMXBean;
    long lastYieldTime;
    long SimStepSizeNanoSec;
    
    private static synchronized void loadLibrary(String nativeLibraryName) {
        if(libraryRegistry.contains(nativeLibraryName)) return;
        try {
            System.loadLibrary(nativeLibraryName);
        } catch(UnsatisfiedLinkError error) {
            System.err.println("Unable to link native controller "+nativeLibraryName);
            System.err.println(" java.library.path="+System.getProperty("java.library.path"));
            System.err.println(" exception="+error);
            throw new Error("Native controller not found");
        }
        libraryRegistry.add(nativeLibraryName);
    }
    
    public NativeController(String nativeLibraryName) {
        loadLibrary(nativeLibraryName);
        initializationContext = this.nativeInitialize();
        this.SimStepSizeNanoSec = (long) (PhysicsParameters.get().getPhysicsSimulationStepSize() * PhysicsParameters.get().getPhysicsSimulationControllerStepFactor() * 1000000000);
        this.threadMXBean = ManagementFactory.getThreadMXBean();	
    }
    
    public void setInternalController(NativeControllerProvider controller) {
        this.controller = controller;
    }
    
    public Controller getInternalController() {
    	return controller;
    }
    
    public int getInitializationContext() {
    	return initializationContext;
    }
        
    public void activate() {
    	System.out.println("Activating, controller="+controller);
        this.nativeActivate(initializationContext);
    }

    private native void nativeActivate(int context);
    private synchronized native int nativeInitialize();
    
    void iterationSimulatorHook(boolean isActive) {
    	    	
        if(!isActive && idleLevel++>sleepThreshold) {
        	idleLevel = 0;
            eventLock = new Object();
            synchronized(eventLock) {
                try {
                    eventLock.wait();
                } catch(InterruptedException exn) {
                    throw new Error("Error: interrupted while waiting");
                }
            }
        }     
        //do { Thread.yield(); } while(controller.getModule().getSimulation().isPaused());
        while(controller.getModule().getSimulation().isPaused()) Thread.yield();

        long elapsed = threadMXBean.getCurrentThreadUserTime() - lastYieldTime;

        //need a certain accuracy: on OS X is ok, check on linux/win
    	if(elapsed > SimStepSizeNanoSec) {
    		lastYieldTime = threadMXBean.getCurrentThreadUserTime();
            
            if(PhysicsParameters.get().syncWithControllers()) {
            	try {
					( (JMESimulation) controller.getModule().getSimulation() ).controlSyncBarrier.await();
            	} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println("native ctrl interrupted");
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}	
            }
            else
            	controller.getModule().getSimulation().waitForPhysicsStep(false);
    	}
    }

    //quick hack mehtod
    public int getRole() {
        String name = controller.getModule().getProperty("name");
        for(int i=100;i>=0;i--) {
            if(name.contains(Integer.toString(i))) return i;
        }
        return -1;
    }

	public void setModule(Module module) {
		controller.setModule(module);
	}

	public Module getModule() {
		return controller.getModule();
	}

}
