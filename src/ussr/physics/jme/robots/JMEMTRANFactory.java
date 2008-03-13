package ussr.physics.jme.robots;

import java.awt.Color;
import java.util.List;

import ussr.comm.TransmissionType;
import ussr.description.Robot;
import ussr.description.geometry.BoxShape;
import ussr.description.geometry.CylinderShape;
import ussr.description.geometry.GeometryDescription;
import ussr.description.geometry.RotationDescription;
import ussr.description.robot.ModuleComponentDescription;
import ussr.description.robot.ReceivingDevice;
import ussr.description.robot.TransmissionDevice;
import ussr.model.Actuator;
import ussr.model.Module;
import ussr.model.Sensor;
import ussr.physics.ModuleFactory;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMEGeometryHelper;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.actuators.JMERotationalActuator;
import ussr.physics.jme.connectors.JMEMechanicalConnector;
import ussr.physics.jme.sensors.JMEProximitySensor;
import ussr.physics.jme.sensors.JMETiltSensor;

import com.jme.math.Quaternion;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.contact.MutableContactInfo;
import com.jmex.physics.material.Material;

/**
 * Factory for creating ATRON modules
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class JMEMTRANFactory implements ModuleFactory {
    private JMESimulation simulation;
    float pi = (float)Math.PI;
    DynamicPhysicsNode centerNode, northNode, southNode;

    /**
     * Create the module
     */
    public synchronized void createModule(int module_id, Module module, Robot robot, String module_name) {
        if(!robot.getDescription().getType().startsWith("MTRAN")) throw new Error("Illegal module type: "+robot.getDescription().getType());
        
        if(robot.getDescription().getModuleComponents().size()!=3) throw new RuntimeException("Not an MTRAN");
        createModuleComponents(module, robot, module_id, module_name);
        
        setMaterials(module, robot,module_name);
        setMass(0.2f,northNode);
        setMass(0.2f,southNode);
        setMass(0.05f,southNode);
        
        setName(module, module_name);
        addActuators(module, northNode, southNode, centerNode);
        
        addConnectors(module);
    }
    
    private void setName(Module module, String module_name) {
    	DynamicPhysicsNode northNode = ((JMEModuleComponent) module.getComponent(0)).getModuleNode();
        DynamicPhysicsNode southNode = ((JMEModuleComponent) module.getComponent(1)).getModuleNode();
        northNode.setName(module_name+" North");
        southNode.setName(module_name+" South");
	}

	private void createModuleComponents(Module module, Robot robot, int module_id, String module_name) {
		
        /*CylinderShape northCylinder = (CylinderShape) robot.getDescription().getModuleGeometry().get(0);
        CylinderShape southCylinder = (CylinderShape) robot.getDescription().getModuleGeometry().get(1);
        BoxShape northBox = (BoxShape) robot.getDescription().getModuleGeometry().get(2);
        BoxShape southBox = (BoxShape) robot.getDescription().getModuleGeometry().get(3);
        GeometryDescription centerCylinder = (GeometryDescription) robot.getDescription().getModuleGeometry().get(4);*/
        
        List<ModuleComponentDescription> components = robot.getDescription().getModuleComponents();

        northNode = simulation.getPhysicsSpace().createDynamicNode();
        JMEModuleComponent northComponent = new JMEModuleComponent(simulation,robot,components.get(0),module_name+"_module#"+Integer.toString(module_id)+".north",module,northNode);
        northNode.setName("MTRANNorth");
        Vector3f localNorthPos = new Vector3f(-0.075f/2,0,0);
        northNode.getLocalTranslation().set( northNode.getLocalTranslation().add(localNorthPos) );
        northComponent.setLocalPosition(localNorthPos);
            
        southNode = simulation.getPhysicsSpace().createDynamicNode();
        JMEModuleComponent southComponent = new JMEModuleComponent(simulation,robot,components.get(1),module_name+"module#"+Integer.toString(module_id)+".south",module,southNode);
        southNode.setName("MTRANSouth");
        Vector3f localSouthPos = new Vector3f(0.075f/2,0,0);
        southNode.getLocalTranslation().set( southNode.getLocalTranslation().add(localSouthPos) );
        southComponent.setLocalPosition(localSouthPos);
        
        centerNode = simulation.getPhysicsSpace().createDynamicNode();
        JMEModuleComponent centerComponent = new JMEModuleComponent(simulation,robot,components.get(2),module_name+"module#"+Integer.toString(module_id)+".center",module,centerNode);
        centerNode.setName("MTRANCenter");
        
        module.addComponent(northComponent);
        module.addComponent(centerComponent);
        module.addComponent(southComponent); 
        simulation.getModuleComponents().add(northComponent);
        simulation.getModuleComponents().add(centerComponent);
        simulation.getModuleComponents().add(southComponent);

        /*//optimize collision detection by adding joint? - no how to fix? 
        Joint joint = simulation.getPhysicsSpace().createJoint();
		joint.attach(southNode, northNode);
		RotationalJointAxis xAxis, yAxis;
		xAxis = joint.createRotationalAxis(); xAxis.setDirection(new Vector3f(1,0,0));
    	yAxis = joint.createRotationalAxis(); yAxis.setDirection(new Vector3f(0,1,0));
    	yAxis.setRelativeToSecondObject(true);
		joint.setActive(true);*/
	}
	
	private void setMaterials(Module module, Robot robot, String module_name) {
        northNode.setMaterial(Material.RUBBER); // Better traction for driving experiments
        southNode.setMaterial(Material.RUBBER);
	}

	private void addActuators(Module module, DynamicPhysicsNode northNode, DynamicPhysicsNode southNode, DynamicPhysicsNode centerNode ) {
		JMERotationalActuator northActuator = new JMERotationalActuator(simulation,"north");
        module.addActuator(new Actuator(northActuator));
        northActuator.attach(centerNode,northNode);
        northActuator.setControlParameters(20, 2f, -pi/2, pi/2);
        northActuator.setPIDParameters(10f, 0, 0);
        northActuator.setDirection(0, 1, 0);
        
        JMERotationalActuator southActuator = new JMERotationalActuator(simulation,"south");
        module.addActuator(new Actuator(southActuator));
        southActuator.attach(centerNode,southNode);
        southActuator.setControlParameters(20f, 2f, -pi/2, pi/2);
        southActuator.setPIDParameters(10f, 0, 0);
        southActuator.setDirection(0, 1, 0);
	}

	private void setMass(float mass, DynamicPhysicsNode node) {
	    node.setMass(mass);
	}

	private void addConnectors(Module module) {
        JMEModuleComponent northComponent =  (JMEModuleComponent) module.getComponent(0);
        setConnectorProperties(northComponent, true);
        JMEModuleComponent southComponent =  (JMEModuleComponent) module.getComponent(2);
        setConnectorProperties(southComponent,false);   
	}

	private void setConnectorProperties(JMEModuleComponent component, boolean male) {
		for(int i=0;i<3;i++) {
        	JMEMechanicalConnector c = (JMEMechanicalConnector)component.getConnector(i);
        	c.setUpdateFrequency(10);
        	c.setTimeToConnect(3.0f);
        	c.setTimeToDisconnect(2.0f);
        	if(male) c.setConnectorType(JMEMechanicalConnector.MALE);
        	else c.setConnectorType(JMEMechanicalConnector.FEMALE);
        }
	}

	public String getModulePrefix() {
        return "MTRAN";
    }

    public void setSimulation(PhysicsSimulation simulation) {
        this.simulation = (JMESimulation)simulation;
    }

}
