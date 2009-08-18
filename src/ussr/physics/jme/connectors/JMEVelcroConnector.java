package ussr.physics.jme.connectors;

import java.awt.Color;

import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;

import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.robot.ConnectorDescription;
import ussr.model.Connector;
import ussr.physics.ConnectorBehaviorHandler;
import ussr.physics.PhysicsQuaternionHolder;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.connectors.JMEBasicConnector.AlignAndConnectManager;

public class JMEVelcroConnector extends JMEMagneticConnector {

    public JMEVelcroConnector(Vector3f position, DynamicPhysicsNode moduleNode,
            String baseName, JMESimulation world, JMEModuleComponent component,
            ConnectorDescription description) {
        super(position, moduleNode, baseName, world, component, description);
        super.setAlignmentValues(20f, description.getMaxConnectionDistance()*1.1f, description.getMaxConnectionDistance()*0.1f, 10);
    }

    @Override
    protected void updateHook() {
        if((!isActivelyConnecting) || super.isConnected()) return;
        this.updateConnectorProximity();
        if(this.proximateConnectors.size()>0) {
            for(JMEConnector other: proximateConnectors) {
                if(!handler.connectToProximateConnector(this.getModel(), other.getModel())) continue;
                new AlignAndConnectManager(this.timeToConnect,other);
            }
        }
    }
    
    @Override public boolean isConnected() {
        return this.getAligningConnectorsCount()>0;
    }
}
