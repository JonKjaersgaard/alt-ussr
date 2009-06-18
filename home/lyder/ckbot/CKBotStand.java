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
public abstract class CKBotStand extends CKBot {
	

    
    /**
     * @see ussr.description.Robot#getDescription()
     */
    public RobotDescription getDescription() {
        RobotDescription description = new RobotDescription("CKBotStand");
        
    	final float pi = (float)Math.PI;
    	final float unit = 0.060f;
    	
    	BoxShape standFace = new BoxShape(new VectorDescription(0.03f,0.09f,0.005f),new VectorDescription(0f,0.00f,0f),new RotationDescription(0,0,0));
    	
    	standFace.setColor(Color.GREEN);
    	
    	standFace.setAccurateCollisionDetection(true);

    	PhysicsParameters.get().setResolutionFactor(1);
    	
        ModuleComponentDescription standComponent = new ModuleComponentDescription(new GeometryDescription[] {standFace});

        ConnectorDescription.Common common = new ConnectorDescription.Common();
        ConeShape connector = new ConeShape(0.005f,0.01f);
        connector.setColor(Color.WHITE);
        common.setGeometry(new GeometryDescription[] { connector });
        common.setType( ConnectorDescription.Type.MECHANICAL_CONNECTOR_RIGID );
        common.setMaxConnectionDistance(0.03f);
        
        ConnectorDescription[] standConnectors = new ConnectorDescription[] {
                new ConnectorDescription(common,"Connector 0",new VectorDescription(0,0,-0.005f),new RotationDescription(0,0,0),Color.WHITE),
                new ConnectorDescription(common,"Connector 1",new VectorDescription(0,0.06f,-0.005f),new RotationDescription(0,0,0),Color.WHITE),
                new ConnectorDescription(common,"Connector 1",new VectorDescription(0,-0.06f,-0.005f),new RotationDescription(0,0,0),Color.WHITE),
        };
        
        standComponent.setConnectors(standConnectors);
        
        description.setModuleComponents(new ModuleComponentDescription[] {standComponent});
        return description;
    }
}
