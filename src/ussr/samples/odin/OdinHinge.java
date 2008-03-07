package ussr.samples.odin;

import java.awt.Color;

import ussr.robotbuildingblocks.ConeShape;
import ussr.robotbuildingblocks.CylinderShape;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.RobotDescription;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.SphereShape;
import ussr.robotbuildingblocks.VectorDescription;

public abstract class OdinHinge extends Odin {

	/**
	 * A contractive rod with a connector in each end
	 */
	public RobotDescription getDescription() {
		RobotDescription description = new RobotDescription("OdinHinge");
		float pi = (float)Math.PI;
        CylinderShape cylinderNorth = new CylinderShape(0.035f/2f,0.06f/2f,new VectorDescription(-0.06f/4f,0,0), new RotationDescription(0,-pi/2,0));
        CylinderShape cylinderSouth = new CylinderShape(0.035f/2f,0.06f/2f,new VectorDescription(0.06f/4f,0,0), new RotationDescription(0,-pi/2,0));
        SphereShape center = new SphereShape(0.030f/2f,new VectorDescription(0,0,0), new RotationDescription(pi/2,-pi/2,0));
        ConeShape coneCap1 = new ConeShape(0.035f/2f,0.035f, new VectorDescription(-0.03f-0.035f/2f,0,0), new RotationDescription(pi,-pi/2,0));
        ConeShape coneCap2 = new ConeShape(0.032f/2f,0.035f, new VectorDescription(0.03f+0.035f/2f,0,0), new RotationDescription(0,-pi/2,0));
        
        cylinderNorth.setColor(Color.WHITE);
        cylinderSouth.setColor(Color.WHITE);
        center.setColor(Color.RED);
        coneCap1.setColor(Color.WHITE);
        coneCap2.setColor(Color.WHITE);
        
        cylinderNorth.setAccurateCollisionDetection(false);
        cylinderSouth.setAccurateCollisionDetection(false);
        center.setAccurateCollisionDetection(false);
        coneCap1.setAccurateCollisionDetection(false);
        coneCap2.setAccurateCollisionDetection(false);
        
	    description.setModuleGeometry(new GeometryDescription[] {cylinderNorth,cylinderSouth,center,coneCap1,coneCap2});

	    SphereShape connector = new SphereShape(0.001f);
        connector.setColor(Color.WHITE);
        description.setConnectorGeometry(new GeometryDescription[] { connector });
        float unit = (float) (0.06f/2+0.035f); 
        description.setConnectorPositions(new VectorDescription[] {
        		//new VectorDescription(-unit, 0, 0),
        		//new VectorDescription(unit, 0, 0),
        });
        description.setConnectorType( RobotDescription.ConnectorType.MECHANICAL_CONNECTOR_BALL_SOCKET );
        description.setMaxConnectionDistance(6);
        return description;
	}
}
