/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import ussr.physics.PhysicsConnector;
import ussr.physics.PhysicsEntity;
import ussr.physics.PhysicsModuleComponent;

/**
 * 
 * An abstract connector for a modular robot: can connect to other connectors,
 * provide information about other connectors available for connection, and can
 * provide information about its connection status.  
 * 
 * @author ups
 *
 */
public class Connector extends Entity {

    /**
     * The physics model for the connector
     */
    private PhysicsConnector physics;

    /**
     * Construct a new connector
     * @param connector the physics model for the connector
     */
    public Connector(PhysicsConnector connector) {
        this.physics = connector;
        connector.setModel(this);
    }

    /**
     * Return true if is there is a connector available for interaction, false otherwise
     * @return whether there is another connector available for interaction
     */
    public boolean hasProximateConnector() {
    	physics.update();
        return physics.hasProximateConnector();
    }
    
  
    /**
     * Returns true if this connector is connected to another connector, false otherwise
     * @return whether the connector is connected
     */
    public boolean isConnected() {
        return physics.isConnected();
    }
    
    /**
     * Connect this connector to another connector, if this connector is in the immediate
     * proximity
     * @param other the connector to connect to
     */
    public void connect() {
        physics.connect();
    }
    
    /**
     * For debugging
     */
    public String toString() {
        return "Connector<"+physics+">";
    }
    
    public List<? extends PhysicsEntity> getPhysics() {
        ArrayList<PhysicsEntity> result = new ArrayList<PhysicsEntity>();
        result.add(physics);
        return result;
    }

    public void disconnect() {
        physics.disconnect();
    }

	public void setColor(Color color) {
		physics.setConnectorColor(color);
	}
	
	public void reset() {
		physics.reset();
	}
}
