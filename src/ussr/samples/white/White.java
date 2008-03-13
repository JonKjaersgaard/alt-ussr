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

import ussr.robotbuildingblocks.BoxShape;
import ussr.robotbuildingblocks.ConeShape;
import ussr.robotbuildingblocks.ConnectorDescription;
import ussr.robotbuildingblocks.CylinderShape;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.ModuleComponentDescription;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RobotDescription;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;


public abstract class White implements Robot {
    public static final float UNIT =0.1f;
    
    /**
     * @see ussr.robotbuildingblocks.Robot#getDescription()
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
