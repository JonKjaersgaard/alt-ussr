/*Copyright*/
package com.jmex.physics.contact;

import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;

/**
 * Instances of this class are used to query contact details with the {@link ContactCallback} interface.
 * See all setter methods for details.
 *
 * @author Irrisor
 * @see ContactCallback
 */
public abstract class PendingContact extends MutableContactInfo implements ContactInfo {

    private static final Vector3f tmpVelocity = new Vector3f();

    public Vector3f getContactVelocity( Vector3f store ) {
        if ( store == null ) {
            store = new Vector3f();
        }
        Vector3f actualBounceVel = store.set( 0, 0, 0 );
        if ( getNode1() instanceof DynamicPhysicsNode ) {
            actualBounceVel.subtractLocal( ( (DynamicPhysicsNode) getNode1() )
                    .getLinearVelocity( tmpVelocity ) );
        }
        if ( getNode2() instanceof DynamicPhysicsNode ) {
            actualBounceVel.addLocal( ( (DynamicPhysicsNode) getNode2() )
                    .getLinearVelocity( tmpVelocity ) );
        }
        actualBounceVel.multLocal( getContactNormal( tmpVelocity ) );
        return store;
    }

    protected PendingContact() {
        clear();
    }


}

/*
 * $log$
 */

