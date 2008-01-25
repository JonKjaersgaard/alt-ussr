package ussr.physics.jme.connectors;

import java.awt.Color;
import java.util.List;

import ussr.physics.jme.JMEGeometryHelper;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.RobotDescription;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.SceneElement;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jmex.physics.DynamicPhysicsNode;

public class JMEConnectorGeometry {
	private Color initialColor = null;
	private volatile TriMesh mesh;
	private volatile JMESimulation world;
	private Vector3f localPosition;
	
	public JMEConnectorGeometry(Vector3f position, DynamicPhysicsNode node, JMESimulation world, JMEModuleComponent component, RobotDescription selfDesc) {
		this.world= world;
		this.localPosition = position;
		// Create visual appearance
        List<GeometryDescription> geometry = selfDesc.getConnectorGeometry();
        assert geometry.size()==1; // Only tested with size 1 geometry
        for(GeometryDescription element: geometry) {
        	mesh = JMEGeometryHelper.createShape(node, "Mechanical Connector Mesh", element);
            mesh.getLocalTranslation().set( mesh.getLocalTranslation().add(new Vector3f(position)) );
            //TODO Mesh is already rotated - change this to here 
            node.attachChild( mesh );
            component.getComponentGeometries().add(mesh);
            world.associateGeometry(node, mesh);
            world.getHelper().setColor(mesh, element.getColor());
        }
	}
	
	public Vector3f getLocalPosition() {
		return localPosition;
	}
	public Quaternion getLocalRotation() {
		return mesh.getLocalRotation();
	}

	public Spatial getSpatial() {
		return mesh;
	}
	
    public void resetColor() {
        setConnectorColor(initialColor);
    }
    
    public void setConnectorColor(Color color) {
        if(initialColor==null) initialColor = color;
		world.getHelper().setColor(mesh, color);
    }
    
    public void setConnectorVisibility(boolean visible) {
		if(visible) {
			mesh.setCullMode(SceneElement.CULL_DYNAMIC);
		}
		else {
			mesh.setCullMode(SceneElement.CULL_ALWAYS);
		}
	}
}