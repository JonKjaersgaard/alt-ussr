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
import ussr.model.Controller;

/**
 * Odin muscle module
 * 
 * @author Modular Robots @ MMMI
 */
public abstract class OdinMuscle extends Odin {

	/**
	 * A contractive rod with a connector in each end
	 */
	public RobotDescription getDescription() {
		RobotDescription description = new RobotDescription("OdinMuscle");
		final float pi = (float)Math.PI;
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
        
        ModuleComponentDescription externalComponent = new ModuleComponentDescription(new GeometryDescription[] {cylinderExternal,coneCap1});
        ModuleComponentDescription internalComponent = new ModuleComponentDescription(new GeometryDescription[] {cylinderInternal,coneCap2});

        ConnectorDescription.Common common = new ConnectorDescription.Common();
	    SphereShape connector = new SphereShape(0.001f);
        connector.setColor(Color.WHITE);
        common.setGeometry(new GeometryDescription[] { connector });
        common.setType( ConnectorDescription.Type.MECHANICAL_CONNECTOR_BALL_SOCKET); 
        float unit = (float) (0.06f/2+0.035f); 
        externalComponent.setConnectors(new ConnectorDescription[] { new ConnectorDescription(common,new VectorDescription(-unit, 0, 0)) });
        internalComponent.setConnectors(new ConnectorDescription[] { new ConnectorDescription(common,new VectorDescription(unit, 0, 0)) });
        //description.setMaxConnectionDistance(6);
        
        description.setModuleComponents(new ModuleComponentDescription[] { externalComponent, internalComponent });
        return description;
	}
}
