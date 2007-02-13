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
import ussr.robotbuildingblocks.ConeShape;
import ussr.robotbuildingblocks.CylinderShape;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.ReceivingDevice;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.SphereShape;
import ussr.robotbuildingblocks.TransmissionDevice;
import ussr.robotbuildingblocks.VectorDescription;

import com.jme.bounding.BoundingBox;
import com.jme.bounding.BoundingSphere;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.SceneElement;
import com.jme.scene.SharedMesh;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Cone;
import com.jme.scene.shape.Cylinder;
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

    public static TriMesh createShape(DynamicPhysicsNode moduleNode, String name, GeometryDescription element) {
    	TriMesh shape = null;
        if(element instanceof SphereShape) {
        	shape = new Sphere( name, 9, 9, ((SphereShape)element).getRadius()); 
        	shape.setModelBound( new BoundingSphere() );
        }
        else if(element instanceof AtronShape) {
        	AtronShape half = (AtronShape) element;
        	shape = constructAtronModel(name, half);
        	shape.setModelBound(new BoundingSphere());
        //	shape = moduleNode.createBox(name);
        	/*shape = new Sphere( name, 9, 9, 0.025f); 
        	if(((AtronShape) element).isNorth()) shape.setLocalTranslation(new Vector3f(0.0f,0,0.1f));
        	shape.setModelBound( new BoundingSphere() );*/
        }
        else if(element instanceof ConeShape) {
        	shape = new Cone(name,8, 8, ((ConeShape)element).getRadius(),((ConeShape)element).getHeight(),true); 
        	shape.setModelBound( new BoundingSphere() );
        }
        else if(element instanceof CylinderShape) {
        	shape = new Cylinder(name, 8, 8, ((CylinderShape)element).getRadius(),((CylinderShape)element).getHeight(),true); 
        	shape.setModelBound(new BoundingBox()); //BoundingBox makes the simulation "unstable?"
        }
        else throw new Error("Only sphere and atron geometries supported for now");
        
        VectorDescription translation = element.getTranslation();
    	shape.getLocalTranslation().set( translation.getX(), translation.getY(), translation.getZ() );
    	
    	RotationDescription rotation = element.getRotation();
    	//shape.getLocalRotation().set(rotation.getRotation());
    	shape.setLocalRotation(rotation.getRotation());
        shape.updateModelBound();
    	moduleNode.attachChild(shape);
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
		if(!half.isNorth()) atronMesh.setLocalScale(0.95f*atronMesh.getLocalScale().x);
		return atronMesh;
	}
    public static void loadAtronModel(float radius) {
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
