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

package com.jmex.physics.impl.joode.geometry;

import java.util.ArrayList;

import com.jme.scene.Spatial;
import com.jmex.physics.PhysicsCollisionGeometry;
import com.jmex.physics.PhysicsNode;

/**
 * Helper class to provide physics node merging interface
 *
 * @author jspohr
 */
public class JoodePhysicsNodeMerger {
    /**
     * Merges the set of children of a physics node into another's.
     * The other node will have its children removed.
     *
     * @param lhs PhysicsNode which receives all children of the other one.
     * @param rhs PhysicsNode whose children are moved to the other one.
     */
    static public void mergePhysicsNode( PhysicsNode lhs, PhysicsNode rhs ) {
        ArrayList<Spatial> children = new ArrayList<Spatial>( rhs.getChildren() );
        for ( Spatial child : children ) {
            if ( child instanceof PhysicsCollisionGeometry ) {
                PhysicsCollisionGeometry collisionGeometry = (PhysicsCollisionGeometry) child;
                boolean inheritsMaterial = ( collisionGeometry.getMaterial() == rhs.getMaterial() )
                        && ( rhs.getMaterial() != rhs.getSpace().getDefaultMaterial() );
                if ( collisionGeometry instanceof JoodeGeometry ) {
                    ( (JoodeGeometry) collisionGeometry ).setNode( lhs );
                }
                if ( inheritsMaterial ) {
                    collisionGeometry.setMaterial( rhs.getMaterial() );
                }
            }
            lhs.attachChild( child );
        }
    }
}

/*
 * $log$
 */
