/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.physics;

import ussr.physics.jme.JMESimulation;

/**
 * An factory for creating implementation-level objects used by higher-level parts of the
 * simulator (currently only the simulator itself).
 *
 * TODO: replace hard-coded object instantiation with something more flexible 
 * 
 * @author ups
 * 
 */
public class PhysicsFactory {
    /**
     * Create a new physics simulation
     * @return a new physics simulation
     */
    public static PhysicsSimulation createSimulator() {
        return new JMESimulation(); // hard-coded for now, should change
    }
}
