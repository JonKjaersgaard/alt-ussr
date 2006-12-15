/**
 * 
 */
package ussr.physics;

import ussr.model.Connector;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public interface PhysicsConnector {

    public boolean hasProximateConnector();
    public Connector getProximateConnector();
    public boolean isConnected();
    public void connectTo(PhysicsConnector other);
    public void setModel(Connector connector);
  
}
