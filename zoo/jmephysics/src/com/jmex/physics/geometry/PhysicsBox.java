package com.jmex.physics.geometry;

import com.jme.math.Vector3f;
import com.jme.renderer.Renderer;
import com.jme.scene.shape.Box;
import com.jmex.physics.PhysicsCollisionGeometry;
import com.jmex.physics.PhysicsDebugger;
import com.jmex.physics.PhysicsNode;

/**
 * Box has a size of (1,1,1) and a center of (0,0,0) - change center via local translation and size via local scale.
 *
 * @author Irrisor
 */
public abstract class PhysicsBox extends PhysicsCollisionGeometry {

    protected PhysicsBox( PhysicsNode node ) {
        super( node );
    }

    private static final Box debugShape = new Box( "PhysicsBox", new Vector3f(), 1, 1, 1 );

    static {
        PhysicsDebugger.setupDebugGeom( debugShape );
    }

    @Override
    protected void drawDebugShape( PhysicsNode physicsNode, Renderer r ) {
        Vector3f size = debugShape.getLocalScale();
        size.set( getWorldScale() ).multLocal( 0.5f ); // as extent = size/2
        debugShape.setLocalTranslation( getWorldTranslation() );
        debugShape.setLocalRotation( getWorldRotation() );
        debugShape.updateWorldVectors();
        PhysicsDebugger.drawDebugShape( debugShape, getWorldTranslation(), this, r,
                Math.max( Math.max( size.x, size.y ), size.z ) );
    }

    @Override
    public float getVolume() {
        Vector3f size = getWorldScale();
        return size.x * size.y * size.z;
    }

	@Override
	public Class getClassTag() {
		return PhysicsBox.class;
	}
}

/*
* $log$
*/
