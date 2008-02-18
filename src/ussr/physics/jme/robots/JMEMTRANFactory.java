package ussr.physics.jme.robots;

import java.awt.Color;

import ussr.comm.TransmissionType;
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
import ussr.robotbuildingblocks.BoxShape;
import ussr.robotbuildingblocks.CylinderShape;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.ReceivingDevice;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.TransmissionDevice;

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
  //create MTRAN
    public void createModule(int module_id, Module module, Robot robot, String module_name) {
        if(!robot.getDescription().getType().startsWith("MTRAN")) throw new Error("Illegal module type: "+robot.getDescription().getType());
        
        if(robot.getDescription().getModuleGeometry().size()!=5) throw new RuntimeException("Not an MTRAN");
        createModuleComponents(module, robot, module_id, module_name);
        
        setMaterials(module, robot,module_name);
        setMass(0.2f,northNode);
        setMass(0.2f,southNode);
        setMass(0.05f,southNode);
        
        setName(module, module_name);
        addActuators(module, northNode, southNode, centerNode);
        
        addConnectors(module);
        /*addCommunication(module, robot);
        addProximitySensors(module);
        
        addTiltSensors(module,northComponent.getModuleNode());*/
    }
    
    private void setName(Module module, String module_name) {
    	DynamicPhysicsNode northNode = ((JMEModuleComponent) module.getComponent(0)).getModuleNode();
        DynamicPhysicsNode southNode = ((JMEModuleComponent) module.getComponent(1)).getModuleNode();
        northNode.setName(module_name+" North");
        southNode.setName(module_name+" South");
	}

	private void createModuleComponents(Module module, Robot robot, int module_id, String module_name) {
		
        CylinderShape northCylinder = (CylinderShape) robot.getDescription().getModuleGeometry().get(0);
        CylinderShape southCylinder = (CylinderShape) robot.getDescription().getModuleGeometry().get(1);
        BoxShape northBox = (BoxShape) robot.getDescription().getModuleGeometry().get(2);
        BoxShape southBox = (BoxShape) robot.getDescription().getModuleGeometry().get(3);
        GeometryDescription centerCylinder = (GeometryDescription) robot.getDescription().getModuleGeometry().get(4);
        
        northNode = simulation.getPhysicsSpace().createDynamicNode();
        
        
        
        JMEModuleComponent northCylinderComponent = new JMEModuleComponent(simulation,robot,northCylinder,module_name+"_module#"+Integer.toString(module_id)+".north",module,northNode);
        JMEModuleComponent northBoxComponent = new JMEModuleComponent(simulation,robot,northBox,module_name+"_module#"+Integer.toString(module_id)+".north",module,northNode);
        northNode.setName("MTRANNorth");
        Vector3f localNorthPos = new Vector3f(-0.075f/2,0,0);
        northNode.getLocalTranslation().set( northNode.getLocalTranslation().add(localNorthPos) );
        northCylinderComponent.setLocalPosition(localNorthPos);
        northBoxComponent.setLocalPosition(localNorthPos);
            
        southNode = simulation.getPhysicsSpace().createDynamicNode();
        JMEModuleComponent southCylinderComponent = new JMEModuleComponent(simulation,robot,southCylinder,module_name+"module#"+Integer.toString(module_id)+".south",module,southNode);
        JMEModuleComponent southBoxComponent = new JMEModuleComponent(simulation,robot,southBox,module_name+"_module#"+Integer.toString(module_id)+".south",module,southNode);
        southNode.setName("MTRANSouth");
        Vector3f localSouthPos = new Vector3f(0.075f/2,0,0);
        southNode.getLocalTranslation().set( southNode.getLocalTranslation().add(localSouthPos) );
        southCylinderComponent.setLocalPosition(localSouthPos);
        southBoxComponent.setLocalPosition(localSouthPos);
        
        
        centerNode = simulation.getPhysicsSpace().createDynamicNode();
        JMEModuleComponent centerComponent = new JMEModuleComponent(simulation,robot,centerCylinder,module_name+"module#"+Integer.toString(module_id)+".center",module,centerNode);
        centerNode.setName("MTRANCenter");
        
        module.addComponent(northCylinderComponent);
        module.addComponent(northBoxComponent);
        module.addComponent(southCylinderComponent); 
        module.addComponent(southBoxComponent);
        module.addComponent(centerComponent);
        simulation.getModuleComponents().add(northCylinderComponent);
        simulation.getModuleComponents().add(northBoxComponent);
        simulation.getModuleComponents().add(southCylinderComponent);
        simulation.getModuleComponents().add(southBoxComponent);
        simulation.getModuleComponents().add(centerComponent);
        
        

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
	private MutableContactInfo getDefaultATRONContactDetails() {
		MutableContactInfo contactDetails = new MutableContactInfo();
		contactDetails.setBounce( 0.01f );
		contactDetails.setMu(1f );
		contactDetails.setMuOrthogonal(1f);
		contactDetails.setMinimumBounceVelocity(100);
		contactDetails.setSlip(new Vector2f(0.01f,0.01f));
		//contactDetails.setSlip(new Vector2f(0.0f,0.0f));
		//contactDetails.setSlip(new Vector2f( 0.001f, 0.001f));
		//contactDetails.setSlip(new Vector2f(100f,100f));
		return contactDetails; 
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
	private static final int MAX_FIXED_POINT_MASS = 20;
	private void setMass(float mass, DynamicPhysicsNode node) {
	    node.setMass(mass);
	   // System.err.println("Mass set to: "+node.getMass() );
	    //setMassHelper(mass,node,MAX_FIXED_POINT_MASS);
	}
	private void setMassHelper(float mass, DynamicPhysicsNode node, int limit) {
	    if(limit==0) {
	    	System.err.println("Limit on set mass reached, mass is "+node.getMass() );
	    	return;
	    }
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

	private void addConnectors(Module module) {
		float unit = (float)0.065f/2;
        //float maxAlignmentForce = 10;
        //float maxAlignmentDistance = 0.02f;
        //float epsilonAlignmentDistance = 0.01f;
        float h=(float)Math.sqrt(2);
        Vector3f[] northPos = new Vector3f[]{new Vector3f(-unit,0,0),new Vector3f(0, -unit, 0 ),new Vector3f(0, unit, 0 )};
        Quaternion[] northRot = new Quaternion[]{new Quaternion(new float[]{0,pi/2,0}),new Quaternion(new float[]{-pi/2,0,0}),new Quaternion(new float[]{pi/2,0,0})};
        JMEModuleComponent northComponent =  (JMEModuleComponent) module.getComponent(1);
        //Vector3f[] northAlignPos1 = new Vector3f[]{new Vector3f(0.0377124f,0.0377124f,-0.0266667f),new Vector3f(-0.0377124f,0.0377124f,-0.0266667f),new Vector3f(-0.0377124f,-0.0377124f,-0.0266667f),new Vector3f(0.0377124f,-0.0377124f,-0.0266667f)};
        //Vector3f[] northAlignPos2 = new Vector3f[]{new Vector3f(0.0226274f,0.0226274f,-0.048f),new Vector3f(-0.0226274f,0.0226274f,-0.048f),new Vector3f(-0.0226274f,-0.0226274f,-0.048f),new Vector3f(0.0226274f,-0.0226274f,-0.048f)};
        northComponent.addConnector("Connector "+0,northPos[0],Color.black,northRot[0]);
        northComponent.addConnector("Connector "+1,northPos[1],Color.black,northRot[1]);
        northComponent.addConnector("Connector "+2,northPos[2],Color.black,northRot[2]);
        setConnectorProperties(northComponent, true);
        
        Vector3f[] southPos = new Vector3f[]{new Vector3f(unit,0,0),new Vector3f(0, -unit, 0 ),new Vector3f(0, unit, 0 )};
        Quaternion[] southRot = new Quaternion[]{new Quaternion(new float[]{0,-pi/2,0}),new Quaternion(new float[]{-pi/2,0,0}),new Quaternion(new float[]{pi/2,0,0})};
        JMEModuleComponent southComponent =  (JMEModuleComponent) module.getComponent(3);
        southComponent.addConnector("Connector "+4,southPos[0],Color.white,southRot[0]);
        southComponent.addConnector("Connector "+5,southPos[1],Color.white,southRot[1]);
        southComponent.addConnector("Connector "+6,southPos[2],Color.white,southRot[2]);
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
