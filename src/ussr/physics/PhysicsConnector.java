/**
 * 
 */
package ussr.physics;

import java.util.List;

import ussr.model.Connector;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public interface PhysicsConnector {

    public boolean otherConnectorAvailable();
    public List<Connector> getAvailableConnectors();
    public boolean isConnected();
    public void connectTo(PhysicsConnector other);
    public void setModel(Connector connector);
  
}
