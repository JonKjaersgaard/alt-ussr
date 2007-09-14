package ussr.physics.jme.robots;

import java.awt.Color;

import com.jme.math.Quaternion;
import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.contact.ContactHandlingDetails;
import com.jmex.physics.contact.MutableContactInfo;
import com.jmex.physics.material.Material;

import ussr.comm.TransmissionType;
import ussr.model.Actuator;
import ussr.model.Module;
import ussr.model.Sensor;
import ussr.physics.jme.JMEGeometryHelper;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMEProximitySensor;
import ussr.physics.jme.JMERotationalActuator;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.JMETiltSensor;
import ussr.robotbuildingblocks.AtronShape;
import ussr.robotbuildingblocks.ReceivingDevice;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.TransmissionDevice;

public class JMEATRONFactory implements JMEModuleFactory {
    private JMESimulation simulation;
    
    public void createModule(int module_id, Module module, Robot robot, String module_name) {
        if(!robot.getDescription().getType().startsWith("ATRON")) throw new Error("Illegal module type: "+robot.getDescription().getType());
        //create ATRON
        if(robot.getDescription().getModuleGeometry().size()!=2) throw new RuntimeException("Not an ATRON");
        AtronShape northShape = (AtronShape) robot.getDescription().getModuleGeometry().get(0);
        AtronShape southShape = (AtronShape) robot.getDescription().getModuleGeometry().get(1);
        
        
        DynamicPhysicsNode northNode = simulation.getPhysicsSpace().createDynamicNode();
        JMEModuleComponent northComponent = new JMEModuleComponent(simulation,robot,northShape,module_name+"_module#"+Integer.toString(module_id)+".north",module,northNode);
        northNode.setName("AtronNorth");

        
        DynamicPhysicsNode southNode = simulation.getPhysicsSpace().createDynamicNode();
        JMEModuleComponent southComponent = new JMEModuleComponent(simulation,robot,southShape,module_name+"module#"+Integer.toString(module_id)+".south",module,southNode);
        southNode.setName("AtronSouth");
        float pi = (float)Math.PI;
        float unit = (float) (0.045f/Math.sqrt(2)); //4.5cm from center of mass to connector
        
        northComponent.addConnector("Connector 0", new Vector3f(  unit,  unit, -unit),Color.black,new Quaternion(new float[]{0,-pi/4,pi/4}));  //male
        northComponent.addConnector("Connector 1", new Vector3f( -unit,  unit, -unit ),Color.white,new Quaternion(new float[]{0,pi/4,-pi/4})); //female
        northComponent.addConnector("Connector 2", new Vector3f( -unit, -unit, -unit ),Color.black, new Quaternion(new float[]{0,pi/4,pi/4})); //male 
        northComponent.addConnector("Connector 3", new Vector3f(  unit, -unit, -unit ),Color.white, new Quaternion(new float[]{0,-pi/4,-pi/4})); //...
        
        southComponent.addConnector("Connector 4", new Vector3f(  unit,  unit,  unit ),Color.black,new Quaternion(new float[]{pi,pi/4,pi/4}));
        southComponent.addConnector("Connector 5", new Vector3f( -unit,  unit,  unit ),Color.white, new Quaternion(new float[]{pi,-pi/4,-pi/4}));
        southComponent.addConnector("Connector 6", new Vector3f( -unit, -unit,  unit ),Color.black, new Quaternion(new float[]{pi,-pi/4,pi/4}));
        southComponent.addConnector("Connector 7", new Vector3f(  unit, -unit,  unit ),Color.white, new Quaternion(new float[]{pi,pi/4,-pi/4}));
        
        module.addComponent(northComponent);
        module.addComponent(southComponent); //hvad skal håndteres ved fx placering af moduler?
        
        TransmissionDevice atronTrans = new TransmissionDevice(TransmissionType.IR,0.05f);
        ReceivingDevice atronRec = new ReceivingDevice(TransmissionType.IR,10);
        for(int channel=0;channel<8;channel++) {
            module.addTransmissionDevice(JMEGeometryHelper.createTransmitter(module, module.getConnectors().get(channel),atronTrans));
            module.addReceivingDevice(JMEGeometryHelper.createReceiver(module, module.getConnectors().get(channel),atronRec));
        }
        
        // Proximity sensors
        for(int channel=0; channel<8; channel++) {
            module.addSensor(new Sensor(new JMEProximitySensor(simulation, "ProximitySensor-"+channel, module.getConnectors().get(channel),0.3f)));
        }
        
        simulation.getModuleComponents().add(northComponent);
        simulation.getModuleComponents().add(southComponent);
        if(robot.getDescription().getType().contains("smooth")) {
            northNode.setMaterial(Material.ICE); // A bit more smooth
            southNode.setMaterial(Material.ICE);
        } else {
            northNode.setMaterial(Material.RUBBER); // Better traction for driving experiments
            southNode.setMaterial(Material.RUBBER);
        }
        northNode.setMass(0.400f); //800 grams in total
        southNode.setMass(0.400f);
        MutableContactInfo contactDetails = new MutableContactInfo();
        contactDetails.setBounce( 0.01f );
        contactDetails.setMu( 0.1f );
        contactDetails.setSlip(new Vector2f(0.01f,0.01f));
        
        southNode.getMaterial().putContactHandlingDetails(simulation.getStaticPlane().getMaterial(), contactDetails);
        northNode.getMaterial().putContactHandlingDetails(simulation.getStaticPlane().getMaterial(), contactDetails);
        
        JMERotationalActuator centerActuator = new JMERotationalActuator(simulation,"center");
        module.addActuator(new Actuator(centerActuator));
        centerActuator.attach(southNode,northNode);
        if(robot.getDescription().getType().contains("super"))
            centerActuator.setControlParameters(500f, 2f, 0, 0); //Extreme super ATRON
        else
            centerActuator.setControlParameters(0.2f, 1f, 0, 0); //Relaxed super ATRON
        
        centerActuator.setControlParameters(500f, 4f, 0, 0); //Extreme super ATRON
        //northNode.setMaterial(Material.ICE); // A bit more smooth
         // southNode.setMaterial(Material.ICE);
        
        ///centerActuator.setControlParameters(2, (float)(2*Math.PI/6), 0, 0); //100N, 2*pi/6 rad/s, no rotational limits
        
        centerActuator.setDirection(0, 0, 1);
        centerActuator.activate(10);
        
        ContactHandlingDetails con = simulation.getStaticPlane().getMaterial().getContactHandlingDetails(southNode.getMaterial());
        
        // Tilt sensors - NOTE: rotation is probably not correct, but is meant to be inverse of connector rotation
        JMETiltSensor xsensor = new JMETiltSensor(simulation, "TiltSensor:x", 'x', northNode, new RotationDescription(0,-pi,-pi/4));
        JMETiltSensor ysensor = new JMETiltSensor(simulation, "TiltSensor:y", 'y', northNode, new RotationDescription(0,-pi,-pi/4));
        JMETiltSensor zsensor = new JMETiltSensor(simulation, "TiltSensor:z", 'z', northNode, new RotationDescription(0,-pi,-pi/4));
        module.addSensor(new Sensor(xsensor));
        module.addSensor(new Sensor(ysensor));
        module.addSensor(new Sensor(zsensor));
    }

    public String getModulePrefix() {
        return "ATRON";
    }

    public void setSimulation(JMESimulation simulation) {
        this.simulation = simulation;
    }

}
