/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics.jme.connectors;

import ussr.description.robot.ConnectorDescription;
import ussr.description.robot.RobotDescription;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;

/**
 * Rigid mechanical connector
 * 
 * @author Modular Robots @ MMMI
 */
public class JMERigidMechanicalConnector extends JMEMechanicalConnector {

	public JMERigidMechanicalConnector(Vector3f position, DynamicPhysicsNode moduleNode, String baseName, JMESimulation world, JMEModuleComponent component, ConnectorDescription description) {
		super(position, moduleNode, baseName, world, component, description);
	}
	
	protected synchronized void addAxis(Joint connection) {
		//joint is rigid do not add axis
	}
	public boolean canConnectTo(JMEConnector connector) {
		if(!(connector instanceof JMERigidMechanicalConnector)) return false;
		JMERigidMechanicalConnector c = (JMERigidMechanicalConnector)connector;
		if(connectorType==FEMALE||c.connectorType==MALE) return false;
		if(getPos().distance(c.getPos())>maxConnectDistance) return false;
		return true;
	}
	public boolean canConnectNow(JMEConnector connector) {
		if(!canConnectTo(connector)) return false;
		if(!connectorAligner.isAligned(connector)) return false;
		return true;
	}
	public boolean canDisconnectFrom(JMEConnector connector) {
		if(connectorType==MALE) {
			return true; 
		}
    	return false;
    }
	protected void align(JMEConnector connector) { 
	/*	JMEMechanicalConnector c = (JMEMechanicalConnector)connector;
		Vector3f direction = getPos().subtract(c.getPos()).normalize();
		float forceSize = 0.1f;//getPos().distance(c.getPos());
		getNode().addForce(direction.mult(-forceSize), getLocalPos());
		c.getNode().addForce(direction.mult(forceSize), c.getLocalPos());
		System.out.println("Distance = "+getPos().distance(c.getPos()));//+getPos()+" "+c.getPos());
		
	*/	
	}
	
}
