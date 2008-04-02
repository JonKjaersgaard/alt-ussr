/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics.jme.connectors;

import ussr.description.robot.ConnectorDescription;
import ussr.model.Connector;
import ussr.physics.ConnectorBehaviorHandler;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.RotationalJointAxis;

/**
 * Preliminary implementation of a magnetic connector
 * 
 * @author Modular Robots @ MMMI
 */
public class JMEMagneticConnector extends JMEMechanicalConnector {
    private RotationalJointAxis xAxis,yAxis;
    protected boolean isActivelyConnecting = true;
    
    /**
     * The connector behavior handler is used to decide whether to automatically connector to proximate
     * connectors.  Initialized with null object that provides connect-always behavior. 
     */
    protected ConnectorBehaviorHandler handler = new ConnectorBehaviorHandler() {
        public boolean connectToProximateConnector(Connector target, Connector proximate) { return true; }
    };

    public JMEMagneticConnector(Vector3f position, DynamicPhysicsNode moduleNode, String baseName,
            JMESimulation world, JMEModuleComponent component, ConnectorDescription description) {
        super(position, moduleNode, baseName, world, component, description);
    }

    public void setAlignmentValues(float force, float distance, float epsilon, int timeToConnect) {
        this.connectorAligner.addAlignmentPoint(this.connectorGeometry.getLocalPosition(), 1, 0, force, distance, epsilon);
        this.timeToConnect = timeToConnect;
    }
    
    @Override
    protected void addAxis(Joint connection) {
        // No axes, meaning that connector is rigid
    }

    @Override
    public boolean canConnectNow(JMEConnector connector) {
        if(!canConnectTo(connector)) return false;
        if(!connectorAligner.isAligned(connector)) return false;
        return true;
    }

    @Override
    public boolean canConnectTo(JMEConnector connector) {
        return connector instanceof JMEMagneticConnector;
    }

    @Override
    public boolean canDisconnectFrom(JMEConnector connector) {
        return true;
    }
 
    @Override
    protected void updateColor() {
        connectorGeometry.setConnectorVisibility(true);
        connectorGeometry.resetColor();
    }
    
    @Override
    protected void updateHook() {
        if((!isActivelyConnecting) || isConnected()) return;
        if(this.proximateConnectors.size()>0) {
            System.out.println("Proximate connectors: "+proximateConnectors.size());
            for(JMEConnector other: proximateConnectors) {
                if(!handler.connectToProximateConnector(this.getModel(), other.getModel())) continue;
                //this.connectTo(other);
                new AlignAndConnectManager(this.timeToConnect,other);
                break;
            }
        }
    }
    
    public void setIsActivelyConnecting(boolean active) {
        this.isActivelyConnecting = active;
    }

    @Override
    protected void handleProximateConnectorsOverflow() {
        // Ignore, it's OK to have many proximate connectors for this one
    }
    
    @Override
    protected void handleConnectedConnectorsOverflow() {
        this.disconnect();
    }

    @Override
    protected void handleConnectorBeyondSafetyRegion(JMEConnector connector) {
        this.disconnect();
    }
    
    @Override
    public void setConnectorBehaviorHandler(ConnectorBehaviorHandler handler) {
        this.handler  = handler;
    }
    
    @Override
    public void disconnect() {
        this.isActivelyConnecting = false;
        for(JMEConnector other: connectedConnectors)
            ((JMEMagneticConnector)other).setIsActivelyConnecting(false);
        super.disconnect();
    }
}
