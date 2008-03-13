/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.description.geometry;

import ussr.description.Description;

/**
 * An implementation of the <tt>GeometryDescription</tt> interface that provides the 
 * {@link ussr.description.geometry.GeometryDescription#copy() copy} operation. 
 * 
 * @author ups
 */
public abstract class GeometryDescriptionImpl extends Description implements GeometryDescription, Cloneable {
    
    public GeometryDescription copy() {
        try {
            return (GeometryDescription)this.clone();
        } catch (CloneNotSupportedException e) {
            throw new Error("Internal error: should not happen");
        }
    }

}
