package ussr.physics.jme.robots;

import java.awt.Color;

import ussr.comm.TransmissionType;
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
import ussr.robotbuildingblocks.AtronShape;
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
public class JMEATRONFactory implements ModuleFactory {
    private JMESimulation simulation;
    float pi = (float)Math.PI;
    
  //create ATRON
    public void createModule(int module_id, Module module, Robot robot, String module_name) {
        if(!robot.getDescription().getType().startsWith("ATRON")) throw new Error("Illegal module type: "+robot.getDescription().getType());
        
        if(robot.getDescription().getModuleGeometry().size()!=2) throw new RuntimeException("Not an ATRON");
        
        createModuleComponents(module, robot, module_id, module_name);
        
        JMEModuleComponent northComponent = (JMEModuleComponent)module.getComponent(0);
        JMEModuleComponent southComponent = (JMEModuleComponent)module.getComponent(1);

        setMass(0.4f,northComponent.getModuleNode());
        setMass(0.4f,southComponent.getModuleNode());
        setMaterials(module, robot,module_name);
        setName(module, module_name);
        addConnectors(module);
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
        AtronShape northShape = (AtronShape) robot.getDescription().getModuleGeometry().get(0);
        AtronShape southShape = (AtronShape) robot.getDescription().getModuleGeometry().get(1);
        
        
        DynamicPhysicsNode northNode = simulation.getPhysicsSpace().createDynamicNode();
        JMEModuleComponent northComponent = new JMEModuleComponent(simulation,robot,northShape,module_name+"_module#"+Integer.toString(module_id)+".north",module,northNode);
        northNode.setName("AtronNorth");

        DynamicPhysicsNode southNode = simulation.getPhysicsSpace().createDynamicNode();
        JMEModuleComponent southComponent = new JMEModuleComponent(simulation,robot,southShape,module_name+"module#"+Integer.toString(module_id)+".south",module,southNode);
        southNode.setName("AtronSouth");

        module.addComponent(northComponent);
        module.addComponent(southComponent); //hvad skal håndteres ved fx placering af moduler?
        
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
			northNode.generatePhysicsGeometry(false);
			//northNode.getMaterial().putContactHandlingDetails(simulation.getStaticPlane().getMaterial(), getRubberATRONContactDetails());
			northNode.setMaterial(Material.RUBBER);
		}
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
	private void addCenterActuator(Module module, Robot robot) {
    	JMERotationalActuator centerActuator = new JMERotationalActuator(simulation,"center");
        module.addActuator(new Actuator(centerActuator));
        DynamicPhysicsNode northNode = ((JMEModuleComponent) module.getComponent(0)).getModuleNode();
        DynamicPhysicsNode southNode = ((JMEModuleComponent) module.getComponent(1)).getModuleNode();
        centerActuator.attach(southNode,northNode);
        float stepSize = PhysicsParameters.get().getPhysicsSimulationStepSize();//is this a hack or not?
        float velocity = 0.01f/stepSize*6.28f/6;
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
	    setMassHelper(mass,node,MAX_FIXED_POINT_MASS);
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
            if(!robot.getDescription().getType().contains("gentle")) {
                module.getTransmitters().get(channel).setMaxBaud(19200);
                module.getTransmitters().get(channel).setMaxBufferSize(128);
            }
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
        //float unit = (float) (0.045f/Math.sqrt(2)); //4.5cm from center of mass to connector
		float unit = (float)0.0282843;// 0.0388909;//0.033137f;//0.033137f = 2.9 	507498E-7 //why this value?
        float maxAlignmentForce = 10;
        float maxAlignmentDistance = 0.02f;
        float epsilonAlignmentDistance = 0.01f;
        Color[] colors = new Color[]{Color.black,Color.white,Color.black,Color.white};
        float h=(float)Math.sqrt(2);
        Vector3f[] northPos = new Vector3f[]{new Vector3f(unit,unit,-h*unit),new Vector3f( -unit,  unit, -h*unit ),new Vector3f( -unit, -unit, -h*unit ),new Vector3f(  unit, -unit, -h*unit )};
        Quaternion[] northRot = new Quaternion[]{new Quaternion(new float[]{0,-pi/4,pi/4}),new Quaternion(new float[]{0,pi/4,-pi/4}),new Quaternion(new float[]{0,pi/4,pi/4}),new Quaternion(new float[]{0,-pi/4,-pi/4})};
        JMEModuleComponent northComponent =  (JMEModuleComponent) module.getComponent(0);
        Vector3f[] northAlignPos1 = new Vector3f[]{new Vector3f(0.0377124f,0.0377124f,-0.0266667f),new Vector3f(-0.0377124f,0.0377124f,-0.0266667f),new Vector3f(-0.0377124f,-0.0377124f,-0.0266667f),new Vector3f(0.0377124f,-0.0377124f,-0.0266667f)};
        Vector3f[] northAlignPos2 = new Vector3f[]{new Vector3f(0.0226274f,0.0226274f,-0.048f),new Vector3f(-0.0226274f,0.0226274f,-0.048f),new Vector3f(-0.0226274f,-0.0226274f,-0.048f),new Vector3f(0.0226274f,-0.0226274f,-0.048f)};
        for(int i=0;i<4;i++) {
        	northComponent.addConnector("Connector "+i,northPos[i],colors[i],northRot[i]);
        	//northComponent.getConnector(i).getConnectorAligner().addAlignmentPoint(northPos[i], 1, (i%2+1), maxAlignmentForce);
        	//moduleComponent.getConnector(i).getConnectorAligner().addAlignmentPoint(cPos[i].add(new Vector3f(0,-unit,0)), 2, 0, 0.1f);
        	northComponent.getConnector(i).getConnectorAligner().addAlignmentPoint(northAlignPos1[i], 1, (i%2+1), maxAlignmentForce, maxAlignmentDistance,epsilonAlignmentDistance);
        	northComponent.getConnector(i).getConnectorAligner().addAlignmentPoint(northAlignPos2[i], 1, (i%2+1), maxAlignmentForce, maxAlignmentDistance,epsilonAlignmentDistance);
        }
        setConnectorProperties(northComponent);
        
        
        Vector3f[] southPos = new Vector3f[]{new Vector3f(unit,unit,h*unit),new Vector3f(-unit,unit,h*unit),new Vector3f(-unit,-unit,h*unit),new Vector3f(unit,-unit,h*unit)};
        Quaternion[] southRot = new Quaternion[]{new Quaternion(new float[]{pi,pi/4,pi/4}),new Quaternion(new float[]{pi,-pi/4,-pi/4}),new Quaternion(new float[]{pi,-pi/4,pi/4}),new Quaternion(new float[]{pi,pi/4,-pi/4})};
        JMEModuleComponent southComponent =  (JMEModuleComponent) module.getComponent(1);
        Vector3f[] southAlignPos1 = new Vector3f[]{new Vector3f(0.0377124f,0.0377124f,0.0266667f),new Vector3f(-0.0377124f,0.0377124f,0.0266667f),new Vector3f(-0.0377124f,-0.0377124f,0.0266667f),new Vector3f(0.0377124f,-0.0377124f,0.0266667f)};
        Vector3f[] southAlignPos2 = new Vector3f[]{new Vector3f(0.0226274f,0.0226274f,0.048f),new Vector3f(-0.0226274f,0.0226274f,0.048f),new Vector3f(-0.0226274f,-0.0226274f,0.048f),new Vector3f(0.0226274f,-0.0226274f,0.048f)};
        for(int i=0;i<4;i++) {
        	southComponent.addConnector("Connector "+(i+4),southPos[i],colors[i],southRot[i]);
        	southComponent.getConnector(i).getConnectorAligner().addAlignmentPoint(southAlignPos1[i], 1, (i%2+1), maxAlignmentForce, maxAlignmentDistance, epsilonAlignmentDistance);
        	southComponent.getConnector(i).getConnectorAligner().addAlignmentPoint(southAlignPos2[i], 1, (i%2+1), maxAlignmentForce, maxAlignmentDistance,epsilonAlignmentDistance);
        	//moduleComponent.getConnector(i).getConnectorAligner().addAlignmentPoint(cPos[i].add(new Vector3f(0,-unit,0)), 2, 0, 0.1f);
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
