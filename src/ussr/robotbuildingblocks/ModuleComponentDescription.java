/**
 * 
 */
package ussr.robotbuildingblocks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class ModuleComponentDescription extends Description {
    private List<GeometryDescription> geometry;
    private List<ConnectorDescription> connectors = Collections.EMPTY_LIST;

    /**
     * @param geometry
     * @param connectors
     */
    public ModuleComponentDescription(GeometryDescription geometry) {
        this.geometry = Arrays.asList(new GeometryDescription[] { geometry });
    }

    /**
     * @param geometry
     * @param connectors
     */
    public ModuleComponentDescription(GeometryDescription geometry, ConnectorDescription[] connectors) {
        this(geometry,Arrays.asList(connectors));
    }

        /**
     * @param geometry
     * @param connectors
     */
    public ModuleComponentDescription(GeometryDescription geometry, List<ConnectorDescription> connectors) {
        this.geometry = Arrays.asList(new GeometryDescription[] { geometry });
        this.connectors = connectors;
    }
    /**
     * @param geometry
     * @param connectors
     */
    public ModuleComponentDescription(List<GeometryDescription> geometry, List<ConnectorDescription> connectors) {
        this.geometry = geometry;
        this.connectors = connectors;
    }
    public ModuleComponentDescription(GeometryDescription[] geometry) {
        this.geometry = Arrays.asList(geometry);
    }

    /**
     * @return the geometry
     */
    public List<GeometryDescription> getGeometry() {
        return geometry;
    }
    /**
     * @param geometry the geometry to set
     */
    public void setGeometry(List<GeometryDescription> geometry) {
        this.geometry = geometry;
    }
    /**
     * Set the connector descriptons: the descriptions of the connectors (and implicitly their number)
     * @param descriptions the connector positions
     */
    public List<ConnectorDescription> getConnectors() {
        return connectors;
    }
    /**
     * @param connectors the connectors to set
     */
    public void setConnectors(List<ConnectorDescription> connectors) {
        this.connectors = connectors;
    }
    public void setConnectors(ConnectorDescription[] connectors) {
        this.connectors = Arrays.asList(connectors);
    }
    
}
