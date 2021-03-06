/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics;

import java.awt.Color;
import java.util.List;

import ussr.model.Connector;

/**
 * A simulated physical connector, useable independently of the underlying physics engine.
 * 
 * @author ups
 *
 */
public interface PhysicsConnector extends PhysicsEntity {

    /**
     * Return true if is there is a connector available for interaction, false otherwise
     * @return whether there is another connector available for interaction
     */
    public boolean hasProximateConnector();

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
    public void connect();
    
    /**
     * Set the model-level connector representing this connector simulation 
     * @param connector the connector model to associate with this connector
     */
    public void setModel(Connector connector);
    
    public Connector getModel();

    public void disconnect();
    
    public boolean isDisconnected();

	public void setConnectorColor(Color color);
	
	/**
     * Update it this connector has another connector near enough to connect to
     *
     */
    public void update();
    
    public void setRotation(PhysicsQuaternionHolder rot);

    /**
     * Set the connector behavior handler for this connector
     * @see ussr.physics.ConnectorBehaviorHandler
     * @param handler the connector behavior handler
     */
    public void setConnectorBehaviorHandler(ConnectorBehaviorHandler handler);
	
    public void setTimeToConnect(float time);
    
    public void setTimeToDisconnect(float time);
    
    public String getName();

    public Color getConnectorColor();

    public List<? extends PhysicsConnector> getConnectedConnectors();
    public PhysicsModuleComponent getComponent();

}
