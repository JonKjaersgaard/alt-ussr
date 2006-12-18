package ussr.physics.jme;

import java.util.List;

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

}