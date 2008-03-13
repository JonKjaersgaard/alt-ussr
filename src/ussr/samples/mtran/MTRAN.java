/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.mtran
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
import ussr.physics.PhysicsParameters;


public abstract class MTRAN implements Robot {
    
    /**
     * @see ussr.description.Robot#getDescription()
     */
    public RobotDescription getDescription() {
        RobotDescription description = new RobotDescription("MTRAN");
        
    	final float pi = (float)Math.PI;
    	final float unit = 0.065f/2f;
    	
    	CylinderShape hemi1a = new CylinderShape(0.99f*unit,2*unit*0.99f,new VectorDescription(0,0,0), new RotationDescription(pi/2,-pi/2,0));
    	CylinderShape hemi2a = new CylinderShape(0.99f*unit,2*unit*0.99f,new VectorDescription(0,0,0), new RotationDescription(pi/2,-pi/2,0));
    	
        hemi1a.setColor(Color.red); hemi2a.setColor(Color.blue);
        hemi1a.setAccurateCollisionDetection(true);
        hemi2a.setAccurateCollisionDetection(true); //true for self-reconfiguration
        PhysicsParameters.get().setResolutionFactor(2);
        
        BoxShape hemi1b = new BoxShape(new VectorDescription(unit/2,unit,unit),new VectorDescription(-unit/2,0,0), new RotationDescription(0,0,0));
        BoxShape hemi2b = new BoxShape(new VectorDescription(unit/2,unit,unit),new VectorDescription(unit/2,0,0), new RotationDescription(0,0,0));
        hemi1b.setColor(Color.red); hemi2b.setColor(Color.blue);
        hemi1b.setAccurateCollisionDetection(true);
        hemi2b.setAccurateCollisionDetection(true); //true for self-reconfiguration
        
        BoxShape center = new BoxShape(new VectorDescription(unit,unit/4,unit/4),new VectorDescription(0,0,0), new RotationDescription(0,0,0));
        center.setAccurateCollisionDetection(true);
        center.setColor(Color.black);

        ModuleComponentDescription northComponent = new ModuleComponentDescription(new GeometryDescription[] {hemi1a, hemi1b});
        ModuleComponentDescription southComponent = new ModuleComponentDescription(new GeometryDescription[] {hemi2a, hemi2b});
        ModuleComponentDescription centerComponent = new ModuleComponentDescription(new GeometryDescription[] {center});

        ConnectorDescription.Common common = new ConnectorDescription.Common();
        ConeShape connector = new ConeShape(0.0025f,0.025f);
        connector.setColor(Color.WHITE);
        common.setGeometry(new GeometryDescription[] { connector });
        common.setType( ConnectorDescription.Type.MECHANICAL_CONNECTOR_RIGID );
        common.setMaxConnectionDistance(0.03f);
        
        float h=(float)Math.sqrt(2);
        VectorDescription[] northPos = new VectorDescription[]{new VectorDescription(-unit,0,0),new VectorDescription(0, -unit, 0 ),new VectorDescription(0, unit, 0 )};
        Quaternion[] northRot = new Quaternion[]{new Quaternion(new float[]{0,pi/2,0}),new Quaternion(new float[]{-pi/2,0,0}),new Quaternion(new float[]{pi/2,0,0})};
        ConnectorDescription[] northConnectors = new ConnectorDescription[] {
                new ConnectorDescription(common,"Connector 0",northPos[0],new RotationDescription(northRot[0]),Color.black),
                new ConnectorDescription(common,"Connector 1",northPos[1],new RotationDescription(northRot[1]),Color.black),
                new ConnectorDescription(common,"Connector 2",northPos[2],new RotationDescription(northRot[2]),Color.black)
        };
        VectorDescription[] southPos = new VectorDescription[]{new VectorDescription(unit,0,0),new VectorDescription(0, -unit, 0 ),new VectorDescription(0, unit, 0 )};
        Quaternion[] southRot = new Quaternion[]{new Quaternion(new float[]{0,-pi/2,0}),new Quaternion(new float[]{-pi/2,0,0}),new Quaternion(new float[]{pi/2,0,0})};
        ConnectorDescription[] southConnectors = new ConnectorDescription[] {
                new ConnectorDescription(common,"Connector 4",southPos[0],new RotationDescription(southRot[0]),Color.white),
                new ConnectorDescription(common,"Connector 5",southPos[1],new RotationDescription(southRot[1]),Color.white),
                new ConnectorDescription(common,"Connector 6",southPos[2],new RotationDescription(southRot[2]),Color.white)
        };

        northComponent.setConnectors(northConnectors);
        southComponent.setConnectors(southConnectors);

        description.setModuleComponents(new ModuleComponentDescription[] {northComponent,southComponent,centerComponent});
        return description;
    }
}
