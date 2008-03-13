/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.white
;

import java.awt.Color;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

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


public abstract class White implements Robot {
    public static final float UNIT =0.1f;
    
    /**
     * @see ussr.description.Robot#getDescription()
     */
	
	
    public RobotDescription getDescription() {
    	RobotDescription description = new RobotDescription("White");
    	BoxShape moduleShape = new BoxShape(new VectorDescription(UNIT,UNIT,UNIT), new VectorDescription(0,0,0), new RotationDescription(0,0,0));
    	moduleShape.setColor(Color.RED);
        moduleShape.setAccurateCollisionDetection(false);
        
        ConnectorDescription.Common common = new ConnectorDescription.Common();
        CylinderShape connector = new CylinderShape(0.005f,0.1f);
        connector.setColor(Color.BLACK);
        common.setGeometry(new GeometryDescription[] { connector });
        common.setType( ConnectorDescription.Type.MECHANICAL_CONNECTOR_HINGE);
        common.setMaxConnectionDistance(0.03f);
        Color[] colors = new Color[]{Color.black,Color.white,Color.black,Color.white};
        VectorDescription[] cPos = new VectorDescription[]{new VectorDescription(UNIT,0,UNIT),new VectorDescription(UNIT,0,-UNIT),new VectorDescription(-UNIT,0,-UNIT),new VectorDescription(-UNIT,0,UNIT)};
        ConnectorDescription[] connectors = new ConnectorDescription[4];
        for(int i=0; i<4; i++)
            connectors[i] = new ConnectorDescription(common,cPos[i],new RotationDescription(new Quaternion(new float[]{((float)Math.PI)/2,0,0})),colors[i]);

        ModuleComponentDescription module = new ModuleComponentDescription(moduleShape,connectors);
        description.setModuleComponents(new ModuleComponentDescription[] { module });

        return description;
    }
}
