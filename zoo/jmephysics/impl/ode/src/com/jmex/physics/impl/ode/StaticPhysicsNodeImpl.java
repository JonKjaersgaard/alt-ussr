package com.jmex.physics.impl.ode;

import java.util.ArrayList;
import java.util.List;

import org.odejava.PlaceableGeom;

import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jmex.physics.PhysicsCollisionGeometry;
import com.jmex.physics.PhysicsNode;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.StaticPhysicsNode;
import com.jmex.physics.impl.ode.geometry.OdeGeometry;
import com.jmex.physics.impl.ode.geometry.OdePhysicsNodeMerger;

/**
 * @author Irrisor
 */
public class StaticPhysicsNodeImpl extends StaticPhysicsNode implements OdePhysicsNode {

	public void sceneFromOde() {
        // static objects are not moved by ODE
    }

    public StaticPhysicsNodeImpl( OdePhysicsSpace space ) {
        this.space = space;
    }

    @Override
    public PhysicsSpace getSpace() {
        return space;
    }

    private final OdePhysicsSpace space;

    private final List<PlaceableGeom> geoms = new ArrayList<PlaceableGeom>();

    private void addGeom( PlaceableGeom geom ) {
        if ( isActive() ) {
            ( (OdePhysicsSpace) getSpace() ).addGeom( geom );
        }
        geom.setPhysicsObject( this );
        geoms.add( geom );
    }

    private void removeGeom( PlaceableGeom geom ) {
        if ( isActive() ) {
            ( (OdePhysicsSpace) getSpace() ).removeGeom( geom );
        }
        geoms.remove( geom );
    }

    @Override
    public boolean setActive( boolean value ) {
        boolean changed = super.setActive( value );
        if ( changed ) {
            if ( value ) {
                for ( int i = geoms.size() - 1; i >= 0; i-- ) {
                    PlaceableGeom geom = geoms.get( i );
                    ( (OdePhysicsSpace) getSpace() ).addGeom( geom );
                }
            }
            else {
                for ( int i = geoms.size() - 1; i >= 0; i-- ) {
                    PlaceableGeom geom = geoms.get( i );
                    ( (OdePhysicsSpace) getSpace() ).removeGeom( geom );
                }
            }
        }
        return changed;
    }

    @Override
    public int attachChild( Spatial child ) {
        Node oldParent = child != null ? child.getParent() : null;
        int index = super.attachChild( child );
        if ( child instanceof OdeGeometry ) {
            OdeGeometry odeGeometry = (OdeGeometry) child;
            if ( oldParent != this ) {
                addGeom( odeGeometry.getOdeGeom() );
            }
        }
        else if ( child instanceof PhysicsCollisionGeometry ) {
            throw new IllegalArgumentException( "Cannot handle geometries from different implementations!" );
        }
        return index;
    }

    @Override
    public int detachChild( Spatial child ) {
        Node oldParent = child != null ? child.getParent() : null;
        int index = super.detachChild( child );
        if ( child instanceof OdeGeometry ) {
            OdeGeometry odeGeometry = (OdeGeometry) child;
            if ( oldParent == this ) {
                removeGeom( odeGeometry.getOdeGeom() );
            }
        }
        return index;
    }

    public void updateTransforms( PlaceableGeom geom ) {
        PhysicsCollisionGeometry geometry = geom.getGeometry();
        geom.setPosition( geometry.getWorldTranslation() );
        geom.setQuaternion( geometry.getWorldRotation() );
    }

	@Override
	public void mergeWith(PhysicsNode other) {
		OdePhysicsNodeMerger.mergePhysicsNode(this, other);
	}
}

/*
* $log$
*/
