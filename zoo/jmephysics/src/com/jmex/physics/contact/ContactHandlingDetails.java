/*Copyright*/
package com.jmex.physics.contact;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;

/**
 * Interface for contact handling detail like friction, bouciness, etc.
 *
 * @author Irrisor
 */
public interface ContactHandlingDetails {
    boolean isIgnored();

    /**
     * @return friction coefficient
     * @see MutableContactInfo#setMu(float)
     */
    float getMu();

    /**
     * @return othogonal friction coefficient
     * @see MutableContactInfo#setMuOrthogonal(float)
     */
    float getMuOrthogonal();

    /**
     * @return bounce parameter
     * @see MutableContactInfo#setBounce(float)
     */
    float getBounce();

    /**
     * @return minimum bounce velocity
     * @see MutableContactInfo#setMinimumBounceVelocity(float)
     */
    float getMinimumBounceVelocity();

    /**
     * @param store where to store the retrieved value (null to create a new vector)
     * @return surface motion
     * @see MutableContactInfo#setSurfaceMotion(com.jme.math.Vector2f)
     */
    Vector2f getSurfaceMotion( Vector2f store );

    /**
     * @param store where to store the retrieved value (null to create a new vector)
     * @return slip
     * @see MutableContactInfo#setSlip(com.jme.math.Vector2f)
     */
    Vector2f getSlip( Vector2f store );

    /**
     * Query the direction for the friction - TODO: remove this?
     *
     * @param store where to put the value, null to create a new Vector
     * @return store
     */
    public Vector3f getFrictionDirection( Vector3f store );
}

/*
 * $log$
 */
