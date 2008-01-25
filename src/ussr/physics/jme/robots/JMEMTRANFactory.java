package ussr.physics.jme.robots;

import java.awt.Color;

import ussr.model.Actuator;
import ussr.model.Module;
import ussr.physics.ModuleFactory;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.actuators.JMERotationalActuator;
import ussr.robotbuildingblocks.BoxShape;
import ussr.robotbuildingblocks.CylinderShape;
import ussr.robotbuildingblocks.Robot;

import com.jme.math.Vector2f;
import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.contact.ContactHandlingDetails;
import com.jmex.physics.contact.MutableContactInfo;
import com.jmex.physics.material.Material;

/**
 * Factory for creating MTRAN modules
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class JMEMTRANFactory implements ModuleFactory {
    private JMESimulation simulation;
    
    public void createModule(int module_id, Module module, Robot robot, String module_name) {
        if(!robot.getDescription().getType().startsWith("MTRAN")) throw new Error("Illegal module type: "+robot.getDescription().getType());
        //create MTRAN
        if(robot.getDescription().getModuleGeometry().size()!=4) throw new RuntimeException("Not an MTRAN");
        CylinderShape northCylinder = (CylinderShape) robot.getDescription().getModuleGeometry().get(0);
        CylinderShape southCylinder = (CylinderShape) robot.getDescription().getModuleGeometry().get(1);
        BoxShape northBox = (BoxShape) robot.getDescription().getModuleGeometry().get(2);
        BoxShape southBox = (BoxShape) robot.getDescription().getModuleGeometry().get(3);
        
        
        DynamicPhysicsNode northNode = simulation.getPhysicsSpace().createDynamicNode();
        northNode.getLocalTranslation().set( northNode.getLocalTranslation().add(new Vector3f(-0.015f,0,0)) );
        
        JMEModuleComponent northCylinderComponent = new JMEModuleComponent(simulation,robot,northCylinder,module_name+"_module#"+Integer.toString(module_id)+".north",module,northNode);
        JMEModuleComponent northBoxComponent = new JMEModuleComponent(simulation,robot,northBox,module_name+"_module#"+Integer.toString(module_id)+".north",module,northNode);
        northNode.setName("MTRANNorth");

        
        DynamicPhysicsNode southNode = simulation.getPhysicsSpace().createDynamicNode();
        southNode.getLocalTranslation().set( southNode.getLocalTranslation().add(new Vector3f(0.015f,0,0)) );
        JMEModuleComponent southCylinderComponent = new JMEModuleComponent(simulation,robot,southCylinder,module_name+"module#"+Integer.toString(module_id)+".south",module,southNode);
        JMEModuleComponent southBoxComponent = new JMEModuleComponent(simulation,robot,southBox,module_name+"_module#"+Integer.toString(module_id)+".south",module,southNode);
        southNode.setName("MTRANSouth");
        DynamicPhysicsNode centerNode = simulation.getPhysicsSpace().createDynamicNode();
        centerNode.setName("MTRANCenter");
        
        float pi = (float)Math.PI;
        
        /*northComponent.addConnector("Connector 0", new Vector3f(  unit,  unit, -unit),Color.black,new Quaternion(new float[]{0,-pi/4,pi/4}));  //male
        northComponent.addConnector("Connector 1", new Vector3f( -unit,  unit, -unit ),Color.white,new Quaternion(new float[]{0,pi/4,-pi/4})); //female
        northComponent.addConnector("Connector 2", new Vector3f( -unit, -unit, -unit ),Color.black, new Quaternion(new float[]{0,pi/4,pi/4})); //male 
        northComponent.addConnector("Connector 3", new Vector3f(  unit, -unit, -unit ),Color.white, new Quaternion(new float[]{0,-pi/4,-pi/4})); //...
        
        southComponent.addConnector("Connector 4", new Vector3f(  unit,  unit,  unit ),Color.black,new Quaternion(new float[]{pi,pi/4,pi/4}));
        southComponent.addConnector("Connector 5", new Vector3f( -unit,  unit,  unit ),Color.white, new Quaternion(new float[]{pi,-pi/4,-pi/4}));
        southComponent.addConnector("Connector 6", new Vector3f( -unit, -unit,  unit ),Color.black, new Quaternion(new float[]{pi,-pi/4,pi/4}));
        southComponent.addConnector("Connector 7", new Vector3f(  unit, -unit,  unit ),Color.white, new Quaternion(new float[]{pi,pi/4,-pi/4}));
        */
        module.addComponent(northCylinderComponent);
        module.addComponent(northBoxComponent);
        module.addComponent(southCylinderComponent); 
        module.addComponent(southBoxComponent);
        
     /*   TransmissionDevice atronTrans = new TransmissionDevice(TransmissionType.IR,0.05f);
        ReceivingDevice atronRec = new ReceivingDevice(TransmissionType.IR,10);
        for(int channel=0;channel<8;channel++) {
            module.addTransmissionDevice(JMEGeometryHelper.createTransmitter(module, module.getConnectors().get(channel),atronTrans));
            module.addReceivingDevice(JMEGeometryHelper.createReceiver(module, module.getConnectors().get(channel),atronRec));
        }*/
        
        // Proximity sensors
       /* for(int channel=0; channel<8; channel++) {
            module.addSensor(new Sensor(new JMEProximitySensor(simulation, "ProximitySensor-"+channel, module.getConnectors().get(channel),0.3f)));
        }*/
        
        simulation.getModuleComponents().add(northCylinderComponent);
        simulation.getModuleComponents().add(northBoxComponent);
        simulation.getModuleComponents().add(southCylinderComponent);
        simulation.getModuleComponents().add(southBoxComponent);
        if(robot.getDescription().getType().contains("smooth")) {
            northNode.setMaterial(Material.ICE); // A bit more smooth
            southNode.setMaterial(Material.ICE);
        } else {
            northNode.setMaterial(Material.RUBBER); // Better traction for driving experiments
            southNode.setMaterial(Material.RUBBER);
        }
        northNode.setMass(0.200f); //450 grams in total
        southNode.setMass(0.200f);
        centerNode.setMass(0.050f);
        MutableContactInfo contactDetails = new MutableContactInfo();
        contactDetails.setBounce( 0.01f );
        contactDetails.setMu( 0.1f );
        contactDetails.setSlip(new Vector2f(0.01f,0.01f));
        
        southNode.getMaterial().putContactHandlingDetails(simulation.getStaticPlane().getMaterial(), contactDetails);
        northNode.getMaterial().putContactHandlingDetails(simulation.getStaticPlane().getMaterial(), contactDetails);
        JMERotationalActuator northActuator = new JMERotationalActuator(simulation,"north");
        module.addActuator(new Actuator(northActuator));
        northActuator.attach(northNode,centerNode);
        northActuator.setControlParameters(10f, 1f, -pi/2, pi/2); 
        northActuator.setDirection(0, 1, 0);
       // northActuator.activate(10);
        
        
        JMERotationalActuator southActuator = new JMERotationalActuator(simulation,"south");
        module.addActuator(new Actuator(southActuator));
        southActuator.attach(southNode,centerNode);
        southActuator.setControlParameters(10f, 1f, -pi/2, pi/2);
        southActuator.setDirection(0, 1, 0);
       // southActuator.activate(10);
        
        
        
        
        ContactHandlingDetails con = simulation.getStaticPlane().getMaterial().getContactHandlingDetails(southNode.getMaterial());
        
        // Tilt sensors - NOTE: rotation is probably not correct, but is meant to be inverse of connector rotation
      /*  JMETiltSensor xsensor = new JMETiltSensor(simulation, "TiltSensor:x", 'x', northNode, new RotationDescription(0,-pi,-pi/4));
        JMETiltSensor ysensor = new JMETiltSensor(simulation, "TiltSensor:y", 'y', northNode, new RotationDescription(0,-pi,-pi/4));
        JMETiltSensor zsensor = new JMETiltSensor(simulation, "TiltSensor:z", 'z', northNode, new RotationDescription(0,-pi,-pi/4));
        
        module.addSensor(new Sensor(xsensor));
        module.addSensor(new Sensor(ysensor));
        module.addSensor(new Sensor(zsensor));
        */
        
       //northCylinderComponent.setModuleComponentColor(Color.red);
        
        //northBoxComponent.setModuleComponentColor(Color.red);
    }

    public String getModulePrefix() {
        return "MTRAN";
    }

    public void setSimulation(PhysicsSimulation simulation) {
        this.simulation = (JMESimulation)simulation;
    }

}
