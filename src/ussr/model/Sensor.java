/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.model;

import java.util.ArrayList;
import java.util.List;

import ussr.physics.PhysicsEntity;

/**
 * 
 * An abstract sensor for a modular robot: can sense a value
 *  
 * @author Modular Robots @ MMMI
 *
 */
public class Sensor extends Entity {

    /**
     * The physics model for the actuator
     */
    private PhysicsSensor physics;

    /**
     * Construct a new actuator
     * @param actuator the physics model for the actuator
     */
    public Sensor(PhysicsSensor sensor) {
        this.physics = sensor;
        sensor.setModel(this);
    }
    /**
     * Read the value of this sensor
     * @return sensor value
     */
    public float readValue()
    {
    	return physics.readValue();
    }

      /**
     * For debugging
     */
    public String toString() {
        return "Actuator<"+physics+">";
    }
    
    public List<? extends PhysicsEntity> getPhysics() {
        ArrayList<PhysicsEntity> result = new ArrayList<PhysicsEntity>();
        result.add(physics);
        return result;
    }
    
    public String getName() { return physics.getName(); }
    
	public void reset() {
		physics.reset();		
	}

	/**
     * Set the sensitivity of a sensor
     * @arg sensitivity a positive floating point value that indicates the sensitivity
	 */
	public void setSensitivity(float sensitivity) {
	    physics.setSensitivity(sensitivity);
    } 
}
