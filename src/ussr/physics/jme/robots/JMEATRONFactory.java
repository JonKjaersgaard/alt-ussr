/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics.jme.robots;

import java.awt.Color;

import ussr.comm.TransmissionType;
import ussr.description.Robot;
import ussr.description.geometry.MeshShape;
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
import ussr.physics.jme.connectors.JMEConnector;
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
public class JMEATRONFactory implements ModuleFactory {
    private JMESimulation simulation;
    float pi = (float)Math.PI;
    
//create ATRON
    public void createModule(int module_id, Module module, Robot robot, String module_name) {
        if(!robot.getDescription().getType().startsWith("ATRON")) throw new Error("Illegal module type: "+robot.getDescription().getType());
        
        if(robot.getDescription().getModuleComponents().size()!=2) throw new RuntimeException("Not an ATRON");
        createModuleComponents(module, robot, module_id, module_name);
        
        JMEModuleComponent northComponent = (JMEModuleComponent)module.getComponent(0);
        JMEModuleComponent southComponent = (JMEModuleComponent)module.getComponent(1);

        setMass(0.4f,northComponent.getModuleNode());
        setMass(0.4f,southComponent.getModuleNode());
        setMaterials(module, robot,module_name);
        setName(module, module_name);
        updateConnectors(module);
        addCommunication(module, robot);
        addProximitySensors(module);
        addCenterActuator(module, robot);
        addTiltSensors(module,northComponent.getModuleNode());
    }
    
    private void setName(Module module, String module_name) {
    	DynamicPhysicsNode northNode = ((JMEModuleComponent) module.getComponent(0)).getModuleNode();
        DynamicPhysicsNode southNode = ((JMEModuleComponent) module.getComponent(1)).getModuleNode();
        northNode.setName(module_name+" North");
        southNode.setName(module_name+" South");
	}

	private void createModuleComponents(Module module, Robot robot, int module_id, String module_name) {
	    ModuleComponentDescription northDescription = robot.getDescription().getModuleComponents().get(0);
	    ModuleComponentDescription southDescription = robot.getDescription().getModuleComponents().get(1);
        //AtronShape northShape = (AtronShape) northDescription.getGeometry();
	    //AtronShape southShape = (AtronShape) southDescription.getGeometry();
        
        
        DynamicPhysicsNode northNode = simulation.getPhysicsSpace().createDynamicNode();
        JMEModuleComponent northComponent = new JMEModuleComponent(simulation,robot,northDescription,module_name+"_module#"+Integer.toString(module_id)+".north",module,northNode);
        northNode.setName("AtronNorth");

        DynamicPhysicsNode southNode = simulation.getPhysicsSpace().createDynamicNode();
        JMEModuleComponent southComponent = new JMEModuleComponent(simulation,robot,southDescription,module_name+"module#"+Integer.toString(module_id)+".south",module,southNode);
        southNode.setName("AtronSouth");

        module.addComponent(northComponent);
        module.addComponent(southComponent); //hvad skal h�ndteres ved fx placering af moduler?
        
        simulation.getModuleComponents().add(northComponent);
        simulation.getModuleComponents().add(southComponent);
	}
    
	private void setMaterials(Module module, Robot robot, String module_name) {
    	DynamicPhysicsNode northNode = ((JMEModuleComponent) module.getComponent(0)).getModuleNode();
        DynamicPhysicsNode southNode = ((JMEModuleComponent) module.getComponent(1)).getModuleNode();
    	if(true||robot.getDescription().getType().contains("smooth")) {
    		northNode.setMaterial(Material.ICE); // A bit more smooth
            southNode.setMaterial(Material.ICE);
    	} else {
    		northNode.setMaterial(Material.RUBBER); // Better traction for driving experiments
			southNode.setMaterial(Material.RUBBER);
        }
		
		southNode.getMaterial().putContactHandlingDetails(simulation.getStaticPlane().getMaterial(), getDefaultATRONContactDetails());
		northNode.getMaterial().putContactHandlingDetails(simulation.getStaticPlane().getMaterial(), getDefaultATRONContactDetails());
		if(robot.getDescription().getType().contains("rubberRing")||module_name.contains("wheel")) { //TODO not a pretty hack "wheel"
			northNode.setLocalScale(1.05f);
			southNode.setLocalScale(0.95f);
			northNode.generatePhysicsGeometry(true);
			//southNode.generatePhysicsGeometry(true);

			//northNode.getMaterial().putContactHandlingDetails(simulation.getStaticPlane().getMaterial(), getRubberATRONContactDetails());
			MutableContactInfo cd =  getDefaultATRONContactDetails();
			cd.setSlip(new Vector2f(0,0));
			northNode.getMaterial().putContactHandlingDetails(simulation.getStaticPlane().getMaterial(), cd);
			//northNode.setMaterial(Material.RUBBER);
		}
	}
	private MutableContactInfo getDefaultATRONContactDetails() {
		MutableContactInfo contactDetails = new MutableContactInfo();
		contactDetails.setBounce( 0.01f );
		contactDetails.setMu(1f);
		contactDetails.setMuOrthogonal(1f);
		contactDetails.setMinimumBounceVelocity(100);
		//contactDetails.setSlip(new Vector2f(0.01f,0.01f));
		contactDetails.setSlip(new Vector2f(0,0)); //djc do not commit
		//contactDetails.setSlip(new Vector2f(0.0f,0.0f));
		//contactDetails.setSlip(new Vector2f( 0.001f, 0.001f));
		//contactDetails.setSlip(new Vector2f(100f,100f));
		return contactDetails; 
	}
	private void addCenterActuator(Module module, Robot robot) {
    	JMERotationalActuator centerActuator = new JMERotationalActuator(simulation,"center");
        module.addActuator(new Actuator(centerActuator));
        DynamicPhysicsNode northNode = ((JMEModuleComponent) module.getComponent(0)).getModuleNode();
        DynamicPhysicsNode southNode = ((JMEModuleComponent) module.getComponent(1)).getModuleNode();
        centerActuator.attach(southNode,northNode);
        //float stepSize = PhysicsParameters.get().getPhysicsSimulationStepSize();//is this a hack or not? - yes it was..
        float velocity = 6.28f/6f;//0.01f/stepSize*6.28f/6;
        if(robot.getDescription().getType().contains("super")) {
            centerActuator.setControlParameters(500f, 2f, 0, 0); //Extreme super ATRON
        }
        else if(robot.getDescription().getType().contains("realistic")) {
        	//TODO does this hack ("0.01f/stepSize*") indicate some underlying problem?
            centerActuator.setControlParameters(3f, velocity, 0, 0); //realistic: 6 seconds for one rotation, maxacceleration=2 just able to lift two, no friction= can exploit momentum - problem?
            centerActuator.setMaxStopAcceleration(15f); //TODO no validated?
        }
        else if(robot.getDescription().getType().contains("gentle")) {
            centerActuator.setControlParameters(1f, velocity, 0, 0); //Extreme super ATRON            
        } else {
        	centerActuator.setControlParameters(500f, 4f, 0, 0); //Extreme super ATRON
        }
        centerActuator.setErrorThreshold(0.001f);
        centerActuator.setDirection(0, 0, 1);
       // centerActuator.setPIDParameters(8, -1000f, 10);
        centerActuator.setPIDParameters(50, 100, 1);
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

	// Note: calling setMass multiple times is sometimes required, but this
	// can lead to a stack overflow, hence we have imposed an arbitrary limit...
	private static final int MAX_FIXED_POINT_MASS = 10;
	private void setMass(float mass, DynamicPhysicsNode node) {
	    //setMassHelper(mass,node,MAX_FIXED_POINT_MASS);
		node.setMass(mass);
		//System.out.println("Mass now:"+ node.getMass());
	}
	private void setMassHelper(float mass, DynamicPhysicsNode node, int limit) {
	    if(limit==0) return;
	    float density = mass*1f/node.getMass();
    	node.getMaterial().setDensity(density);
    	node.computeMass();
    	if(Math.abs((node.getMass()-mass))>mass/100) setMassHelper(mass, node, limit-1); //why oh why is this nesseary???
	}

	private void addCommunication(Module module, Robot robot) {
    	TransmissionDevice atronTrans = new TransmissionDevice(TransmissionType.IR,0.05f);
        ReceivingDevice atronRec = new ReceivingDevice(TransmissionType.IR,10);
        for(int channel=0;channel<8;channel++) {
            module.addTransmissionDevice(JMEGeometryHelper.createTransmitter(module, module.getConnectors().get(channel),atronTrans)); //use connector hardware for communication!
            module.addReceivingDevice(JMEGeometryHelper.createReceiver(module, module.getConnectors().get(channel),atronRec));
            module.getTransmitters().get(channel).setMaxBaud(19200);
            module.getTransmitters().get(channel).setMaxBufferSize(128);
        }
        if(robot.getDescription().getType().contains("radio")) {
        	int channel = 8;
        	TransmissionDevice atronRadioTrans = new TransmissionDevice(TransmissionType.RADIO,Float.MAX_VALUE);
            ReceivingDevice atronRadioRec = new ReceivingDevice(TransmissionType.RADIO,10);
            module.addTransmissionDevice(JMEGeometryHelper.createTransmitter(module, null, atronRadioTrans)); //use connector hardware for communication!
            module.addReceivingDevice(JMEGeometryHelper.createReceiver(module, null, atronRadioRec));
            module.getTransmitters().get(channel).setMaxBaud(19200);
            module.getTransmitters().get(channel).setMaxBufferSize(128);
        }
	}

	private void addProximitySensors(Module module) {
		DynamicPhysicsNode northNode = ((JMEModuleComponent) module.getComponent(0)).getModuleNode();
        DynamicPhysicsNode southNode = ((JMEModuleComponent) module.getComponent(1)).getModuleNode();
        for(int channel=0; channel<4; channel++) {
            module.addSensor(new Sensor(new JMEProximitySensor(simulation, "ProximitySensor-"+channel, module.getConnectors().get(channel),0.3f,northNode)));
        }
        for(int channel=4; channel<8; channel++) {
            module.addSensor(new Sensor(new JMEProximitySensor(simulation, "ProximitySensor-"+channel, module.getConnectors().get(channel),0.3f,southNode)));
        }
	}

	private void updateConnectors(Module module) {
        float maxAlignmentForce = 10;
        float maxAlignmentDistance = 0.02f;
        float epsilonAlignmentDistance = 0.01f;
        JMEModuleComponent northComponent =  (JMEModuleComponent) module.getComponent(0);
        Vector3f[] northAlignPos1 = new Vector3f[]{new Vector3f(0.0377124f,0.0377124f,-0.0266667f),new Vector3f(-0.0377124f,0.0377124f,-0.0266667f),new Vector3f(-0.0377124f,-0.0377124f,-0.0266667f),new Vector3f(0.0377124f,-0.0377124f,-0.0266667f)};
        Vector3f[] northAlignPos2 = new Vector3f[]{new Vector3f(0.0226274f,0.0226274f,-0.048f),new Vector3f(-0.0226274f,0.0226274f,-0.048f),new Vector3f(-0.0226274f,-0.0226274f,-0.048f),new Vector3f(0.0226274f,-0.0226274f,-0.048f)};
        for(int i=0;i<4;i++) {
        	northComponent.getConnector(i).getConnectorAligner().addAlignmentPoint(northAlignPos1[i], 1, (i%2+1), maxAlignmentForce, maxAlignmentDistance,epsilonAlignmentDistance);
        	northComponent.getConnector(i).getConnectorAligner().addAlignmentPoint(northAlignPos2[i], 1, (i%2+1), maxAlignmentForce, maxAlignmentDistance,epsilonAlignmentDistance);
        }
        setConnectorProperties(northComponent);
       
        JMEModuleComponent southComponent =  (JMEModuleComponent) module.getComponent(1);
        Vector3f[] southAlignPos1 = new Vector3f[]{new Vector3f(0.0377124f,0.0377124f,0.0266667f),new Vector3f(-0.0377124f,0.0377124f,0.0266667f),new Vector3f(-0.0377124f,-0.0377124f,0.0266667f),new Vector3f(0.0377124f,-0.0377124f,0.0266667f)};
        Vector3f[] southAlignPos2 = new Vector3f[]{new Vector3f(0.0226274f,0.0226274f,0.048f),new Vector3f(-0.0226274f,0.0226274f,0.048f),new Vector3f(-0.0226274f,-0.0226274f,0.048f),new Vector3f(0.0226274f,-0.0226274f,0.048f)};
        for(int i=0;i<4;i++) {
        	southComponent.getConnector(i).getConnectorAligner().addAlignmentPoint(southAlignPos1[i], 1, (i%2+1), maxAlignmentForce, maxAlignmentDistance, epsilonAlignmentDistance);
        	southComponent.getConnector(i).getConnectorAligner().addAlignmentPoint(southAlignPos2[i], 1, (i%2+1), maxAlignmentForce, maxAlignmentDistance,epsilonAlignmentDistance);
        }
        setConnectorProperties(southComponent);
        
	}

	private void setConnectorProperties(JMEModuleComponent component) {
		for(int i=0;i<4;i++) {
        	JMEMechanicalConnector c = (JMEMechanicalConnector)component.getConnector(i);
        	c.setUpdateFrequency(10);
        	c.setTimeToConnect(3.0f);
        	c.setTimeToDisconnect(2.0f);
        	if(i%2==0) c.setConnectorType(JMEMechanicalConnector.MALE);
        	else c.setConnectorType(JMEMechanicalConnector.FEMALE);
        }
	}

	public String getModulePrefix() {
        return "ATRON";
    }

    public void setSimulation(PhysicsSimulation simulation) {
        this.simulation = (JMESimulation)simulation;
    }

}
