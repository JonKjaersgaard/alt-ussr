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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.JointAxis;
import com.jmex.physics.RotationalJointAxis;
import com.jmex.physics.TranslationalJointAxis;
import com.jmex.physics.impl.joode.DynamicPhysicsNodeImpl;
import com.jmex.physics.impl.joode.JoodePhysicsSpace;
import net.java.dev.joode.Body;
import net.java.dev.joode.World;
import net.java.dev.joode.joint.JointBall;
import net.java.dev.joode.joint.JointFixed;
import net.java.dev.joode.joint.JointHinge;
import net.java.dev.joode.joint.JointHinge2;
import net.java.dev.joode.joint.JointSlider;

/**
 * @author Irrisor
 */
public class JoodeJoint extends Joint {
    private final JoodePhysicsSpace space;

    public JoodeJoint( JoodePhysicsSpace space ) {
        this.space = space;
    }

    @Override
    protected TranslationalJointAxis createTranslationalAxisImplementation() {
        return new TranslationalJoodeJointAxis();
    }

    @Override
    protected RotationalJointAxis createRotationalAxisImplementation() {
        return new RotationalJoodeJointAxis();
    }

    @Override
    public JoodePhysicsSpace getSpace() {
        return space;
    }

    @Override
    protected void added( JointAxis axis ) {
        super.added( axis );
        typeChanged = true;
    }

    @Override
    public void removed( JointAxis axis ) {
        super.removed( axis );
        typeChanged = true;
    }

    private boolean typeChanged = true;
    private net.java.dev.joode.joint.Joint joode;

    @Override
    public void reset() {
        typeChanged = true;
    }

    private final Vector3f anchor = new Vector3f();

    @Override
    public void setAnchor( Vector3f anchor ) {
        this.anchor.set( anchor );
        if ( joode != null ) {
            adjustAnchor();
        }
    }

    @Override
    public Vector3f getAnchor( Vector3f store ) {
        if ( store == null ) {
            store = new Vector3f();
        }
        return store.set( anchor );
    }

    public void setSpring( float springConstant, float dampingCoefficient ) {
        if ( joode == null || joode instanceof JointBall || joode instanceof JointFixed ) {
            //        float r = 2.0f * dampingCoefficient *
            //                FastMath.sqrt( sprungMass * springConstant );
            if ( !Float.isNaN( springConstant ) && !Float.isNaN( dampingCoefficient ) ) {
                float r = dampingCoefficient;
                float hk = getSpace().getStepSize() * springConstant;

                setERP( hk / ( hk + r ) );
                setCFM( 1.0f / ( hk + r ) );
            } else {
                setERP( Float.NaN );
                setCFM( Float.NaN );
            }
        } else {
            throw new UnsupportedOperationException( "spring only supported for with 0 axes or 3 rotational axes" );
        }
    }

    protected float erp = Float.NaN;
    protected float cfm = Float.NaN;

    public void setERP( float value ) {
        erp = value;
        applyERP();
    }

    protected void applyERP() {
        if ( joode != null ) {
            if ( !Float.isNaN( erp ) ) {
                applyERP( erp );
            } else {
                applyERP( getSpace().getJoodeWorld().global_erp );
            }
        }
    }

    private void applyERP( float erp ) {
        //TODO: joode.setParam( JoodeConstants.dParamERP, erp );
    }

    public void setCFM( float value ) {
        cfm = value;
        applyCFM();
    }

    protected void applyCFM() {
        if ( joode != null ) {
            if ( !Float.isNaN( cfm ) ) {
                applyCFM( cfm );
            } else {
                applyCFM( getSpace().getJoodeWorld().global_cfm );
            }
        }
    }

    private void applyCFM( float cfm ) {
        if ( joode instanceof JointFixed ) {
            JointFixed joint = (JointFixed) joode;
            //TODO: fixed joint cfm
        }
        else if ( joode instanceof JointBall ) {
            JointBall joint = (JointBall) joode;
            //TODO: ball joint cfm
        }
        else if ( joode instanceof JointHinge ) {
            JointHinge joint = (JointHinge) joode;
            joint.limot.normal_cfm = cfm;
        }
        else if ( joode instanceof JointHinge2 ) {
            JointHinge2 joint = (JointHinge2) joode;
            joint.limot1.normal_cfm = cfm;
            joint.limot2.normal_cfm = cfm;
        }
        else if ( joode instanceof JointSlider ) {
            JointSlider joint = (JointSlider) joode;
            joint.limot.normal_cfm = cfm;
        } else {
            throw new UnsupportedOperationException( "CFM for Joint type " + joode.getClass().getName() +
                    " not supported" );
        }
    }

    public void updateJointType() {
        if ( typeChanged ) {
            if ( joode != null ) {
                //todo: joode.delete();
                joode = null;
            }
            int numTranslational = 0;
            int numRotational = 0;
            int index = 0;
            JointAxis[] axes = new JointAxis[3];
            for ( JointAxis axis : getAxes() ) {
                if ( index > 2 ) {
                    unsupported( "this physics implementation support a maximum of three axes " +
                            "for Joints." );
                }
                if ( axis.isTranslationalAxis() ) {
                    numTranslational++;
                    axes[index++] = axis;
                }
                if ( axis.isRotationalAxis() ) {
                    numRotational++;
                    axes[index++] = axis;
                }
            }
            World odeJavaWorld = this.getSpace().getJoodeWorld();
            if ( numTranslational > 0 ) {
                if ( numRotational > 0 ) {
                    unsupported( "this physics implementation supports either rotational" +
                            " axes or a translational axis - not both." );
                }
                if ( numTranslational > 1 ) {
                    unsupported( "this physics implementation supports a maximum of 1 " +
                            "translational axis." );
                }
                // ok 1 translational axis can be implemented by a SliderJoint
                // but we can't do rotation then
                JointSlider slider = new JointSlider( odeJavaWorld );
                joode = slider;
                attach( true );
                changeDelegate( axes[0], new SliderJointAxis( axes[0], slider ) );
            } else if ( numRotational > 0 ) {
                if ( numRotational == 1 ) {
                    // hinge
                    if ( axes[0].isRelativeToSecondObject() ) {
                        unsupported( "this implemantation supports a rotational axis only if it is " +
                                "relative to the first object" );
                    }
                    JointHinge hinge = new JointHinge( odeJavaWorld );
                    joode = hinge;
                    attach( true );
                    changeDelegate( axes[0], new HingeJointAxis( axes[0], hinge, null ) );
                } else if ( numRotational == 2 ) {
                    // universal or hinge2
                    if ( !Float.isInfinite( axes[1].getPositionMaximum() ) ) {
                        unsupported( "the second rotational axis cannot be restricted in this implementation" );
                    }
                    if ( axes[0].isRelativeToSecondObject() || !axes[1].isRelativeToSecondObject() ) {
                        unsupported( "this implemantation supports two rotational axes only if the second one is " +
                                "relative to the second object, the first axis must be relative to the first object" );
                    }

                    if ( !Float.isInfinite( axes[0].getPositionMinimum() ) ) {
                        // hinge2 has stops
                        JointHinge2 joint = new JointHinge2( odeJavaWorld );
                        changeDelegate( axes[0], new Hinge2JointAxis( axes[0], joint, this ) );
                        changeDelegate( axes[1], new Hinge2JointAxis2( axes[1], joint, this ) );
                        joode = joint;
                    } else {
                        // universal does not have stops
                        unsupported( "Joode does not have universal joints yet? :/" );
                        //TODO: joint = new JointUniversal( this.getName(), odeJavaWorld );
                    }
                    attach( true );
                } else if ( numRotational == 3 ) {
                    // ball, possibly plus angular motor
                    joode = new JointBall( odeJavaWorld );
                    attach( true );
                    //TODO AMotor
                }
            } else {
                JointFixed jointFixed = new JointFixed( odeJavaWorld );
                joode = jointFixed;
                attach( true );
            }
        }
        typeChanged = false;
    }

    @Override
    public void setName( final String value ) {
        super.setName( value );
    }

    private final Vector3f anchorTmp = new Vector3f();

    private void attach( boolean reanchor ) {
        if ( joode != null ) {
            Body body1 = nodes.size() > 0 ? nodes.get( 0 ).getBody() : null;
            Body body2 = nodes.size() > 1 ? nodes.get( 1 ).getBody() : null;
            joode.attach( body1, body2 );
            applyCFM();
            applyERP();
            if ( reanchor ) {
                adjustAnchor();
            }
        }
    }

    private void adjustAnchor() {
        if ( nodes.size() < 2 ) {
            applyAnchor( anchor.x, anchor.y, anchor.z );
        } else {
            anchorTmp.set( anchor );
            DynamicPhysicsNodeImpl node1 = nodes.get( 0 );
            node1.getWorldRotation().multLocal( anchorTmp );
            anchorTmp.addLocal( node1.getWorldTranslation() );
            applyAnchor( anchorTmp.x, anchorTmp.y, anchorTmp.z );
        }
    }

    private void applyAnchor( float x, float y, float z ) {
        if ( joode instanceof JointFixed ) {
            JointFixed joint = (JointFixed) joode;
            joint.setFixed();
        }
        else if ( joode instanceof JointBall ) {
            JointBall joint = (JointBall) joode;
            joint.setBallAnchor( x, y, z );
            joint.setBallAnchor2( x, y, z );
        }
        else if ( joode instanceof JointHinge ) {
            JointHinge joint = (JointHinge) joode;
            joint.setAnchor( x, y, z );
        }
        else if ( joode instanceof JointHinge2 ) {
            JointHinge2 joint = (JointHinge2) joode;
            joint.setAnchor( x, y, z );
        }
        else if ( joode instanceof JointSlider ) {
            JointSlider joint = (JointSlider) joode;
            //TODO: set slider offset
        } else {
            throw new UnsupportedOperationException( "CFM for Joint type " + joode.getClass().getName() +
                    " not supported" );
        }
    }

    private void changeDelegate( JointAxis toBeReplaced, JointAxis newAxis ) {
        ( (JoodeJointAxis) toBeReplaced ).setDelegate( newAxis );
    }

    @Override
    public boolean setActive( boolean value ) {
        boolean changed = super.setActive( value );
        if ( changed ) {
            if ( !value ) {
                detach();
            } else {
                attach( false );
            }
        }
        return changed;
    }

    @Override
    public void attach( DynamicPhysicsNode leftNode, DynamicPhysicsNode rightNode ) {
        leftNode.updateWorldVectors();
        rightNode.updateWorldVectors();
        nodes.clear();
        nodes.add( (DynamicPhysicsNodeImpl) leftNode );
        nodes.add( (DynamicPhysicsNodeImpl) rightNode );
        attach( true );
    }

    @Override
    public void attach( DynamicPhysicsNode node ) {
        node.updateWorldVectors();
        nodes.clear();
        nodes.add( (DynamicPhysicsNodeImpl) node );
        attach( true );
    }

    @Override
    public void detach() {
        nodes.clear();
        if ( joode != null ) {
            joode.attach( null, null );
        }
    }

    private final List<DynamicPhysicsNodeImpl> nodes = new ArrayList<DynamicPhysicsNodeImpl>( 2 );
    private List<? extends DynamicPhysicsNode> immutableNodes;

    @Override
    public List<? extends DynamicPhysicsNode> getNodes() {
        if ( immutableNodes == null ) {
            immutableNodes = Collections.unmodifiableList( nodes );
        }
        return immutableNodes;
    }

    private void unsupported( String message ) {
        setActive( false ); // to allow next update to succeed
        throw new UnsupportedOperationException( message + " Problematic Joint: " + this );
    }

    void checkType() {
        typeChanged = true;
    }
}

/*
 * $log$
 */

