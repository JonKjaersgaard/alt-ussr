package com.jmex.physics.impl.jbullet.joints;

import com.bulletphysics.BulletGlobals;
import com.jmex.physics.RotationalJointAxis;

public class JBulletRotationalJointAxis extends RotationalJointAxis {

    private float availableAccelleration = 0f;
    private float desiredVelocity = 0f;
    private float positionMinimum = -BulletGlobals.FLT_EPSILON;
    private float positionMaximum = BulletGlobals.FLT_EPSILON;

    @Override
    public float getAvailableAcceleration() {
        return availableAccelleration;
    }

    @Override
    public float getDesiredVelocity() {
        return desiredVelocity;
    }

    @Override
    public float getPosition() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getPositionMaximum() {
        return positionMaximum;
    }

    @Override
    public float getPositionMinimum() {
        return positionMinimum;
    }

    @Override
    public float getVelocity() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void setAvailableAcceleration( float value ) {
        this.availableAccelleration = value;
    }

    @Override
    public void setDesiredVelocity( float value ) {
        this.desiredVelocity = value;
    }

    @Override
    public void setPositionMaximum( float value ) {
        this.positionMaximum = value;
    }

    @Override
    public void setPositionMinimum( float value ) {
        this.positionMinimum = value;
    }

}
