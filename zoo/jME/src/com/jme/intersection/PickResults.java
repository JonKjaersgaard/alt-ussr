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

package com.jme.intersection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.jme.math.Ray;
import com.jme.scene.batch.GeomBatch;

/**
 * <code>PickResults</code> contains information resulting from a pick test.
 * The results will contain a list of every node that was "struck" during a
 * pick test.
 *
 * @author Mark Powell
 * @version $Id: PickResults.java,v 1.12 2006/06/21 20:33:02 nca Exp $
 */
public abstract class PickResults {

    private ArrayList<PickData> nodeList;
    private boolean checkDistance;
    private DistanceComparator distanceCompare;

    /**
     * Constructor instantiates a new <code>PickResults</code> object.
     */
    public PickResults() {
        nodeList = new ArrayList<PickData>();
    }

    /**
     * remember modification of the list to allow sorting after all picks have been added - not each time.
     */
    private boolean modified = false;

    /**
     * <code>addGeometry</code> places a new <code>Geometry</code> spatial into the
     * results list.
     *
     * @param node the geometry to be placed in the results list.
     */
    public void addPickData(PickData data) {
        nodeList.add(data);
        modified = true;
    }

    /**
     * <code>getNumber</code> retrieves the number of geometries that have been
     * placed in the results.
     *
     * @return the number of Geometry objects in the list.
     */
    public int getNumber() {
        return nodeList.size();
    }

    /**
     * <code>getGeometry</code> retrieves a Geometry from a specific index.
     *
     * @param i the index requested.
     * @return the Geometry at the specified index.
     */
    public PickData getPickData(int i) {
        if ( modified ) {
            if(checkDistance) {
                Collections.sort(nodeList, distanceCompare);
            }
            modified = false;
        }
        return nodeList.get(i);
    }

    /**
     * <code>clear</code> clears the list of all Geometry objects.
     */
    public void clear() {
        nodeList.clear();
    }
    
    public abstract void addPick(Ray ray, GeomBatch s);
	
	public abstract void processPick();

    public boolean willCheckDistance() {
        return checkDistance;
    }

    public void setCheckDistance(boolean checkDistance) {
        this.checkDistance = checkDistance;
        if(checkDistance) {
            distanceCompare = new DistanceComparator();
        }
    }
    
    private class DistanceComparator implements Comparator<PickData> {

        public int compare(PickData o1, PickData o2) {
            if (o1.getDistance() <= o2.getDistance())
                return -1;
            
            return 1;
        }
    }
}
