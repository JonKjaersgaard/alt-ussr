/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics.jme.connectors;

import ussr.physics.PhysicsConnector;
import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;

/**
 * Interface for connectors for the JME based simulation
 * 
 * @author Modular Robots @ MMMI
 *
 */
public interface JMEConnector extends PhysicsConnector {

    public Vector3f getPos();
    
	public Vector3f getPosRel();
    
    public DynamicPhysicsNode getNode();
    
    public JMEConnectorGeometry getConnectorGeometry();

	public JMEConnectorAligner getConnectorAligner();
    
}