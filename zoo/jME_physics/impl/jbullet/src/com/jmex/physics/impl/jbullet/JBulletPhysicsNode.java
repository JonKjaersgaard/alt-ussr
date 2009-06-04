package com.jmex.physics.impl.jbullet;

import com.bulletphysics.dynamics.RigidBody;
import com.jme.math.Quaternion;

public interface JBulletPhysicsNode {

    public static final Quaternion QUAT_ZERO_ROT = new Quaternion();

    public boolean isDirty();

    public void setDirty( boolean dirty );

    public RigidBody getBody();

    public void rebuildRigidBody();

    public void updateWorldVectors();
}
