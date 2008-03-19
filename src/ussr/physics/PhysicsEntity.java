/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics;

import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;

/**
 * Abstract interface for any kind of physics entity, allowing physicsal attributes
 * of the entity to be determined
 * 
 * @author Modular Robots @ MMMI
 */
public interface PhysicsEntity {
    /**
     * Get the global rotation of the entity
     * @return the global rotation
     */
    public RotationDescription getRotation();
    
    /**
     * Get the global position of the entity
     * @return the global position
     */
    public VectorDescription getPosition();
    
    /**
     * Reset the state of the physics entity
     */
    public void reset();
	public void setPosition(VectorDescription position);
	public void setRotation(RotationDescription rotation);
	public void clearDynamics();
	public void addExternalForce(float forceX, float forceY, float forceZ);
}
