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
        description.setModuleGeometry(new GeometryDescription[] { new SphereShape(1.13f, new VectorDescription( 0.0f, 0.0f, -0.025f)), 
        														  new SphereShape(1.15f, new VectorDescription( 0.0f, 0.0f, 0.025f)) }); 
        description.setConnectorGeometry(new GeometryDescription[] { new SphereShape(0.1f) });
        float zpos = (float) (1.14f*Math.sin( 45 ));
        float xypos = (float) (1.14f*Math.cos( 45 )); 
        	
        
        description.setConnectorPositions(new VectorDescription[] {
                new VectorDescription( xypos, xypos, zpos ),
                new VectorDescription( xypos, -xypos, zpos ),
                new VectorDescription( -xypos, xypos, zpos ),
                new VectorDescription( -xypos, -xypos, zpos ),
                new VectorDescription( xypos, xypos, -zpos ),
                new VectorDescription( xypos, -xypos, -zpos ),
                new VectorDescription( -xypos, xypos, -zpos ),
                new VectorDescription( -xypos, -xypos, -zpos ),
        });
        description.setConnectorType( RobotDescription.ATRON_CONNECTOR );
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
