/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.description.geometry;

import java.awt.Color;



/**
 * A description of an ATRON half geometry, currently only includes a radius.
 * 
 * TODO: Generalize into a CAD model?
 * 
 * @author Modualar Robots @ MMMI
 *
 */
public class AtronShape extends GeometryDescriptionImpl {
    
    /**
     * The radius of the sphere
     */
    private float radius;
    
    /**
     * Flag indicating what hemisphere the shape is modelling
     */
    private boolean north;
    
    /** 
     * local translation of the atron half 
     */    
     private VectorDescription translation;
     
     /** 
      * local rotation of the atron half 
      */    
     private RotationDescription rotation;
    
     /**
      * Color of atron
      */
     private Color color;

     /**
      * Accurate collision detection or not
      */
     private boolean accurateCD = false;

     /**
     * Create a description of an ATRON hemisphere
     * @param radius the radius of the sphere
     * @param north true if this is the northern hemisphere
     * @param translation the position
     * @param rotation the rotation
     */
    public AtronShape(float radius, boolean north, VectorDescription translation, RotationDescription rotation) { 
    	this.radius = radius; 
    	setNorth(north); 
    	this.translation = translation;
		this.rotation = rotation;
    }
    
    /**
     * Get the radius of the geometry
     * @return the radius
     */
    public float getRadius() { return radius; }
    
    public void setColor(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
    
    /**
     * Set what hemisphere this shape is modelling
     * @param north true if northern hemisphere, false if southern
     */
    public void setNorth(boolean north) {
    	this.north = north;
    }
    
    /**
     * Get what hemispshere this shape is modelling
     * @return true if northen hemisphere, false if southern
     */
    public boolean isNorth() {
    	return north;
    }
    
    public VectorDescription getTranslation() { return translation; }
    public RotationDescription getRotation() { return rotation; }
    
    /**
     * Get whether or not to use accurate triangle based collision detection
     */
	public boolean getAccurateCollisionDetection() {
		return accurateCD;
	}

	/**
     * Set whether or not to use accurate triangle based collision detection
     */
	public void setAccurateCollisionDetection(boolean accurateCD) {
		this.accurateCD = accurateCD;
	}
}
