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

import com.jme.math.Vector3f;
import com.jmex.physics.PhysicsNode;
import com.jmex.physics.geometry.PhysicsRay;
import net.java.dev.joode.Mass;
import net.java.dev.joode.geom.Geom;
import net.java.dev.joode.geom.Ray;
import net.java.dev.joode.space.Space;


/**
 * @author Irrisor
 */
public class JoodeRay extends PhysicsRay implements JoodeGeometry {

    private Ray geom;

    public Geom getOdeGeom() {
        return geom;
    }

    public JoodeRay( PhysicsNode node, Space space ) {
        super( node );
        geom = new Ray( space, new Vector3f( 1, 1, 1 ).length() );
        geom.setUserData( this );
    }

    protected void activate() {
    }

    private final Vector3f direction = new Vector3f();

    @Override
    public void updateWorldVectors() {
        super.updateWorldVectors();
        direction.set( 1, 1, 1 );
        getWorldRotation().mult( direction.multLocal( getWorldScale() ), direction );
        float length = direction.length();
        geom.setLength( length );
        direction.divideLocal( length );
        Vector3f pos = getWorldTranslation();
        geom.setPosition( pos.x, pos.y, pos.z );
        throw new UnsupportedOperationException( "Ray not implemented properly in joode?" );
//        TODO: geom.setDirection( direction.x, direction.y, direction.z );
    }

    @Override
    public void setNode( PhysicsNode node ) {
        super.setNode( node );
    }

    public Mass createMass() {
        return null;
    }
}

/*
 * $Log: JoodeRay.java,v $
 * Revision 1.1  2006/12/23 22:07:05  irrisor
 * Ray added, Picking interface (natives pending), JOODE implementation added, license header added
 *
 * Revision 1.2  2006/12/15 16:22:29  irrisor
 * rays don't generate contact points, max contact increased in windows-lib (linux and macos rebuild pending to allow for correct TriMeshTest)
 *
 * Revision 1.1  2006/12/15 15:06:50  irrisor
 * Ray added
 *
 */

