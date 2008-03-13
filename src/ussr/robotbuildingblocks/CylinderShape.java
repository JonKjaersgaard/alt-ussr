/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.robotbuildingblocks;

import java.awt.Color;

/**
 * A description of a cylinder geometry for use in building a robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class CylinderShape extends GeometryDescriptionImpl {
    
    /**
     * The radius of the cylinder
     */
    private float radius;
    /**
     * The height of the cylinder
     */
    private float height;

    /** 
    * local translation of the cylinder 
    */    
    private VectorDescription translation;
    
    /** 
     * local rotation of the cylinder 
     */    
    private RotationDescription rotation;
    /**
     * Color of the cylinder
     */
    private Color color;
	
    
    /**
     * Create a description of a cylinder geometry
     * @param radius the radius of the cylinder
     * @param height the height of the cylinder
     * @param translation the local translation of the cylinder 
     */

    public CylinderShape(float radius, float height, VectorDescription translation) { 
		this.radius = radius;
		this.height = height; 
		this.translation = translation;
		this.rotation = new RotationDescription(0f,0f,0f);
    }

    
    public CylinderShape(float radius, float height) {
    	this.radius = radius;
    	this.height = height;
    	this.translation = new VectorDescription(0f,0f,0f);
    	this.rotation = new RotationDescription(0f,0f,0f);
    }

    public CylinderShape( VectorDescription translation ) { 
    	this.translation = translation;
    	this.radius = 1;
    	this.height = 1;
    	this.rotation = new RotationDescription(0f,0f,0f);
    }

    
    public CylinderShape(float radius, float height, VectorDescription translation, RotationDescription rotation) {
    	this.radius = radius;
		this.height = height; 
		this.translation = translation;
		this.rotation = rotation;
	}


	/**
     * Get the radius of the sphere geometry
     * @return the radius
     */
    public float getRadius() { return radius; }
    public float getHeight() { return height; }
    public VectorDescription getTranslation() { return translation; }
    public RotationDescription getRotation() { return rotation; }


    public void setColor(Color color) {
        this.color = color;
    }
    
    public Color getColor() {
        return color;
    }
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
