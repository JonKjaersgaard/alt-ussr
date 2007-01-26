/*
 * Copyright (c) 2005-2006 jME Physics 2
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 *  * Neither the name of 'jME Physics 2' nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 * TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.jmex.physics.impl.joode.joints;

import com.jmex.physics.RotationalJointAxis;
import com.jmex.physics.JointAxis;
import com.jme.math.Vector3f;
import net.java.dev.joode.joint.JointHinge2;

/**
 * @author Irrisor
 */
public class Hinge2JointAxis extends RotationalJointAxis {
    private final JointHinge2 joint;
    private final JoodeJoint stopsChangeTypeOf;

    public Hinge2JointAxis( JointAxis toCopy, JointHinge2 hinge, JoodeJoint stopsChangeTypeOf ) {
        this.joint = hinge;
        this.stopsChangeTypeOf = stopsChangeTypeOf;
        copy( toCopy );
    }

    @Override
    public void setDirection( Vector3f direction ) {
        float length = direction.length();
        if ( length == 0 ) {
            throw new IllegalArgumentException( "Axis direction may not be zero!" );
        }
        joint.setAxis1( direction.x, direction.y, direction.z );
        super.setDirection( direction );
    }

    @Override
    public float getVelocity() {
        throw new UnsupportedOperationException();
        //TODO: return joint.getAngleRate();
    }

    @Override
    public float getPosition() {
        throw new UnsupportedOperationException();
        //TODO: return joint.getAngle();
    }

    @Override
    public void setAvailableAcceleration( float value ) {
        if ( Float.isNaN( value ) ) {
            value = 0;
        }
        joint.limot1.fmax = value;
    }

    @Override
    public void setDesiredVelocity( float value ) {
        joint.limot1.vel = value;
    }

    @Override
    public float getAvailableAcceleration() {
        return joint.limot1.fmax;
    }

    @Override
    public float getDesiredVelocity() {
        return joint.limot1.vel;
    }

    @Override
    public float getPositionMaximum() {
        return joint.limot1.histop;
    }

    @Override
    public float getPositionMinimum() {
        return joint.limot1.lostop;
    }

    @Override
    public void setPositionMaximum( float value ) {
        if ( stopsChangeTypeOf != null ) {
            if ( Float.isInfinite( value ) != Float.isInfinite( getPositionMaximum() ) ) {
                stopsChangeTypeOf.checkType();
            }
        }
        joint.limot1.histop = value;
    }

    @Override
    public void setPositionMinimum( float value ) {
        if ( stopsChangeTypeOf != null ) {
            if ( Float.isInfinite( value ) != Float.isInfinite( getPositionMinimum() ) ) {
                stopsChangeTypeOf.checkType();
            }
        }
        joint.limot1.lostop = value;
    }
}

/*
 * $Log: Hinge2JointAxis.java,v $
 * Revision 1.1  2006/12/23 22:07:06  irrisor
 * Ray added, Picking interface (natives pending), JOODE implementation added, license header added
 *
 */

