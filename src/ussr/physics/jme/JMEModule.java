package ussr.physics.jme;

import java.util.ArrayList;
import java.util.List;

import com.jme.bounding.BoundingSphere;
import com.jme.input.InputHandler;
import com.jme.input.action.InputActionEvent;
import com.jme.input.action.InputActionInterface;
import com.jme.input.util.SyntheticButton;
import com.jme.math.Vector3f;
import com.jme.scene.TriMesh;
import com.jme.scene.shape.Sphere;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.Joint;
import com.jmex.physics.contact.ContactInfo;

import ussr.description.GeometryDescription;
import ussr.description.RobotDescription;
import ussr.description.SphereShape;
import ussr.description.VectorDescription;
import ussr.model.Module;
import ussr.model.Robot;
import ussr.physics.PhysicsModule;

public class JMEModule implements PhysicsModule {
    /**
     * The abstract module represented by the jme entity  
     */
    private Module model;
    
    private JMESimulation world;

    public class ModuleCollisionAction implements InputActionInterface {

        public void performAction(InputActionEvent evt) {
            if(!world.connectorsAreActive) return;
            ContactInfo contactInfo = ( (ContactInfo) evt.getTriggerData() );
            String g1 = contactInfo.getGeometry1().getName();
            String g2 = contactInfo.getGeometry2().getName();
            if(world.connectorRegistry.containsKey(g1) && world.connectorRegistry.containsKey(g2)) {
                Joint join = world.getPhysicsSpace().createJoint();
                join.attach(world.connectorRegistry.get(g1),world.connectorRegistry.get(g2));
                world.dynamicJoints.add(join);
            }
        }

    }

    private List<DynamicPhysicsNode> dynamicNodes = new ArrayList<DynamicPhysicsNode>();
    public JMEModule(JMESimulation world, Robot robot, String name) {
        this.world = world;
        RobotDescription selfDesc = robot.getDescription();
        // Create central module node
        DynamicPhysicsNode moduleNode = world.getPhysicsSpace().createDynamicNode();
        dynamicNodes.add(moduleNode);
        // Create visual appearance
        for(GeometryDescription element: selfDesc.getModuleGeometry())
            JMEDescriptionHelper.createShape(moduleNode, name, element);
        // Finalize
        moduleNode.generatePhysicsGeometry();
        world.getRootNode().attachChild( moduleNode );
        moduleNode.computeMass();
        // Create connectors
        for(VectorDescription p: selfDesc.getConnectorPosition())
            addConnector(new Vector3f(p.getX(), p.getY(), p.getZ()),moduleNode,name,selfDesc.getConnectorGeometry());
    }
    
    private void addConnector(Vector3f position, DynamicPhysicsNode moduleNode, String baseName, List<GeometryDescription> geometry) {
        // Create connector node
        DynamicPhysicsNode connector = world.getPhysicsSpace().createDynamicNode();
        dynamicNodes.add(connector);
        // Create visual appearance
        for(GeometryDescription element: geometry) {
            TriMesh mesh = JMEDescriptionHelper.createShape(connector, baseName+position.toString(), element);
            world.connectorRegistry.put(mesh.getName(),connector);
            mesh.getLocalTranslation().set( mesh.getLocalTranslation().add(position) );
            mesh.setModelBound( new BoundingSphere() );
            mesh.updateModelBound();
            connector.attachChild( mesh );
        }
        // Finalize
        connector.generatePhysicsGeometry();
        world.getRootNode().attachChild( connector );
        connector.computeMass();
        // Joint
        Joint connect = world.getPhysicsSpace().createJoint();
        connect.attach(moduleNode, connector);
        // Collision handler
        final SyntheticButton collisionEventHandler = connector.getCollisionEventHandler();
        world.getInput().addAction( new ModuleCollisionAction(),
                collisionEventHandler.getDeviceName(), collisionEventHandler.getIndex(),
                InputHandler.AXIS_NONE, false );
    }

    /**
     * @return the dynamicNode
     */
    public List<DynamicPhysicsNode> getNodes() {
        return dynamicNodes;
    }

    
}
