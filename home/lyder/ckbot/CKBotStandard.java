/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ckbot
;

import java.awt.Color;

import ussr.description.Robot;
import ussr.description.geometry.BoxShape;
import ussr.description.geometry.ConeShape;
import ussr.description.geometry.CylinderShape;
import ussr.description.geometry.GeometryDescription;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.robot.ConnectorDescription;
import ussr.description.robot.ModuleComponentDescription;
import ussr.description.robot.RobotDescription;
import ussr.physics.PhysicsParameters;


/**
 * Preliminary implementation of the CKBot robot
 * 
 * @author lyder
 */
public abstract class CKBotStandard extends CKBot {
    
    /**
     * @see ussr.description.Robot#getDescription()
     */
    public RobotDescription getDescription() {
        RobotDescription description = new RobotDescription("CKBotStandard");
        
    	final float pi = (float)Math.PI;
    	final float unit = 0.065f/2f;
    	
    	BoxShape headFace = new BoxShape(new VectorDescription(0.025f,0.03f,0.0025f),new VectorDescription(0f,0f,-0.0275f),new RotationDescription(0,0,0));
    	BoxShape headBox = new BoxShape(new VectorDescription(0.025f,0.025f,0.0125f),new VectorDescription(0,0,-0.0125f),new RotationDescription(0,0,0));
    	CylinderShape headCylinder = new CylinderShape(0.025f,0.05f,new VectorDescription(0,0,0),new RotationDescription(pi/2,0,0));
    	
    	BoxShape tailFace = new BoxShape(new VectorDescription(0.03f,0.03f,0.0025f),new VectorDescription(0f,0f,0.0275f),new RotationDescription(0,0,0));
    	BoxShape tailBox1 = new BoxShape(new VectorDescription(0.025f,0.0025f,0.0125f),new VectorDescription(0f,0.0275f,0.0125f),new RotationDescription(0,0,0));
    	CylinderShape tailCylinder1 = new CylinderShape(0.025f,0.005f,new VectorDescription(0,0.0275f,0),new RotationDescription(-pi/2,0,0));
    	BoxShape tailBox2 = new BoxShape(new VectorDescription(0.025f,0.0025f,0.0125f),new VectorDescription(0f,-0.0275f,0.0125f),new RotationDescription(0,0,0));
    	CylinderShape tailCylinder2 = new CylinderShape(0.025f,0.005f,new VectorDescription(0,-0.0275f,0),new RotationDescription(pi/2,0,0));
    	
    	headFace.setColor(Color.BLUE);
    	headBox.setColor(Color.BLUE);
    	headCylinder.setColor(Color.BLUE);
    	
    	tailFace.setColor(Color.RED);
    	tailBox1.setColor(Color.RED);
    	tailCylinder1.setColor(Color.RED);
    	tailBox2.setColor(Color.RED);
    	tailCylinder2.setColor(Color.RED);
    	
    	headFace.setAccurateCollisionDetection(true);
    	headBox.setAccurateCollisionDetection(true);
    	headCylinder.setAccurateCollisionDetection(true);
    	
    	tailFace.setAccurateCollisionDetection(true);
    	tailBox1.setAccurateCollisionDetection(true);
    	tailCylinder1.setAccurateCollisionDetection(true);
    	tailBox2.setAccurateCollisionDetection(true);
    	tailCylinder2.setAccurateCollisionDetection(true);
    	PhysicsParameters.get().setResolutionFactor(1);
    	
        ModuleComponentDescription headComponent = new ModuleComponentDescription(new GeometryDescription[] {headFace,headBox,headCylinder});
        ModuleComponentDescription tailComponent = new ModuleComponentDescription(new GeometryDescription[] {tailFace,tailBox1,tailCylinder1,tailBox2,tailCylinder2});

        ConnectorDescription.Common common = new ConnectorDescription.Common();
        ConeShape connector = new ConeShape(0.005f,0.01f);
        connector.setColor(Color.WHITE);
        common.setGeometry(new GeometryDescription[] { connector });
        common.setType( ConnectorDescription.Type.MECHANICAL_CONNECTOR_RIGID );
        common.setMaxConnectionDistance(0.03f);
        
        ConnectorDescription[] headConnectors = new ConnectorDescription[] {
                new ConnectorDescription(common,"Connector 0",new VectorDescription(0,0,0.035f),new RotationDescription(pi,0,0),Color.WHITE),
                new ConnectorDescription(common,"Connector 1",new VectorDescription(0,0.035f,0),new RotationDescription(pi/2,0,0),Color.WHITE),
                new ConnectorDescription(common,"Connector 2",new VectorDescription(0,-0.035f,0),new RotationDescription(-pi/2,0,0),Color.WHITE)
        };
        
        ConnectorDescription[] tailConnectors = new ConnectorDescription[] {
                new ConnectorDescription(common,"Connector 3",new VectorDescription(0,0,-0.035f),new RotationDescription(0,0,0),Color.WHITE),
        };
        
        headComponent.setConnectors(headConnectors);
        tailComponent.setConnectors(tailConnectors);
        
        description.setModuleComponents(new ModuleComponentDescription[] {headComponent,tailComponent});
        return description;
    }
}
