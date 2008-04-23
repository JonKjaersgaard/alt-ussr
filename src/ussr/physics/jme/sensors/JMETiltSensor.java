/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics.jme.sensors;

import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.model.Sensor;
import ussr.physics.jme.JMESimulation;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;

/**
 * Preliminary tilt sensor, only superficially tested, but is capable
 * of measuring some forms of tilting.
 * 
 * @author Modular Robots @ MMMI
 */
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
        Vector3f allAxes[] = new Vector3f[3];
        Quaternion qq = new Quaternion();
        qq.fromRotationMatrix(node.getLocalRotation().toRotationMatrix().mult(rotation.getRotation().toRotationMatrix()));
        qq.toAxes(allAxes);
        Vector3f vector; int index; 
        switch(axes) {
        case 'x': index = 0; break;
        case 'y': index = 1; break;
        case 'z': index = 2; break;
        default: throw new Error("Axis wrong in tilt sensor "+axes); 
        }
        vector = allAxes[index];
        Vector3f g = new Vector3f(0,-1,0);
        return vector.angleBetween(g);
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

	public void reset() {
	}
	public void setPosition(VectorDescription position) {
		throw new Error("Method not implemented");
	}

	public void setRotation(RotationDescription rotation) {
		this.rotation = rotation;		
	}
	public void clearDynamics() {
		node.clearDynamics();
	}
	public void addExternalForce(float forceX, float forceY, float forceZ) {
		node.addForce(new Vector3f(forceX,forceY,forceZ));		
	}
	public void moveTo(VectorDescription position, RotationDescription rotation) {
		throw new Error("Method not implemented");
	}
	public void setSensitivity(float sensitivity) {
        ;
    }
}
