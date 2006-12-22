/**
 * 
 */
package ussr.physics.jme;

import java.awt.Color;
import java.util.List;

import ussr.comm.GenericReceiver;
import ussr.comm.GenericTransmitter;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.model.Module;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.ReceivingDevice;
import ussr.robotbuildingblocks.SphereShape;
import ussr.robotbuildingblocks.TransmissionDevice;
import ussr.robotbuildingblocks.VectorDescription;

import com.jme.bounding.BoundingSphere;
import com.jme.scene.SceneElement;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Sphere;
import com.jme.scene.state.MaterialState;
import com.jme.scene.state.RenderState;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.material.Material;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class JMEDescriptionHelper {

    public static TriMesh createShape(DynamicPhysicsNode moduleNode, String name,GeometryDescription element) {
        if(!(element instanceof SphereShape)) 
            throw new Error("Only sphere geometries supported for now"); 
        Sphere meshSphere = new Sphere( name, 9, 9, ((SphereShape)element).getRadius()); 
        VectorDescription translation = ((SphereShape)element).getTranslation();
        meshSphere.getLocalTranslation().set( translation.getX(), translation.getY(), translation.getZ() );
        meshSphere.setModelBound( new BoundingSphere() );
        meshSphere.updateModelBound();
        moduleNode.attachChild( meshSphere );
        return meshSphere;
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

}
