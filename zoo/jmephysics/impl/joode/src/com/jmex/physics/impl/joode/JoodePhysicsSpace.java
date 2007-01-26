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

package com.jmex.physics.impl.joode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jme.scene.Spatial;
import com.jme.util.LoggingSystem;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.PhysicsCollisionGeometry;
import com.jmex.physics.PhysicsNode;
import com.jmex.physics.PhysicsSpace;
import com.jmex.physics.PhysicsUpdateCallback;
import com.jmex.physics.StaticPhysicsNode;
import com.jmex.physics.contact.ContactInfo;
import com.jmex.physics.contact.PendingContact;
import com.jmex.physics.geometry.PhysicsBox;
import com.jmex.physics.geometry.PhysicsCapsule;
import com.jmex.physics.geometry.PhysicsCylinder;
import com.jmex.physics.geometry.PhysicsMesh;
import com.jmex.physics.geometry.PhysicsRay;
import com.jmex.physics.geometry.PhysicsSphere;
import com.jmex.physics.impl.joode.geometry.JoodeBox;
import com.jmex.physics.impl.joode.geometry.JoodeCapsule;
import com.jmex.physics.impl.joode.geometry.JoodeCylinder;
import com.jmex.physics.impl.joode.geometry.JoodeMesh;
import com.jmex.physics.impl.joode.geometry.JoodeRay;
import com.jmex.physics.impl.joode.geometry.JoodeSphere;
import com.jmex.physics.impl.joode.joints.JoodeJoint;
import net.java.dev.joode.Body;
import net.java.dev.joode.Mass;
import net.java.dev.joode.World;
import net.java.dev.joode.collision.Contact;
import net.java.dev.joode.collision.ContactGeom;
import net.java.dev.joode.collision.SurfaceParameters;
import net.java.dev.joode.collision.collider.Colliders;
import net.java.dev.joode.force.Gravity;
import net.java.dev.joode.geom.Geom;
import net.java.dev.joode.geom.Ray;
import net.java.dev.joode.joint.JointContact;
import net.java.dev.joode.joint.JointGroupID;
import net.java.dev.joode.space.NearCallback;
import net.java.dev.joode.space.OctTreeSpace;
import net.java.dev.joode.space.Space;
import net.java.dev.joode.util.Vector3;

/**
 * @author Irrisor
 */
public class JoodePhysicsSpace extends PhysicsSpace implements NearCallback {

    private JoodePendingContact contact = new JoodePendingContact();
    protected float stepSize;

    protected Gravity gravity;

    private static void computeODEDefaultFrictionDirection( Vector3f normal, Vector3f primary, Vector3f secondary ) {
        if ( FastMath.abs( normal.z ) > SQRT_2 ) {
            // choose p in y-z plane
            float a = normal.y * normal.y + normal.z * normal.z;
            float k = FastMath.invSqrt( a );
            primary.x = 0;
            primary.y = -normal.z * k;
            primary.z = normal.y * k;
            // set q = n x p
            if ( secondary != null ) {
                secondary.x = a * k;
                secondary.y = -normal.x * primary.z;
                secondary.z = normal.x * primary.y;
            }
        } else {
            // choose p in x-y plane
            float a = normal.x * normal.x + normal.y * normal.y;
            float k = FastMath.invSqrt( a );
            primary.x = -normal.y * k;
            primary.y = normal.x * k;
            primary.z = 0;
            // set q = n x p
            if ( secondary != null ) {
                secondary.x = -normal.z * primary.y;
                secondary.y = normal.z * primary.x;
                secondary.z = a * k;
            }
        }
    }

    private float timeSinceStartOfUpdate;
    public static final float SQRT_2 = FastMath.sqrt( 2 );

    public Body createBody( String name ) {
        return new Body( world, Mass.createSphere( 1, 1 ) );
    }

    public void deleteBody( Body body ) {
        world.removeBody( body );
    }

    public void addGeom( Geom geom ) {
        if ( geom.parent_space == null && geom.body == null ) {
            space.add( geom );
        }
        PhysicsCollisionGeometry geometry = (PhysicsCollisionGeometry) geom.getUserData();
        if ( geometry instanceof JoodeMesh ) {
            JoodeMesh mesh = (JoodeMesh) geometry;
            triMeshes.add( mesh );
        }
    }

    public void removeGeom( Geom geom ) {
        space.remove( geom );
        PhysicsCollisionGeometry geometry = (PhysicsCollisionGeometry) geom.getUserData();
        if ( geometry instanceof JoodeMesh ) {
            JoodeMesh mesh = (JoodeMesh) geometry;
            triMeshes.remove( mesh );
        }
    }

    public Space getJoodeSpace() {
        return space;
    }

    static class JoodeFactory implements Factory {
        public JoodeFactory() {
            setFactory( this );
        }

        public PhysicsSpace create() {
            JoodePhysicsSpace space = new JoodePhysicsSpace();
            space.setUpdateRate( 100 );
            space.setStepSize( 1 / 100f );
            return space;
        }
    }

    @Override
    public DynamicPhysicsNode createDynamicNode() {
        DynamicPhysicsNodeImpl dynamicPhysicsNode = new DynamicPhysicsNodeImpl( this );
        dynamicPhysicsNode.setActive( true );
        return dynamicPhysicsNode;
    }

    @Override
    public StaticPhysicsNode createStaticNode() {
        StaticPhysicsNodeImpl staticPhysicsNode = new StaticPhysicsNodeImpl( this );
        staticPhysicsNode.setActive( true );
        return staticPhysicsNode;
    }

    private ArrayList<JoodeJoint> joints = new ArrayList<JoodeJoint>();
    private List<? extends Joint> jointsImmutable = Collections.unmodifiableList( joints );

    @Override
    public Joint createJoint() {
        JoodeJoint joint = new JoodeJoint( this );
        addJoint( joint );
        return joint;
    }

    @Override
    protected void addJoint( Joint joint ) {
        if ( !joint.isActive() ) {
            JoodeJoint joodeJoint = (JoodeJoint) joint;
            super.addJoint( joint );
            joints.add( joodeJoint );
        }
    }

    @Override
    protected void removeJoint( Joint joint ) {
        super.removeJoint( joint );
        //noinspection SuspiciousMethodCalls
        joints.remove( joint );
    }


    /**
     * This is an ODE-specific method and may be removed (or moved) if other dynamics implementations are used
     *
     * @return the ODEJava World object
     */
    public World getJoodeWorld() {
        return world;
    }

    /**
     * The slowest but most accurate step function
     */
    public static final int SF_STEP_SIMULATION = 0;

    /**
     * A faster but lees accurate alternative
     */
    public static final int SF_STEP_FAST = 2;

    /**
     * The step function that has superseded SF_STEP_FAST
     */
    public static final int SF_STEP_QUICK = 1;

    // Signals which one of the above that is to be used.
    private int stepFunction;

    // The space to add bodys to.
    private World world;

    // The space to add geometry to.
    private Space space;

    /*
    // List of ContactInfos which is returned after every update call
    private List<ContactInfo> contactInfos;


    private List<ContactInfo> lastContactInfos;

    @Override
    public List<ContactInfo> getLastContactInfos() {
        return lastContactInfos;
    }*/

    // Keeps track of all the PhysicsObjects in this world.
    private final ArrayList<PhysicsNode> physicsNodes;
    private final ArrayList<JoodeMesh> triMeshes = new ArrayList<JoodeMesh>();
    private final List<? extends PhysicsNode> physicsNodesImmutable;

    // Varables used to govern the frequency of update calls.
    private float updateRate;

    private float elapsedTime;

    private Vector3f tmp1 = new Vector3f();
    private Vector3f tmp2 = new Vector3f();
    private Vector3f tmpBounceVel = new Vector3f();

    /**
     * Initialises the world, space and gravity. Sets some default values: <br>
     * gravity = Earths <br>
     * step interactions = 15 <br>
     * step size = 0.02 <br>
     * update rate = 0 (update as fast as possible) <br>
     * step function = SF_STEP_QUICK
     */
    public JoodePhysicsSpace() {

        // Create the space which we add geometry to.
        space = new OctTreeSpace( null );

        // Create the ODE world which we'll add bodys to.
        world = new World();
        //todo: world.setAutoDisableBodies( true );

        // Set the default gravity.
        gravity = new Gravity( world, new Vector3( 0, -9.81f, 0 ) );

        // According to the odejava doc, setting this will remove
        // some sort of weirdness that would otherwise occur...
        //todo: world.setContactSurfaceThickness( 0.001f );

        // Set the default step interactions.
        //todo: world.setStepInteractions( 15 );

        // Set a default step size.
        setStepSize( 0.02f );

        // Set the default step function.
        stepFunction = SF_STEP_QUICK; //TODO: make this selectable in interface? 

        // use collisions
        for ( int i = 0; i < MAX_CONTACTS; i++ ) {
            this.contactBuffer[i] = new ContactGeom();
        }

        // create the results
//        contactInfos = new ArrayList<ContactInfo>();

        // create the collision pair list
        physicsNodes = new ArrayList<PhysicsNode>();
        physicsNodesImmutable = Collections.unmodifiableList( physicsNodes );

        // Default to no UPS restriction.
        updateRate = -1;
        elapsedTime = 0;
    }

    @Override
    public void addNode( PhysicsNode node ) {
        super.addNode( node );

        // log some stuff:
        Logger logger = LoggingSystem.getLogger();
        if ( logger.isLoggable( Level.INFO ) ) {
            logger.log( Level.INFO,
                    "PhysicsNode (" + node.getName() + ") has been created" );
        }

        // add it to the arraylist
        physicsNodes.add( node );
    }

    /**
     * Removes a <code>PhysicsObject</code> from the simulation.
     *
     * @param obj The object to remove.
     */
    protected void removeNode( PhysicsNode obj ) {

        if ( !physicsNodes.remove( obj ) ) {
            return;
        }
        removeNode( obj );

        // print out a statement
        Logger logger = LoggingSystem.getLogger();
        if ( logger.isLoggable( Level.INFO ) ) {
            logger.log( Level.INFO,
                    "PhysicsObject ("
                            + obj.getName()
                            + ") has been removed from PhysicsWorld and will no longer take place in the simulation" );
        }
    }

    /**
     * Set how much to step by each time.
     *
     * @param stepSize The step size to set. Default is 0.02.
     */
    public void setStepSize( float stepSize ) {
        this.stepSize = stepSize;
        LoggingSystem.getLogger().log( Level.INFO,
                "Setting StepSize of PhysicsWorld to : " + stepSize );
    }

    /**
     * Obtains how far to step by each update call.
     *
     * @return The current step size. Default is 0.02.
     */
    public float getStepSize() {
        return stepSize;
    }

    /**
     * Sets how many updates per second that is wanted. Default is running as
     * fast as possible (i.e. -1).
     *
     * @param ups number of steps per second Note: cannot be 0.
     */
    public void setUpdateRate( float ups ) {
        // updateRate = timer.getResolution() / ups;
        updateRate = 1.0f / ups;
        LoggingSystem.getLogger().log( Level.INFO,
                "Changed the number of updates in a second of PhysicsWorld to: " + ups );
    }

    /**
     * Returns how many updates per second we run at. Default is running as fast
     * as possible (-1).
     *
     * @return The updaterate. Default is -1.
     */
    public int getUpdateRate() {
        // return (int) (updateRate * timer.getResolution());
        return (int) ( 1.0f / updateRate );
    }

    /**
     * Temp variables to flatline memory usage.
     */
    private final Quaternion inverseWorldRotation = new Quaternion();

    /**
     * Sets the world translation of a spatial according to a supplied body.
     * This is done by going "the other way around"; figuring
     * out what its local translation has to be in order for the world
     * translation to be the same as the one of the body.
     *
     * @param spat             The Spatial to synchronize.
     * @param worldTranslation input translation
     * @param store            output translation
     */
    void worldToLocal( Spatial spat, Vector3f worldTranslation, Vector3f store ) {
        if ( spat.getParent() != null ) {
            store.set( worldTranslation ).subtractLocal( spat.getParent().getWorldTranslation() );
            store.divideLocal( spat.getParent().getWorldScale() );
            inverseWorldRotation.set( spat.getParent().getWorldRotation() ).inverseLocal()
                    .multLocal( store );
        } else {
            store.set( worldTranslation );
        }
    }

    /**
     * Sets the world rotation of a spatial according to a supplied body.
     * This is done by going "the other way around"; figuring
     * out what its local rotation has to be in order for the world
     * rotation to be the same as the one of the body.
     *
     * @param spat          The Spatial to synchronize.
     * @param worldRotation desired world rotation
     */
    void setWorldRotation( Spatial spat, Quaternion worldRotation ) {
        Quaternion tmpQuat = spat.getLocalRotation();
        if ( spat.getParent() != null ) {
            inverseWorldRotation.set( spat.getParent().getWorldRotation() ).inverseLocal().mult(
                    worldRotation, tmpQuat );
        } else {
            tmpQuat.set( worldRotation );
        }
    }

    @Override
    public List<? extends PhysicsNode> getNodes() {
        return physicsNodesImmutable;
    }

    @Override
    public List<? extends Joint> getJoints() {
        return jointsImmutable;
    }

    public void delete() {
        removeAllFromUpdateCallbacks();
        removeAllObjects();
        removeAllJoints();
    }

    private void removeAllJoints() {

        @SuppressWarnings("unchecked")
        List<Joint> list = (List<Joint>) joints.clone();
        for ( Joint joint : list ) {
            removeJoint( joint );
        }
    }

    /**
     * Steps the world forward. Perform all calculations and detect collisions.
     * Should be called every frame.
     * <p/>
     * Note, that if you have set an update rate, this method will check with
     * the timer if it should do any logic. If not, it escapes. If the time
     * elapsed is greater than the update interval it computes more than one
     * step.
     * </p>
     *
     * @param tpf time that has elapsed since the last call of update
     */
    @Override
    public void update( float tpf ) {
//        lastContactInfos = null;
        if ( tpf < 0 ) {
            tpf = 0;
        }
        timeSinceStartOfUpdate = 0;
        elapsedTime += tpf;

//        contactInfos.clear();

        boolean updated = false;

        while ( elapsedTime >= updateRate ) {
            if ( !updated ) {
                for ( JoodeJoint joint : joints ) {
                    joint.updateJointType();
                }
            }

            // call update callbacks
            for ( int i = this.sizeOfUpdateCallbacks() - 1; i >= 0; i-- ) {
                PhysicsUpdateCallback updateCallback = this.getFromUpdateCallbacks( i );
//                if ( !updated ) {
//                    updateCallback.beforeUpdate( this );
//                }
                updateCallback.beforeStep( this, getStepSize() );
            }
            updated = true;

            computeTimeStep();

            if ( this.sizeOfUpdateCallbacks() > 0 ) {
                updateScene();
            }

            // call update callbacks
            for ( int i = this.sizeOfUpdateCallbacks() - 1; i >= 0; i-- ) {
                PhysicsUpdateCallback updateCallback = this.getFromUpdateCallbacks( i );
                updateCallback.afterStep( this, getStepSize() );
            }

            if ( updateRate > 0 ) {
                elapsedTime -= updateRate;
                timeSinceStartOfUpdate += updateRate;
            } else {
                elapsedTime = 0;
                break;
            }
        }

        if ( updated ) {
            if ( this.sizeOfUpdateCallbacks() == 0 ) {
                updateScene();
            } else {
                // call update callbacks
//                for ( int i = this.sizeOfUpdateCallbacks() - 1; i >= 0; i-- ) {
//                    PhysicsUpdateCallback updateCallback = this.getFromUpdateCallbacks( i );
//                    updateCallback.afterUpdate( this );
//                }
            }
//            lastContactInfos = contactInfos;
//            return contactInfos;
        }
    }

    public void pick( PhysicsCollisionGeometry geometry ) {
        throw new UnsupportedOperationException();
        //TODO: pick
//        OdeGeometry odeGeometry = (OdeGeometry) geometry;
//        collision.collide2( space.getId().getSwigCPtr(), odeGeometry.getOdeGeom().getId().getSwigCPtr() );
//
//        iterateContacts( false );
    }


    private int numContacts = 0;

    private ContactGeom[] contactBuffer = new ContactGeom[MAX_CONTACTS];

    private JointGroupID contactGroup = new JointGroupID();
    private static final int MAX_CONTACTS = 50;

    public void resetCollisionData() {
        this.numContacts = 0;
        this.contactGroup.destroy();
    }

    public void call( Object data, Geom g1, Geom g2 ) {
        // near collisions are propagated for indepth analysis
        Body body1 = g1.body;
        Body body2 = g2.body;
        if ( body1 != body2 ) {
            if ( body1 != null ) {
                for ( net.java.dev.joode.joint.Joint joint : body1.joints ) {
                    if ( !( joint instanceof JointContact ) ) {
                        if ( joint.body[0] == body2 || joint.body[1] == body2 ) {
                            return; // don't collide bodies that are connected via non-contact joints
                        }
                    }
                }
            }
            this.numContacts += Colliders.dCollide( g1, g2, this.numContacts,
                    this.contactBuffer, this.numContacts, 1 );
        }
    }

    private void computeTimeStep() {
        // collide objects in a given space

        resetCollisionData();
        space.collide( null, this );

        // read and modify contact information
        iterateContacts( true );

        for ( int i = triMeshes.size() - 1; i >= 0; i-- ) {
            JoodeMesh mesh = triMeshes.get( i );
            mesh.updateOdeLastTransformation();
        }

        // See what step function to use and then step through the world.
        if ( stepFunction == SF_STEP_QUICK ) {
            world.quickStep( getStepSize() );
        } else if ( stepFunction == SF_STEP_FAST ) {
            throw new UnsupportedOperationException( "StepFast not implemented" );
        } else if ( stepFunction == SF_STEP_SIMULATION ) {
            world.step( getStepSize() );
        }
    }

    private void updateScene() {
        for ( int i = physicsNodes.size() - 1; i >= 0; i-- ) {
            PhysicsNode node = physicsNodes.get( i );
            ( (PhysicsNodeImpl) node ).sceneFromOde();
            if ( node.getParent() == null ) {
                node.updateGeometricState( 0, true );
            }
        }
    }

    private final Vector2f tmpVec2 = new Vector2f();

    /**
     * Loop through the collisions that have occured and set the contact
     * information. Eg. Bounce factor.
     *
     * @param applyContacts false to do event handling only
     */
    private void iterateContacts( boolean applyContacts ) {
        for ( int i = 0; i < numContacts; i++ ) {
            ContactGeom contactGeom = contactBuffer[i];

            final Geom geom1 = contactGeom.g1;
            final Geom geom2 = contactGeom.g2;
            if ( geom1 == null || geom2 == null ) {
                contactGeom.g1 = null;
                contactGeom.g2 = null;
                continue;
            }
            PhysicsNode obj1 = ( (PhysicsCollisionGeometry) geom1.getUserData() ).getPhysicsNode();
            PhysicsNode obj2 = ( (PhysicsCollisionGeometry) geom2.getUserData() ).getPhysicsNode();

            if ( obj1 != obj2 ) {
                // don't let rays generate contact points in ode but generate events
                boolean applyThisContact =
                        applyContacts
                                && !( geom1 instanceof Ray ) && !( geom2 instanceof Ray );
                contact.clear();
                contact.currentContactGeom = contactGeom;
                if ( applyThisContact ) {
                    adjustContact( contact );
                }

                if ( contact.isIgnored() ) {
                    contactGeom.g1 = null;
                    contactGeom.g2 = null;
                } else {
                    if ( !applyThisContact ) {
                        // don't apply contact points in but generate events
                        contactGeom.g1 = null;
                        contactGeom.g2 = null;
                    } else {
                        int mode = SurfaceParameters.dContactApprox1;
                        SurfaceParameters parameters = new SurfaceParameters();
                        parameters.mode = mode;

                        if ( !Float.isNaN( contact.getMu() ) ) {
                            parameters.mu = contact.getMu();
                        } else {
                            parameters.mu = 100;
                        }

                        if ( !Float.isNaN( contact.getBounce() ) ) {
                            mode |= SurfaceParameters.dContactBounce;
                            parameters.mode = mode;
                            parameters.bounce = contact.getBounce();
                        }
                        if ( !Float.isNaN( contact.getMinimumBounceVelocity() ) ) {
                            parameters.bounce_vel = contact.getMinimumBounceVelocity();
                        } else {
                            parameters.bounce_vel = 1f;
                        }
                        if ( !Float.isNaN( contact.getMuOrthogonal() ) ) {
                            mode |= SurfaceParameters.dContactMu2;
                            parameters.mode = mode;
                            parameters.mu2 = contact.getMuOrthogonal();
                        }
                        Vector3f fdir = contact.getFrictionDirection( tmp2 );
                        Vector3 fdir1 = null;
                        if ( !Float.isNaN( fdir.x ) && !Float.isNaN( fdir.y ) && !Float.isNaN( fdir.z ) ) {
                            mode |= SurfaceParameters.dContactFDir1;
                            fdir1 = VecMathConvert.convert( fdir, fdir1 );
                        }
                        Vector2f surfaceMotion = contact.getSurfaceMotion( tmpVec2 );
                        if ( !Float.isNaN( surfaceMotion.x ) && surfaceMotion.x != 0 ) {
                            mode |= SurfaceParameters.dContactMotion1;
                            parameters.mode = mode;
                            parameters.motion1 = surfaceMotion.x;
                        }
                        if ( !Float.isNaN( surfaceMotion.y ) && surfaceMotion.y != 0 ) {
                            mode |= SurfaceParameters.dContactMotion2;
                            parameters.mode = mode;
                            parameters.motion2 = surfaceMotion.y;
                        }
                        Vector2f slip = contact.getSlip( tmpVec2 );
                        if ( !Float.isNaN( slip.x ) ) {
                            mode |= SurfaceParameters.dContactSlip1;
                            parameters.mode = mode;
                            parameters.slip1 = slip.x;
                        }
                        if ( !Float.isNaN( slip.y ) ) {
                            mode |= SurfaceParameters.dContactSlip2;
                            parameters.mode = mode;
                            parameters.slip2 = slip.y;
                        }

                        if ( contactGeom.g1 != null ) {
                            Contact joodeContact = new Contact( this.contactBuffer[i], parameters, null );
                            joodeContact.fdir1 = fdir1;
                            JointContact contactJoint = new JointContact( this.world, joodeContact );
                            this.contactGroup.add( contactJoint );
                            if ( contactJoint.contact.geom.g1.body != null
                                    || contactJoint.contact.geom.g2.body != null ) {
                                contactJoint.attach( contactJoint.contact.geom.g1.body, contactJoint.contact.geom.g2.body );
                            }
                        }
                    }

                    // compute collision velocity (must be done before
                    // returning contact)
                    Vector3f actualBounceVel = tmpBounceVel.set( 0, 0, 0 );
                    if ( obj1 != null && !obj1.isStatic() ) {
                        actualBounceVel.subtractLocal( ( (DynamicPhysicsNode) obj1 )
                                .getLinearVelocity( tmp1 ) );
                    }
                    if ( obj2 != null && !obj2.isStatic() ) {
                        actualBounceVel.addLocal( ( (DynamicPhysicsNode) obj2 )
                                .getLinearVelocity( tmp1 ) );
                    }
                    actualBounceVel.multLocal( VecMathConvert.convert( contactGeom.normal, tmp2 ) );
                    //TODO: allow to trash contacts if results are not needed
                    ContactInfo info = reusableContactInfo( contact );
                    collisionEvent( info );
                }
            }
        }
    }

    private static class ReusableContactInfo implements ContactInfo {
        private final Vector3f normal = new Vector3f();
        private final Vector3f position = new Vector3f();
        private final Vector3f velocity = new Vector3f();

        public Vector3f getContactNormal( Vector3f store ) {
            if ( store == null ) {
                store = new Vector3f();
            }
            return store.set( normal );
        }

        public Vector3f getContactPosition( Vector3f store ) {
            if ( store == null ) {
                store = new Vector3f();
            }
            return store.set( position );
        }

        public Vector3f getContactVelocity( Vector3f store ) {
            if ( store == null ) {
                store = new Vector3f();
            }
            return store.set( velocity );
        }

        private PhysicsCollisionGeometry geometry1;
        private PhysicsCollisionGeometry geometry2;
        private PhysicsNode node1;
        private PhysicsNode node2;

        public PhysicsCollisionGeometry getGeometry1() {
            return geometry1;
        }

        public PhysicsCollisionGeometry getGeometry2() {
            return geometry2;
        }

        public PhysicsNode getNode1() {
            return node1;
        }

        public PhysicsNode getNode2() {
            return node2;
        }

        private float depth;
        private float time;

        public float getPenetrationDepth() {
            return depth;
        }

        public void getDefaultFrictionDirections( Vector3f primaryStore, Vector3f secondaryStore ) {
            computeODEDefaultFrictionDirection( normal, primaryStore, secondaryStore );
        }

        public float getTime() {
            return time;
        }

        private void set( ContactInfo info ) {
            info.getContactNormal( normal );
            info.getContactPosition( position );
            info.getContactVelocity( velocity );
            geometry1 = info.getGeometry1();
            geometry2 = info.getGeometry2();
            node1 = info.getNode1();
            node2 = info.getNode2();
            depth = info.getPenetrationDepth();
            time = info.getTime();
        }

        @Override
        public String toString() {
            return "ContactInfo: " + getGeometry1() + " <-> " + getGeometry2();
        }
    }

    private ContactInfo reusableContactInfo( ContactInfo info ) {
        //TODO: reuse!
        ReusableContactInfo reusableContactInfo = new ReusableContactInfo();
        reusableContactInfo.set( info );
        return reusableContactInfo;
    }

    /**
     * Clean up the system. Should be called before ending your application.
     */
    public void cleanup() {
        //space.delete();
        //world.delete();
        //todo: Joode.dClose();
    }

    /**
     * Change the directional gravity. Earths gravity = (0, -9.81, 0)
     *
     * @param gravity The gravity vector.
     */
    @Override
    public void setDirectionalGravity( Vector3f gravity ) {
        throw new UnsupportedOperationException( "gravity change not implemented" );
//        TODO: world.setGravity( gravity.x, gravity.y, gravity.z );
//
//        LoggingSystem.getLogger().log(
//                Level.INFO,
//                "Setting Gravity of PhysicsWorld to: (" + gravity.x + ", " + gravity.y + ", "
//                        + gravity.z + ")" );
    }

    @Override
    public Vector3f getDirectionalGravity( Vector3f store ) {
        throw new UnsupportedOperationException( "gravity read not implemented" );
        //todo: return world.getGravity( store );
    }

    /**
     * Sets the method which we use to update the world. It can be one of the
     * following: - SF_STEP_SIMULATION (The slowest but most accurate step
     * function) - SF_STEP_FAST (A faster but lees accurate alternative) -
     * SF_STEP_QUICK (The step function that has superseded SF_STEP_FAST)
     * Default is SF_STEP_QUICK.
     *
     * @param stepFunction The stepFunction to set.
     */
    public void setStepFunction( int stepFunction ) {
        this.stepFunction = stepFunction;

        if ( stepFunction == SF_STEP_FAST ) {
            LoggingSystem.getLogger().log( Level.INFO, "Setting the Physics Solver to use StepFast" );
        } else if ( stepFunction == SF_STEP_QUICK ) {
            LoggingSystem.getLogger()
                    .log( Level.INFO, "Setting the Physics Solver to use StepQuick" );
        } else {
            LoggingSystem.getLogger().log( Level.INFO,
                    "Setting the Physics Solver to use Simulation" );
        }

    }

    /**
     * Gets the method which we use to update the world.
     *
     * @return The current step function being used.
     */
    public int getStepFunction() {
        return stepFunction;
    }

    /**
     * Removes all PhysicsObjects in the world. Could be useful for
     * level-switching and such.
     */
    public void removeAllObjects() {
        @SuppressWarnings("unchecked")
        List<PhysicsNode> list = (List<PhysicsNode>) physicsNodes.clone();
        for ( PhysicsNode node : list ) {
            removeNode( node );
        }
    }

    @Override
    protected PhysicsBox createBox( String name, PhysicsNode node ) {
        JoodeBox geometry = new JoodeBox( node, null );
        geometry.setName( name );
        if ( node != null ) {
            node.attachChild( geometry );
        }
        return geometry;
    }

    @Override
    protected PhysicsSphere createSphere( String name, PhysicsNode node ) {
        JoodeSphere geometry = new JoodeSphere( node, null );
        geometry.setName( name );
        if ( node != null ) {
            node.attachChild( geometry );
        }
        return geometry;
    }

    @Override
    public PhysicsCylinder createCylinder( String name, PhysicsNode node ) {
        JoodeCylinder geometry = new JoodeCylinder( node, null );
        geometry.setName( name );
        if ( node != null ) {
            node.attachChild( geometry );
        }
        return geometry;
    }

    @Override
    public PhysicsCapsule createCapsule( String name, PhysicsNode node ) {
        JoodeCapsule geometry = new JoodeCapsule( node, null );
        geometry.setName( name );
        if ( node != null ) {
            node.attachChild( geometry );
        }
        return geometry;
    }

    @Override
    public PhysicsMesh createMesh( String name, PhysicsNode node ) {
        JoodeMesh geometry = new JoodeMesh( node );
        geometry.setName( name );
        if ( node != null ) {
            node.attachChild( geometry );
        }
        return geometry;
    }

    @Override
    public PhysicsRay createRay( String name, PhysicsNode node ) {
        JoodeRay geometry = new JoodeRay( node, null );
        geometry.setName( name );
        if ( node != null ) {
            node.attachChild( geometry );
        }
        return geometry;
    }

    private class JoodePendingContact extends PendingContact {
        private ContactGeom currentContactGeom;

        public Vector3f getContactNormal( Vector3f store ) {
            return VecMathConvert.convert( currentContactGeom.normal, store );
        }

        public Vector3f getContactPosition( Vector3f store ) {
            return VecMathConvert.convert( currentContactGeom.pos, store );
        }

        public PhysicsCollisionGeometry getGeometry1() {
            return (PhysicsCollisionGeometry) currentContactGeom.g1.getUserData();
        }

        public PhysicsCollisionGeometry getGeometry2() {
            return (PhysicsCollisionGeometry) currentContactGeom.g2.getUserData();
        }

        public PhysicsNode getNode1() {
            return getGeometry1().getPhysicsNode();
        }

        public PhysicsNode getNode2() {
            return getGeometry2().getPhysicsNode();
        }

        public float getPenetrationDepth() {
            return currentContactGeom.depth;
        }

        public void getDefaultFrictionDirections( Vector3f primaryStore, Vector3f secondaryStore ) {
            Vector3f n = getContactNormal( tmp2 );
            computeODEDefaultFrictionDirection( n, primaryStore, secondaryStore );
        }

        public float getTime() {
            return timeSinceStartOfUpdate;
        }
    }
}

/*
* $log$
*/
