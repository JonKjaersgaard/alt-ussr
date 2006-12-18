/*Copyright*/
package com.jmex.physics.impl.ode;

import org.odejava.PlaceableGeom;

/**
 * @author Irrisor
 */
public interface OdePhysicsNode {

    void sceneFromOde();

    void updateTransforms( PlaceableGeom geom );
}

/*
 * $log$
 */
