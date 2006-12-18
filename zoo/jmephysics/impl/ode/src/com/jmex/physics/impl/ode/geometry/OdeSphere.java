/*Copyright*/
package com.jmex.physics.impl.ode.geometry;

import com.jme.math.Vector3f;
import com.jmex.physics.PhysicsNode;
import com.jmex.physics.geometry.PhysicsSphere;
import com.jmex.physics.impl.ode.OdePhysicsNode;
import org.odejava.GeomSphere;
import org.odejava.PlaceableGeom;

/**
 * @author Irrisor
 */
public class OdeSphere extends PhysicsSphere implements OdeGeometry {
    private final GeomSphere geom;

    public PlaceableGeom getOdeGeom() {
        return geom;
    }

    public OdeSphere( PhysicsNode node ) {
        super( node );
        geom = new GeomSphere( getName(), 1 );
        geom.setGeometry( this );
    }

    @Override
    public void updateWorldVectors() {
        super.updateWorldVectors();
        //TODO: only if necessary!
        ( (OdePhysicsNode) getPhysicsNode() ).updateTransforms( geom );
        final Vector3f worldScale = this.worldScale;
        if ( worldScale.x <= 0 || worldScale.y <= 0 || worldScale.z <= 0 ) {
            // this makes ODE crash to prefer to throw an exception
            throw new IllegalArgumentException( "scale must not have 0 as a component!" );
        }
        float scale = Math.max( Math.max( worldScale.x, worldScale.y ), worldScale.z );
        worldScale.set( scale, scale, scale );
        geom.setRadius( scale );
    }

	@Override
	public void setNode(PhysicsNode node) {
		super.setNode(node);
	}
}

/*
 * $log$
 */

