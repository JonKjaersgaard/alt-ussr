/*Copyright*/
package com.jmex.physics.impl.ode.geometry;

import com.jme.math.Vector3f;
import com.jmex.physics.PhysicsNode;
import com.jmex.physics.geometry.PhysicsCylinder;
import com.jmex.physics.impl.ode.OdePhysicsNode;
import org.odejava.GeomCylinder;
import org.odejava.PlaceableGeom;

/**
 * @author Irrisor
 */
public class OdeCylinder extends PhysicsCylinder implements OdeGeometry {
    private final GeomCylinder geom;

    public PlaceableGeom getOdeGeom() {
        return geom;
    }

    public OdeCylinder( PhysicsNode node ) {
        super( node );
        geom = new GeomCylinder( getName(), 1, 1 );
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
        worldScale.y = worldScale.x;
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

