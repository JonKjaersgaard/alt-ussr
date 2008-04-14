/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.model;

import ussr.physics.PhysicsEntity;

/**
 * A physics engine representation of a sensor: can read the value of the sensor and
 * allows the corresponding model to be set.
 * 
 * @author Modular Robots @ MMMI
 *
 */
public interface PhysicsSensor extends PhysicsEntity {
    
    /**
     * Set the abstract sensor representation for this physics entity
     * @param sensor the sensor to associate with this entity
     */
	void setModel(Sensor sensor);
	
	/**
     * Read the value of this sensor
     * @return sensor value
     */
 	float readValue();
 	
 	/**
 	 * Return the name of the sensor (debugging purposes)
 	 * @return the name of the sensor
 	 */
    String getName();

    /**
     * Set the sensitivity of a sensor
     * @arg sensitivity a positive floating point value that indicates the sensitivity
     */
    void setSensitivity(float sensitivity);
}
