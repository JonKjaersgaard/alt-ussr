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
import com.jmex.physics.geometry.PhysicsCapsule;
import com.jmex.physics.impl.joode.PhysicsNodeImpl;
import net.java.dev.joode.Mass;
import net.java.dev.joode.geom.Capsule;
import net.java.dev.joode.geom.Geom;
import net.java.dev.joode.space.Space;

/**
 * @author Irrisor
 */
public class JoodeCapsule extends PhysicsCapsule implements JoodeGeometry {
    private final Capsule geom;

    public Geom getOdeGeom() {
        return geom;
    }

    public JoodeCapsule( PhysicsNode node, Space space ) {
        super( node );
        geom = new Capsule( space, 1, 1 );
        geom.setUserData( this );
    }

    @Override
    public void updateWorldVectors() {
        super.updateWorldVectors();
        //TODO: only if necessary!
        ( (PhysicsNodeImpl) getPhysicsNode() ).updateTransforms( geom );
        final Vector3f worldScale = this.worldScale;
        if ( worldScale.x <= 0 || worldScale.y <= 0 || worldScale.z <= 0 ) {
            // this makes ODE crash to prefer to throw an exception
            throw new IllegalArgumentException( "scale must not have 0 as a component!" );
        }
        //noinspection SuspiciousNameCombination
        worldScale.y = worldScale.x; // yes the actual world scale is changed here
        geom.setRadius( worldScale.x );
        geom.setLength( worldScale.z );
    }

    @Override
    public void setNode( PhysicsNode node ) {
        super.setNode( node );
    }

    public Mass createMass() {
        float density = getMaterial().getDensity();
        return Mass.createCapsule( density, 0 /*TODO: correct? */, geom.getRadius(), geom.getLength() );
    }
}

/*
 * $log$
 */

