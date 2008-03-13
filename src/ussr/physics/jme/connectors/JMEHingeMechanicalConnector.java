package ussr.physics.jme.connectors;

import ussr.description.ConnectorDescription;
import ussr.description.RobotDescription;
import ussr.description.VectorDescription;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.RotationalJointAxis;

public class JMEHingeMechanicalConnector extends JMEMechanicalConnector {
	private RotationalJointAxis axis;
	private float lowerLimit = Float.NEGATIVE_INFINITY;
	private float upperLimit = Float.POSITIVE_INFINITY;
	private float alignmentForce = 0.1f;
	
	public JMEHingeMechanicalConnector(Vector3f position, DynamicPhysicsNode moduleNode, String baseName, JMESimulation world, JMEModuleComponent component, ConnectorDescription description) {
		super(position, moduleNode, baseName, world, component, description);
	}
	protected synchronized void addAxis(Joint connection) {
		axis = connection.createRotationalAxis(); 
		axis.setDirection(new Vector3f(0,1,0));
		axis.setPositionMinimum(lowerLimit);
		axis.setPositionMaximum(upperLimit); 
	}
	public boolean canConnectTo(JMEConnector connector) {
		if(getPos().distance(connector.getPos())>maxConnectDistance)
			return false;
		return true;
	}
	public boolean canConnectNow(JMEConnector connector) {
		if(!canConnectTo(connector)) return false;
		if(!connectorAligner.isAligned(connector)) return false;
		return true;
	}
	public boolean canDisconnectFrom(JMEConnector connector) {
    	return true;
	}
}
