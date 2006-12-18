/*
 * Open Dynamics Engine for Java (odejava) Copyright (c) 2004, Jani Laakso, All
 * rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials
 * provided with the distribution. Neither the name of the odejava nor the
 * names of its contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.odejava.collision;

import org.odejava.Space;

/*
 * Needs testing, setting nearcallback does not work for some reason yet,
 * odejava.cpp at function setJavaNearCallback needs a fix..
 * 
 * Created 11.02.2004 (dd.mm.yyyy)
 * 
 * @author Jani Laakso E-mail: jani.laakso@itmill.com
 * 
 * see http://odejava.dev.java.net
 *  
 */

public abstract class PureJavaCollision extends Collision {

    // Set pure Java callback method that handles all nearCallback
    // tasks on the Java side.
    public native void setJavaCallback( Object obj, String methodName );
    //public native void setTriMeshCallback(String methodName);
    //public native void setRayCallback(String methodName);

    public native void javaSpaceCollide( long spaceID_CPtr );

    // Collide space's objects with javaNearCallback function
    public native void javaSpaceCollide2(
            int geomSpaceAddr1,
            int geomSpaceAddr2 );

    // Attachs single contact joint into contact jointgroup
    //public native void jointAttach(long jointId, int body1Addr, int
    // body2Addr);

    /**
     * Collide uses ODE's spaceCollide. NearCallbacks are catched on the Java
     * side. Contacts need to be added into contact jointgroup manually.
     *
     * @param space
     */
    public void collide( Space space ) {
        // Empty contact joint group
        emptyContactGroup();
        // Collide objects, generates all contacts
        javaSpaceCollide( space.getId().getSwigCPtr() );
    }

    /**
     * JavaCollide2 uses ODE's spaceCollide2. Arguments can be spaces or geoms.
     * NearCallbacks are catched on the Java side. Contacts need to be added
     * into contact jointgroup manually.
     *
     * @param o1
     * @param o2
     */
    public void collide2( int o1, int o2 ) {
        // Empty contact joint group
        emptyContactGroup();
        // Collide objects, generates all contacts
        javaSpaceCollide2( o1, o2 );
    }

    public void setCallbackMethod( Object obj, String method ) {
        // Set method that receives callbacks from ODE
        setJavaCallback( obj, "nearCallback" );
    }

    // User needs to implement own nearCallback method
    public abstract void nearCallback( int o1, int o2 );

}
