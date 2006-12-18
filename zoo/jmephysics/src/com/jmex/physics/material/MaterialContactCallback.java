/*Copyright*/
package com.jmex.physics.material;

import com.jme.math.Quaternion;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jmex.physics.PhysicsCollisionGeometry;
import com.jmex.physics.contact.ContactCallback;
import com.jmex.physics.contact.ContactHandlingDetails;
import com.jmex.physics.contact.PendingContact;

/**
 * A helper class used internally to apply info from materials to contacts.
 *
 * @author Irrisor
 */
public class MaterialContactCallback implements ContactCallback {
    private MaterialContactCallback() {
    }

    private static final MaterialContactCallback instance = new MaterialContactCallback();

    public static MaterialContactCallback get() {
        return instance;
    }

    private final Vector2f oldMotion = new Vector2f();
    private final Vector3f materialMotion = new Vector3f();
    private final Vector3f normal = new Vector3f();
    private final Vector3f dir1 = new Vector3f();
    private final Vector3f dir2 = new Vector3f();
    private final Quaternion frictionRotation = new Quaternion();

    public boolean adjustContact( PendingContact contact ) {
        Material m1 = contact.getGeometry1().getMaterial();
        Material m2 = contact.getGeometry2().getMaterial();
        ContactHandlingDetails details = m1.getContactHandlingDetails( m2 );
        if ( details != null ) {
            contact.copy( details );
        }
        applyMotion( m1, contact, contact.getGeometry1() );
        applyMotion( m2, contact, contact.getGeometry2() );
        return details != null;
    }

    private void applyMotion( Material material, PendingContact contact, PhysicsCollisionGeometry geometry ) {
        material.getSurfaceMotion( materialMotion );
        if ( materialMotion.x != 0 || materialMotion.y != 0 || materialMotion.z != 0 ) {
            contact.getContactNormal( normal );
            contact.getDefaultFrictionDirections( dir1, dir2 );
            contact.setFrictionDirection( dir1 );
            frictionRotation.fromAxes( normal, dir1, dir2 );
            frictionRotation.inverseLocal(); // this is expensive :(

            geometry.getWorldRotation().multLocal( materialMotion );
            frictionRotation.multLocal( materialMotion );
            contact.getSurfaceMotion( oldMotion );
            oldMotion.x += materialMotion.y;
            oldMotion.y += materialMotion.z;
            contact.setSurfaceMotion( oldMotion );
        }
    }
}

/*
 * $log$
 */

