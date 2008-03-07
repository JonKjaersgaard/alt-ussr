/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.white
;

import java.awt.Color;

import ussr.robotbuildingblocks.BoxShape;
import ussr.robotbuildingblocks.ConeShape;
import ussr.robotbuildingblocks.CylinderShape;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RobotDescription;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;


public abstract class White implements Robot {
    
    /**
     * @see ussr.robotbuildingblocks.Robot#getDescription()
     */
	
	
    public RobotDescription getDescription() {
    	float unit =0.1f;
    	RobotDescription description = new RobotDescription("White");
    	BoxShape module = new BoxShape(new VectorDescription(unit,unit,unit), new VectorDescription(0,0,0), new RotationDescription(0,0,0));
    	module.setColor(Color.RED);
        module.setAccurateCollisionDetection(false);
        
        description.setModuleGeometry(new GeometryDescription[] {module});

        CylinderShape connector = new CylinderShape(0.005f,0.1f);
        connector.setColor(Color.BLACK);
        description.setConnectorGeometry(new GeometryDescription[] { connector });
        description.setConnectorPositions(new VectorDescription[] {});
        description.setConnectorType( RobotDescription.ConnectorType.MECHANICAL_CONNECTOR_HINGE);
        description.setMaxConnectionDistance(0.03f);
        return description;
    }
}
