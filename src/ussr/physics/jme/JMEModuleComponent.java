package ussr.physics.jme;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ussr.model.Connector;
import ussr.model.Module;
import ussr.physics.PhysicsModuleComponent;
import ussr.physics.PhysicsSimulation;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.ReceivingDevice;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RobotDescription;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.TransmissionDevice;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.RobotDescription.ConnectorType;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.geometry.PhysicsCapsule;
import com.jmex.physics.geometry.PhysicsMesh;

public class JMEModuleComponent implements PhysicsModuleComponent {
    /**
     * The abstract module represented by the jme entity  
     */
    private Module model;
    
    /**
     * The world in which this module is being simulated
     */
    private JMESimulation world;
    
    private RobotDescription selfDesc;

    /**
     * The complete set of dynamic nodes representing this module
     */
    private List<DynamicPhysicsNode> dynamicNodes = new ArrayList<DynamicPhysicsNode>();

    /**
     * The connectors associated with this module
     */
    private List<JMEConnector> connectors = new ArrayList<JMEConnector>();
    
    private DynamicPhysicsNode moduleNode;
    private List<TriMesh> geometries = new ArrayList<TriMesh>();

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
        this.selfDesc = robot.getDescription();
        // Setup dynamic physics node
        moduleNode = dynamicNode;
        dynamicNodes.add(moduleNode);
        // Create visual appearance
        TriMesh shape = JMEDescriptionHelper.createShape(moduleNode, name, element);
        geometries.add(shape);
        world.associateGeometry(moduleNode,shape);
        JMEDescriptionHelper.setColor(world,shape,element.getColor());

        // Finalize
        
        //PhysicsCapsule cap = moduleNode.createCapsule(""); //erstat med denne form for collision geometrier
        //cap.setLocalScale(new Vector3f(0.060f,1,0f));
        //PhysicsMesh mesh = moduleNode.createMesh("");
        //mesh.copyFrom(triMesh);

   		moduleNode.generatePhysicsGeometry(element.getAccurateCollisionDetection());
   		moduleNode.computeMass(); //do we always want to do that?
   		world.getRootNode().attachChild( moduleNode );       
        
        
        // Create connectors
        for(VectorDescription p: selfDesc.getConnectorPositions()) {
            Vector3f position = new Vector3f(p.getX(), p.getY(), p.getZ());
            JMEConnector connector = createConnector(world, name, position, selfDesc);
            model.addConnector(new Connector(connector));
            connectors.add(connector);
        }
        //TODO change this way of creating communication sometimes we can it to be at a connector
        // Create communicators
        for(TransmissionDevice transmitter: selfDesc.getTransmitters())
            model.addTransmissionDevice(JMEDescriptionHelper.createTransmitter(model, model,transmitter));
        // Create communicators
        for(ReceivingDevice receiver: selfDesc.getReceivers())
            model.addReceivingDevice(JMEDescriptionHelper.createReceiver(model, model, receiver));
            
    }
	public void addConnector(String name, Vector3f position, Color color, Quaternion rotation) {
		addConnector(name, position, color);
		getConnector(name).setRotation(rotation);
	}
	public void addConnector(String name, Vector3f position, Color color) {
		addConnector(name, position);
		getConnector(name).setConnectorColor(color);
	}
    public void addConnector(String name, Vector3f position) {
    	 JMEConnector connector = createConnector(world, name, position, selfDesc);
         model.addConnector(new Connector(connector));
         connectors.add(connector);
    }
    public JMEConnector getConnector(int index) {
        return connectors.get(index);
    }
    public JMEConnector getConnector(String name) {
    	for(JMEConnector c: connectors)	{
    		if(c.getName().equals(name)) return c;
    	}
    	return null;
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
        else if(type==ConnectorType.MECHANICAL_CONNECTOR_RIGID)
            connector = new JMERigidMechanicalConnector(position,moduleNode,name,world,this,selfDesc);
        else if(type==ConnectorType.MECHANICAL_CONNECTOR_BALL_SOCKET)
            connector = new JMEBallSocketConnector(position,moduleNode,name,world,this,selfDesc);
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
        for(DynamicPhysicsNode node: dynamicNodes) {
        	for(Spatial child:  node.getChildren()) {
        		JMEDescriptionHelper.setColor(world, child, color);        	
        	}
        }
    }

    public PhysicsSimulation getSimulation() {
        return world;
    }
    
    public DynamicPhysicsNode getModuleNode() {
        return moduleNode;
    }

    public List<TriMesh> getComponentGeometries() {
        return geometries;
    }

    public List<TriMesh> getModuleGeometries() {
        throw new Error("not implemented");
    }
	public VectorDescription getPosition() {
		Vector3f p = moduleNode.getWorldTranslation(); //TODO not testet
		return new VectorDescription(p.x,p.y,p.z);
	}
	public RotationDescription getRotation() {
		return new RotationDescription(moduleNode.getWorldRotation()); //TODO not testet
	}
}
