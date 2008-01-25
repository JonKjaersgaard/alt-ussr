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
package com.jmex.physics.impl.ode.joints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.JointAxis;
import com.jmex.physics.RotationalJointAxis;
import com.jmex.physics.TranslationalJointAxis;
import com.jmex.physics.impl.ode.DynamicPhysicsNodeImpl;
import com.jmex.physics.impl.ode.OdePhysicsSpace;
import org.odejava.Body;
import org.odejava.JointBall;
import org.odejava.JointFixed;
import org.odejava.JointHinge;
import org.odejava.JointHinge2;
import org.odejava.JointSlider;
import org.odejava.JointUniversal;
import org.odejava.OdeJavaJoint;
import org.odejava.World;
import org.odejava.ode.OdeConstants;

/**
 * @author Irrisor
 */
public class OdeJoint extends Joint {
    private final OdePhysicsSpace space;

    public OdeJoint( OdePhysicsSpace space ) {
        this.space = space;
    }

    @Override
    protected TranslationalJointAxis createTranslationalAxisImplementation() {
        return new TranslationalOdeJointAxis();
    }

    @Override
    protected RotationalJointAxis createRotationalAxisImplementation() {
        return new RotationalOdeJointAxis();
    }

    @Override
    public OdePhysicsSpace getSpace() {
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
    private OdeJavaJoint ode;

    @Override
    public void reset() {
        typeChanged = true;
    }

    private final Vector3f anchor = new Vector3f();

    @Override
    public void setAnchor( Vector3f anchor ) {
        this.anchor.set( anchor );
        if ( ode != null ) {
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
        if ( ode == null || ode instanceof JointBall || ode instanceof JointFixed ) {
            //        float r = 2.0f * dampingCoefficient *
            //                FastMath.sqrt( sprungMass * springConstant );
        	
            if ( !Float.isNaN( springConstant ) && !Float.isNaN( dampingCoefficient ) ) {
                float r = dampingCoefficient;
                float hk = getSpace().getODEJavaWorld().getStepSize() * springConstant;
                setERP( hk / ( hk + r ) );
                setCFM( 1.0f / ( hk + r ) );
                
            }
            else {
                setERP( Float.NaN );
                setCFM( Float.NaN );
            }
        }
        else {
            throw new UnsupportedOperationException( "spring only supported for with 0 axes or 3 rotational axes" );
        }
    }

    private float erp = Float.NaN;
    private float cfm = Float.NaN;

    public void setERP( float value ) {
        erp = value;
        applyERP();
    }

    private void applyERP() {
        if ( ode != null ) {
            if ( !Float.isNaN( erp ) ) {
                ode.setParam( OdeConstants.dParamERP, erp );
            }
            else {
                ode.setParam( OdeConstants.dParamERP, getSpace().getODEJavaWorld().getErrorReductionParameter() );
            }
        }
    }

    public void setCFM( float value ) {
        cfm = value;
        applyCFM();
    }

    private void applyCFM() {
        if ( ode != null ) {
        	if ( !Float.isNaN( cfm ) ) {
                ode.setParam( OdeConstants.dParamCFM, cfm );
            }
            else {
                ode.setParam( OdeConstants.dParamCFM, getSpace().getODEJavaWorld().getConstraintForceMix() );
            }
        }
    }

    public void updateJointType() {
        if ( typeChanged ) {
            if ( ode != null ) {
                ode.delete();
                ode = null;
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
            World odeJavaWorld = this.getSpace().getODEJavaWorld();
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
                JointSlider slider = new JointSlider( this.getName(), odeJavaWorld );
                ode = slider;
                attach( true );
                changeDelegate( axes[0], new SliderJointAxis( axes[0], slider ) );
            }
            else if ( numRotational > 0 ) {
                if ( numRotational == 1 ) {
                    // hinge
                    if ( axes[0].isRelativeToSecondObject() ) {
                        unsupported( "this implemantation supports a rotational axis only if it is " +
                                "relative to the first object" );
                    }
                    JointHinge hinge = new JointHinge( this.getName(), odeJavaWorld );
                    ode = hinge;
                    attach( true );
                    changeDelegate( axes[0], new HingeJointAxis( axes[0], hinge, null ) );
                }
                else if ( numRotational == 2 ) {
                    // universal or hinge2
                    if ( !Float.isInfinite( axes[1].getPositionMaximum() ) ) {
                        unsupported( "the second rotational axis cannot be restricted in this implementation" );
                    }
                    if ( axes[0].isRelativeToSecondObject() || !axes[1].isRelativeToSecondObject() ) {
                        unsupported( "this implemantation supports two rotational axes only if the second one is " +
                                "relative to the second object, the first axis must be relative to the first object" );
                    }

                    OdeJavaJoint joint;
                    if ( !Float.isInfinite( axes[0].getPositionMinimum() ) ) {
                        // hinge2 has stops
                        joint = new JointHinge2( this.getName(), odeJavaWorld );
                    }
                    else {
                        // universal does not have stops
                        joint = new JointUniversal( this.getName(), odeJavaWorld );
                    }
                    ode = joint;
                    attach( true );
                    changeDelegate( axes[0], new HingeJointAxis( axes[0], joint, this ) );
                    changeDelegate( axes[1], new HingeJointAxis2( axes[1], joint ) );
                }
                else if ( numRotational == 3 ) {
                    // ball, possibly plus angular motor
                    ode = new JointBall( this.getName(), odeJavaWorld );
                    attach( true );
                    //TODO AMotor
                }
            }
            else {
                JointFixed jointFixed = new JointFixed( this.getName(), odeJavaWorld );
                ode = jointFixed;
                attach( true );
            }
            
            if ( ode != null )
            		ode.setName( getName() );
        }
        typeChanged = false;
    }

    @Override
    public void setName( final String value ) {
        super.setName( value );
        if ( ode != null )
        		ode.setName( value );
    }

    private final Vector3f anchorTmp = new Vector3f();

    private void attach( boolean reanchor ) {
        if ( ode != null ) {
            Body body1 = nodes.size() > 0 ? nodes.get( 0 ).getBody() : null;
            Body body2 = nodes.size() > 1 ? nodes.get( 1 ).getBody() : null;
            ode.attach( body1, body2 );
            applyCFM();
            applyERP();
            if ( reanchor ) {
                adjustAnchor();
                if ( ode instanceof JointFixed ) {
                    JointFixed jointFixed = (JointFixed) ode;
                    jointFixed.setFixed();
                }
            }
            //System.out.println("Joint is "+ode.getClass()+" between "+body1+" and "+body2);
        }
    }

    private void adjustAnchor() {
        if ( nodes.size() < 2 ) {
            ode.setAnchor( anchor.x, anchor.y, anchor.z );
        }
        else {
            anchorTmp.set( anchor );
            DynamicPhysicsNodeImpl node1 = nodes.get( 0 );
            node1.getWorldRotation().multLocal( anchorTmp );
            anchorTmp.addLocal( node1.getWorldTranslation() );
            ode.setAnchor( anchorTmp.x, anchorTmp.y, anchorTmp.z );
        }
    }

    private void changeDelegate( JointAxis toBeReplaced, JointAxis newAxis ) {
        ( (OdeJointAxis) toBeReplaced ).setDelegate( newAxis );
    }

    @Override
    public boolean setActive( boolean value ) {
        boolean changed = super.setActive( value );
        if ( changed ) {
            if ( !value ) {
                detach();
            }
            else {
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
       // System.out.println(leftNode+" attached to "+rightNode);
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
        if ( ode != null ) {
            ode.attach( null, null );
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

