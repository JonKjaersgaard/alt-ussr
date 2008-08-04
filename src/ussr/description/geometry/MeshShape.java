/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.description.geometry;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;



/**
 * A description of an ATRON half geometry, currently only includes a radius.
 * 
 * TODO: Generalize into a CAD model?
 * 
 * @author Modualar Robots @ MMMI
 *
 */
public class MeshShape extends GeometryDescriptionImpl {
    
    /**
     * What the mesh shape is representing
     */
    private String name;
    
    /**
     * The radius of the sphere
     */
    private float radius;
    
    /**
     * Flags describing the shape
     */
    private Map<String,Object> properties = new HashMap<String,Object>();
    
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
    public MeshShape(String name, float radius, VectorDescription translation, RotationDescription rotation) {
        this.name = name;
    	this.radius = radius; 
    	this.translation = translation;
		this.rotation = rotation;
    }

    public void setProperty(String name, Object value) {
        this.properties.put(name, value);
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
    * return property
     */
    public Object getProperty(String name) {
    	return properties.get(name);
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

    public String getName() {
        return name;
    }
}
