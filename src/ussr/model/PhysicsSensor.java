/**
 * 
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
}
