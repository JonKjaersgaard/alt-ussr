/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples;

import java.awt.Color;

import javax.vecmath.Vector3f;

import ussr.model.Controller;
import ussr.model.ControllerImpl;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RobotDescription;
import ussr.robotbuildingblocks.SphereShape;
import ussr.robotbuildingblocks.VectorDescription;

/**
 * A small round robot with connectors at N/S/E/W/U/D; connectors can stick to each
 * other when close, in this case stickiness is globally controlled by the user.  
 * 
 * @author ups
 */
public abstract class ATRON implements Robot {
    
    /**
     * @see ussr.robotbuildingblocks.Robot#getDescription()
     */
    public RobotDescription getDescription() {
        RobotDescription description = new RobotDescription();
        SphereShape hemi1 = new SphereShape(1.13f, new VectorDescription( 0.0f, 0.0f, -0.025f));
        SphereShape hemi2 = new SphereShape(1.15f, new VectorDescription( 0.0f, 0.0f, 0.025f));
        hemi2.setColor(Color.RED);
        description.setModuleGeometry(new GeometryDescription[] { hemi1, hemi2 }); 
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
        description.setConnectorType( RobotDescription.ConnectorType.MECHANICAL_CONNECTOR );
        description.setMaxConnectionDistance(6);
        return description;
    }

    
}
