/**
 * 
 */
package ussr.model;

import ussr.physics.PhysicsEntity;

/**
 * @author david
 *
 */
public interface PhysicsSensor extends PhysicsEntity {
	void setModel(Sensor sensor);
	float readValue();
}
