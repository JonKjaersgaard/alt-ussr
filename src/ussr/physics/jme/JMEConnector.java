package ussr.physics.jme;

import java.awt.Color;

import ussr.model.Connector;
import ussr.physics.PhysicsConnector;

import com.jmex.physics.DynamicPhysicsNode;

public interface JMEConnector extends PhysicsConnector {

    public DynamicPhysicsNode getNode();

    public boolean hasProximateConnector();

    public boolean isConnected();

    public boolean connect();

    public void reset();

    public String toString();

    public void setModel(Connector connector);
    
    public void setConnectorColor(Color color);

}