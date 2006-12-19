/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.sandbox.atron;

import javax.vecmath.Vector3f;

import ussr.description.GeometryDescription;
import ussr.description.RobotDescription;
import ussr.description.SphereShape;
import ussr.description.VectorDescription;
import ussr.model.Controller;
import ussr.model.ControllerImpl;
import ussr.model.Robot;
import ussr.description.SphereShape;

/**
 * A small round robot with connectors at N/S/E/W/U/D; connectors can stick to each
 * other when close, in this case stickiness is globally controlled by the user.  
 * 
 * @author ups
 */
public class ATRON implements Robot {
    
    /**
     * @see ussr.model.Robot#getDescription()
     */
    public RobotDescription getDescription() {
        RobotDescription description = new RobotDescription();
        description.setModuleGeometry(new GeometryDescription[] { new SphereShape(2f, new VectorDescription( -0.5f, 0.0f, 0.0f)), 
        														  new SphereShape(2f, new VectorDescription( +0.5f, 0.0f, 0.0f)) }); 
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
        return new ATRONController();
    }
    
}
