/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
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
import ussr.physics.PhysicsParameters;
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
 * Factory for creating CKBot modules
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class JMECKBotFactory implements ModuleFactory {
    private JMESimulation simulation;
    float pi = (float)Math.PI;
    DynamicPhysicsNode centerNode, northNode, southNode;

    /**
     * Create modules
     */
    public synchronized void createModule(int module_id, Module module, Robot robot, String module_name) {
    	if(simulation==null) throw new Error("Internal error: factory not initialized");
        if(robot.getDescription().getType()=="CKBotStandard")
            createCKBotStandard(module_id, module, robot, module_name);
        else if(robot.getDescription().getType()=="CKBotL7")
            createCKBotL7(module_id, module, robot); 
        else if(robot.getDescription().getType()=="CKBotSpring")
            createCKBotSpring(module_id, module, robot);
        else throw new Error("Illegal module type: "+robot.getDescription().getType());
        
        ((JMEModuleComponent)module.getPhysics().get(0)).getModuleNode().setName(module_name);
    }
    
    private void createCKBotStandard(int module_id, Module module, Robot robot, String module_name) {
    	DynamicPhysicsNode headNode = simulation.getPhysicsSpace().createDynamicNode();
    	DynamicPhysicsNode tailNode = simulation.getPhysicsSpace().createDynamicNode();
    	
    	if(robot.getDescription().getModuleComponents().size()!=2) throw new RuntimeException("Not an CKBot Standard module");
    	
    	List<ModuleComponentDescription> components = robot.getDescription().getModuleComponents();
    	JMEModuleComponent headComponent = new JMEModuleComponent(simulation,robot,components.get(0),module_name+"_module#"+Integer.toString(module_id)+".head",module,headNode);
    	headNode.setName("CKBotStandardHead");
    	Vector3f localHeadPos = new Vector3f(0,0,0);
    	headNode.getLocalTranslation().set( headNode.getLocalTranslation().add(localHeadPos) );
    	headComponent.setLocalPosition(localHeadPos);
    	
    	JMEModuleComponent tailComponent = new JMEModuleComponent(simulation,robot,components.get(1),module_name+"_module#"+Integer.toString(module_id)+".tail",module,tailNode);
    	tailNode.setName("CKBotStandardTail");
    	Vector3f localTailPos = new Vector3f(0,0,0);
    	tailNode.getLocalTranslation().set( tailNode.getLocalTranslation().add(localTailPos) );
    	tailComponent.setLocalPosition(localTailPos);
    	
    	module.addComponent(headComponent);
    	module.addComponent(tailComponent);
    	simulation.getModuleComponents().add(headComponent);
    	simulation.getModuleComponents().add(tailComponent);
    	
        headNode.setMaterial(Material.RUBBER); // Better traction for driving experiments
        tailNode.setMaterial(Material.RUBBER);
        
		headNode.getMaterial().putContactHandlingDetails(simulation.getStaticPlane().getMaterial(), getDefaultCKBotContactDetails());
		tailNode.getMaterial().putContactHandlingDetails(simulation.getStaticPlane().getMaterial(), getDefaultCKBotContactDetails());
    
		headNode.setMass(0.05f);
		tailNode.setMass(0.09f);
		
	    headNode.setName(module_name+" Head");
	    tailNode.setName(module_name+" Tail");
	   
		JMERotationalActuator tailActuator = new JMERotationalActuator(simulation,"tail");
        module.addActuator(new Actuator(tailActuator));
        tailActuator.attach(headNode,tailNode);
        
        float stepSize = PhysicsParameters.get().getPhysicsSimulationStepSize();
        //float velocity = 0.01f/stepSize*6.28f/4;
        float velocity = 6.28f/4;
        
        tailActuator.setControlParameters(8f, velocity, -pi/2, pi/2); //maxacc=20 before, vel =2
        tailActuator.setPIDParameters(10f, 0, 0);
        tailActuator.setDirection(0, 1, 0);  

		for(int i=0;i<3;i++) {
        	JMEMechanicalConnector c = (JMEMechanicalConnector)headComponent.getConnector(i);
        	c.setUpdateFrequency(10);
        	c.setTimeToConnect(0.1f);
        	c.setTimeToDisconnect(2.0f);
        	c.setConnectorType(JMEMechanicalConnector.UNISEX);
        }
		
		for(int i=0;i<1;i++) {
        	JMEMechanicalConnector c = (JMEMechanicalConnector)tailComponent.getConnector(i);
        	c.setUpdateFrequency(10);
        	c.setTimeToConnect(0.1f);
        	c.setTimeToDisconnect(2.0f);
        	c.setConnectorType(JMEMechanicalConnector.UNISEX);
        }
		TransmissionDevice ckBotTrans = new TransmissionDevice(TransmissionType.WIRE_UNISEX,0.05f);
        ReceivingDevice ckBotRec = new ReceivingDevice(TransmissionType.WIRE_UNISEX,10);
        for(int channel=0;channel<4;channel++) {
            module.addTransmissionDevice(JMEGeometryHelper.createTransmitter(module, module.getConnectors().get(channel),ckBotTrans)); //use connector hardware for communication!
            module.addReceivingDevice(JMEGeometryHelper.createReceiver(module, module.getConnectors().get(channel),ckBotRec));
            module.getTransmitters().get(channel).setMaxBaud(19200);
            module.getTransmitters().get(channel).setMaxBufferSize(128);
        }	
    }
    
    private void createCKBotL7(int module_id, Module module, Robot robot) {
        
    }
    
    private void createCKBotSpring(int module_id, Module module, Robot robot) {
        
    }
	
	private MutableContactInfo getDefaultCKBotContactDetails() {
		MutableContactInfo contactDetails = new MutableContactInfo();
		contactDetails.setBounce( 0.01f );
		contactDetails.setMu(100f);
		contactDetails.setMuOrthogonal(100f);
		contactDetails.setMinimumBounceVelocity(100);
		contactDetails.setSlip(new Vector2f(0.0f,0.0f));
		return contactDetails; 
	}

	public String getModulePrefix() {
        return "CKBot";
    }

    public void setSimulation(PhysicsSimulation simulation) {
        this.simulation = (JMESimulation)simulation;
    }

}
