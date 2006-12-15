package ussr.model;

import ussr.physics.PhysicsConnector;
import ussr.physics.jme.JMEConnector;

public class Connector extends Entity {

    /**
     * The physics model for the connecto
     */
    private PhysicsConnector physics;

    public Connector(PhysicsConnector connector) {
        this.physics = connector;
        connector.setModel(this);
    }

    public boolean hasProximateConnector() {
        return physics.hasProximateConnector();
    }
    
    public Connector getProximateConnector() {
        return physics.getProximateConnector();
    }
    
    public boolean isConnected() {
        return physics.isConnected();
    }
    
    public void connectTo(Connector other) {
        physics.connectTo(other.physics);
    }
    
    public String toString() {
        return "Connector<"+physics+">";
    }
}
