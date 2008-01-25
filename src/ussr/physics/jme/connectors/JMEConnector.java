package ussr.physics.jme.connectors;

import java.awt.Color;

import ussr.model.Connector;
import ussr.physics.PhysicsConnector;

import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;

public interface JMEConnector extends PhysicsConnector {

    public boolean hasProximateConnector();

    public boolean isConnected();

    public void connect();
    
    public boolean isDisconnected();
    
    public void disconnect();

    public void reset();

    public String toString();

    public void setModel(Connector connector);
    
    public Connector getModel();
    
    public void setConnectorColor(Color color);
    
    public void setTimeToConnect(float time);
    
    public void setTimeToDisconnect(float time);
    
    public String getName();
    
    public Vector3f getPos();
    
	public Vector3f getPosRel();
    
    public DynamicPhysicsNode getNode();
    
    public JMEConnectorGeometry getConnectorGeometry();

	public JMEConnectorAligner getConnectorAligner();
    
}