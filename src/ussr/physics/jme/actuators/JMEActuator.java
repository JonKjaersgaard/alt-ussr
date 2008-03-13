/**
 * 
 */
package ussr.physics.jme.actuators;

import ussr.model.Connector;
import ussr.model.PhysicsActuator;

import com.jmex.physics.DynamicPhysicsNode;

/**
 * Interface for actuators for the JME-based simulation
 * 
 * @author david
 *
 */
public interface JMEActuator extends PhysicsActuator {
    /**
     * Connect two dynamic physics nodes using this actuator
     * @param d1 the one node to connect
     * @param d2 the other node to connect
     */
    public void attach(DynamicPhysicsNode d1, DynamicPhysicsNode d2);
}
