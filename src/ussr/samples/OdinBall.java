package ussr.samples;

import java.awt.Color;

import ussr.model.Controller;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.RobotDescription;
import ussr.robotbuildingblocks.SphereShape;
import ussr.robotbuildingblocks.VectorDescription;

public class OdinBall extends Odin {

	/**
	 * A passive ball with 12 connectors in a ccp lattice
	 */
	public RobotDescription getDescription() {
		RobotDescription description = new RobotDescription();
		description.setType("OdinBall");
        SphereShape ball = new SphereShape(0.025f); 
        ball.setColor(Color.RED);
	    description.setModuleGeometry(new GeometryDescription[] {ball});

	    SphereShape connector = new SphereShape(0.005f);
        connector.setColor(Color.WHITE);
        description.setConnectorGeometry(new GeometryDescription[] { connector });
        float unit = (float) (0.025f/Math.sqrt(2)); 
        description.setConnectorPositions(new VectorDescription[] { //hvordan skal de tælles?
        		new VectorDescription(-1*unit, -1*unit, 0*unit),
        		new VectorDescription(-1*unit, 0*unit, -1*unit),
        		new VectorDescription(-1*unit, 0*unit, 1*unit),
        		new VectorDescription(-1*unit, 1*unit, 0*unit),
        		new VectorDescription(0*unit, -1*unit, -1*unit),
        		new VectorDescription(0*unit, -1*unit, 1*unit),
        		new VectorDescription(0*unit, 1*unit, -1*unit),
        		new VectorDescription(0*unit, 1*unit, 1*unit),
        		new VectorDescription(1*unit, -1*unit, 0*unit),
        		new VectorDescription(1*unit, 0*unit, -1*unit),
        		new VectorDescription(1*unit, 0*unit, 1*unit),
        		new VectorDescription(1*unit, 1*unit, 0*unit),
        });
        description.setConnectorType( RobotDescription.ConnectorType.MECHANICAL_CONNECTOR );
        //description.setMaxConnectionDistance(6);
        return description;
	}
	public Controller createController() {
		return new OdinSampleController1("OdinBall");
	}
}
