/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.robotbuildingblocks;

import java.awt.Color;


/**
 * A description of a atron half geometry, currently only includes a radius.
 * TODO: add position and perhaps also other properties
 * 
 * @author ups
 *
 */
public class AtronShape extends Description implements GeometryDescription {
    
    /**
     * The radius of the sphere
     */
    private float radius;
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
     * Create a description of a sphere geometry, passing the radius of the
     * sphere as a parameter (no other parameters supported currently)
     * @param radius the radius of the sphere
     * @param north 
     */
    public AtronShape(float radius, boolean north, VectorDescription translation, RotationDescription rotation) { 
    	this.radius = radius; 
    	setNorth(north); 
    	this.translation = translation;
		this.rotation = rotation;
    }
    
    /**
     * Get the radius of the sphere geometry
     * @return the radius
     */
    public float getRadius() { return radius; }
    
    /**
     * Color of atron
     */
    private Color color;
    public void setColor(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
    public void setNorth(boolean north) {
    	this.north = north;
    }
    public boolean isNorth() {
    	return north;
    }
    public VectorDescription getTranslation() { return translation; }
    public RotationDescription getRotation() { return rotation; }
    
    private boolean accurateCD = false;
    /**
     * get wether or not to use accurate triangle based collision detection
     */
	public boolean getAccurateCollisionDetection() {
		return accurateCD;
	}

	/**
     * set wether or not to use accurate triangle based collision detection
     */
	public void setAccurateCollisionDetection(boolean accurateCD) {
		this.accurateCD = accurateCD;
	}
}
