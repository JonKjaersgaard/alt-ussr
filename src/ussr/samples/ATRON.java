/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples;

import java.awt.Color;

import ussr.model.Controller;
import ussr.robotbuildingblocks.AtronShape;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RobotDescription;
import ussr.robotbuildingblocks.RotationDescription;
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
        description.setType("ATRON");
        if(false) {
	        SphereShape hemi1 = new SphereShape(1.13f, new VectorDescription( 0.0f, 0.0f, -0.125f)); 
	        SphereShape hemi2 = new SphereShape(1.15f, new VectorDescription( 0.0f, 0.0f, 0.125f)); 
	        hemi2.setColor(Color.RED);
	        description.setModuleGeometry(new GeometryDescription[] { hemi1, hemi2 });
        }
        else {
        	float pi = (float)Math.PI;
  	        AtronShape hemi1 = new AtronShape(0.9f, true, new VectorDescription(0f,0f,-0.001f),new RotationDescription(0,pi,pi/4)); //north
  	        AtronShape hemi2 = new AtronShape(1f, false, new VectorDescription(0f,0f,0.001f),new RotationDescription(0,0f,pi/4)); //south
  	        hemi1.setColor(Color.blue); hemi2.setColor(Color.RED);
  	        hemi1.setAccurateCollisionDetection(true);
  	        hemi2.setAccurateCollisionDetection(true);
  	        description.setModuleGeometry(new GeometryDescription[] {hemi1, hemi2});
  	        //put center actuator here!- but how?
        }
        SphereShape connector = new SphereShape(0.005f);
        connector.setColor(Color.WHITE);
        description.setConnectorGeometry(new GeometryDescription[] { connector });
        /*float zpos = (float) (1.14f*Math.sin( 45 ));
        float xypos = (float) (1.14f*Math.cos( 45 ));*/
        float unit = (float) (0.045f/Math.sqrt(2)); //4.5cm from center of mass to connector
        description.setConnectorPositions(new VectorDescription[] { //hvad er nord og syd - hvad er 1-7?
        		/*new VectorDescription(  unit,  unit, -unit ), //north connector 0
        		new VectorDescription( -unit,  unit, -unit ), //north connector 1
        		new VectorDescription( -unit, -unit, -unit ), //north connector 2
        		new VectorDescription(  unit, -unit, -unit ), //north connector 3
        		new VectorDescription(  unit,  unit,  unit ), //south connector 4
        		new VectorDescription( -unit,  unit,  unit ), //south connector 5
        		new VectorDescription( -unit, -unit,  unit ), //south connector 6
        		new VectorDescription(  unit, -unit,  unit ), //south connector 7*/
        		
                /*new VectorDescription( xypos, xypos, zpos ),
                new VectorDescription( xypos, -xypos, zpos ),
                new VectorDescription( -xypos, xypos, zpos ),
                new VectorDescription( -xypos, -xypos, zpos ),
                new VectorDescription( xypos, xypos, -zpos ),
                new VectorDescription( xypos, -xypos, -zpos ),
                new VectorDescription( -xypos, xypos, -zpos ),
                new VectorDescription( -xypos, -xypos, -zpos ),*/
        });
        description.setConnectorType( RobotDescription.ConnectorType.MECHANICAL_CONNECTOR );
        description.setMaxConnectionDistance(6);
        return description;
    }
}
