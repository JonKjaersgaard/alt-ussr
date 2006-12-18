/*Copyright*/
package com.jmex.physics.geometry;

import com.jme.math.FastMath;
import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.shape.Cylinder;
import com.jme.scene.shape.Dome;
import com.jmex.physics.PhysicsCollisionGeometry;
import com.jmex.physics.PhysicsDebugger;
import com.jmex.physics.PhysicsNode;

/**
 * Capsule has a radius of 1, a height of 1, and a center of (0,0,0) - change center via local translation,
 * radius via local x scale and height via local z scale.
 *
 * @author Irrisor
 */
public abstract class PhysicsCapsule extends PhysicsCollisionGeometry {

    protected PhysicsCapsule( PhysicsNode node ) {
        super( node );
    }

    private static final Node debugShape = new Node( "PhysicsCapsule" );

    private static final Cylinder cylinder = new Cylinder( "PhysicsCapsule", 8, 10, 1, 1, true );
    private static final Dome dome1 = new Dome( "PhysicsCapsule", 5, 10, 1 );
    private static final Dome dome2 = new Dome( "PhysicsCapsule", 5, 10, 1 );

    static {
        debugShape.attachChild( cylinder );
        debugShape.attachChild( dome1 );
        debugShape.attachChild( dome2 );
        PhysicsDebugger.setupDebugGeom( cylinder );
        PhysicsDebugger.setupDebugGeom( dome1 );
        PhysicsDebugger.setupDebugGeom( dome2 );
        dome2.getLocalRotation().fromAngleNormalAxis( FastMath.PI / 2, new Vector3f( 1, 0, 0 ) );
        dome1.getLocalRotation().fromAngleNormalAxis( -FastMath.PI / 2, new Vector3f( 1, 0, 0 ) );
    }

    @Override
    protected void drawDebugShape( PhysicsNode physicsNode, Renderer r ) {
        final Vector3f worldScale = getWorldScale();
        final float radius = worldScale.x;
        final float height = worldScale.z;
        Vector3f size = cylinder.getLocalScale();
        size.set( radius, radius, height ); // keep the capsule base a circle
        dome1.getLocalScale().set( radius, radius, radius );
        dome2.getLocalScale().set( radius, radius, radius );
        dome1.getLocalTranslation().set( 0, 0, -height / 2 );
        dome2.getLocalTranslation().set( 0, 0, height / 2 );
        debugShape.setLocalTranslation( getWorldTranslation() );
        debugShape.getLocalRotation().set( getWorldRotation() );
        debugShape.updateGeometricState( 0, true );
        PhysicsDebugger.drawDebugShape( PhysicsCapsule.debugShape, getWorldTranslation(), this, r,
                Math.max( Math.max( size.x, size.y ), size.z ) );
    }

    @Override
    public float getVolume() {
        Vector3f size = getWorldScale();
        final float radius = size.x;
        final float height = size.z;
        return FastMath.PI * FastMath.sqr( radius ) * ( height + 4f / 3 * radius );
    }

	@Override
	public Class getClassTag() {
		return PhysicsCapsule.class;
	}
}

/*
 * $log$
 */

