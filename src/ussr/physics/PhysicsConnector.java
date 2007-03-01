/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.physics;

import java.awt.Color;

import com.jme.math.Quaternion;

import ussr.model.Connector;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;

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
    public boolean connect();
    
    /**
     * Set the model-level connector representing this connector simulation 
     * @param connector the connector model to associate with this connector
     */
    public void setModel(Connector connector);

    public void disconnect();

	public void setConnectorColor(Color color);
	
	/**
     * Update it this connector has another connector near enough to connect to
     *
     */
    public void update();
    
    public void setRotation(Quaternion rot);
	
	
}
