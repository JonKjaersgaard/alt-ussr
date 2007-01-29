/**
 * 
 */
package ussr.physics.jme;

import java.awt.Color;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;

import ussr.comm.GenericReceiver;
import ussr.comm.GenericTransmitter;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.model.Module;
import ussr.robotbuildingblocks.AtronShape;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.ReceivingDevice;
import ussr.robotbuildingblocks.SphereShape;
import ussr.robotbuildingblocks.TransmissionDevice;
import ussr.robotbuildingblocks.VectorDescription;

import com.jme.bounding.BoundingSphere;
import com.jme.math.Matrix3f;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.SceneElement;
import com.jme.scene.SharedMesh;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.RenderState;
import com.jme.util.export.binary.BinaryImporter;
import com.jmex.model.XMLparser.Converters.MaxToJme;
import com.jmex.physics.DynamicPhysicsNode;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class JMEDescriptionHelper {

    public static TriMesh createShape(DynamicPhysicsNode moduleNode, String name,GeometryDescription element) {
    	TriMesh shape = null;
        if(element instanceof SphereShape) {
        	Sphere meshSphere = new Sphere( name, 9, 9, ((SphereShape)element).getRadius()); 
        	VectorDescription translation = ((SphereShape)element).getTranslation();
        	meshSphere.getLocalTranslation().set( translation.getX(), translation.getY(), translation.getZ() );
        	meshSphere.setModelBound( new BoundingSphere() );
        	meshSphere.updateModelBound();
        	shape = meshSphere;
        	moduleNode.attachChild(shape);
        }
        else if(element instanceof AtronShape) {
        	AtronShape half = (AtronShape) element;
        	shape = constructAtronModel(name, half);
        	moduleNode.attachChild(shape);
        	moduleNode.setModelBound(new BoundingSphere());
        	moduleNode.updateModelBound();
        	moduleNode.generatePhysicsGeometry();
        }
        else throw new Error("Only sphere and atron geometries supported for now");
        
        return shape;
    }
    
    public static Transmitter createTransmitter(Module module, TransmissionDevice transmitter) {
        return new GenericTransmitter(module, transmitter.getType(),transmitter.getRange());
    }

    public static Receiver createReceiver(Module module, ReceivingDevice receiver) {
        return new GenericReceiver(module, receiver.getType(), receiver.getBufferSize());
    }

    public static void setColor(JMESimulation world, SceneElement object, Color color) {
        if(color==null) return;
        object.setRenderState(world.color2jme(color));
        object.updateRenderState();
    }

    public static Spatial cloneElement(TriMesh element) {
        if(!(element instanceof Sphere)) throw new Error("not supported");
        Sphere oldSphere = (Sphere)element;
        Sphere meshSphere = new Sphere( oldSphere.getName(), 9, 9, oldSphere.radius);
        meshSphere.setLocalTranslation(oldSphere.getLocalTranslation());
        meshSphere.setLocalRotation(oldSphere.getLocalRotation());
        RenderState state = oldSphere.getRenderState(RenderState.RS_MATERIAL);
        if(state!=null) meshSphere.setRenderState(state);
        meshSphere.setModelBound( new BoundingSphere() );
        meshSphere.updateModelBound();
       return meshSphere;
    }
	
    static TriMesh atronModel;
	private static TriMesh constructAtronModel(String name, AtronShape half) {
		if(atronModel==null) loadAtronModel(half.getRadius());
		SharedMesh atronMesh = new SharedMesh(name,atronModel);
		Matrix3f rotMat = new Matrix3f(); //rotate north 180 degree
		if(half.isNorth()) {
			//atronMesh.setLocalTranslation(new Vector3f(0,0,-0.01f));
			rotMat = new Matrix3f(1, 0, 0, 0, -1, 0, 0, 0, -1); //rotate north 180 degree
		}
		else {
			//atronMesh.setLocalTranslation(new Vector3f(0,0,0.01f));
		}
		//rotate both 45 degree
		rotMat = rotMat.mult(new Matrix3f(0.707107f, 0.707107f, 0.f, -0.707107f, 0.707107f, 0.f, 0.f, 0.f, 1.f));
		
		atronMesh.setLocalRotation(rotMat);
		
		//TriMesh atron = new TriMesh("Atron Node");
		//connectors? - special atron nodes with connectors?
		return atronMesh;
	}
    public static void loadAtronModel(float radius) {
//		atronModel = new Sphere("", 10, 10, 0.11f);
//		return;

		try {
			MaxToJme C1 = new MaxToJme();
			ByteArrayOutputStream BO = new ByteArrayOutputStream();
			URL maxFile = JMEDescriptionHelper.class.getClassLoader().getResource("ATRON.3DS");
			C1.convert(new BufferedInputStream(maxFile.openStream()),BO);
			Node atronNode = (Node)BinaryImporter.getInstance().load(new ByteArrayInputStream(BO.toByteArray()));
			atronModel = (TriMesh)(((Node)atronNode.getChild(0)).getChild(0));
			atronModel.setLocalScale(0.092f*radius);
			System.out.println("CAD Model loaded - nTriangles = "+atronNode.getTriangleCount());
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}
