/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.physics;

import java.util.List;
import ussr.model.Connector;

/**
 * A simulated physical connector, useable independently of the underlying physics engine.
 * 
 * @author ups
 *
 */
public interface PhysicsConnector {

    /**
     * Return true if is there is a connector available for interaction, false otherwise
     * @return whether there is another connector available for interaction
     */
    public boolean otherConnectorAvailable();

    /**
     * Return the connectors that have been detected as available for interaction, null if no connectors
     * were detected
     * @return the available connectors, null if there is no available connector
     */
    public List<Connector> getAvailableConnectors();
    
    /**
     * Returns true if this connector is connected to another connector, false otherwise
     * @return whether the connector is connected
     */
    public boolean isConnected();
    
    /**
     * Connect this connector to another connector, if this connector is in the immediate
     * proximity
     * @param other the connector to connect to
     */
    public void connectTo(PhysicsConnector other);
    
    /**
     * Set the model-level connector representing this connector simulation 
     * @param connector the connector model to associate with this connector
     */
    public void setModel(Connector connector);
  
}
