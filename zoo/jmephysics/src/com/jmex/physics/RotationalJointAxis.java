/*Copyright*/
package com.jmex.physics;

/**
 * A rotational axis for a {@link Joint}.
 * @see JointAxis
 * @see Joint#createRotationalAxis()
 * @author Irrisor
 */
public abstract class RotationalJointAxis extends JointAxis {

    protected RotationalJointAxis() {
    }

    @Override
    public final boolean isTranslationalAxis() {
        return false;
    }

    @Override
    public final boolean isRotationalAxis() {
        return true;
    }
    
    public Class getClassTag() {
		return RotationalJointAxis.class;
    }
}

/*
 * $log$
 */

