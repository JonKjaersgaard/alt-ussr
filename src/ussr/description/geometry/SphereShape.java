/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.description.geometry;

import java.awt.Color;


/**
 * A description of a sphere geometry, for building a robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class SphereShape extends GeometryDescriptionImpl {
    
    /**
     * The radius of the sphere
     */
    private float radius;

    /** 
    * local translation of the sphere 
    */    
    private VectorDescription translation;
    
    /** 
     * local rotation of the sphere 
     */    
    private RotationDescription rotation;
    
    /**
     * Color of the sphere
     */
    private Color color;
    
    private boolean accurateCD = false;

    /**
     * Create a description of a sphere geometry, passing the radius of the
     * sphere as a parameter (no other parameters supported currently)
     * @param radius the radius of the sphere
     */

    public SphereShape(float radius, VectorDescription translation) { 
		this.radius = radius; 
		this.translation = translation;
		this.rotation = new RotationDescription(0f,0f,0f);
    }
    public SphereShape(float radius, VectorDescription translation, RotationDescription rotation) { 
		this.radius = radius; 
		this.translation = translation;
		this.rotation = rotation;
    }
    
    public SphereShape(float radius) { 
    	this.radius = radius; 
    	this.translation = new VectorDescription(0f,0f,0f);
    	this.rotation = new RotationDescription(0f,0f,0f);
    }

    public SphereShape( VectorDescription translation ) { 
    	this.translation = translation;
    	this.radius = 1;
    	this.rotation = new RotationDescription(0f,0f,0f);
    }

    
    /**
     * Get the radius of the sphere geometry
     * @return the radius
     */
    public float getRadius() { return radius; }
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
