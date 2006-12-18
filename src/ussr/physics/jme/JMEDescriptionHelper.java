/**
 * 
 */
package ussr.physics.jme;

import java.awt.Color;
import java.util.List;

import ussr.description.GeometryDescription;
import ussr.description.SphereShape;

import com.jme.bounding.BoundingSphere;
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
        meshSphere.setModelBound( new BoundingSphere() );
        meshSphere.updateModelBound();
        moduleNode.attachChild( meshSphere );
        return meshSphere;
    }

}
