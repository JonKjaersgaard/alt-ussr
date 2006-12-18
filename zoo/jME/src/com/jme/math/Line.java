/*
 * Copyright (c) 2003-2006 jMonkeyEngine
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * * Redistributions of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in the
 *   documentation and/or other materials provided with the distribution.
 *
 * * Neither the name of 'jMonkeyEngine' nor the names of its contributors 
 *   may be used to endorse or promote products derived from this software 
 *   without specific prior written permission.
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

package com.jme.math;

import java.io.IOException;
import java.io.Serializable;

import com.jme.util.export.InputCapsule;
import com.jme.util.export.JMEExporter;
import com.jme.util.export.JMEImporter;
import com.jme.util.export.OutputCapsule;
import com.jme.util.export.Savable;

/**
 * <code>Line</code> defines a line. Where a line is defined as infinite along
 * two points. The two points of the line are defined as the origin and direction.
 * @author Mark Powell
 * @version $Id: Line.java,v 1.8 2006/06/01 15:05:35 nca Exp $
 */
public class Line implements Serializable, Savable {
    //todo: merge with Ray?
    private static final long serialVersionUID = 1L;

    private Vector3f origin;
    private Vector3f direction;

    /**
     * Constructor instantiates a new <code>Line</code> object. The origin and
     * direction are set to defaults (0,0,0).
     *
     */
    public Line() {
        origin = new Vector3f();
        direction = new Vector3f();
    }

    /**
     * Constructor instantiates a new <code>Line</code> object. The origin
     * and direction are set via the parameters.
     * @param origin the origin of the line.
     * @param direction the direction of the line.
     */
    public Line(Vector3f origin, Vector3f direction) {
        this.origin = origin;
        this.direction = direction;
    }

    /**
     *
     * <code>getOrigin</code> returns the origin of the line.
     * @return the origin of the line.
     */
    public Vector3f getOrigin() {
        return origin;
    }

    /**
     *
     * <code>setOrigin</code> sets the origin of the line.
     * @param origin the origin of the line.
     */
    public void setOrigin(Vector3f origin) {
        this.origin = origin;
    }

    /**
     *
     * <code>getDirection</code> returns the direction of the line.
     * @return the direction of the line.
     */
    public Vector3f getDirection() {
        return direction;
    }

    /**
     *
     * <code>setDirection</code> sets the direction of the line.
     * @param direction the direction of the line.
     */
    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    /**
     *
     * <code>random</code> determines a random point along the line.
     * @return a random point on the line.
     */
    public Vector3f random() {
        Vector3f result = new Vector3f();
        float rand = (float)Math.random();

        result.x = (origin.x * (1 - rand)) + (direction.x * rand);
        result.y = (origin.y * (1 - rand)) + (direction.y * rand);
        result.z = (origin.z * (1 - rand)) + (direction.z * rand);

        return result;
    }

    public void write(JMEExporter e) throws IOException {
        OutputCapsule capsule = e.getCapsule(this);
        capsule.write(origin, "origin", Vector3f.ZERO);
        capsule.write(direction, "direction", Vector3f.ZERO);
    }

    public void read(JMEImporter e) throws IOException {
        InputCapsule capsule = e.getCapsule(this);
        origin = (Vector3f)capsule.readSavable("origin", new Vector3f(Vector3f.ZERO));
        direction = (Vector3f)capsule.readSavable("direction", new Vector3f(Vector3f.ZERO));
    }
    
    public Class getClassTag() {
        return this.getClass();
    }
}
