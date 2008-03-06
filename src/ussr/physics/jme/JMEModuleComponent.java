package ussr.physics.jme;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ussr.model.Connector;
import ussr.model.Module;
import ussr.physics.PhysicsModuleComponent;
import ussr.physics.PhysicsQuaternionHolder;
import ussr.physics.PhysicsSimulation;
import ussr.physics.PhysicsSimulationHelper;
import ussr.physics.jme.actuators.JMEActuator;
import ussr.physics.jme.connectors.JMEBallSocketConnector;
import ussr.physics.jme.connectors.JMEConnector;
import ussr.physics.jme.connectors.JMEHingeMechanicalConnector;
import ussr.physics.jme.connectors.JMEMagneticConnector;
import ussr.physics.jme.connectors.JMERigidMechanicalConnector;
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
import com.jme.scene.shape.Sphere;
import com.jmex.physics.DynamicPhysicsNode;

/**
 * The JME implementation of a module component: a fixed physics element that can be attached to
 * other module components using actuators to form a complete module.
 * 
 * @author Modular Robots @ MMMI
 *
 */
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
        TriMesh shape = JMEGeometryHelper.createShape(moduleNode, name, element);
        geometries.add(shape);
        world.associateGeometry(moduleNode,shape);
        world.getHelper().setColor(shape,element.getColor());

        moduleNode.setIsCollidable(true);
        moduleNode.generatePhysicsGeometry(element.getAccurateCollisionDetection());
   		//moduleNode.computeMass(); //do we always want to do that?
   		
   		world.getRootNode().attachChild( moduleNode );   
        
        
        // Create connectors
   		int counter = 0;
        for(VectorDescription p: selfDesc.getConnectorPositions()) {
            Vector3f position = new Vector3f(p.getX(), p.getY(), p.getZ());
            this.addConnector("Connector "+(counter++), position, Color.BLACK, new Quaternion());
        }
        //TODO change this way of creating communication sometimes we can it to be at a connector
        // Create communicators
        for(TransmissionDevice transmitter: selfDesc.getTransmitters())
            model.addTransmissionDevice(JMEGeometryHelper.createTransmitter(model, model,transmitter));
        // Create communicators
        for(ReceivingDevice receiver: selfDesc.getReceivers())
            model.addReceivingDevice(JMEGeometryHelper.createReceiver(model, model, receiver));
            
    }
	public void addConnector(String name, Vector3f position, Color color, Quaternion rotation) {
		addConnector(name, position, color);
		getConnector(name).setRotation(new PhysicsQuaternionHolder(rotation));
	}
	public void addConnector(String name, Vector3f position, Color color) {
		addConnector(name, position);
		getConnector(name).setConnectorColor(color);
	}
    public void addConnector(String name, Vector3f position) {
    	 JMEConnector connector = createConnector(world, name, position, selfDesc);
         Connector c = new Connector(connector);
         model.addConnector(c);
         c.setProperty("name", name);
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
        else if(type==ConnectorType.MECHANICAL_CONNECTOR_HINGE)
        	connector = new JMEHingeMechanicalConnector(position,moduleNode,name,world,this,selfDesc); 
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

    public void setModuleComponentColor(Color color) {
        for(DynamicPhysicsNode node: dynamicNodes) {
        	for(Spatial child:  node.getChildren()) {
        		world.getHelper().setColor(child, color);
        	}
        }
    }
    public Color getModuleComponentColor() {
    	return world.getHelper().getColor(dynamicNodes.get(0).getChild(0));
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
		Vector3f p = moduleNode.getLocalTranslation(); //TODO not testet
		return new VectorDescription(p.x,p.y,p.z);
	}
	public RotationDescription getRotation() {
		return new RotationDescription(moduleNode.getLocalRotation()); //TODO not testet
	}
    public PhysicsSimulationHelper getSimulationHelper() {
        return this.getSimulation().getHelper();
    }
    
    Quaternion localRotation = new Quaternion();
    Vector3f localPosition = new Vector3f();
    /**
     * Local means relative to module position
     * 
     */
    public void setLocalPosition(Vector3f p) {
    	localPosition = p;
    }
    public VectorDescription getLocalPosition() {
    	return new VectorDescription(localPosition);
    }
    public void setLocalRotation(Quaternion q) {
    	localRotation = q;
    }
    public RotationDescription getLocalRotation() {
    	return new RotationDescription(localRotation);
    }
	public void setPosition(VectorDescription p) {
		getModuleNode().getLocalTranslation().set(p.getX(), p.getY(), p.getZ()).addLocal(localPosition);
		getModuleNode().updateWorldVectors();
	}
	public void setRotation(RotationDescription p) {
        getModuleNode().setLocalRotation(new Quaternion(p.getRotation().mult(localRotation)));
        getModuleNode().updateWorldVectors();
	}
	public void clearDynamics() {
		getModuleNode().clearDynamics();
		
	}
}
