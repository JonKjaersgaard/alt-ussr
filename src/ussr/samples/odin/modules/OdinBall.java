package ussr.samples.odin.modules;

import java.awt.Color;

import ussr.model.Controller;
import ussr.robotbuildingblocks.ConnectorDescription;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.ModuleComponentDescription;
import ussr.robotbuildingblocks.RobotDescription;
import ussr.robotbuildingblocks.SphereShape;
import ussr.robotbuildingblocks.VectorDescription;

public abstract class OdinBall extends Odin {

	/**
	 * A passive ball with 12 connectors in a ccp lattice
	 */
	public RobotDescription getDescription() {
		RobotDescription description = new RobotDescription("OdinBall");
        SphereShape ball = new SphereShape(0.025f); 
        ball.setColor(Color.RED);
        ModuleComponentDescription moduleComponent = new ModuleComponentDescription(ball);
        ConnectorDescription.Common common = new ConnectorDescription.Common();
	    SphereShape connector = new SphereShape(0.001f);
        connector.setColor(Color.WHITE);
        common.setGeometry(new GeometryDescription[] { connector });
        common.setType( ConnectorDescription.Type.MECHANICAL_CONNECTOR_BALL_SOCKET );
        final float unit = (float) (0.025f/Math.sqrt(2)); 
        moduleComponent.setConnectors(new ConnectorDescription[] { //hvordan skal de tælles?
        		new ConnectorDescription(common,new VectorDescription(-1*unit, -1*unit, 0*unit)),
        		new ConnectorDescription(common,new VectorDescription(-1*unit, 0*unit, -1*unit)),
        		new ConnectorDescription(common,new VectorDescription(-1*unit, 0*unit, 1*unit)),
        		new ConnectorDescription(common,new VectorDescription(-1*unit, 1*unit, 0*unit)),
        		new ConnectorDescription(common,new VectorDescription(0*unit, -1*unit, -1*unit)),
        		new ConnectorDescription(common,new VectorDescription(0*unit, -1*unit, 1*unit)),
        		new ConnectorDescription(common,new VectorDescription(0*unit, 1*unit, -1*unit)),
        		new ConnectorDescription(common,new VectorDescription(0*unit, 1*unit, 1*unit)),
        		new ConnectorDescription(common,new VectorDescription(1*unit, -1*unit, 0*unit)),
        		new ConnectorDescription(common,new VectorDescription(1*unit, 0*unit, -1*unit)),
        		new ConnectorDescription(common,new VectorDescription(1*unit, 0*unit, 1*unit)),
        		new ConnectorDescription(common,new VectorDescription(1*unit, 1*unit, 0*unit))
        });
        //description.setMaxConnectionDistance(6);
        description.setModuleComponents(new ModuleComponentDescription[] { moduleComponent} );
        return description;
	}
}
