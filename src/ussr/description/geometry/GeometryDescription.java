/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.description.geometry;

import java.awt.Color;


/**
 * Common interface for geometry descriptions (that can be used for building a robot)
 * 
 * @author Modular Robots @ MMMI
 *
 */
public interface GeometryDescription {
    /**
     * Set the color of the geometry
     * @param color the color to assign to the geometry
     */
    public void setColor(Color color);
    /**
     * Get the color of the geometry
     * @return the geometry of the color
     */
    public Color getColor();
    /**
     * Get whether accurate collision detection should be used for this geometry
     * @return whether accurate collision detection is used for 
     */
	public boolean getAccurateCollisionDetection();
	public void setAccurateCollisionDetection(boolean accurateCD);
	public VectorDescription getTranslation();
	public RotationDescription getRotation();
	
	/**
	 * Get a copy of this object (using clone())
	 * @return a copy of this object
	 */
    public GeometryDescription copy();
	
}
