/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.atron;

import java.awt.Color;

import ussr.physics.PhysicsParameters;
import ussr.robotbuildingblocks.AtronShape;
import ussr.robotbuildingblocks.ConeShape;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RobotDescription;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.SphereShape;
import ussr.robotbuildingblocks.VectorDescription;

/**
 * The ATRON modular self-reconfigurable robot
 *   
 * @author Modular Robots @ÊMMMI
 */
public abstract class ATRON implements Robot {
    
    protected boolean zuper = false, smooth = false, realistic =false, rubberRing = false, gentle = false;// Different variants
    private PhysicsParameters parameters;

    public void setSuper() { zuper = true; }
    public void setRealistic() { realistic = true; }
    public void setGentle() { gentle = true; }
    
    /**
     * @see ussr.robotbuildingblocks.Robot#getDescription()
     */
    public RobotDescription getDescription() {
        RobotDescription description = new RobotDescription();
        description.setType("ATRON"+(zuper ? " super" : "")+(realistic ? " realistic" : "")+(smooth ? " smooth" : "")+(rubberRing ? " rubberRing" : "")+(gentle ? " gentle" : ""));
        if(false) {
	        SphereShape hemi1 = new SphereShape(1.13f, new VectorDescription( 0.0f, 0.0f, -0.125f)); 
	        SphereShape hemi2 = new SphereShape(1.15f, new VectorDescription( 0.0f, 0.0f, 0.125f)); 
	        hemi2.setColor(Color.RED);
	        description.setModuleGeometry(new GeometryDescription[] { hemi1, hemi2 });
        }
        else {
        	float pi = (float)Math.PI;
        	AtronShape hemi1, hemi2;
        	hemi1 = new AtronShape(smooth ? 0.5f : 0.935f, true, new VectorDescription(0f,0f,-0.00f),new RotationDescription(0,pi,pi/4)); //north
  	        hemi2 = new AtronShape(smooth ? 0.5f : 0.935f, false, new VectorDescription(0f,0f,0.00f),new RotationDescription(0,0f,pi/4)); //south
        	
        	hemi1.setColor(smooth ? Color.magenta : Color.blue); hemi2.setColor(Color.RED);
        	boolean accurate = PhysicsParameters.get().getRealisticCollision();
  	        hemi1.setAccurateCollisionDetection(accurate);
  	        hemi2.setAccurateCollisionDetection(accurate); //true for self-reconfiguration
  	        description.setModuleGeometry(new GeometryDescription[] {hemi1, hemi2});
  	        //put center actuator here!- but how?
        }
        //SphereShape connector = new SphereShape(0.005f);
//        ConeShape connector = new ConeShape(0.005f,0.005f);
        ConeShape connector = new ConeShape(2.5f*0.005f,0.05f);
        connector.setColor(Color.WHITE);
        description.setConnectorGeometry(new GeometryDescription[] { connector });
        
        /*float zpos = (float) (1.14f*Math.sin( 45 ));
        float xypos = (float) (1.14f*Math.cos( 45 ));*/
       // float unit = (float) (0.045f/Math.sqrt(2)); //4.5cm from center of mass to connector
        description.setConnectorPositions(new VectorDescription[] {});
        
    //    description.setTransmitters(new TransmissionDevice[] { new TransmissionDevice(TransmissionType.IR,0.1f) });
    //    description.setReceivers(new ReceivingDevice[] { new ReceivingDevice(TransmissionType.IR,10) });
        
        description.setConnectorType( RobotDescription.ConnectorType.MECHANICAL_CONNECTOR_RIGID );
        description.setMaxConnectionDistance(0.03f);
        return description;
    }

    public void setSmooth() {
        smooth = true;
    }
    public void setRubberRing() {
    	rubberRing = true;
    }
}
