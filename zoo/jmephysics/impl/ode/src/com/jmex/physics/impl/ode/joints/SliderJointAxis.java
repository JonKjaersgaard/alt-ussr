/*Copyright*/
package com.jmex.physics.impl.ode.joints;

import com.jme.math.Vector3f;
import com.jmex.physics.JointAxis;
import com.jmex.physics.TranslationalJointAxis;
import org.odejava.JointSlider;
import org.odejava.ode.OdeConstants;

/**
 * @author Irrisor
 */
public class SliderJointAxis extends TranslationalJointAxis {
    private final JointSlider ode;

    public SliderJointAxis( JointAxis toCopy, JointSlider slider ) {
        this.ode = slider;
        copy( toCopy );
    }

    @Override
    public void setDirection( Vector3f direction ) {
        float length = direction.length();
        if ( length == 0 ) {
            throw new IllegalArgumentException( "Axis direction may not be zero!" );
        }
        if ( ode.getBody1() != null ) {
            ode.setAxis1( direction.x, direction.y, direction.z );
        }
        super.setDirection( direction );
    }

    @Override
    public float getVelocity() {
        return ode.getPositionRate();
    }

    @Override
    public float getPosition() {
        return ode.getPosition();
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
        return ode.getMaximumPosition();
    }

    @Override
    public float getPositionMinimum() {
        return ode.getMinimumPosition();
    }

    @Override
    public void setPositionMaximum( float value ) {
        ode.setMaximumPosition( value );
    }

    @Override
    public void setPositionMinimum( float value ) {
        ode.setMinimumPosition( value );
    }
}

/*
 * $log$
 */

