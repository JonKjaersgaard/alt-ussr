/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.robotbuildingblocks;

import java.awt.Color;

/**
 * A description of a box geometry for use in building a robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class BoxShape extends Description implements GeometryDescription {
    /** 
    * local translation of the box
    */    
    private VectorDescription translation;
    /**
     * local extension of the box
     */
    private VectorDescription extend;
    
    /** 
     * local rotation of the box 
     */    
    private RotationDescription rotation;
    /**
     * Color of the box
     */
    private Color color;
    /**
     * true if accurate collision should be used
     */
    private boolean accurateCD = false;
	
    
    /**
     * Create a description of a box geometry
     * @param extend the (x,y,z) extensions of the box
     * @param translation the local translation of the box
     * @param rotation the local rotation of the box
     */
    public BoxShape(VectorDescription extend, VectorDescription translation, RotationDescription rotation) {
    	this.extend = extend;
    	this.translation = translation;
		this.rotation = rotation;
	}
    /**
     * Create a description of a box geometry at default position (0,0,0) and rotation
     * @param sideLength the length of the box
     */

	public BoxShape(float sideLength) {
		this.extend = new VectorDescription(sideLength,sideLength,sideLength);
    	this.translation = new VectorDescription(0,0,0);
		this.rotation = new RotationDescription(0,0,0);
	}

	/**
     * Get the extend of the box geometry
     * @return the extend in (x,y,z)
     */
    public VectorDescription getExtend() {return extend;}
    /**
     * Get the local translation of the box geometry
     * @return the local translation
     */
    public VectorDescription getTranslation() { return translation; }
    /**
     * The the local rotation of the box geometry
     * @return the local rotation
     */
    public RotationDescription getRotation() { return rotation; }

    public void setColor(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
    
    /**
     * get whether or not to use accurate triangle based collision detection
     */
	public boolean getAccurateCollisionDetection() {
		return accurateCD;
	}
	/**
     * set whether or not to use accurate triangle based collision detection
     */
	public void setAccurateCollisionDetection(boolean accurateCD) {
		this.accurateCD = accurateCD;
	}
}
