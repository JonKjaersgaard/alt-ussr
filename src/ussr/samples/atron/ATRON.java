/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.atron;

import java.awt.Color;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

import ussr.description.Robot;
import ussr.description.geometry.AtronShape;
import ussr.description.geometry.ConeShape;
import ussr.description.geometry.GeometryDescription;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.SphereShape;
import ussr.description.geometry.VectorDescription;
import ussr.description.robot.ConnectorDescription;
import ussr.description.robot.ModuleComponentDescription;
import ussr.description.robot.RobotDescription;
import ussr.physics.PhysicsParameters;

/**
 * The ATRON modular self-reconfigurable robot
 *   
 * @author Modular Robots @ÊMMMI
 */
public abstract class ATRON implements Robot {
    
    /**
     *  Distance between two lattice positions on physical atrons (8cm) 
     */
    public static final float UNIT = 0.08f;

    private static final float eigth = (float)(0.25*Math.PI);
    private static final float quart = (float)(0.5*Math.PI);
    private static final float half = (float)(Math.PI);

    public static final RotationDescription ROTATION_WE = new RotationDescription(new VectorDescription(-eigth,0,0),new VectorDescription(0,-quart,0));
    public static final RotationDescription ROTATION_EW = new RotationDescription(new VectorDescription(eigth,0,0),new VectorDescription(0,quart,0));
    public static final RotationDescription ROTATION_DU = new RotationDescription(-quart,eigth,0);
    public static final RotationDescription ROTATION_UD = new RotationDescription(quart,eigth+quart,0);
    public static final RotationDescription ROTATION_SN = new RotationDescription(0,half,eigth);
    public static final RotationDescription ROTATION_NS_BROKEN = new RotationDescription(0,0,eigth+3*quart);
    public static final RotationDescription ROTATION_NS = new RotationDescription(0,0,eigth+quart);

    protected boolean zuper = false, smooth = false, realistic =false, rubberRing = false, gentle = false;// Different variants
    private PhysicsParameters parameters;

    public void setSuper() { zuper = true; }
    public void setRealistic() { realistic = true; }
    public void setGentle() { gentle = true; }
    
    /**
     * @see ussr.description.Robot#getDescription()
     */
    public RobotDescription getDescription() {
        String type = "ATRON"+(zuper ? " super" : "")+(realistic ? " realistic" : "")+(smooth ? " smooth" : "")+(rubberRing ? " rubberRing" : "")+(gentle ? " gentle" : "");
        RobotDescription description = new RobotDescription(type);
        // Module components
        float pi = (float)Math.PI;
        AtronShape hemi1, hemi2;
        hemi1 = new AtronShape(smooth ? 0.5f : 0.935f, true, new VectorDescription(0f,0f,-0.00f),new RotationDescription(0,pi,pi/4)); //north
        hemi2 = new AtronShape(smooth ? 0.5f : 0.935f, false, new VectorDescription(0f,0f,0.00f),new RotationDescription(0,0f,pi/4)); //south

        hemi1.setColor(smooth ? Color.magenta : Color.blue); hemi2.setColor(Color.RED);
        boolean accurate = PhysicsParameters.get().getRealisticCollision();
        hemi1.setAccurateCollisionDetection(accurate);
        hemi2.setAccurateCollisionDetection(accurate); // needs to be set to true for self-reconfiguration
        ModuleComponentDescription hemi1desc = new ModuleComponentDescription(hemi1);
        ModuleComponentDescription hemi2desc = new ModuleComponentDescription(hemi2);
        description.setModuleComponents(new ModuleComponentDescription[] {hemi1desc, hemi2desc});
        // TODO: put center actuator here!- but how?

        // Connectors
        ConnectorDescription.Common common = new ConnectorDescription.Common();
        common.setGeometry(new GeometryDescription[] { new ConeShape(2.5f*0.005f,0.05f) });
        common.setType(ConnectorDescription.Type.MECHANICAL_CONNECTOR_RIGID);
        common.setMaxConnectionDistance(0.03f);

        final float unit = 0.0282843f;
        final float h = (float)Math.sqrt(2);
        Color[] colors = new Color[]{Color.black,Color.white,Color.black,Color.white};
        VectorDescription[] northPos = new VectorDescription[]{new VectorDescription(unit,unit,-h*unit),new VectorDescription( -unit,  unit, -h*unit ),new VectorDescription( -unit, -unit, -h*unit ),new VectorDescription(  unit, -unit, -h*unit )};
        Quaternion[] northRotQ = new Quaternion[]{new Quaternion(new float[]{0,-pi/4,pi/4}),new Quaternion(new float[]{0,pi/4,-pi/4}),new Quaternion(new float[]{0,pi/4,pi/4}),new Quaternion(new float[]{0,-pi/4,-pi/4})};
        VectorDescription[] southPos = new VectorDescription[]{new VectorDescription(unit,unit,h*unit),new VectorDescription(-unit,unit,h*unit),new VectorDescription(-unit,-unit,h*unit),new VectorDescription(unit,-unit,h*unit)};
        Quaternion[] southRotQ = new Quaternion[]{new Quaternion(new float[]{pi,pi/4,pi/4}),new Quaternion(new float[]{pi,-pi/4,-pi/4}),new Quaternion(new float[]{pi,-pi/4,pi/4}),new Quaternion(new float[]{pi,pi/4,-pi/4})};
        ConnectorDescription[] northConnectors = new ConnectorDescription[4];
        ConnectorDescription[] southConnectors = new ConnectorDescription[4];
        for(int i=0; i<4; i++)
            northConnectors[i] = new ConnectorDescription(common, northPos[i], new RotationDescription(northRotQ[i]), colors[i]);
        for(int i=0; i<4; i++)
            southConnectors[i] = new ConnectorDescription(common, southPos[i], new RotationDescription(southRotQ[i]), colors[i]);
        hemi1desc.setConnectors(northConnectors);
        hemi2desc.setConnectors(southConnectors);
        
    //    description.setTransmitters(new TransmissionDevice[] { new TransmissionDevice(TransmissionType.IR,0.1f) });
    //    description.setReceivers(new ReceivingDevice[] { new ReceivingDevice(TransmissionType.IR,10) });
        
        return description;
    }

    public void setSmooth() {
        smooth = true;
    }
    public void setRubberRing() {
    	rubberRing = true;
    }
}
