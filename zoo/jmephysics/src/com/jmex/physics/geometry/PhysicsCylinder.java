/*Copyright*/
package com.jmex.physics.geometry;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.shape.Cylinder;
import com.jmex.physics.PhysicsCollisionGeometry;
import com.jmex.physics.PhysicsDebugger;
import com.jmex.physics.PhysicsNode;

/**
 * Cylinder has a radius of 1, a height of 1, and a center of (0,0,0) - change center via local translation,
 * radius via local x scale and height via local z scale.
 *
 * @author Irrisor
 */
public abstract class PhysicsCylinder extends PhysicsCollisionGeometry {

    protected PhysicsCylinder( PhysicsNode node ) {
        super( node );
    }

    private static final Cylinder debugShape = new Cylinder( "PhysicsCylinder", 8, 10, 1, 1, true );

    static {
        PhysicsDebugger.setupDebugGeom( PhysicsCylinder.debugShape );
    }

    @Override
    protected void drawDebugShape( PhysicsNode physicsNode, Renderer r ) {
        Vector3f size = PhysicsCylinder.debugShape.getLocalScale();
        final Vector3f worldScale = getWorldScale();
        final float radius = worldScale.x;
        final float height = worldScale.z;
        size.set( radius, radius, height ); // keep the cylinder base a circle
        PhysicsCylinder.debugShape.setLocalTranslation( getWorldTranslation() );
        PhysicsCylinder.debugShape.getLocalRotation().set( getWorldRotation() );
        PhysicsCylinder.debugShape.updateWorldVectors();
        PhysicsDebugger.drawDebugShape( PhysicsCylinder.debugShape, getWorldTranslation(), this, r,
                Math.max( Math.max( size.x, size.y ), size.z ) );
    }

    @Override
    public float getVolume() {
        Vector3f size = getWorldScale();
        return FastMath.PI * FastMath.sqr( size.x ) * size.y;
    }

	@Override
	public Class getClassTag() {
		return PhysicsCylinder.class;
	}
}

/*
 * $log$
 */

