package ussr.description;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RobotDescription {

    private List<GeometryDescription> moduleGeometryDescription = Collections.emptyList();
    private List<GeometryDescription> connectorGeometryDescription = Collections.emptyList();
    private List<VectorDescription> connectorPositions = Collections.emptyList();
 
    public List<GeometryDescription> getModuleGeometry() {
        return moduleGeometryDescription;
    }
    
    public List<GeometryDescription> getConnectorGeometry() {
        return connectorGeometryDescription;
    }
    
    public List<VectorDescription> getConnectorPosition() {
        return connectorPositions;
    }
    
    public void setModuleGeometry(GeometryDescription[] descriptions) {
        this.moduleGeometryDescription = Arrays.asList(descriptions);        
    }

    public void setConnectorGeometry(GeometryDescription[] descriptions) {
        this.connectorGeometryDescription = Arrays.asList(descriptions);
        
    }

    public void setConnectors(VectorDescription[] descriptions) {
        this.connectorPositions = Arrays.asList(descriptions);        
    }


}
