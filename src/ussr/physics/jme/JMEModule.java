package ussr.physics.jme;

import java.awt.Color;
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
import ussr.description.ReceivingDevice;
import ussr.description.RobotDescription;
import ussr.description.SphereShape;
import ussr.description.TransmissionDevice;
import ussr.description.VectorDescription;
import ussr.model.Connector;
import ussr.model.Module;
import ussr.model.Robot;
import ussr.physics.PhysicsModule;
import ussr.physics.PhysicsSimulation;

public class JMEModule implements PhysicsModule {
    /**
     * The abstract module represented by the jme entity  
     */
    private Module model;
    
    /**
     * The world in which this module is being simulated
     */
    private JMESimulation world;

    /**
     * The complete set of dynamic nodes representing this module
     */
    private List<DynamicPhysicsNode> dynamicNodes = new ArrayList<DynamicPhysicsNode>();

    /**
     * The connectors associated with this module
     */
    private List<JMEConnector> connectors = new ArrayList<JMEConnector>();
    
    private DynamicPhysicsNode moduleNode;
    
    /**
     * 
     * @param world
     * @param robot
     * @param name
     */
    public JMEModule(JMESimulation world, Robot robot, String name) {
        this.world = world;
        this.model = new Module(this);
        this.model.setController(robot.createController());
        RobotDescription selfDesc = robot.getDescription();
        // Create central module node
        moduleNode = world.getPhysicsSpace().createDynamicNode();
        dynamicNodes.add(moduleNode);
        // Create visual appearance
        assert selfDesc.getModuleGeometry().size()==1; // Only tested with size 1 geometry
        for(GeometryDescription element: selfDesc.getModuleGeometry())
            JMEDescriptionHelper.createShape(moduleNode, name, element);
        // Finalize
        moduleNode.generatePhysicsGeometry();
        world.getRootNode().attachChild( moduleNode );
        moduleNode.computeMass();
        // Create connectors
        for(VectorDescription p: selfDesc.getConnectorPositions()) {
            Vector3f position = new Vector3f(p.getX(), p.getY(), p.getZ());
            List<GeometryDescription> geometry = selfDesc.getConnectorGeometry();
            float maxDistance = selfDesc.getMaxConnectionDistance();
            JMEConnector connector = null;
            switch( selfDesc.getConnectorType() ) {
            case RobotDescription.STICKY_CONNECTOR:
                    connector = new JMEStickyConnector(position,moduleNode,name,geometry,world,this,maxDistance);
            		break;
            case RobotDescription.ATRON_CONNECTOR:
                    connector = new JMEATRONConnector(position,moduleNode,name,geometry,world,this,maxDistance);
            		break;
            	default:
            		
            }
            model.addConnector(new Connector(connector));
            connectors.add(connector);
        }
        // Create communicators
        for(TransmissionDevice transmitter: selfDesc.getTransmitters())
            model.addTransmissionDevice(JMEDescriptionHelper.createTransmitter(model,transmitter));
        // Create communicators
        for(ReceivingDevice receiver: selfDesc.getReceivers())
            model.addReceivingDevice(JMEDescriptionHelper.createReceiver(model, receiver));
    }
    
    /**
     * @return the dynamicNode
     */
    public List<DynamicPhysicsNode> getNodes() {
        return dynamicNodes;
    }

    public void reset() {
        for(JMEConnector connector: connectors)
            connector.reset();
    }

    public void changeNotify() {
        model.eventNotify();        
    }

    public Module getModel() {
        return model;
    }

    public void setColor(Color color) {
        for(DynamicPhysicsNode node: dynamicNodes) {
            node.setRenderState(world.color2jme(color));
            node.updateRenderState();
        }
    }

    public PhysicsSimulation getSimulation() {
        return world;
    }
    
    public DynamicPhysicsNode getModuleNode() {
        return moduleNode;
    }
}
