/*Copyright*/
package com.jmex.physics;

/**
 * A translational axis for a {@link Joint}.
 * @see JointAxis
 * @see Joint#createTranslationalAxis() 
 * @author Irrisor
 */
public abstract class TranslationalJointAxis extends JointAxis {
    protected TranslationalJointAxis() {
    }

    @Override
    public final boolean isTranslationalAxis() {
        return true;
    }

    @Override
    public final boolean isRotationalAxis() {
        return false;
    }

    public Class getClassTag() {
		return TranslationalJointAxis.class;
    }
}

/*
 * $log$
 */

