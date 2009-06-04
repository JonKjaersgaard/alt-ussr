/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics.jme.connectors;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import ussr.description.geometry.GeometryDescription;
import ussr.description.robot.ConnectorDescription;
import ussr.physics.jme.JMEGeometryHelper;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.scene.Spatial.CullHint;
import com.jmex.font3d.Font3D;
import com.jmex.font3d.Text3D;
import com.jmex.physics.DynamicPhysicsNode;

/**
 * Connector geometry: the visual appearance of a connector
 * 
 * @author ups
 */
public class JMEConnectorGeometry {
	private Color initialColor = null;
	private volatile TriMesh mesh;
	private volatile JMESimulation world;
	private Vector3f localPosition;
	
	public JMEConnectorGeometry(String name, Vector3f position, DynamicPhysicsNode node, JMESimulation world, JMEModuleComponent component, ConnectorDescription description) {
		this.world= world;
		this.localPosition = position;
		// Create visual appearance
        List<GeometryDescription> geometry = description.getGeometry();
        assert geometry.size()==1; // Only tested with size 1 geometry
        for(GeometryDescription element: geometry) {
        	mesh = JMEGeometryHelper.createShape(node, "Connector mesh for "+name, element);
        	mesh.getLocalTranslation().set( mesh.getLocalTranslation().add(new Vector3f(position)) );
            //TODO Mesh is already rotated - change this to here 
            node.attachChild( mesh );
        	
        /*	Font3D fond =  new Font3D(new Font("Arial", Font.PLAIN, 2), 0.1, true, true, true);
        	Node textNode = new Text3D(fond,"0",0.050f);
        	textNode.setIsCollidable(false);
        	textNode.getLocalTranslation().set( textNode.getLocalTranslation().add(new Vector3f(position)) );
        	
        	Spatial textNode2 = (new Font2D()).createText("1234", 1f, 0);
        	textNode2.getLocalTranslation().set( mesh.getLocalTranslation().add(new Vector3f(position)) );
            node.attachChild(textNode);*/
        	
        	
            component.getComponentGeometries().add(mesh);
            world.associateGeometry(name, mesh);
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
			mesh.setCullHint(CullHint.Dynamic);
		}
		else {
			mesh.setCullHint(CullHint.Always);
		}
	}

    public Color getColor() {
        return world.getHelper().getColor(mesh);
    }
}