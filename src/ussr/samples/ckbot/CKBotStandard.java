/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.ckbot
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
 * @author Konstantinas. To be more precise added different rotations of CKBotStandard
 * in order to use them in "builder" package. These are (from "ROT_0" to "ROTATION_0_MINUS90X_MINUS90Z" ).
 * Please reuse there rotations. Then your simulations should be compatible with QPSS.
 * Removal of these rotations will result in bugs in the "builder" package. 
 */
public abstract class CKBotStandard extends CKBot {
	
	private final static float pi = (float)Math.PI;
    
	/*Here: ROT - rotation, OPPOS - opposite, MIN - minus*/
	public final static RotationDescription ROT_0 = new RotationDescription(0f,0f,0f);
	public final static RotationDescription ROT_0_OPPOS = new RotationDescription(pi,0f,0f);	
	public final static RotationDescription ROT_0_90Z = new RotationDescription(0f,0f,pi/2);
	public final static RotationDescription ROT_0_OPPOS_90Z = new RotationDescription(pi,0f,pi/2);	
	
	public final static RotationDescription ROT_0_90X = new RotationDescription(pi/2,0f,0f);
	public final static RotationDescription ROT_0_MIN90X = new RotationDescription(-pi/2,0f,0f);
	public final static RotationDescription ROT_0_90X_90Y = new RotationDescription(pi/2,pi/2,0f);
	public final static RotationDescription ROT_0_270X_90Y = new RotationDescription(3*pi/2,pi/2,0f);
	
	public final static RotationDescription ROT_0_90Y = new RotationDescription(0f,pi/2,0f);	
	public final static RotationDescription ROT_0_MIN90Y = new RotationDescription(0f,-pi/2,0f);	
	public final static RotationDescription ROT_0_90X_MIN90Z = new RotationDescription(pi/2,0f,-pi/2);	
	public final static RotationDescription ROT_0_MIN90X_MINUS90Z = new RotationDescription(-pi/2,0f,-pi/2);
	
	public final static float UNIT = 0.060f; 
	
	
    /**
     * @see ussr.description.Robot#getDescription()
     */
    public RobotDescription getDescription() {
        RobotDescription description = new RobotDescription("CKBotStandard");
        
    	final float pi = (float)Math.PI;
    	final float unit = 0.060f;
    	
    	BoxShape tailFace = new BoxShape(new VectorDescription(0.025f,0.03f,0.0025f),new VectorDescription(0f,0f,-0.0275f),new RotationDescription(0,0,0));
    	BoxShape tailBox = new BoxShape(new VectorDescription(0.025f,0.025f,0.0125f),new VectorDescription(0,0,-0.0125f),new RotationDescription(0,0,0));
    	CylinderShape tailCylinder = new CylinderShape(0.025f,0.05f,new VectorDescription(0,0,0),new RotationDescription(pi/2,0,0));
    	
    	BoxShape headFace = new BoxShape(new VectorDescription(0.03f,0.03f,0.0025f),new VectorDescription(0f,0f,0.0275f),new RotationDescription(0,0,0));
    	BoxShape headBox1 = new BoxShape(new VectorDescription(0.025f,0.0025f,0.0125f),new VectorDescription(0f,0.0275f,0.0125f),new RotationDescription(0,0,0));
    	CylinderShape headCylinder1 = new CylinderShape(0.025f,0.005f,new VectorDescription(0,0.0275f,0),new RotationDescription(-pi/2,0,0));
    	BoxShape headBox2 = new BoxShape(new VectorDescription(0.025f,0.0025f,0.0125f),new VectorDescription(0f,-0.0275f,0.0125f),new RotationDescription(0,0,0));
    	CylinderShape headCylinder2 = new CylinderShape(0.025f,0.005f,new VectorDescription(0,-0.0275f,0),new RotationDescription(pi/2,0,0));
    	
    	tailFace.setColor(Color.BLUE);
    	tailBox.setColor(Color.BLUE);
    	tailCylinder.setColor(Color.BLUE);
    	
    	headFace.setColor(Color.RED);
    	headBox1.setColor(Color.RED);
    	headCylinder1.setColor(Color.RED);
    	headBox2.setColor(Color.RED);
    	headCylinder2.setColor(Color.RED);
    	
    	tailFace.setAccurateCollisionDetection(true);
    	tailBox.setAccurateCollisionDetection(true);
    	tailCylinder.setAccurateCollisionDetection(true);
    	
    	headFace.setAccurateCollisionDetection(true);
    	headBox1.setAccurateCollisionDetection(true);
    	headCylinder1.setAccurateCollisionDetection(true);
    	headBox2.setAccurateCollisionDetection(true);
    	headCylinder2.setAccurateCollisionDetection(true);
    	PhysicsParameters.get().setResolutionFactor(1);
    	
        ModuleComponentDescription headComponent = new ModuleComponentDescription(new GeometryDescription[] {headFace,headBox1,headCylinder1,headBox2,headCylinder2});
        ModuleComponentDescription tailComponent = new ModuleComponentDescription(new GeometryDescription[] {tailFace,tailBox,tailCylinder});

        ConnectorDescription.Common common = new ConnectorDescription.Common();
        ConeShape connector = new ConeShape(0.005f,0.01f);
        connector.setColor(Color.WHITE);
        common.setGeometry(new GeometryDescription[] { connector });
        common.setType( ConnectorDescription.Type.MECHANICAL_CONNECTOR_RIGID );
        common.setMaxConnectionDistance(0.03f);
        
        ConnectorDescription[] headConnectors = new ConnectorDescription[] {
                new ConnectorDescription(common,"Connector 0",new VectorDescription(0,0,0.030f),new RotationDescription(pi,0,0),Color.WHITE),
                new ConnectorDescription(common,"Connector 1",new VectorDescription(0,0.030f,0),new RotationDescription(pi/2,0,0),Color.WHITE),
                new ConnectorDescription(common,"Connector 2",new VectorDescription(0,-0.030f,0),new RotationDescription(-pi/2,0,0),Color.WHITE)
        };
        
        ConnectorDescription[] tailConnectors = new ConnectorDescription[] {
                new ConnectorDescription(common,"Connector 3",new VectorDescription(0,0,-0.030f),new RotationDescription(0,0,0),Color.WHITE),
        };
        
        headComponent.setConnectors(headConnectors);
        tailComponent.setConnectors(tailConnectors);
        
        description.setModuleComponents(new ModuleComponentDescription[] {headComponent,tailComponent});
        return description;
    }
}
