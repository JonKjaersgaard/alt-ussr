/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
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
