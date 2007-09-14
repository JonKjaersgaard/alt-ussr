/**
 * 
 */
package ussr.physics.jme;

import com.jmex.physics.DynamicPhysicsNode;

import ussr.physics.PhysicsEntity;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public interface JMEPhysicsEntity extends PhysicsEntity {
    public DynamicPhysicsNode getNode();
}
