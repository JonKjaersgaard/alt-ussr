/**
 * 
 */
package ussr.description.geometry;

import ussr.description.Description;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
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
