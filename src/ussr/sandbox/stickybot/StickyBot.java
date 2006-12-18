/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.sandbox.stickybot;

import ussr.description.GeometryDescription;
import ussr.description.RobotDescription;
import ussr.description.SphereShape;
import ussr.description.VectorDescription;
import ussr.model.Controller;
import ussr.model.ControllerImpl;
import ussr.model.Robot;

/**
 * A small round robot with connectors at N/S/E/W/U/D; connectors can stick to each
 * other when close, in this case stickiness is globally controlled by the user.  
 * 
 * @author ups
 */
public class StickyBot implements Robot {
    
    /**
     * @see ussr.model.Robot#getDescription()
     */
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

    /**
     * @see ussr.model.Robot#createController()
     */
    public Controller createController() {
        return new StickyBotController();
    }
    
}
