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

package com.jmex.physics.impl.joode;

import java.util.List;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.PhysicsCollisionGeometry;
import com.jmex.physics.PhysicsNode;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.impl.joode.geometry.JoodeGeometry;
import com.jmex.physics.impl.joode.geometry.JoodePhysicsNodeMerger;
import net.java.dev.joode.Body;
import net.java.dev.joode.Mass;
import net.java.dev.joode.geom.Geom;
import net.java.dev.joode.geom.GeomTransform;

/**
 * @author Irrisor
 */
public class DynamicPhysicsNodeImpl extends DynamicPhysicsNode implements PhysicsNodeImpl {

    private final Body body;

    public Body getBody() {
        return body;
    }

    /**
     * Temp variables to flatline memory usage.
     */
    private final static Vector3f odePos = new Vector3f();

    /**
     * Temp variables to flatline memory usage.
     */
    private final static Quaternion odeRot = new Quaternion();

    public DynamicPhysicsNodeImpl( JoodePhysicsSpace space ) {
        this.space = space;
        body = space.createBody( getName() );
    }

    @Override
    public void setName( String name ) {
        super.setName( name );
    }

    @Override
    public PhysicsSpace getSpace() {
        return space;
    }

    private final JoodePhysicsSpace space;

    private void addGeom( Geom geom ) {
        GeomTransform transform = new GeomTransform( space.getJoodeSpace(), geom );
        geom.body = body;
        transform.setUserData( geom.getUserData() );
        transform.setBody( body );
        if ( isActive() ) {
            ( (JoodePhysicsSpace) getSpace() ).addGeom( transform );
        }
    }

    private void removeGeom( Geom geom ) {
        for ( int i = body.geoms.size() - 1; i >= 0; i-- ) {
            GeomTransform transform = (GeomTransform) body.geoms.get( i );
            if ( transform.getGeom() == geom ) {
                transform.setBody( null );
                if ( isActive() ) {
                    ( (JoodePhysicsSpace) getSpace() ).removeGeom( transform );
                }
                break;
            }
        }
    }

    @Override
    public boolean setActive( boolean value ) {
        boolean changed = super.setActive( value );
        if ( changed ) {
            if ( value ) {
                for ( int i = body.geoms.size() - 1; i >= 0; i-- ) {
                    Geom geom = body.geoms.get( i );
                    ( (JoodePhysicsSpace) getSpace() ).addGeom( geom );
                }
            } else {
                for ( int i = body.geoms.size() - 1; i >= 0; i-- ) {
                    Geom geom = body.geoms.get( i );
                    ( (JoodePhysicsSpace) getSpace() ).removeGeom( geom );
                }
            }
        }
        return changed;
    }

    @Override
    public int attachChild( Spatial child ) {
        Node oldParent = child != null ? child.getParent() : null;
        int index = super.attachChild( child );
        if ( child instanceof JoodeGeometry ) {
            JoodeGeometry joodeGeometry = (JoodeGeometry) child;
            if ( oldParent != this ) {
                try {
                    addGeom( joodeGeometry.getOdeGeom() );
                } catch ( ClassCastException e ) {
                    throw errorGeomNotSupported( joodeGeometry );
                }
            }
        }
        return index;
    }

    private UnsupportedOperationException errorGeomNotSupported( JoodeGeometry joodeGeometry ) {
        return new UnsupportedOperationException( "This implementation does not support " +
                "this kind of geometry for dynamic nodes: " + joodeGeometry.getClass().getName() );
    }

    @Override
    public int detachChild( Spatial child ) {
        Node oldParent = child != null ? child.getParent() : null;
        int index = super.detachChild( child );
        if ( child instanceof JoodeGeometry ) {
            JoodeGeometry joodeGeometry = (JoodeGeometry) child;
            if ( oldParent == this ) {
                try {
                    removeGeom( joodeGeometry.getOdeGeom() );
                } catch ( ClassCastException e ) {
                    throw errorGeomNotSupported( joodeGeometry );
                }
            }
        }
        return index;
    }

    private static final Vector3f tmpPosition = new Vector3f();
    private static final net.java.dev.joode.util.Quaternion tmpRotation = new net.java.dev.joode.util.Quaternion();

    public void updateTransforms( Geom geom ) {
        PhysicsCollisionGeometry geometry = (PhysicsCollisionGeometry) geom.getUserData();
        Vector3f pos = tmpPosition.set( geometry.getLocalTranslation() )
//                .subtractLocal( getCenterOfMass( centerOfMassStore ) )
                .multLocal( getWorldScale() );
        geom.setPosition( pos.x, pos.y, pos.z );
        geom.setQuaternion( VecMathConvert.convert( geometry.getLocalRotation(), tmpRotation ) );
    }

    @Override
    public void updateWorldVectors() {
        super.updateWorldVectors();
        //TODO: only if necessary!
        sceneToOde();
    }

    private void sceneToOde() {
//        localToWorld( getCenterOfMass( centerOfMassStore ), centerOfMassStore );
//        body.setPosition( centerOfMassStore );
        Vector3f pos = getWorldTranslation();
        body.setPosition( pos.x, pos.y, pos.z );
        body.setRotation( VecMathConvert.convert( getWorldRotation(), tmpRotation ) );
    }

    public void sceneFromOde() {
        VecMathConvert.convert( body.pos, odePos );
        VecMathConvert.convert( body.q, odeRot );
//        getWorldRotation().mult( getCenterOfMass( centerOfMassStore ),
//                centerOfMassStore ).multLocal( getWorldScale() );
//        odePos.subtractLocal( centerOfMassStore );
        space.worldToLocal( this, odePos, getLocalTranslation() );
        space.setWorldRotation( this, odeRot );
    }


    @Override
    public void addForce( Vector3f force, Vector3f at ) {
        if ( at != null ) {
            body.addForceAtRelPos( force.x, force.y, force.z, at.x, at.y, at.z );
        } else {
            body.addForce( force.x, force.y, force.z );
        }
    }

    @Override
    public void addTorque( Vector3f torque ) {
        body.addTorque( torque.x, torque.y, torque.z );
    }

    @Override
    public void clearForce() {
        body.facc.set( 0, 0, 0 );
    }

    @Override
    public void clearTorque() {
        body.tacc.set( 0, 0, 0 );
    }

    @Override
    public Vector3f getAngularVelocity( Vector3f store ) {
        if ( store == null ) {
            store = new Vector3f();
        }
        return VecMathConvert.convert( body.avel, store );
    }

    @Override
    public Vector3f getForce( Vector3f store ) {
        if ( store == null ) {
            store = new Vector3f();
        }
        return VecMathConvert.convert( body.facc, store );
    }

    @Override
    public Vector3f getLinearVelocity( Vector3f store ) {
        if ( store == null ) {
            store = new Vector3f();
        }
        return VecMathConvert.convert( body.lvel, store );
    }

    @Override
    public float getMass() {
        return body.mass != null ? body.mass.mass : 0;
    }

    @Override
    public Vector3f getTorque( Vector3f store ) {
        if ( store == null ) {
            store = new Vector3f();
        }
        return VecMathConvert.convert( body.tacc, store );
    }

    @Override
    public boolean isAffectedByGravity() {
        return ( body.flags & Body.dxBodyNoForce ) != 0;
    }

    @Override
    public void setAffectedByGravity( final boolean value ) {
        if ( value ) {
            body.flags |= Body.dxBodyNoForce;
        } else {
            body.flags &= ~Body.dxBodyNoForce;
        }
    }

    @Override
    public void setAngularVelocity( Vector3f velocity ) {
        VecMathConvert.convert( velocity, body.avel );
    }

    private final Vector3f centerOfMass = new Vector3f();

    @Override
    public Vector3f getCenterOfMass( Vector3f store ) {
        if ( store == null ) {
            store = new Vector3f();
        }
        return store.set( centerOfMass );
    }

    @Override
    public void setCenterOfMass( final Vector3f value ) {
        float oldMass = getMass();
        if ( oldMass == 0 ) {
            throw new IllegalStateException( "Current mass is 0 - that's not a valid mass!" );
        }
        for ( int i = this.getQuantity() - 1; i >= 0; i-- ) {
            Spatial spatial = this.getChild( i );
            spatial.getLocalTranslation().addLocal( centerOfMass );
        }

        computeMass();
        centerOfMassStore.set( value ).multLocal( getLocalScale() );
        // todo? : body.setCenterOfMass( centerOfMassStore );

        for ( int i = this.getQuantity() - 1; i >= 0; i-- ) {
            Spatial spatial = this.getChild( i );
            spatial.getLocalTranslation().subtractLocal( value );
        }

        centerOfMass.set( value );
        setMass( oldMass );
    }

    @Override
    public void computeMass() {
        List geoms = body.geoms;
        if ( geoms.size() == 0 ) {
            throw new IllegalStateException( "no collision geometries have been added to this node yet - " +
                    "cannot compute mass!" );
        }
        updateGeometricState( 0, true ); // update geom positions
        Mass mass = null;
        float massValue = 0;
        for ( int i1 = geoms.size() - 1; i1 >= 0; i1-- ) {
            Geom geom = (Geom) geoms.get( i1 );
            JoodeGeometry joodeGeometry = (JoodeGeometry) geom.getUserData();
            Mass additionalMass = joodeGeometry.createMass();
            if ( mass == null ) {
                mass = additionalMass;
            } else if ( additionalMass != null ) {
                mass.add( additionalMass );
            }
            PhysicsCollisionGeometry geometry = (PhysicsCollisionGeometry) geom.getUserData();
            float density = geometry.getMaterial().getDensity();
            massValue += geometry.getVolume() * density;
        }
        body.mass = mass;
        setMass( massValue );
    }

    @Override
    public void setLinearVelocity( Vector3f velocity ) {
        VecMathConvert.convert( velocity, body.lvel );
    }

    @Override
    public void setMass( float value ) {
        if ( value <= 0 ) {
            throw new IllegalArgumentException( "mass cannot be <= 0" );
        }
        body.mass.adjust( value );
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        space.deleteBody( body );
    }

    @Override
    public void mergeWith( PhysicsNode other ) {
        JoodePhysicsNodeMerger.mergePhysicsNode( this, other );
    }
}

/*
* $log$
*/
