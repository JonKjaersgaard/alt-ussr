/**
 * 
 */
package ussr.physics.jme.sensors;

import ussr.description.RotationDescription;
import ussr.description.VectorDescription;
import ussr.model.Entity;
import ussr.model.PhysicsSensor;
import ussr.model.Sensor;
import ussr.physics.jme.JMESimulation;

import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.PhysicsNode;

/**
 * Proximity sensor, works by measuring physical distance to obstacle objects (not
 * other modules).
 * 
 * @author Modular Robots @ MMMI
 * 
 */
public class JMEProximitySensor implements PhysicsSensor {

    private JMESimulation simulation;
    private String name;
    private Sensor model;
    private DynamicPhysicsNode node;
    private float range;
    
    public JMEProximitySensor(JMESimulation simulation, String name, Entity hardware, float range, DynamicPhysicsNode node) {
        this.simulation = simulation;
        this.name = name;
        this.node = node; //((JMEPhysicsEntity)hardware.getPhysics().get(0)).getNode();
        this.range = range;
    }

    /* (non-Javadoc)
     * @see ussr.model.PhysicsSensor#getName()
     */
    public String getName() {
        return name;
    }

    /* (non-Javadoc)
     * @see ussr.model.PhysicsSensor#readValue()
     */
    public float readValue() {
        for(PhysicsNode obstacle: simulation.getObstacles()) {
            float distance = node.getWorldTranslation().distance(obstacle.getWorldTranslation());
            if(distance<range) return 1-distance/range;
        }
        return 0;
    }

    /* (non-Javadoc)
     * @see ussr.model.PhysicsSensor#setModel(ussr.model.Sensor)
     */
    public void setModel(Sensor sensor) {
        this.model = sensor;
    }

    /* (non-Javadoc)
     * @see ussr.physics.PhysicsEntity#getPosition()
     */
    public VectorDescription getPosition() {
        throw new Error("Method not implemented");
    }

    /* (non-Javadoc)
     * @see ussr.physics.PhysicsEntity#getRotation()
     */
    public RotationDescription getRotation() {
        throw new Error("Method not implemented");
    }

	public void reset() {
	}

	public void setPosition(VectorDescription position) {
		throw new Error("Method not implemented");
		
	}

	public void setRotation(RotationDescription rotation) {
		throw new Error("Method not implemented");		
	}
	 public void clearDynamics() {
		node.clearDynamics();
	}
}
