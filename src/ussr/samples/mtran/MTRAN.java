/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.mtran
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


public abstract class MTRAN implements Robot {
    
    /**
     * @see ussr.robotbuildingblocks.Robot#getDescription()
     */
    public RobotDescription getDescription() {
        RobotDescription description = new RobotDescription();
        description.setType("MTRAN");
        
    	float pi = (float)Math.PI;
    	float unit = 0.015f;
    	//CylinderShape hemi1a = new CylinderShape(unit,2*unit*0.99f,new VectorDescription(-unit,0,0), new RotationDescription(pi/2,-pi/2,0));
        //CylinderShape hemi2a = new CylinderShape(unit,2*unit*0.99f,new VectorDescription(unit,0,0), new RotationDescription(pi/2,-pi/2,0));
    	CylinderShape hemi1a = new CylinderShape(0.99f*unit,2*unit*0.99f,new VectorDescription(0,0,0), new RotationDescription(pi/2,-pi/2,0));
        CylinderShape hemi2a = new CylinderShape(0.99f*unit,2*unit*0.99f,new VectorDescription(0,0,0), new RotationDescription(pi/2,-pi/2,0));
    	
    	hemi1a.setColor(Color.BLACK); hemi2a.setColor(Color.WHITE);
        hemi1a.setAccurateCollisionDetection(true);
        hemi2a.setAccurateCollisionDetection(true); //true for self-reconfiguration
        
        //BoxShape hemi1b = new BoxShape(new VectorDescription(unit/2,unit,unit),new VectorDescription(-3*unit/2,0,0), new RotationDescription(0,0,0));
        //BoxShape hemi2b = new BoxShape(new VectorDescription(unit/2,unit,unit),new VectorDescription(3*unit/2,0,0), new RotationDescription(0,0,0));
        BoxShape hemi1b = new BoxShape(new VectorDescription(unit/2,unit,unit),new VectorDescription(-unit/2,0,0), new RotationDescription(0,0,0));
        BoxShape hemi2b = new BoxShape(new VectorDescription(unit/2,unit,unit),new VectorDescription(unit/2,0,0), new RotationDescription(0,0,0));
    	hemi1b.setColor(Color.BLACK); hemi2b.setColor(Color.WHITE);
        hemi1b.setAccurateCollisionDetection(false);
        hemi2b.setAccurateCollisionDetection(false); //true for self-reconfiguration
        
        description.setModuleGeometry(new GeometryDescription[] {hemi1a, hemi2a,hemi1b, hemi2b});

        ConeShape connector = new ConeShape(0.005f,0.05f);
        connector.setColor(Color.WHITE);
        description.setConnectorGeometry(new GeometryDescription[] { connector });
        description.setConnectorPositions(new VectorDescription[] {});
        description.setConnectorType( RobotDescription.ConnectorType.MECHANICAL_CONNECTOR_RIGID );
        description.setMaxConnectionDistance(0.03f);
        return description;
    }
}
