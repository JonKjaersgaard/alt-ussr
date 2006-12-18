/*Copyright*/
package com.jmex.physics.contact;

/**
 * Callback for querying Contact details (e.g. friction).
 *
 * @author Irrisor
 */
public interface ContactCallback {
    /**
     * Called when a contact occurs and contact details are needed.
     * Don't execute any logic or scenegraph modifying code inside this method. Use the results of the
     * {@link com.jmex.physics.PhysicsSpace#update} method instead.
     *
     * @param contact occured contact
     * @return true if the contact details were set, false if the next callback should be invoked
     */
    boolean adjustContact( PendingContact contact );
}

/*
 * $Log: ContactCallback.java,v $
 * Revision 1.3  2006/02/19 10:45:39  irrisor
 * materials added
 *
 * Revision 1.2  2006/02/14 11:29:10  irrisor
 * no more activation and deactivation but attach/detach and add/remove
 *
 * Revision 1.1  2006/02/12 18:30:44  irrisor
 * inital version - basic new API
 *
 */
