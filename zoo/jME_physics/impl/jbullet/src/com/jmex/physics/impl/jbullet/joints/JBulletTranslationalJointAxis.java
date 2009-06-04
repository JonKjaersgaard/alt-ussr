package com.jmex.physics.impl.jbullet.joints;

import com.bulletphysics.BulletGlobals;
import com.jmex.physics.TranslationalJointAxis;

public class JBulletTranslationalJointAxis extends TranslationalJointAxis {

    private float availableAccelleration = 0f;
    private float desiredVelocity = 0f;
    private float positionMinimum = Float.NEGATIVE_INFINITY;
    private float positionMaximum = Float.POSITIVE_INFINITY;

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
