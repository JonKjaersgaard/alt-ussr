/*Copyright*/
package com.jmex.physics.impl.ode.geometry;

import com.jme.math.Vector3f;
import com.jmex.physics.PhysicsNode;
import com.jmex.physics.geometry.PhysicsCapsule;
import com.jmex.physics.impl.ode.OdePhysicsNode;
import org.odejava.GeomCapsule;
import org.odejava.PlaceableGeom;

/**
 * @author Irrisor
 */
public class OdeCapsule extends PhysicsCapsule implements OdeGeometry {
    private final GeomCapsule geom;

    public PlaceableGeom getOdeGeom() {
        return geom;
    }

    public OdeCapsule( PhysicsNode node ) {
        super( node );
        geom = new GeomCapsule( getName(), 1, 1 );
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
        worldScale.y = worldScale.x; // yes the actual world scale is changed here
        geom.setRadius( worldScale.x );
        geom.setLength( worldScale.z );
    }

	@Override
	public void setNode(PhysicsNode node) {
		super.setNode(node);
	}
}

/*
 * $log$
 */

