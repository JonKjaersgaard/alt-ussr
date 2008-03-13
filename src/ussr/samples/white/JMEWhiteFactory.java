package ussr.samples.white;

import java.awt.Color;

import ussr.comm.TransmissionType;
import ussr.description.Robot;
import ussr.description.geometry.BoxShape;
import ussr.description.geometry.RotationDescription;
import ussr.description.robot.ReceivingDevice;
import ussr.description.robot.TransmissionDevice;
import ussr.model.Module;
import ussr.model.Sensor;
import ussr.physics.ModuleFactory;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMEGeometryHelper;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.connectors.JMEConnector;
import ussr.physics.jme.connectors.JMEConnectorAligner;
import ussr.physics.jme.sensors.JMETiltSensor;

import com.jme.math.Quaternion;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.contact.MutableContactInfo;
import com.jmex.physics.material.Material;

public class JMEWhiteFactory implements ModuleFactory {
    private JMESimulation simulation;
    private static final float pi = (float)Math.PI;
    
  //create ATRON
    public void createModule(int module_id, Module module, Robot robot, String module_name) {
        if(!robot.getDescription().getType().startsWith("White")) throw new Error("Illegal module type: "+robot.getDescription().getType());
        
        if(robot.getDescription().getModuleComponents().size()!=1) throw new RuntimeException("Not an White");
        
        createModuleComponents(module, robot, module_id, module_name);
        
        JMEModuleComponent moduleComponent = (JMEModuleComponent)module.getComponent(0);

        setMass(0.2f,moduleComponent.getModuleNode()); //200 grams?
        
        setMaterials(module, robot,module_name);
        addConnectors(module);
        addCommunication(module);
        addTiltSensors(module,moduleComponent.getModuleNode());
     }
    
    private void createModuleComponents(Module module, Robot robot, int module_id, String module_name) {
        DynamicPhysicsNode moduleNode = simulation.getPhysicsSpace().createDynamicNode();
        JMEModuleComponent moduleComponent = new JMEModuleComponent(simulation,robot,robot.getDescription().getModuleComponents().get(0),module_name+"_module#"+Integer.toString(module_id)+".north",module,moduleNode);
        moduleNode.setName("WhiteModule");
        module.addComponent(moduleComponent);
        simulation.getModuleComponents().add(moduleComponent);
	}
    
	private void setMaterials(Module module, Robot robot, String module_name) {
    	DynamicPhysicsNode moduleNode = ((JMEModuleComponent) module.getComponent(0)).getModuleNode();
    	moduleNode.setMaterial(Material.ICE);
	}
	private void addTiltSensors(Module module, DynamicPhysicsNode node) {
    	// Tilt sensors - NOTE: rotation is probably not correct, but is meant to be inverse of connector rotation
    	 JMETiltSensor xsensor = new JMETiltSensor(simulation, "TiltSensor:x", 'x', node, new RotationDescription(0,-pi,-pi/4));
         JMETiltSensor ysensor = new JMETiltSensor(simulation, "TiltSensor:y", 'y', node, new RotationDescription(0,-pi,-pi/4));
         JMETiltSensor zsensor = new JMETiltSensor(simulation, "TiltSensor:z", 'z', node, new RotationDescription(0,-pi,-pi/4));
         module.addSensor(new Sensor(xsensor));
         module.addSensor(new Sensor(ysensor));
         module.addSensor(new Sensor(zsensor));		
	}

	private void setMass(float mass, DynamicPhysicsNode node) {
	    node.setMass(mass);
    	if(Math.abs((node.getMass()-mass))>mass/100) setMass(mass, node); //why oh why is this nesseary???
	}

	private void addCommunication(Module module) {
    	TransmissionDevice atronTrans = new TransmissionDevice(TransmissionType.IR,0.05f);
        ReceivingDevice atronRec = new ReceivingDevice(TransmissionType.IR,10);
        for(int channel=0;channel<4;channel++) {
            module.addTransmissionDevice(JMEGeometryHelper.createTransmitter(module, module.getConnectors().get(channel),atronTrans));
            module.addReceivingDevice(JMEGeometryHelper.createReceiver(module, module.getConnectors().get(channel),atronRec));
        }		
	}

	private void addConnectors(Module module) {
        float maxAlginmentForce = 0.1f;
        float maxAlignmentDistance = 0.1f;
        float epsilonAlignmentDistance = 0.01f;
        //JMEModuleComponent moduleComponent =  (JMEModuleComponent) module.getComponent(0);
        for(int i=0;i<4;i++) {
            JMEConnector connector = (JMEConnector)module.getConnectors().get(i).getPhysics().get(0);
            Vector3f pos = connector.getPos();
        	connector.getConnectorAligner().addAlignmentPoint(pos.add(new Vector3f(0,White.UNIT,0)), 1, 0, maxAlginmentForce, maxAlignmentDistance,epsilonAlignmentDistance);
        	connector.getConnectorAligner().addAlignmentPoint(pos.add(new Vector3f(0,-White.UNIT,0)), 2, 0, maxAlginmentForce,maxAlignmentDistance,epsilonAlignmentDistance);
        	connector.setTimeToConnect(3.0f); //FIXME not a good model instead use a criteria (connect when distance < epsilon)
        	connector.setTimeToDisconnect(0.0f);
        }
         
	}

	public String getModulePrefix() {
        return "White";
    }

    public void setSimulation(PhysicsSimulation simulation) {
    	this.simulation = (JMESimulation)simulation;
    }
}
