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
public class HingeJointAxis extends RotationalJointAxis {
    private final OdeJavaJoint ode;
    private final OdeJoint stopsChangeTypeOf;

    public HingeJointAxis( JointAxis toCopy, OdeJavaJoint hinge, OdeJoint stopsChangeTypeOf ) {
        this.ode = hinge;
        this.stopsChangeTypeOf = stopsChangeTypeOf;
        copy( toCopy );
    }

    @Override
    public void setDirection( Vector3f direction ) {
        float length = direction.length();
        if ( length == 0 ) {
            throw new IllegalArgumentException( "Axis direction may not be zero!" );
        }
        ode.setAxis1( direction.x, direction.y, direction.z );
        super.setDirection( direction );
    }

    @Override
    public float getVelocity() {
        return ode.getAngleRate();
    }

    @Override
    public float getPosition() {
        return ode.getAngle();
    }

    @Override
    public void setAvailableAcceleration( float value ) {
        if ( Float.isNaN( value ) ) {
            value = 0;
        }
        ode.setParam( OdeConstants.dParamFMax, value );
    }

    @Override
    public void setDesiredVelocity( float value ) {
        ode.setParam( OdeConstants.dParamVel, value );
    }

    @Override
    public float getAvailableAcceleration() {
        return ode.getParam( OdeConstants.dParamFMax );
    }

    @Override
    public float getDesiredVelocity() {
        return ode.getParam( OdeConstants.dParamVel );
    }

    @Override
    public float getPositionMaximum() {
        return ode.getMaxAngleStop();
    }

    @Override
    public float getPositionMinimum() {
        return ode.getMinAngleStop();
    }

    @Override
    public void setPositionMaximum( float value ) {
        if ( stopsChangeTypeOf != null ) {
            if ( Float.isInfinite( value ) != Float.isInfinite( getPositionMaximum() ) ) {
                stopsChangeTypeOf.checkType();
            }
        }
        ode.setMaxAngleStop( value );
    }

    @Override
    public void setPositionMinimum( float value ) {
        if ( stopsChangeTypeOf != null ) {
            if ( Float.isInfinite( value ) != Float.isInfinite( getPositionMinimum() ) ) {
                stopsChangeTypeOf.checkType();
            }
        }
        ode.setMinAngleStop( value );
    }
}

/*
 * $log$
 */

