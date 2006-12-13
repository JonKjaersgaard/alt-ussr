/**
 * 
 */
package ussr.physics;

import ussr.physics.jme.JMESimulation;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class PhysicsFactory {
    public static PhysicsSimulation createSimulator() {
        return new JMESimulation();
    }
}
