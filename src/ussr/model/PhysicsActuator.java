/**
 * 
 */
package ussr.model;

import ussr.physics.PhysicsEntity;

/**
 * @author david
 *
 */
public interface PhysicsActuator extends PhysicsEntity {

	void setModel(Actuator actuator);

	boolean isActive();

	boolean activate(float goalValue);

	void disactivate();

	void reset();

	float getEncoderValue();

}
