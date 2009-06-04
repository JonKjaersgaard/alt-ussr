package com.jmex.physics.impl.jbullet.joints;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.dynamics.constraintsolver.Generic6DofConstraint;
import com.bulletphysics.linearmath.Transform;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.JointAxis;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.RotationalJointAxis;
import com.jmex.physics.TranslationalJointAxis;
import com.jmex.physics.impl.jbullet.JBulletDynamicPhysicsNode;
import com.jmex.physics.impl.jbullet.JBulletPhysicsSpace;
import com.jmex.physics.impl.jbullet.util.VecmathConverter;

public class JBulletJoint extends Joint {

    private Vector3f anchor = new Vector3f();
    private boolean isDirty = true;
    private JBulletPhysicsSpace space;
    private JBulletDynamicPhysicsNode[] nodes = new JBulletDynamicPhysicsNode[2];
    private JBulletRotationalJointAxis[] rotationalAxes = new JBulletRotationalJointAxis[3];
    private JBulletTranslationalJointAxis[] translationalAxes = new JBulletTranslationalJointAxis[3];

    private List<JBulletDynamicPhysicsNode> nodeList = new ArrayList<JBulletDynamicPhysicsNode>();
    private RigidBody staticBody;

    private Generic6DofConstraint constraint = null;

    public JBulletJoint( JBulletPhysicsSpace space ) {
        this.space = space;
    }

    public void buildConstraint() {
        //This is a physics picker joint.  We don't handle this yet.
        if ( nodes[0] == null ) {
            isDirty = false;
            if ( constraint != null )
            {
                ( (JBulletPhysicsSpace) getSpace() ).dynamicsWorld.removeConstraint( constraint );
                constraint = null;
            }
            return;
        }

        Vector3f[] directions = new Vector3f[3];
        directions[0] = ( rotationalAxes[0] != null ) ? rotationalAxes[0].getDirection( directions[0] ) : ( translationalAxes[0] != null ) ? translationalAxes[0].getDirection( directions[0] ) : null;
        directions[1] = ( rotationalAxes[1] != null ) ? rotationalAxes[1].getDirection( directions[1] ) : ( translationalAxes[1] != null ) ? translationalAxes[1].getDirection( directions[1] ) : null;
        directions[2] = ( rotationalAxes[2] != null ) ? rotationalAxes[2].getDirection( directions[2] ) : ( translationalAxes[2] != null ) ? translationalAxes[2].getDirection( directions[2] ) : null;

        Transform transformRelativeToA = new Transform();
        Transform transformRelativeToB = new Transform();
        transformRelativeToA.setIdentity();
        transformRelativeToB.setIdentity();
        if ( nodes[1] == null ) {
            Vector3f anchorVec = new Vector3f();
            anchorVec = getAnchor( anchorVec );
            nodes[0].getWorldRotation().inverse().mult( anchorVec.subtract( nodes[0].getWorldTranslation(), anchorVec ), anchorVec );
//            FIXME: VecmathConverter.convert( anchorVec, transformRelativeToA.origin );
        } else {
            Vector3f anchorVec = new Vector3f();
            anchorVec = getAnchor( anchorVec );
            VecmathConverter.convert( anchorVec, transformRelativeToA.origin );
            nodes[0].getWorldRotation().mult( anchorVec, anchorVec ).addLocal( nodes[0].getWorldTranslation() );
            nodes[1].getWorldRotation().inverse().mult( anchorVec.subtract( nodes[1].getWorldTranslation(), anchorVec ), anchorVec );
            VecmathConverter.convert( anchorVec, transformRelativeToB.origin );
        }

        if ( directions[2] != null ) {
            Quaternion q = new Quaternion();
            q.fromAxes( directions[0], directions[1], directions[2] );
            q.multLocal( nodes[0].getWorldRotation().multLocal( nodes[0].getLocalRotation() ) );
            transformRelativeToA.setRotation( VecmathConverter.convert( q ) );
            if ( nodes[1] != null ) {
                q.fromAxes( directions[0], directions[1], directions[2] );
                q.multLocal( nodes[1].getWorldRotation().multLocal( nodes[1].getLocalRotation() ) );
                transformRelativeToB.setRotation( VecmathConverter.convert( q ) );
            }
        } else if ( directions[1] != null ) {
            Quaternion q = new Quaternion();
            Vector3f crossedAxis = directions[1].cross( directions[0] );
            q.fromAxes( directions[0], directions[1], crossedAxis );
            q.multLocal( nodes[0].getWorldRotation().multLocal( nodes[0].getLocalRotation() ) );
            transformRelativeToA.setRotation( VecmathConverter.convert( q ) );
            if ( nodes[1] != null ) {
                q.fromAxes( directions[0], directions[1], crossedAxis );
                q.multLocal( nodes[1].getWorldRotation().multLocal( nodes[1].getLocalRotation() ) );
                transformRelativeToB.setRotation( VecmathConverter.convert( q ) );
            }
        } else if ( directions[0] != null ) {
            Quaternion q = new Quaternion();
            float angleBetween = Vector3f.UNIT_X.angleBetween( directions[0] );
            Vector3f rotationalAxis = directions[0].cross( Vector3f.UNIT_X );
            q.fromAngleAxis( angleBetween, rotationalAxis );
            q.multLocal( nodes[0].getWorldRotation().multLocal( nodes[0].getLocalRotation() ) );
            transformRelativeToA.setRotation( VecmathConverter.convert( q ) );
            if ( nodes[1] != null ) {
                q.fromAngleAxis( angleBetween, rotationalAxis );
                q.multLocal( nodes[1].getWorldRotation().multLocal( nodes[1].getLocalRotation() ) );
                transformRelativeToB.setRotation( VecmathConverter.convert( q ) );
            }
        } else {
            //TODO: why not? throw new IllegalStateException( "Cannot build a joint that has no axes attached." );
        }

        if ( constraint != null ) {
            //TODO: investigate how to re-build the constraint correctly.
        }

        if ( nodes[1] != null ) {
            constraint = new Generic6DofConstraint( nodes[0].getBody(), nodes[1].getBody(), transformRelativeToA, transformRelativeToB, true );
        } else {
            constraint = new Generic6DofConstraint( nodes[0].getBody(), staticBody, transformRelativeToA, transformRelativeToB, true );
        }

//        FIX ME:constraint.setLinearLowerLimit( new javax.vecmath.Vector3f(
//                ( translationalAxes[0] == null ) ? 0f : translationalAxes[0].getPositionMinimum(),
//                ( translationalAxes[1] == null ) ? 0f : translationalAxes[1].getPositionMinimum(),
//                ( translationalAxes[2] == null ) ? 0f : translationalAxes[2].getPositionMinimum()
//        ) );
//        FIX ME:constraint.setLinearUpperLimit( new javax.vecmath.Vector3f(
//                ( translationalAxes[0] == null ) ? 0f : translationalAxes[0].getPositionMaximum(),
//                ( translationalAxes[1] == null ) ? 0f : translationalAxes[1].getPositionMaximum(),
//                ( translationalAxes[2] == null ) ? 0f : translationalAxes[2].getPositionMaximum()
//        ) );
        constraint.setAngularLowerLimit( new javax.vecmath.Vector3f(
                ( rotationalAxes[0] == null ) ? 0f : rotationalAxes[0].getPositionMinimum(),
                ( rotationalAxes[1] == null ) ? 0f : rotationalAxes[1].getPositionMinimum(),
                ( rotationalAxes[2] == null ) ? 0f : rotationalAxes[2].getPositionMinimum()
        ) );
        constraint.setAngularUpperLimit( new javax.vecmath.Vector3f(
                ( rotationalAxes[0] == null ) ? 0f : rotationalAxes[0].getPositionMaximum(),
                ( rotationalAxes[1] == null ) ? 0f : rotationalAxes[1].getPositionMaximum(),
                ( rotationalAxes[2] == null ) ? 0f : rotationalAxes[2].getPositionMaximum()
        ) );

        float combinedMass = nodes[0].getMass();
        if ( nodes[1] != null ) {
            combinedMass += nodes[1].getMass();
        }

        constraint.getTranslationalLimitMotor().limitSoftness = 0.1f;
        constraint.getTranslationalLimitMotor().restitution = 0.9f;

        for ( int loop = 0; loop < 3; loop++ ) {
            if ( rotationalAxes[loop] == null ) {
                continue;
            }
            constraint.getRotationalLimitMotor( loop ).maxMotorForce = rotationalAxes[loop].getAvailableAcceleration() / combinedMass;
            constraint.getRotationalLimitMotor( loop ).targetVelocity = rotationalAxes[loop].getDesiredVelocity();
        }

        ( (JBulletPhysicsSpace) getSpace() ).dynamicsWorld.addConstraint( constraint, !isCollisionEnabled() );

        isDirty = false;
    }

    @Override
    public void attach( DynamicPhysicsNode leftNode, DynamicPhysicsNode rightNode ) {
        if ( !( leftNode instanceof JBulletDynamicPhysicsNode ) ||
                !( rightNode instanceof JBulletDynamicPhysicsNode ) ) {
            throw new IllegalArgumentException( "Cannot attach nodes from a non-JBullet physics space." );
        }

        nodes[0] = (JBulletDynamicPhysicsNode) leftNode;
        nodes[1] = (JBulletDynamicPhysicsNode) rightNode;

        isDirty = true;
    }

    @Override
    public void attach( DynamicPhysicsNode node ) {
        if ( !( node instanceof JBulletDynamicPhysicsNode ) ) {
            throw new IllegalArgumentException( "Cannot attach nodes from a non-JBullet physics space." );
        }

        nodes[0] = (JBulletDynamicPhysicsNode) node;
        nodes[1] = null;

        RigidBodyConstructionInfo info = new RigidBodyConstructionInfo( 0f, null, null );
        staticBody = new RigidBody( info );

        isDirty = true;
    }

    @Override
    protected void added( JointAxis axis ) {
        super.added( axis );

        for ( int loop = 0; loop < 3; loop++ ) {
            //checkRotationalAxes:
            if ( rotationalAxes[loop] != null ) {
                if ( axis.isRotationalAxis() ) {
                    continue;
                }
                if ( axis.getDirection( null ).equals( rotationalAxes[loop].getDirection( null ) ) ) {
                    if ( translationalAxes[loop] != null ) {
                        throw new IllegalArgumentException( "Cannot have more than 1 translational and 1 rotational axis defined in the same direction." );
                    } else {
                        translationalAxes[loop] = (JBulletTranslationalJointAxis) axis;
                        isDirty = true;
                        return;
                    }
                }
            } else if ( translationalAxes[loop] != null ) {
                if ( axis.isTranslationalAxis() ) {
                    continue;
                }
                if ( axis.getDirection( null ).equals( translationalAxes[loop].getDirection( null ) ) ) {
                    rotationalAxes[loop] = (JBulletRotationalJointAxis) axis;
                    isDirty = true;
                    return;
                }
            } else {
                if ( axis.isRotationalAxis() ) {
                    rotationalAxes[loop] = (JBulletRotationalJointAxis) axis;
                } else {
                    translationalAxes[loop] = (JBulletTranslationalJointAxis) axis;
                }
                isDirty = true;
                return;
            }
        }
        throw new IllegalArgumentException( "Cannot have more than 3 different axis directions defined per joint. (1 translational, 1 rotational per direction)" );
    }

    @Override
    public void removed( JointAxis axis ) {
        super.removed( axis );

        for ( int loop = 0; loop < 3; loop++ ) {
            if ( axis.isRotationalAxis() && rotationalAxes[loop] == axis ) {
                rotationalAxes[loop] = null;
            }
            if ( axis.isTranslationalAxis() && translationalAxes[loop] == axis ) {
                translationalAxes[loop] = null;
            }
        }
    }

    @Override
    protected RotationalJointAxis createRotationalAxisImplementation() {
        return new JBulletRotationalJointAxis();
    }

    @Override
    protected TranslationalJointAxis createTranslationalAxisImplementation() {
        return new JBulletTranslationalJointAxis();
    }

    @Override
    public void detach() {
        nodes[0] = null;
        nodes[1] = null;

        isDirty = true;
    }

    @Override
    public Vector3f getAnchor( Vector3f store ) {
        store.set( this.anchor );
        return store;
    }

    @Override
    public float getBreakingLinearForce() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getBreakingTorque() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public float getDampingCoefficient() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<? extends DynamicPhysicsNode> getNodes() {
        if ( nodeList == null ) {
            nodeList = new ArrayList<JBulletDynamicPhysicsNode>();
        }
        nodeList.clear();
        if ( nodes[0] != null ) {
            nodeList.add( nodes[0] );
        }
        if ( nodes[1] != null ) {
            nodeList.add( nodes[1] );
        }
        return Collections.unmodifiableList( nodeList );
    }

    @Override
    public PhysicsSpace getSpace() {
        return this.space;
    }

    @Override
    public float getSpringConstant() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean isCollisionEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void reset() {

        isDirty = true;
    }

    @Override
    public void setAnchor( Vector3f anchor ) {
        this.anchor.set( anchor );
        isDirty = true;
    }

    @Override
    public void setBreakingLinearForce( float breakingLinearForce ) {

        isDirty = true;
    }

    @Override
    public void setBreakingTorque( float breakingTorque ) {

        isDirty = true;
    }

    @Override
    public void setCollisionEnabled( boolean enabled ) {

        isDirty = true;
    }

    @Override
    public void setSpring( float springConstant, float dampingCoefficient ) {

        isDirty = true;
    }

    public boolean isDirty() {
        return isDirty;
    }

}
