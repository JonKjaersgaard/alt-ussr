package ussr.physics.jme;

import ussr.model.Connector;
import ussr.model.Module;
import ussr.robotbuildingblocks.RobotDescription;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;

public class JMERigidMechanicalConnector extends JMEMechanicalConnector {

	public JMERigidMechanicalConnector(Vector3f position, DynamicPhysicsNode moduleNode, String baseName, JMESimulation world, JMEModuleComponent component, RobotDescription selfDesc) {
		super(position, moduleNode, baseName, world, component, selfDesc);
	}
	protected synchronized void addAxis(Joint connection) {
		//joint is rigid do not add axis
	}

	
}
