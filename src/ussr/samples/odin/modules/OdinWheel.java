package ussr.samples.odin.modules;

import java.awt.Color;

import ussr.robotbuildingblocks.ConeShape;
import ussr.robotbuildingblocks.ConnectorDescription;
import ussr.robotbuildingblocks.CylinderShape;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.ModuleComponentDescription;
import ussr.robotbuildingblocks.RobotDescription;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.SphereShape;
import ussr.robotbuildingblocks.VectorDescription;

public abstract class OdinWheel extends Odin {

	/**
	 * A contractive rod with a connector in each end
	 */
	public RobotDescription getDescription() {
		RobotDescription description = new RobotDescription("OdinWheel");
		float pi = (float)Math.PI;
        CylinderShape cylinder = new CylinderShape(0.035f/2f,0.06f,new VectorDescription(0,0,0), new RotationDescription(0,-pi/2,0));
        SphereShape wheel = new SphereShape(0.035f,new VectorDescription(0,0,0), new RotationDescription(pi/2,-pi/2,0));
        ConeShape coneCap1 = new ConeShape(0.035f/2f,0.035f, new VectorDescription(-0.03f-0.035f/2f,0,0), new RotationDescription(pi,-pi/2,0));
        ConeShape coneCap2 = new ConeShape(0.032f/2f,0.035f, new VectorDescription(0.03f+0.035f/2f,0,0), new RotationDescription(0,-pi/2,0));
        
        cylinder.setColor(Color.WHITE);
        wheel.setColor(Color.BLUE);
        coneCap1.setColor(Color.WHITE);
        coneCap2.setColor(Color.WHITE);
        
        cylinder.setAccurateCollisionDetection(false);
        wheel.setAccurateCollisionDetection(false);
        coneCap1.setAccurateCollisionDetection(false);
        coneCap2.setAccurateCollisionDetection(false);
        
        ModuleComponentDescription axleComponent = new ModuleComponentDescription(new GeometryDescription[] {cylinder,coneCap1,coneCap2});
        ModuleComponentDescription wheelComponent = new ModuleComponentDescription(new GeometryDescription[] {wheel});

        ConnectorDescription.Common common = new ConnectorDescription.Common();
	    SphereShape connector = new SphereShape(0.001f);
        connector.setColor(Color.WHITE);
        common.setGeometry(new GeometryDescription[] { connector });
        common.setType( ConnectorDescription.Type.MECHANICAL_CONNECTOR_BALL_SOCKET );
        float unit = 0.06f/2+0.035f; 
        axleComponent.setConnectors(new ConnectorDescription[] {
        		new ConnectorDescription(common,new VectorDescription(-unit, 0, 0)),
        		new ConnectorDescription(common,new VectorDescription(unit, 0, 0))
        });
        //description.setMaxConnectionDistance(6);
        description.setModuleComponents(new ModuleComponentDescription[] { axleComponent, wheelComponent });
        return description;
	}
}
