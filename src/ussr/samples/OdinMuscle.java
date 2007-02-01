package ussr.samples;

import java.awt.Color;

import ussr.model.Controller;
import ussr.robotbuildingblocks.ConeShape;
import ussr.robotbuildingblocks.CylinderShape;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.RobotDescription;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.SphereShape;
import ussr.robotbuildingblocks.VectorDescription;

public class OdinMuscle extends Odin {

	/**
	 * A passive ball with 12 connectors in a ccp lattice
	 */
	public RobotDescription getDescription() {
		RobotDescription description = new RobotDescription();
		description.setType("OdinMuscle");
		float pi = (float)Math.PI;
        CylinderShape cylinderExternal = new CylinderShape(0.035f/2f,0.06f,new VectorDescription(0,0,0), new RotationDescription(0,-pi/2,0));
        CylinderShape cylinderInternal = new CylinderShape(0.032f/2f,0.06f,new VectorDescription(0,0,0), new RotationDescription(0,-pi/2,0));
        ConeShape coneCap1 = new ConeShape(0.035f/2f,0.035f, new VectorDescription(-0.03f-0.035f/2f,0,0), new RotationDescription(pi,-pi/2,0));
        ConeShape coneCap2 = new ConeShape(0.032f/2f,0.035f, new VectorDescription(0.03f+0.035f/2f,0,0), new RotationDescription(0,-pi/2,0));
        
        cylinderExternal.setColor(Color.RED);
        cylinderInternal.setColor(Color.BLUE);
        coneCap1.setColor(Color.RED);
        coneCap2.setColor(Color.BLUE);
        
        cylinderExternal.setAccurateCollisionDetection(false);
        cylinderInternal.setAccurateCollisionDetection(false);
        coneCap1.setAccurateCollisionDetection(false);
        coneCap2.setAccurateCollisionDetection(false);
        
	    description.setModuleGeometry(new GeometryDescription[] {cylinderExternal,cylinderInternal,coneCap1,coneCap2});

	    SphereShape connector = new SphereShape(0.005f);
        connector.setColor(Color.WHITE);
        description.setConnectorGeometry(new GeometryDescription[] { connector });
        float unit = (float) (0.045f/Math.sqrt(2)); 
        description.setConnectorPositions(new VectorDescription[] { //hvad er nord og syd - hvad er 1-7?
        		new VectorDescription(-unit, 0, 0),
        		new VectorDescription(unit, 0, 0),
        });
        description.setConnectorType( RobotDescription.ConnectorType.MECHANICAL_CONNECTOR );
        //description.setMaxConnectionDistance(6);
        return description;
	}
	public Controller createController() {
		return new OdinSampleController1();
	}
}
