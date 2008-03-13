/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.odin.modules;

import java.awt.Color;

import ussr.description.geometry.ConeShape;
import ussr.description.geometry.CylinderShape;
import ussr.description.geometry.GeometryDescription;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.SphereShape;
import ussr.description.geometry.VectorDescription;
import ussr.description.robot.ConnectorDescription;
import ussr.description.robot.ModuleComponentDescription;
import ussr.description.robot.RobotDescription;

/**
 * Odin spring module
 * 
 * @author Modular Robots @ MMMI
 */
public abstract class OdinSpring extends Odin {

	/**
	 * A contractive rod with a connector in each end
	 */
	public RobotDescription getDescription() {
		RobotDescription description = new RobotDescription("OdinSpring");
		final float pi = (float)Math.PI;
        CylinderShape cylinder = new CylinderShape(0.035f/4f,0.06f,new VectorDescription(0,0,0), new RotationDescription(0,-pi/2,0));
        ConeShape coneCap1 = new ConeShape(0.035f/2f,0.035f, new VectorDescription(-0.03f-0.035f/2f,0,0), new RotationDescription(pi,-pi/2,0));
        ConeShape coneCap2 = new ConeShape(0.032f/2f,0.035f, new VectorDescription(0.03f+0.035f/2f,0,0), new RotationDescription(0,-pi/2,0));
        
        cylinder.setColor(Color.BLACK);
        coneCap1.setColor(Color.WHITE);
        coneCap2.setColor(Color.WHITE);
        
        cylinder.setAccurateCollisionDetection(false);
        cylinder.setAccurateCollisionDetection(false);
        coneCap1.setAccurateCollisionDetection(false);
        coneCap2.setAccurateCollisionDetection(false);
        
        ModuleComponentDescription northComponent = new ModuleComponentDescription(new GeometryDescription[] { cylinder,coneCap1 });
        ModuleComponentDescription southComponent = new ModuleComponentDescription(new GeometryDescription[] { cylinder,coneCap2 });

        ConnectorDescription.Common common = new ConnectorDescription.Common();
	    SphereShape connector = new SphereShape(0.001f);
        connector.setColor(Color.WHITE);
        common.setGeometry(new GeometryDescription[] { connector });
        common.setType( ConnectorDescription.Type.MECHANICAL_CONNECTOR_BALL_SOCKET );
        final float unit = 0.06f/2+0.035f; 
        northComponent.setConnectors(new ConnectorDescription[] {new ConnectorDescription(common,new VectorDescription(-unit, 0, 0))});
        southComponent.setConnectors(new ConnectorDescription[] {new ConnectorDescription(common,new VectorDescription(unit, 0, 0))});
        //description.setMaxConnectionDistance(6);
        
        description.setModuleComponents(new ModuleComponentDescription[] {northComponent,southComponent});
        return description;
	}
}
