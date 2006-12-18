/*Copyright*/
package com.jmex.physics.impl.ode.geometry;

import com.jme.math.Vector3f;
import com.jmex.physics.PhysicsNode;
import com.jmex.physics.geometry.PhysicsBox;
import com.jmex.physics.impl.ode.OdePhysicsNode;
import org.odejava.GeomBox;
import org.odejava.PlaceableGeom;

/**
 * @author Irrisor
 */
public class OdeBox extends PhysicsBox implements OdeGeometry {
	
	private GeomBox geom;

    public PlaceableGeom getOdeGeom() {
        return geom;
    }

    public OdeBox( PhysicsNode node ) {
        super( node );
        geom = new GeomBox( getName(), 1, 1, 1 );
        geom.setGeometry( this );
    }

    protected void activate() {
    }

    @Override
    public void updateWorldVectors() {
        super.updateWorldVectors();
        //TODO: only if necessary!
        ( (OdePhysicsNode) getPhysicsNode() ).updateTransforms( geom );
        final Vector3f worldScale = this.worldScale;
        if ( worldScale.x <= 0 || worldScale.y <= 0 || worldScale.z <= 0 ) {
            // this makes ODE crash - so prefer to throw an exception
            throw new IllegalArgumentException( "scale must not have 0 as a component!" );
        }
        geom.setSize( worldScale );
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        geom.delete();
    }

	@Override
	public void setNode(PhysicsNode node) {
		super.setNode(node);
	}
}

/*
 * $log$
 */

