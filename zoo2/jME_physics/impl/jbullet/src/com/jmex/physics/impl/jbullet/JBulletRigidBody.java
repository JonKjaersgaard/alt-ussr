package com.jmex.physics.impl.jbullet;

import javax.vecmath.Vector3f;

import com.bulletphysics.BulletGlobals;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import com.jmex.physics.impl.jbullet.util.VecmathConverter;

public class JBulletRigidBody extends RigidBody {

    private com.jme.math.Matrix3f tempRotMatrix = new com.jme.math.Matrix3f();

    public JBulletRigidBody( RigidBodyConstructionInfo constructionInfo ) {
        super( constructionInfo );
    }

    public JBulletRigidBody( float mass, MotionState motionState, CollisionShape collisionShape ) {
        this( mass, motionState, collisionShape, new Vector3f() );
    }

    public JBulletRigidBody( float mass, MotionState motionState, CollisionShape collisionShape, Vector3f localInertia ) {
        super( mass, motionState, collisionShape, localInertia );
    }

    public boolean isDirty() {
        //TODO check for dirty.
        return true;
    }

    public void setWorldTranslation( com.jme.math.Vector3f worldTranslation ) {
        final Transform transform = getWorldTransform(new Transform());
        VecmathConverter.convert( worldTranslation, transform.origin );
        setWorldTransform( transform );
    }

    public void setWorldRotation( com.jme.math.Quaternion worldRotation ) {
        worldRotation.toRotationMatrix( tempRotMatrix );
        final Transform transform = getWorldTransform(new Transform());
        VecmathConverter.convert( tempRotMatrix, transform.basis );
        setWorldTransform( transform );
    }

    public Vector3f getLinearVelocity() {
        return getLinearVelocity( new Vector3f() );
    }

    public Vector3f getAngularVelocity() {
        return getAngularVelocity( new Vector3f() );
    }

    @Deprecated
    public Vector3f getGravity() {
        return getGravity( new Vector3f() );
    }

    @Deprecated
    public Vector3f getCenterOfMassPosition() {
        return getCenterOfMassPosition( new Vector3f() );
    }

    @Deprecated
    public void setCenterOfMassPosition( Vector3f pos ) {
        final Transform transform = getCenterOfMassTransform( new Transform() );
        transform.origin.set( pos );
        setCenterOfMassTransform( transform );
    }
}
