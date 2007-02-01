/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.robotbuildingblocks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ussr.comm.Transmitter;

/**
 * 
 * A description of the geometry of a complete robot module, including the shape
 * of the core, the shape of a connector, and the positions of the connectors and
 * their range.
 *
 * @author ups
 *
 */

public class RobotDescription extends Description {
    
    public static enum ConnectorType {
        MAGNETIC_CONNECTOR,
        MECHANICAL_CONNECTOR,
        NONE
    }
	
    public static enum PhysicsCompositionStructure {
        COMPONENT,
        MODULE,
        STRUCTURE
    }
    
    /**
     * The geometric shapes that constitute the core of the module
     */
    private List<GeometryDescription> moduleGeometryDescription = Collections.emptyList();
    
    /**
     * The geometric shapes that constitute a single connector
     */
    private List<GeometryDescription> connectorGeometryDescription = Collections.emptyList();
    
    /**
     * The positions of the connectors (and implicitly their number) relative to the center
     * of the core
     */
    private List<VectorDescription> connectorPositions = Collections.emptyList();
    
    private ConnectorType connectorType = ConnectorType.NONE;
    private PhysicsCompositionStructure structure = PhysicsCompositionStructure.MODULE;
    
    /**
     * The maximal radius from the center of a connector to the center of another connector
     * where the connectors can reach each other (e.g., connect)
     */
    private float maxConnectionDistance = 0;

    private List<TransmissionDevice> transmitters = Collections.emptyList();
    private List<ReceivingDevice> receivers = Collections.emptyList();
 
    /**
     * Get the module geometry: the geometric shapes that constitute the core of the module
     * @return the core geometry shapes
     */
    public List<GeometryDescription> getModuleGeometry() {
        return moduleGeometryDescription;
    }
    
    /**
     * Get the connector geometry: the geometric shapes that constitute a single connector
     * @return the connector geometry shapes
     */
    public List<GeometryDescription> getConnectorGeometry() {
        return connectorGeometryDescription;
    }
    
    /**
     * Get the connector positions: the positions of the connectors (and implicitly their number) relative to the center
     * of the core
     * @return the connector positions
     */
    public List<VectorDescription> getConnectorPositions() {
        return connectorPositions;
    }
    
    /**
     * Get the max connection distance: The maximal radius from the center of a connector to the center of another connector
     * where the connectors can reach each other (e.g., connect)
     * @return the max connection distance
     */
    public float getMaxConnectionDistance() {
        return maxConnectionDistance;
    }

    /**
     * Set the module geometry: the geometric shapes that constitute the core of the module
     * @param descriptions the core geometry shapes
     */
    public void setModuleGeometry(GeometryDescription[] descriptions) {
        this.moduleGeometryDescription = Arrays.asList(descriptions);        
    }

    /**
     * Get the connector geometry: the geometric shapes that constitute a single connector
     * @param descriptions the connector geometry shapes
     */
    public void setConnectorGeometry(GeometryDescription[] descriptions) {
        this.connectorGeometryDescription = Arrays.asList(descriptions);
        
    }

    /**
     * Get the connector positions: the positions of the connectors (and implicitly their number) relative to the center
     * of the core
     * @param descriptions the connector positions
     */
    public void setConnectorPositions(VectorDescription[] descriptions) {
        this.connectorPositions = Arrays.asList(descriptions);        
    }

    public ConnectorType getConnectorType() {
    	return this.connectorType;
    }
    
    public void setConnectorType( ConnectorType connectorType ) {
    	this.connectorType=connectorType;
    }

    
    /**
     * Set the max connection distance: The maximal radius from the center of a connector to the center of another connector
     * where the connectors can reach each other (e.g., connect)
     * @param the max connection distance
     */
    public void setMaxConnectionDistance(float limit) {
        maxConnectionDistance = limit;
    }

    public void setTransmitters(TransmissionDevice[] transmitters) {
        this.transmitters = Arrays.asList(transmitters);
    }

    public void setReceivers(ReceivingDevice[] transmitters) {
        this.receivers = Arrays.asList(transmitters);
    }

    public List<TransmissionDevice> getTransmitters() {
        return this.transmitters;
    }

    public List<ReceivingDevice> getReceivers() {
        return this.receivers;
    }

    /**
     * @return the structure
     */
    public PhysicsCompositionStructure getStructure() {
        return structure;
    }

    /**
     * @param structure the structure to set
     */
    public void setStructure(PhysicsCompositionStructure structure) {
        this.structure = structure;
    }

    private String type;
    /**
     * Type of robot
     * @return
     */
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
