/*
 * Copyright (c) 2005-2006 jME Physics 2
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of 'jME Physics 2' nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.jmex.physics.impl.ode;

import java.util.ArrayList;
import java.util.List;

import org.odejava.PlaceableGeom;
import org.odejava.Geom;

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

    private final List<Geom> geoms = new ArrayList<Geom>();

    private void addGeom( Geom geom ) {
        if ( isActive() ) {
            ( (OdePhysicsSpace) getSpace() ).addGeom( geom );
        }
        geom.setPhysicsObject( this );
        geoms.add( geom );
    }

    private void removeGeom( Geom geom ) {
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
                    Geom geom = geoms.get( i );
                    ( (OdePhysicsSpace) getSpace() ).addGeom( geom );
                }
            }
            else {
                for ( int i = geoms.size() - 1; i >= 0; i-- ) {
                    Geom geom = geoms.get( i );
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
