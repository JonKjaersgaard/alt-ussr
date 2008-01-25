package ussr.model;

import java.util.ArrayList;
import java.util.List;

import ussr.physics.PhysicsEntity;

/**
 * 
 * An abstract actuator for a modular robot: can actuate two module components 
 * relative to each other. Has sensors to sense its state, and embeds its own
 * controller.
 *  
 * @author david
 *
 */
public class Actuator extends Entity {

    /**
     * The physics model for the actuator
     */
    private PhysicsActuator physics;

    /**
     * Construct a new actuator
     * @param actuator the physics model for the actuator
     */
    public Actuator(PhysicsActuator actuator) {
        this.physics = actuator;
        actuator.setModel(this);
    }

    /**
     * Returns true if this actuator is active
     * @return whether the actuator is active (actuating)
     */
    public boolean isActive() {
        return physics.isActive();
    }
    
    /**
     * Activate this actuator - to go for a particular goal value
     * 
     * @return whether the actuator were activated
     */
    public boolean activate(float goalValue) {
        return physics.activate(goalValue);
    }
    
    /**
     * Stop the actuator
     *
     */
    public void disactivate() {
        physics.disactivate();
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

    /**
     * Get the encoder value of this actuator
     * @return the encode value
     */
	public float getEncoderValue() {
		return physics.getEncoderValue();
	}

	public void reset() {
		physics.reset();
	}
}
