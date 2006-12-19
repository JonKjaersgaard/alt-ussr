/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.description;

/**
 * A description of a sphere geometry, currently only includes a radius.
 * TODO: add position and perhaps also other properties
 * 
 * @author ups
 *
 */
public class SphereShape extends Description implements GeometryDescription {
    
    /**
     * The radius of the sphere
     */
    private float radius;

    /** 
    * local translation of the sphere 
    */    
    private VectorDescription translation;
    
    /**
     * Create a description of a sphere geometry, passing the radius of the
     * sphere as a parameter (no other parameters supported currently)
     * @param radius the radius of the sphere
     */

    public SphereShape(float radius, VectorDescription translation) { 
		this.radius = radius; 
		this.translation = translation;
}

    
    public SphereShape(float radius) { 
    	this.radius = radius; 
    	this.translation = new VectorDescription(0f,0f,0f);
    }

    public SphereShape( VectorDescription translation ) { 
    	this.translation = translation;
    	this.radius = 1;
    }

    
    /**
     * Get the radius of the sphere geometry
     * @return the radius
     */
    public float getRadius() { return radius; }
    public VectorDescription getTranslation() { return translation; }
}
