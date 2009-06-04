package com.jmex.physics.impl.jbullet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bulletphysics.BulletGlobals;
import com.bulletphysics.collision.dispatch.CollisionFlags;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.CompoundShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.jme.math.Matrix3f;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Spatial;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.impl.jbullet.geometry.JBulletGeometry;
import com.jmex.physics.impl.jbullet.util.VecmathConverter;

public class JBulletDynamicPhysicsNode extends DynamicPhysicsNode implements JBulletPhysicsNode {

    private boolean dirty = true;

    private boolean affectedByGravity = true;

    private Quaternion lastRotation = new Quaternion();
    private Vector3f lastPosition = new Vector3f();
    private Vector3f centerOfMass = new Vector3f();

    private JBulletRigidBody body;
    private JBulletPhysicsSpace space;
    private Vector3f linearVelocity = new Vector3f( 0f, 0f, 0f );
    private Vector3f angularVelocity = new Vector3f( 0f, 0f, 0f );
    private float mass = 1f;

    private MotionState motionState = new DefaultMotionState();
    private Matrix3f tempRotMatrix = new Matrix3f();
    private Vector3f tempTranslation = new Vector3f();

    private List<JBulletGeometry> collisionShapes = new ArrayList<JBulletGeometry>();

    private Set<Joint> myJoints = new HashSet<Joint>();

    @Override
    public int attachChild( Spatial child ) {
        int index = super.attachChild( child );
        if ( child instanceof JBulletGeometry ) {
            dirty = true;
            collisionShapes.add( (JBulletGeometry) child );
        }
        return index;
    }

    @Override
    public int detachChild( Spatial child ) {
        int index = super.detachChild( child );
        if ( child instanceof JBulletGeometry ) {
            dirty = true;
            collisionShapes.remove( child );
        }
        return index;
    }

    public void rebuildRigidBody() {

        javax.vecmath.Vector3f linearVelocity = null;
        javax.vecmath.Vector3f angularVelocity = null;
        javax.vecmath.Vector3f localInertia = new javax.vecmath.Vector3f( 0, 0, 0 );
        boolean needsVelocities = false;

        if ( body != null ) {
            space.dynamicsWorld.removeRigidBody( body );
            linearVelocity = body.getLinearVelocity();
            angularVelocity = body.getAngularVelocity();
            needsVelocities = true;
        }

        updateWorldVectors();

        CollisionShape mainShape;
        if ( collisionShapes.size() == 0 ) {
            body = new JBulletRigidBody( 0, motionState, null, localInertia );
            body.setCollisionFlags( body.getCollisionFlags() | CollisionFlags.KINEMATIC_OBJECT );
        } else if ( collisionShapes.size() == 1 && collisionShapes.get( 0 ).getLocalTranslation().equals( Vector3f.ZERO ) && collisionShapes.get( 0 ).getLocalRotation().equals( QUAT_ZERO_ROT ) ) {
            mainShape = collisionShapes.get( 0 ).getJBulletShape();
            mass = collisionShapes.get( 0 ).getVolume() * getMaterial().getDensity();
            mainShape.setLocalScaling( VecmathConverter.convert( getWorldScale().mult( collisionShapes.get( 0 ).getLocalScale() ) ) );
            mainShape.calculateLocalInertia( mass, localInertia );
            RigidBodyConstructionInfo ci = new RigidBodyConstructionInfo( mass, motionState, mainShape, localInertia );
            //Need to insert code here to deal with material types
            //ci.setSomePropertyToDealWithMaterials();
            body = new JBulletRigidBody( ci );
            dirty = false;
            collisionShapes.get( 0 ).setDirty( false );
        } else {
            mainShape = new CompoundShape();
            mass = 0;
            for ( JBulletGeometry geom : collisionShapes ) {
                Transform t = new Transform();
                t.setIdentity();
                CollisionShape cs = geom.getJBulletShape();
                cs.setLocalScaling( VecmathConverter.convert( getWorldScale().mult( geom.getLocalScale() ) ) );
                VecmathConverter.convert( geom.getLocalTranslation().divideLocal( geom.getLocalScale() ), t.origin );
                geom.getLocalRotation().toRotationMatrix( tempRotMatrix );
                VecmathConverter.convert( tempRotMatrix, t.basis );
                ( (CompoundShape) mainShape ).addChildShape( t, geom.getJBulletShape() );
                mass += geom.getVolume() * getMaterial().getDensity();
                geom.setDirty( false );
            }
            //mainShape.setLocalScaling(VecmathConverter.convert(getWorldScale()));
            mainShape.calculateLocalInertia( mass, localInertia );
            RigidBodyConstructionInfo ci = new RigidBodyConstructionInfo( mass, motionState, mainShape, localInertia );
            //Need to insert code here to deal with material types
            //ci.setSomePropertyToDealWithMaterials();
            body = new JBulletRigidBody( ci );

            dirty = false;
        }

        updateWorldVectors();

        if ( needsVelocities ) {
            body.setLinearVelocity( linearVelocity );
            body.setAngularVelocity( angularVelocity );
        }

        if ( !isAffectedByGravity() ) {
            body.setGravity( new javax.vecmath.Vector3f() );
        } else {
            body.setGravity( VecmathConverter.convert( ( ( (JBulletPhysicsSpace) getSpace() ).gravity ) ) );
        }
        if ( isActive() ) {
            ( (JBulletPhysicsSpace) getSpace() ).dynamicsWorld.addRigidBody( body );
        }
    }

    public JBulletDynamicPhysicsNode( JBulletPhysicsSpace space ) {
        this.space = space;
    }

    @Override
    public void addForce( Vector3f force, Vector3f at ) {
        if ( body != null ) {
            body.applyForce( VecmathConverter.convert( force ), VecmathConverter.convert( at ) );
        }
    }

    @Override
    public void addTorque( Vector3f torque ) {
        if ( body != null ) {
            body.applyTorque( VecmathConverter.convert( torque ) );
        }
    }

    @Override
    public void addForce( Vector3f force ) {
        if ( body != null ) {
            body.applyForce( VecmathConverter.convert( force ), new javax.vecmath.Vector3f() );
        }
    }

    @Override
    public void clearForce() {
        // TODO Auto-generated method stub

    }

    @Override
    public void clearTorque() {
        // TODO Auto-generated method stub

    }

    @Override
    public void computeMass() {
        mass = 0;
        for ( JBulletGeometry geom : collisionShapes ) {
            mass += geom.getVolume() * getMaterial().getDensity();
        }
        dirty = true;
    }

    @Override
    public Vector3f getAngularVelocity( Vector3f store ) {
        if ( store == null ) {
            store = new Vector3f();
        }
        VecmathConverter.convert( body.getAngularVelocity(), store );
        return store;
    }

    @Override
    public Vector3f getCenterOfMass( Vector3f store ) {
        return store.set( centerOfMass );
    }

    @Override
    public Vector3f getForce( Vector3f store ) {
        return store.set( Vector3f.ZERO );
    }

    @Override
    public Vector3f getLinearVelocity( Vector3f store ) {
        if ( store == null ) {
            store = new Vector3f();
        }
        if ( body != null )
        {
            VecmathConverter.convert( body.getLinearVelocity(), store );
        }
        else
        {
            store.zero();
        }
        return store;
    }

    @Override
    public float getMass() {
        return mass;
    }

    @Override
    public Vector3f getTorque( Vector3f store ) {
        return store.set( Vector3f.ZERO );
    }

    @Override
    public boolean isAffectedByGravity() {
        return affectedByGravity;
    }

    @Override
    public void rest() {
        if ( body != null ) {
            body.setActivationState( CollisionObject.WANTS_DEACTIVATION );
        }
    }

    @Override
    public void setAffectedByGravity( boolean value ) {
        affectedByGravity = value;
        if ( body == null ) {
            return;
        }
        if ( value ) {
            body.setGravity( VecmathConverter.convert( getSpace().getDirectionalGravity( null ) ) );
        } else {
            body.setGravity( VecmathConverter.convert( Vector3f.ZERO ) );
        }
    }

    @Override
    public void setAngularVelocity( Vector3f velocity ) {
        angularVelocity.set( velocity );
        if ( body != null ) {
            body.setAngularVelocity( VecmathConverter.convert( velocity ) );
        }

    }

    @Override
    public void setCenterOfMass( Vector3f value ) {
        centerOfMass = value;
        if ( body != null ) {
            body.setCenterOfMassPosition( VecmathConverter.convert( value ) );
        }
    }

    @Override
    public void setLinearVelocity( Vector3f velocity ) {
        linearVelocity.set( velocity );
        if ( body != null ) {
            body.setLinearVelocity( VecmathConverter.convert( velocity ) );
        }
    }

    @Override
    public void setMass( float value ) {
        dirty = true;
        mass = value;
    }

    @Override
    public void unrest() {
        if ( body != null ) {
            body.setActivationState( CollisionObject.ACTIVE_TAG );
        }
    }

    @Override
    public PhysicsSpace getSpace() {
        return this.space;
    }

    @Override
    public boolean isResting() {
        if ( body == null ) {
            return true;
        }
        return body.isActive();
    }

    public boolean isDirty() {
        if ( dirty ) {
            return true;
        }
        for ( JBulletGeometry geom : this.collisionShapes ) {
            if ( geom.isDirty() ) {
                return true;
            }
        }
        return false;
    }

    public void setDirty( boolean dirty ) {
        this.dirty = dirty;
    }

    public RigidBody getBody() {
        return body;
    }

    @Override
    public void updateWorldVectors() {
        if ( getParent() != null ) {
            getParent().updateWorldVectors();
        }
        super.updateWorldVectors();
        if ( body != null ) {
            body.setWorldRotation( getWorldRotation() );
            body.setWorldTranslation( getWorldTranslation() );
            motionState.setWorldTransform( body.getWorldTransform(new Transform() ) );
        }
    }

    public void applyPhysicsMovement() {
        VecmathConverter.convert( body.getWorldTransform(new Transform() ).basis, tempRotMatrix );
        VecmathConverter.convert( body.getWorldTransform(new Transform()).origin, tempTranslation );
        if ( getParent() != null ) {
            getLocalTranslation().set( tempTranslation.subtract( getParent().getWorldTranslation() ) );
            getLocalRotation().fromRotationMatrix( tempRotMatrix );
            getLocalRotation().multLocal( getParent().getWorldRotation().inverse() );
        } else {
            getLocalTranslation().set( tempTranslation );
            getLocalRotation().fromRotationMatrix( tempRotMatrix );
        }
    }

    @Override
    public boolean setActive( boolean value ) {
        if ( value != super.isActive() && body != null ) {
            if ( value ) {
                ( (JBulletPhysicsSpace) getSpace() ).dynamicsWorld.addRigidBody( body );
            } else {
                ( (JBulletPhysicsSpace) getSpace() ).dynamicsWorld.removeRigidBody( body );
            }
        }
        return super.setActive( value );
    }

    public void addParticipatingJoint( Joint joint ) {
        myJoints.add( joint );
    }

    public void removeParticipatingJoint( Joint joint ) {
        myJoints.remove( joint );
    }

    public Set<Joint> getMyJoints() {
        return Collections.unmodifiableSet( myJoints );
    }
}
