/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.description.geometry;
/**
 * A description of a cone geometry for use in building a robot
 * @author Modular Robots @ MMMI
 *
 */
public class ConeShape extends CylinderShape {

	public ConeShape(float radius, float height) {
		super(radius, height);
	}
    public ConeShape(float radius, float height, VectorDescription translation) { 
		super(radius,height,translation);
    }
    public ConeShape(float radius, float height, VectorDescription translation, RotationDescription rotation) { 
		super(radius,height,translation,rotation);
    }
}
