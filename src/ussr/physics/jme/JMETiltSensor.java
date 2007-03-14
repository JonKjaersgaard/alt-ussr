package ussr.physics.jme;

import ussr.model.Sensor;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;

import com.jmex.physics.DynamicPhysicsNode;

public class JMETiltSensor implements JMESensor {
	private JMESimulation world;
	private String name;
	private char axes;
	private Sensor model;
	private DynamicPhysicsNode node;
  
    public JMETiltSensor(JMESimulation world, String baseName, char axes, DynamicPhysicsNode node) {
        this.world = world;
        this.name = baseName;
        this.axes = axes;
        this.node = node;
    }

    public float readValue() {
		if(axes=='x') return node.getLocalRotation().x;
		if(axes=='y') return node.getLocalRotation().y;
		if(axes=='z') return node.getLocalRotation().z;
		throw new RuntimeException("Axis wrong in tilt sensor "+axes);
	}

	public void setModel(Sensor model) {
		this.model = model;
	}
	public String getName() {
		return name;
	}
    public String toString() {
        return "sensor_"+name+"<"+axes+">";
    }
	public VectorDescription getPosition() {
		// TODO Auto-generated method stub
		return null;
	}
	public RotationDescription getRotation() {
		return null;//new RotationDescription(node.getWorldRotation());
	}
}
