/**
 * 
 */
package ussr.physics.jme;

import ussr.model.Entity;
import ussr.model.PhysicsSensor;
import ussr.model.Sensor;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;

import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.PhysicsNode;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class JMEProximitySensor implements PhysicsSensor {

    private JMESimulation simulation;
    private String name;
    private Sensor model;
    private DynamicPhysicsNode node;
    private float range;
    
    public JMEProximitySensor(JMESimulation simulation, String name, Entity hardware, float range) {
        this.simulation = simulation;
        this.name = name;
        node = ((JMEPhysicsEntity)hardware.getPhysics().get(0)).getNode();
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
        // TODO Auto-generated method stub
        // return null;
        throw new Error("Method not implemented");
    }

    /* (non-Javadoc)
     * @see ussr.physics.PhysicsEntity#getRotation()
     */
    public RotationDescription getRotation() {
        // TODO Auto-generated method stub
        // return null;
        throw new Error("Method not implemented");
    }

}
