package ussr.physics.jme;

import ussr.model.Sensor;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;

public class JMETiltSensor implements JMESensor {
	private JMESimulation world;
	private String name;
	private char axes;
	private Sensor model;
	private DynamicPhysicsNode node;
    private RotationDescription rotation;
  
    public JMETiltSensor(JMESimulation world, String baseName, char axes, DynamicPhysicsNode node, RotationDescription rotation) {
        this.world = world;
        this.name = baseName;
        this.axes = axes;
        this.node = node;
        this.rotation = rotation;
    }

    public JMETiltSensor(JMESimulation world, String baseName, char axes, DynamicPhysicsNode node) {
        this(world,baseName,axes,node,new RotationDescription(0,0,0));
    }

    public float readValue() {
        Quaternion q = rotation.getRotation();
        float angles[] = new float[3]; q.toAngles(angles);
        Vector3f allAxes[] = new Vector3f[3];
        node.getLocalRotation().toAxes(allAxes);
        Vector3f vector; int index; float offset;
        switch(axes) {
        case 'x': index = 0; break;
        case 'y': index = 1; break;
        case 'z': index = 2; break;
        default: throw new Error("Axis wrong in tilt sensor "+axes); 
        }
        vector = allAxes[index]; offset = angles[index];
        Vector3f g = new Vector3f(0,-1,0);
        // Adding offset is probably not the way to do it, does not appear to work
        return vector.angleBetween(g)+offset;
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
		return rotation;
	}
}
