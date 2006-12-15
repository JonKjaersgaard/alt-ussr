package ussr.sandbox;

import ussr.description.GeometryDescription;
import ussr.description.RobotDescription;
import ussr.description.SphereShape;
import ussr.description.VectorDescription;
import ussr.model.Controller;
import ussr.model.ControllerImpl;
import ussr.model.Robot;

public class StickyBot implements Robot {
    
    public RobotDescription getDescription() {
        RobotDescription description = new RobotDescription();
        description.setModuleGeometry(new GeometryDescription[] { new SphereShape(2) }); 
        description.setConnectorGeometry(new GeometryDescription[] { new SphereShape(1) });
        description.setConnectorPositions(new VectorDescription[] {
            new VectorDescription(-2.0f, 0.0f, 0),
            new VectorDescription(2.0f, 0.0f, 0),
            new VectorDescription(0.0f, 2.0f, 0),
            new VectorDescription(0.0f, -2.0f, 0),
            new VectorDescription(0.0f, 0.0f, -2.0f),
            new VectorDescription(0.0f, 0f, 2.0f) });
        description.setMaxConnectionDistance(6);
        return description;
    }
    
    public Controller createController() {
        return new StickyBotController();
    }
    
}
