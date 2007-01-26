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

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import net.java.dev.joode.util.Vector3;


public class VecMathConvert {
    public static Vector3f convert( Vector3 source, Vector3f store ) {
        if ( store == null ) {
            store = new Vector3f();
        }
        store.x = source.getX();
        store.y = source.getY();
        store.z = source.getZ();
        return store;
    }

    public static Vector3 convert( Vector3f source, Vector3 store ) {
        if ( store == null ) {
            store = new Vector3();
        }
        store.setX( source.x );
        store.setY( source.y );
        store.setZ( source.z );
        return store;
    }

    public static net.java.dev.joode.util.Quaternion convert( Quaternion source, net.java.dev.joode.util.Quaternion store ) {
        if ( store == null ) {
            store = new net.java.dev.joode.util.Quaternion();
        }
        store.set( source.w, source.x, source.y, source.z );
        return store;
    }

    public static Quaternion convert( net.java.dev.joode.util.Quaternion source, Quaternion store ) {
        if ( store == null ) {
            store = new Quaternion();
        }
        store.w = source.get( 0 );
        store.x = source.get( 1 );
        store.y = source.get( 2 );
        store.z = source.get( 3 );
        return store;
    }
}

/*
 * $Log: VecMathConvert.java,v $
 * Revision 1.1  2006/12/23 22:07:04  irrisor
 * Ray added, Picking interface (natives pending), JOODE implementation added, license header added
 *
 */

