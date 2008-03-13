/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics;

/**
 * The physics simulation notifies observers (as per the observer design pattern) at
 * each time step, allowing code to potentially be run at every physics step.  Objects
 * implementing this interface can be subscribed to these step events using the method
 * {@link PhysicsSimulation#subscribePhysicsTimestep(PhysicsObserver)}
 * 
 * @see PhysicsSimulation#subscribePhysicsTimestep(PhysicsObserver)
 * @author Modular Robots @ MMMI
 */
public interface PhysicsObserver {
    /**
     * Method called by the physics simulation at every time step, if this object 
     * has been subscribed to physics time step events. 
     * @param simulation the simulation being observed/generating the event
     */
	public void physicsTimeStepHook(PhysicsSimulation simulation);
}
