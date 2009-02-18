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
 * Odin hinge module
 * 
 * @author Modular Robots @ MMMI
 */
public abstract class OdinHinge extends Odin {

	/**
	 * A contractive rod with a connector in each end
	 */
	public RobotDescription getDescription() {
		RobotDescription description = new RobotDescription("OdinHinge");
		final float pi = (float)Math.PI;
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
        
        ModuleComponentDescription northComponent = new ModuleComponentDescription(new GeometryDescription[] {cylinderNorth,center,coneCap1});
        ModuleComponentDescription southComponent = new ModuleComponentDescription(new GeometryDescription[] {cylinderSouth,coneCap2});
        ConnectorDescription.Common common = new ConnectorDescription.Common();
        GeometryDescription connector = super.makeConnectorShape();
        common.setGeometry(new GeometryDescription[] { connector });
        common.setType( ConnectorDescription.Type.MECHANICAL_CONNECTOR_BALL_SOCKET );
        common.setMaxConnectionDistance(6);
        final float unit = 0.06f/2+0.035f; 
        northComponent.setConnectors(new ConnectorDescription[] { new ConnectorDescription(common,new VectorDescription(-unit, 0, 0)) });
        southComponent.setConnectors(new ConnectorDescription[] { new ConnectorDescription(common,new VectorDescription(unit, 0, 0)) });
        description.setModuleComponents(new ModuleComponentDescription[] { northComponent, southComponent });
        return description;
	}
}
