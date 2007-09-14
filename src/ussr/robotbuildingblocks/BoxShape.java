/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.robotbuildingblocks;

import java.awt.Color;

/**
 * A description of a cylinder geometry, currently only includes a radius.
 * TODO: add position and perhaps also other properties
 * 
 * @author david
 *
 */
public class BoxShape extends Description implements GeometryDescription {
    /** 
    * local translation of the cylinder 
    */    
    private VectorDescription translation;
    
    private VectorDescription extend;
    
    /** 
     * local rotation of the cylinder 
     */    
    private RotationDescription rotation;
    /**
     * Color of the sphere
     */
    private Color color;
    private boolean accurateCD = false;
	
    
    /**
     * Create a description of a box geometry
     * @param extend the (x,y,z) extensions of the box
     * 
     */
    public BoxShape(VectorDescription extend, VectorDescription translation, RotationDescription rotation) {
    	this.extend = extend;
    	this.translation = translation;
		this.rotation = rotation;
	}

	/**
     * Get the extend of the box geometry
     * @return the extend in (x,y,z)
     */
    public VectorDescription getExtend() {return extend;}
    public VectorDescription getTranslation() { return translation; }
    public RotationDescription getRotation() { return rotation; }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
    
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
