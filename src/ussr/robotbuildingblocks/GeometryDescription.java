/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.robotbuildingblocks;

import java.awt.Color;

/**
 * Common interface for geometry descriptions
 * 
 * @author ups
 *
 */
public interface GeometryDescription {
    public void setColor(Color color);
    public Color getColor();
	public boolean getAccurateCollisionDetection();
	public void setAccurateCollisionDetection(boolean accurateCD);
	public VectorDescription getTranslation();
	public RotationDescription getRotation();
}
