package com.jmex.physics.geometry;

import com.jme.math.FastMath;
import com.jme.renderer.Renderer;
import com.jme.scene.shape.Sphere;
import com.jmex.physics.PhysicsCollisionGeometry;
import com.jmex.physics.PhysicsDebugger;
import com.jmex.physics.PhysicsNode;

/**
 * Sphere simply has a radius of 1 and a center of (0,0,0) - change center via local translation and radius via
 * uniform local scale.
 *
 * @author Irrisor
 */
public abstract class PhysicsSphere extends PhysicsCollisionGeometry {

    protected PhysicsSphere( PhysicsNode node ) {
        super( node );
    }

    private static final Sphere debugShape = new Sphere( "PhysicsSphere", 10, 10, 1 );

    static {
        PhysicsDebugger.setupDebugGeom( debugShape );
    }

    @Override
    protected void drawDebugShape( PhysicsNode physicsNode, Renderer r ) {
        float radius = getWorldScale().x;
        debugShape.setLocalScale( radius );
        debugShape.setLocalTranslation( getWorldTranslation() );
        debugShape.setLocalRotation( getWorldRotation() );
        debugShape.updateWorldVectors();
        PhysicsDebugger.drawDebugShape( debugShape, getWorldTranslation(), this, r, radius );
    }

    @Override
    public float getVolume() {
        return getWorldScale().x * 2 * FastMath.PI;
    }

	@Override
	public Class getClassTag() {
		return PhysicsSphere.class;
	}
}

/*
* $log$
*/
