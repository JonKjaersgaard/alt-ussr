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

import ussr.model.Connector;
import ussr.model.Module;
import ussr.physics.PhysicsModuleComponent;
import ussr.physics.PhysicsSimulation;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.ReceivingDevice;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RobotDescription;
import ussr.robotbuildingblocks.SphereShape;
import ussr.robotbuildingblocks.TransmissionDevice;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.RobotDescription.ConnectorType;

public class JMEModuleComponent implements PhysicsModuleComponent {
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
     * @param element 
     * @param name
     * @param dynamicNode 
     */
    public JMEModuleComponent(JMESimulation world, Robot robot, GeometryDescription element, String name, Module module, DynamicPhysicsNode dynamicNode) {
        this.world = world;
        this.model = module;
        RobotDescription selfDesc = robot.getDescription();
        // Setup dynamic physics node
        moduleNode = dynamicNode;
        dynamicNodes.add(moduleNode);
        // Create visual appearance
        TriMesh shape = JMEDescriptionHelper.createShape(moduleNode, name, element);
        JMEDescriptionHelper.setColor(world,shape,element.getColor());
        // Finalize
        moduleNode.generatePhysicsGeometry();
        world.getRootNode().attachChild( moduleNode );
        moduleNode.computeMass();
        // Create connectors
        for(VectorDescription p: selfDesc.getConnectorPositions()) {
            Vector3f position = new Vector3f(p.getX(), p.getY(), p.getZ());
            JMEConnector connector = connector = createConnector(world, name, position, selfDesc);
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
     * @param world
     * @param name
     * @param position
     * @param selfDesc
     * @param maxDistance
     * @param type 
     * @return
     */
    private JMEConnector createConnector(JMESimulation world, String name, Vector3f position, RobotDescription selfDesc) {
        JMEConnector connector;
        ConnectorType type = selfDesc.getConnectorType();
        if(type==ConnectorType.MAGNETIC_CONNECTOR)
            connector = new JMEMagneticConnector(position,moduleNode,name,world,this,selfDesc);
        else if(type==ConnectorType.MECHANICAL_CONNECTOR)
            connector = new JMEMechanicalConnector(position,moduleNode,name,world,this,selfDesc);
        else throw new Error("Unknown connector type");
        return connector;
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

    public void setModuleColor(Color color) {
        for(DynamicPhysicsNode node: dynamicNodes)
            JMEDescriptionHelper.setColor(world, node, color);
    }

    public PhysicsSimulation getSimulation() {
        return world;
    }
    
    public DynamicPhysicsNode getModuleNode() {
        return moduleNode;
    }
}
