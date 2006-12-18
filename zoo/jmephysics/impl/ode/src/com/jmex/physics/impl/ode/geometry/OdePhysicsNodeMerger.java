package com.jmex.physics.impl.ode.geometry;

import java.util.ArrayList;

import com.jme.scene.Spatial;
import com.jmex.physics.PhysicsCollisionGeometry;
import com.jmex.physics.PhysicsNode;

/**
 * Helper class to provide physics node merging interface
 * 
 * @author jspohr
 */
public class OdePhysicsNodeMerger {
    /**
     * Merges the set of children of a physics node into another's.
     * The other node will have its children removed.
     * 
     * @param lhs PhysicsNode which receives all children of the other one.
     * @param rhs PhysicsNode whose children are moved to the other one.
     */
	static public void mergePhysicsNode( PhysicsNode lhs, PhysicsNode rhs ) {
		ArrayList<Spatial> children = new ArrayList<Spatial>( rhs.getChildren() );
		for ( Spatial child : children ) {
			if ( child instanceof PhysicsCollisionGeometry ) {
				PhysicsCollisionGeometry collisionGeometry = (PhysicsCollisionGeometry) child;
				boolean inheritsMaterial = ( collisionGeometry.getMaterial() == rhs.getMaterial() )
					&& ( rhs.getMaterial() != rhs.getSpace().getDefaultMaterial() );
				if ( collisionGeometry instanceof OdeGeometry )
					( (OdeGeometry) collisionGeometry ).setNode( lhs );
				if ( inheritsMaterial )
					collisionGeometry.setMaterial( rhs.getMaterial() );
			}
			lhs.attachChild( child );
		}
	}
}

/*
 * $log$
 */
