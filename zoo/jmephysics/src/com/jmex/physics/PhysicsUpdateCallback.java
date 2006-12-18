/*Copyright*/
package com.jmex.physics;

/**
 * This class provides a callback for extending the behaviour of the physics. From all the methods in subclasses the
 * scenegraph should not be altered.
 *
 * @author Irrisor
 */
public interface PhysicsUpdateCallback {

    /**
     * Define logic that you want to be executed <b>before</b> the <code>PhysicsSpace</code>
     * does it's calculations.
     *
     * @param space space that stepps
     * @param time  amount of time the step simulates
     */
    void beforeStep( PhysicsSpace space, float time );

    /**
     * Define logic that you want to be executed <b>after</b> the <code>PhysicsSpace</code>
     * does it's calculations.
     *
     * @param space space that stepps
     * @param time  amount of time the step simulated
     */
    void afterStep( PhysicsSpace space, float time );
}

/*
 * $log$
 */
