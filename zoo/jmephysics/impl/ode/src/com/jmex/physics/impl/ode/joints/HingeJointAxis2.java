/*Copyright*/
package com.jmex.physics.impl.ode.joints;

import com.jme.math.Vector3f;
import com.jmex.physics.JointAxis;
import com.jmex.physics.RotationalJointAxis;
import org.odejava.OdeJavaJoint;
import org.odejava.ode.OdeConstants;

/**
 * @author Irrisor
 */
public class HingeJointAxis2 extends RotationalJointAxis {
    private final OdeJavaJoint ode;

    public HingeJointAxis2( JointAxis toCopy, OdeJavaJoint hinge ) {
        this.ode = hinge;
        copy( toCopy );
    }

    @Override
    public void setDirection( Vector3f direction ) {
        float length = direction.length();
        if ( length == 0 ) {
            throw new IllegalArgumentException( "Axis direction may not be zero!" );
        }
        ode.setAxis2( direction.x, direction.y, direction.z );
        super.setDirection( direction );
    }

    @Override
    public float getVelocity() {
        return ode.getAngle2Rate();
    }

    @Override
    public float getPosition() {
        return ode.getAngle2();
    }

    @Override
    public void setAvailableAcceleration( float value ) {
        if ( Float.isNaN( value ) ) {
            value = 0;
        }
        ode.setParam( OdeConstants.dParamFMax2, value );
    }

    @Override
    public void setDesiredVelocity( float value ) {
        ode.setParam( OdeConstants.dParamVel2, value );
    }

    @Override
    public float getAvailableAcceleration() {
        return ode.getParam( OdeConstants.dParamFMax2 );
    }

    @Override
    public float getDesiredVelocity() {
        return ode.getParam( OdeConstants.dParamVel2 );
    }

    @Override
    public float getPositionMaximum() {
        return Float.POSITIVE_INFINITY;
    }

    @Override
    public float getPositionMinimum() {
        return Float.NEGATIVE_INFINITY;
    }

    @Override
    public void setPositionMaximum( float value ) {
        if ( !Float.isInfinite( value ) ) {
            throw new UnsupportedOperationException( "second axis cannot be restricted by this implementation" );
        }
    }

    @Override
    public void setPositionMinimum( float value ) {
        if ( !Float.isInfinite( value ) ) {
            throw new UnsupportedOperationException( "second axis cannot be restricted by this implementation" );
        }
    }
}

/*
 * $log$
 */

