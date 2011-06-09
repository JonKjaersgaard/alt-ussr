/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics.jme;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import ussr.description.Robot;
import ussr.description.geometry.GeometryDescription;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.robot.ConnectorDescription;
import ussr.description.robot.ModuleComponentDescription;
import ussr.description.robot.ReceivingDevice;
import ussr.description.robot.RobotDescription;
import ussr.description.robot.TransmissionDevice;
import ussr.description.robot.ConnectorDescription.Type;
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
import ussr.physics.jme.connectors.JMEMechanicalConnector;
import ussr.physics.jme.connectors.JMERigidMechanicalConnector;
import ussr.physics.jme.connectors.JMEVelcroConnector;
import ussr.visualization.VisualizationParameters;

import com.jme.math.Matrix4f;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Spatial;
import com.jme.scene.TriMesh;
import com.jme.scene.Spatial.CullHint;
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
    public JMEModuleComponent(JMESimulation world, Robot robot, ModuleComponentDescription description, String name, Module module, DynamicPhysicsNode dynamicNode) {
        this.world = world;
        this.model = module;
        this.selfDesc = robot.getDescription();
        // Setup dynamic physics node
        moduleNode = dynamicNode;
        dynamicNodes.add(moduleNode);
        // Create visual appearance
        for(GeometryDescription geometry: description.getGeometry()) {
            TriMesh shape = JMEGeometryHelper.createShape(moduleNode, name, geometry);
            geometries.add(shape);
            world.associateGeometry(name,shape);
            world.getHelper().setColor(shape,geometry.getColor());
            moduleNode.setIsCollidable(true);
            moduleNode.generatePhysicsGeometry(geometry.getAccurateCollisionDetection());
        	if(VisualizationParameters.get().getShowPhysicalModules() == false) {
        		shape.setCullHint( CullHint.Always );
        	}
            
        }
        // Attach to the world
   		world.getRootNode().attachChild( moduleNode );   
        
        // Create connectors
   		int counter = 0;
        for(ConnectorDescription cd: description.getConnectors()) {
            int index = model.getConnectors().size();
            VectorDescription p = cd.getPosition();
            Vector3f position = new Vector3f(p.getX(), p.getY(), p.getZ());
            String cname = cd.getName()==null ? "[Hemisphere connector "+(counter++)+"]" : cd.getName();
            cname+=" Module connector #"+index;
            JMEConnector c = this.addConnector(cname, position, cd, cd.getRotation().getRotation());
            c.getModel().setProperty("ussr.connector_number", Integer.toString(index));
            //System.out.println("Module "+name+", connector #"+index+" has name "+cname);
        }
        //TODO change this way of creating communication sometimes we can it to be at a connector
        // Create communicators
        for(TransmissionDevice transmitter: selfDesc.getTransmitters())
            model.addTransmissionDevice(JMEGeometryHelper.createTransmitter(model, model,transmitter));
        // Create communicators
        for(ReceivingDevice receiver: selfDesc.getReceivers())
            model.addReceivingDevice(JMEGeometryHelper.createReceiver(model, model, receiver));
            
    }
	public JMEConnector addConnector(String name, Vector3f position, ConnectorDescription description, Quaternion rotation) {
		JMEConnector c = addConnector(name, position, description);
		c.setRotation(new PhysicsQuaternionHolder(rotation));
		return c;
	}

	public JMEConnector addConnector(String name, Vector3f position, ConnectorDescription description) {
    	 JMEConnector connector = createConnector(world, name, position, description);
         Connector c = new Connector(connector);
         model.addConnector(c);
         c.setProperty("name", name);
         c.readLabels(description);
         connectors.add(connector);
         return connector;
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
     * @param connectorDescription 
     * @param maxDistance
     * @param type 
     * @return
     */
    private JMEConnector createConnector(JMESimulation world, String name, Vector3f position, ConnectorDescription description) {
        JMEConnector connector;
        ConnectorDescription.Type type = description.getType();
        if(type==ConnectorDescription.Type.MAGNETIC_CONNECTOR)
            connector = new JMEMagneticConnector(position,moduleNode,name,world,this,description);
        else if(type==ConnectorDescription.Type.MECHANICAL_CONNECTOR_RIGID)
            connector = new JMERigidMechanicalConnector(position,moduleNode,name,world,this,description);
        else if(type==ConnectorDescription.Type.MECHANICAL_CONNECTOR_HINGE)
        	connector = new JMEHingeMechanicalConnector(position,moduleNode,name,world,this,description); 
        else if(type==ConnectorDescription.Type.MECHANICAL_CONNECTOR_BALL_SOCKET)
            connector = new JMEBallSocketConnector(position,moduleNode,name,world,this,description);
        else if(type==ConnectorDescription.Type.VELCRO_CONNECTOR)
            connector = new JMEVelcroConnector(position,moduleNode,name,world,this,description);
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
        for(JMEConnector connector: connectors) {
            connector.reset();
        }
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
	private Matrix4f toMatrix(Vector3f pos, Quaternion q) {
    	Matrix4f m = new Matrix4f();
		m.setRotationQuaternion(q);
		m.setTranslation(pos);
		return m;
    }
	public void moveTo(VectorDescription p, RotationDescription r) {
		Matrix4f m1 = toMatrix(new Vector3f(),r.getRotation());
		Matrix4f m2 = toMatrix(localPosition, localRotation);
		Matrix4f mRes = m1.mult(m2);
		Vector3f rotatedLocalPos = mRes.toTranslationVector();
		getModuleNode().getLocalTranslation().set(p.getVector()).addLocal(rotatedLocalPos);
        getModuleNode().setLocalRotation(new Quaternion(r.getRotation().mult(localRotation)));
        getModuleNode().updateWorldVectors();
	}
	
	public void clearDynamics() {
		getModuleNode().clearDynamics();
	}
	public void addExternalForce(float forceX, float forceY, float forceZ) {
		getModuleNode().addForce(new Vector3f(forceX, forceY,forceZ));
	}
	
}
