/*Copyright*/
package com.jmex.physics.impl.ode.geometry;

import org.odejava.PlaceableGeom;

import com.jmex.physics.PhysicsNode;

/**
 * @author Irrisor
 */
public interface OdeGeometry {
    PlaceableGeom getOdeGeom();
    void setNode( PhysicsNode node );
}

/*
 * $log$
 */
