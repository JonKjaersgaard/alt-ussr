package ussr.physics.jme.connectors;

import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.jme.bounding.BoundingSphere;
import com.jme.input.InputHandler;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.InputActionInterface;
import com.jme.input.util.SyntheticButton;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.TriMesh;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.RotationalJointAxis;
import com.jmex.physics.contact.ContactInfo;

import ussr.description.ConnectorDescription;
import ussr.description.GeometryDescription;
import ussr.description.RobotDescription;
import ussr.description.RotationDescription;
import ussr.description.VectorDescription;
import ussr.model.Connector;
import ussr.physics.ConnectorBehaviorHandler;
import ussr.physics.PhysicsConnector;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsQuaternionHolder;
import ussr.physics.jme.JMEGeometryHelper;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

public class JMEMagneticConnector extends JMEMechanicalConnector {
    private RotationalJointAxis xAxis,yAxis,zAxis;
    private boolean isActive = false;
    
    /**
     * The connector behavior handler is used to decide whether to automatically connector to proximate
     * connectors.  Initialized with null object that provides connect-always behavior. 
     */
    private ConnectorBehaviorHandler handler = new ConnectorBehaviorHandler() {
        public boolean connectToProximateConnector(Connector target, Connector proximate) { return true; }
    };

    public JMEMagneticConnector(Vector3f position, DynamicPhysicsNode moduleNode, String baseName,
            JMESimulation world, JMEModuleComponent component, ConnectorDescription description) {
        super(position, moduleNode, baseName, world, component, description);
    }

    @Override
    protected void addAxis(Joint connection) {
        xAxis = connection.createRotationalAxis(); xAxis.setDirection(new Vector3f(1,0,0));
        yAxis = connection.createRotationalAxis(); yAxis.setDirection(new Vector3f(0,0,1));
        yAxis.setRelativeToSecondObject(true);
    }

    @Override
    public boolean canConnectNow(JMEConnector connector) {
        if(getPos().distance(connector.getPos())>maxConnectDistance)
            return false;
        return true;
    }

    @Override
    public boolean canConnectTo(JMEConnector connector) {
        if(!canConnectNow(connector)) return false;
        return true;
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
        if((!isActive) || isConnected()) return;
        if(this.proximateConnectors.size()>0) {
            for(JMEConnector other: proximateConnectors) {
                if(!handler.connectToProximateConnector(this.getModel(), other.getModel())) continue;
                this.connectTo(other);
            }
        }
    }
    
    public void setIsActive(boolean active) {
        this.isActive = active;
        if((!active) && this.isConnected()) this.disconnect();
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
    public void setConnectorBehaviorHandler(ConnectorBehaviorHandler handler) {
        this.handler  = handler;
    }
}
