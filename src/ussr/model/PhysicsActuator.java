/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.model;

import ussr.physics.PhysicsEntity;

/**
 * A physics engine representation of an actuator: can actuate two module components 
 * relative to each other. Has sensors to sense its state, and embeds its own
 * controller.
 * 
 * @author Modular Robots @ MMMI
 */
public interface PhysicsActuator extends PhysicsEntity {

    /**
     * Set the abstract actuator representation for this physics entity
     * @param actuator the actuator to associate with this physics entity
     */
    void setModel(Actuator actuator);

    /**
     * Returns true if this actuator is active
     * @return whether the actuator is active (actuating)
     */
	boolean isActive();

    /**
     * Activate this actuator - to go for a particular goal value
     * 
     * @return whether the actuator were activated
     */
	boolean setDesiredPosition(float goalValue);
	
    /**
     * Activate this actuator - to go for a particular goal value
     * 
     * @param goalValue is the goal value to aim for
     * @param direction defines the direction the actuator should take to reach the goal value
     * @return whether the actuator were activated
     */
    boolean setDesiredPosition(float goalValue, Actuator.Direction direction);
    
	/**
     * Activate this actuator - to go for a particular velocity value
     * 
     * @return whether the actuator were activated
     */
	boolean setDesiredVelocity(float velValue);
	/**
     * Set the threshold for the precision of the actuator
     * Note that too low a threshold will case the actuator newer to converge 
     * 
     * @param maxError the error threshold
     */
	void setErrorThreshold(float maxError);
    /**
     * Stop the actuator
     *
     */
	void disactivate();

	/**
	 * Reset the simulated actuator to its initial configuration 
	 */
	void reset();

    /**
     * Get the encoder value of this actuator
     * @return the encode value
     */
	float getEncoderValue();

}
