/*Copyright*/
package com.jmex.physics.contact;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;

/**
 * Helper class to specify contact details.
 *
 * @author Irrisor
 * @see ContactHandlingDetails
 */
public class MutableContactInfo implements ContactHandlingDetails {
    private boolean ignored;
    private float mu;
    private float muOrthogonal;
    private float bounce;
    private float minimumBounceVelocity;
    private Vector2f surfaceMotion;
    private Vector2f slip;
    private static Vector2f tmpVec2f = new Vector2f();
    private static Vector3f tmpVec3f = new Vector3f();

    /**
     * Default ctor.
     */
    public MutableContactInfo() {
        clear();
    }

    /**
     * Copy ctor.
     *
     * @param toCopy details to copy
     */
    public MutableContactInfo( ContactHandlingDetails toCopy ) {
        this();
        copy( toCopy );
    }

    public boolean isIgnored() {
        return this.ignored;
    }

    public void setIgnored( final boolean value ) {
        this.ignored = value;
    }

    public void clear() {
        mu = Float.NaN;
        bounce = Float.NaN;
        muOrthogonal = Float.NaN;
        minimumBounceVelocity = Float.NaN;
        frictionDirection.set( Float.NaN, Float.NaN, Float.NaN );
        if ( slip != null ) {
            slip.set( Float.NaN, Float.NaN );
        }
        if ( surfaceMotion != null ) {
            surfaceMotion.set( 0, 0 );
        }
    }

    /**
     * @return friction coefficient
     * @see #setMu(float)
     */
    public float getMu() {
        return mu;
    }

    /**
     * Coulomb friction coefficient. This must be in the range 0 to {@link Float#POSITIVE_INFINITY}.
     * 0 results in a frictionless contact, and infinity results in a contact that
     * never slips. Note that frictionless contacts are less time consuming to
     * compute than ones with friction, and infinite friction contacts can be
     * cheaper than contacts with finite friction. Friction must always be set.
     *
     * @param mu friction coefficient
     */
    public void setMu( float mu ) {
        this.mu = mu;
    }

    /**
     * @return othogonal friction coefficient
     * @see #setMuOrthogonal(float)
     */
    public float getMuOrthogonal() {
        return muOrthogonal;
    }

    /**
     * Optional Coulomb friction coefficient for orthogonal friction direction (0..Infinity). The direction for this
     * coefficient is orthogonal to the friction direction and the contact normal.
     *
     * @param muOrthogonal othogonal friction coefficient
     */
    public void setMuOrthogonal( float muOrthogonal ) {
        this.muOrthogonal = muOrthogonal;
    }

    /**
     * @return bounce parameter
     * @see #setBounce(float)
     */
    public float getBounce() {
        return bounce;
    }

    /**
     * Restitution parameter (0..1). 0 means the surfaces are not bouncy at all, 1 is maximum bouncyness.
     *
     * @param bounce bouncyness
     */
    public void setBounce( float bounce ) {
        this.bounce = bounce;
    }

    /**
     * @return minimum bounce velocity
     * @see #setMinimumBounceVelocity(float)
     */
    public float getMinimumBounceVelocity() {
        return minimumBounceVelocity;
    }

    /**
     * The minimum incoming velocity necessary for bounce (in m/s). Incoming velocities below this will effectively have
     * a bounce parameter of 0.
     *
     * @param minimumBounceVelocity new value
     */
    public void setMinimumBounceVelocity( float minimumBounceVelocity ) {
        this.minimumBounceVelocity = minimumBounceVelocity;
    }

    /**
     * Surface velocity - if set, the contact surface is assumed to be moving
     * independently of the motion of the nodes. This is kind of like a conveyor belt running over the surface.
     * The x component of the vector specifies motion in friction direction and the y component specifies motion
     * in direction orthogonal to friction direction and normal.
     *
     * @param motion new value, (0, 0) for no motion (not NaN)
     */
    public void setSurfaceMotion( Vector2f motion ) {
        if ( surfaceMotion == null ) {
            surfaceMotion = new Vector2f();
        }
        surfaceMotion.set( motion );
    }

    /**
     * @param store where to store the retrieved value (null to create a new vector)
     * @return surface motion
     * @see #setSurfaceMotion(com.jme.math.Vector2f)
     */
    public Vector2f getSurfaceMotion( Vector2f store ) {
        if ( store == null ) {
            store = new Vector2f();
        }
        if ( surfaceMotion != null ) {
            store.set( surfaceMotion );
        }
        else {
            store.set( 0, 0 );
        }
        return store;
    }

    /**
     * The coefficients of force-dependent-slip (FDS).
     * The x component of the vector specifies motion in friction direction and the y component specifies motion
     * in direction orthogonal to friction direction and normal.
     */
    public void setSlip( Vector2f slip ) {
        if ( this.slip == null ) {
            this.slip = new Vector2f();
        }
        this.slip.set( slip );
    }

    /**
     * @param store where to store the retrieved value (null to create a new vector)
     * @return slip
     * @see #setSlip(com.jme.math.Vector2f)
     */
    public Vector2f getSlip( Vector2f store ) {
        if ( store == null ) {
            store = new Vector2f();
        }
        if ( slip != null ) {
            store.set( slip );
        }
        else {
            store.set( Float.NaN, Float.NaN );
        }
        return store;
    }

    /**
     * Copy contact details into this pending contact.
     *
     * @param details copy source
     */
    public void copy( ContactHandlingDetails details ) {
        boolean ignored = details.isIgnored();
        setIgnored( ignored );
        if ( !ignored ) {
            setBounce( details.getBounce() );
            setMinimumBounceVelocity( details.getMinimumBounceVelocity() );
            setMu( details.getMu() );
            setMuOrthogonal( details.getMuOrthogonal() );
            setSlip( details.getSlip( tmpVec2f ) );
            setSurfaceMotion( details.getSurfaceMotion( tmpVec2f ) );
            setFrictionDirection( details.getFrictionDirection( tmpVec3f ) );
        }
    }

    private final Vector3f frictionDirection = new Vector3f();

    /**
     * @see ContactHandlingDetails#getFrictionDirection(com.jme.math.Vector3f)
     */
    public void setFrictionDirection( Vector3f direction ) {
        frictionDirection.set( direction );
    }

    public Vector3f getFrictionDirection( Vector3f store ) {
        if ( store == null ) {
            store = new Vector3f();
        }
        return store.set( frictionDirection );
    }
}

/*
 * $log$
 */

