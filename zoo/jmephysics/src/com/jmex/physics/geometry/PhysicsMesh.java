/*Copyright*/
package com.jmex.physics.geometry;

import java.io.IOException;

import com.jme.scene.TriMesh;
import com.jme.util.export.JMEExporter;
import com.jmex.physics.PhysicsCollisionGeometry;
import com.jmex.physics.PhysicsNode;

/**
 * A PhysicsMesh can represent any triangle soup that is used a collision geometry.
 * @see #copyFrom(com.jme.scene.TriMesh)
 * @author Irrisor
 */
public abstract class PhysicsMesh extends PhysicsCollisionGeometry {
    protected PhysicsMesh( PhysicsNode node ) {
        super( node );
    }

    /**
     * Copy data from a scene TriMesh to the collision info. The implementation may hold a reference to the specified
     * TriMesh data but usually will not update the collision info automatically.
     * @param triMesh where to copy triangle data from
     */
    public abstract void copyFrom( TriMesh triMesh );

	@Override
	public Class getClassTag() {
		return PhysicsMesh.class;
	}

	@Override
	public void write(JMEExporter ex) throws IOException {
		// TODO: write collision mesh independent of the physics implementation
		throw new IOException("Not implemented: currently, saving PhysicsMesh collision geometry doesn't work");
	}
}

/*
 * $log$
 */

