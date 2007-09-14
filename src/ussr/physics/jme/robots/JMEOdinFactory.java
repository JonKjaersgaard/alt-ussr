package ussr.physics.jme.robots;

import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.material.Material;

import ussr.comm.TransmissionType;
import ussr.model.Actuator;
import ussr.model.Module;
import ussr.model.Sensor;
import ussr.physics.jme.JMEGeometryHelper;
import ussr.physics.jme.JMELinearActuator;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMERotationalActuator;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.JMETiltSensor;
import ussr.robotbuildingblocks.ConeShape;
import ussr.robotbuildingblocks.CylinderShape;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.ReceivingDevice;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.TransmissionDevice;

public class JMEOdinFactory implements JMEModuleFactory {
    private JMESimulation simulation;
    
    public void createModule(int module_id, Module module, Robot robot, String module_name) {
        if(simulation==null) throw new Error("Internal error: factory not initialized");
        if(robot.getDescription().getType()=="OdinMuscle")
            createOdinMuscle(module_id, module, robot);
        else if(robot.getDescription().getType()=="OdinWheel")
            createOdinWheel(module_id, module, robot); 
        else if(robot.getDescription().getType()=="OdinHinge")
            createOdinHinge(module_id, module, robot);
        else if(robot.getDescription().getType()=="OdinBattery")
            createOdinBattery(module_id, module, robot);
        else if(robot.getDescription().getType()=="OdinBall")
            createOdinBall(module_id, module, robot);
        else if(robot.getDescription().getType()=="OdinSpring")
        	createOdinSpring(module_id, module, robot);
        else throw new Error("Illegal module type: "+robot.getDescription().getType());
    }

    public String getModulePrefix() {
        return "Odin";
    }

    private void createOdinBall(int i, final Module module, Robot robot) {
        DynamicPhysicsNode moduleNode = simulation.getPhysicsSpace().createDynamicNode();            
        int j=0;
        for(GeometryDescription geometry: robot.getDescription().getModuleGeometry()) {
            JMEModuleComponent physicsModule = new JMEModuleComponent(simulation,robot,geometry,"module#"+Integer.toString(i)+"."+(j++),module,moduleNode);
            module.addComponent(physicsModule);
            simulation.getModuleComponents().add(physicsModule);
        }
        
        TransmissionDevice odinTrans = new TransmissionDevice(TransmissionType.WIRE_FEMALE,0.01f);
        ReceivingDevice odinRec = new ReceivingDevice(TransmissionType.WIRE_FEMALE,10);
        for(int channel=0;channel<12;channel++) {
            module.addTransmissionDevice(JMEGeometryHelper.createTransmitter(module, module.getConnectors().get(channel),odinTrans));
            module.addReceivingDevice(JMEGeometryHelper.createReceiver(module, module.getConnectors().get(channel),odinRec));
        }
        moduleNode.setMaterial(Material.GRANITE);
        moduleNode.setMass(0.020f); //20 grams?
    }
    private void createOdinBattery(int i, final Module module, Robot robot) {
        //create OdinBattery
        CylinderShape cylinderShape = (CylinderShape) robot.getDescription().getModuleGeometry().get(0);
        ConeShape externalConeShape = (ConeShape) robot.getDescription().getModuleGeometry().get(1);
        ConeShape internalConeShape = (ConeShape) robot.getDescription().getModuleGeometry().get(2);
        
        DynamicPhysicsNode moduleNode = simulation.getPhysicsSpace().createDynamicNode();
        
        JMEModuleComponent cylinderComponent = new JMEModuleComponent(simulation,robot,cylinderShape,"module#"+Integer.toString(i)+".north",module,moduleNode);
        
        JMEModuleComponent coneNorth = new JMEModuleComponent(simulation,robot,externalConeShape,"module#"+Integer.toString(i)+".north",module,moduleNode);
        JMEModuleComponent coneSouth = new JMEModuleComponent(simulation,robot,internalConeShape,"module#"+Integer.toString(i)+".south",module,moduleNode);
         
        float unit = 0.06f/2f+0.035f; 
        cylinderComponent.addConnector("Connector 1", new Vector3f(-unit, 0, 0));
        cylinderComponent.addConnector("Connector 2", new Vector3f(unit, 0, 0));
        
        
        module.addComponent(cylinderComponent);
        module.addComponent(coneNorth);
        module.addComponent(coneSouth);
        

        TransmissionDevice odinTrans = new TransmissionDevice(TransmissionType.WIRE_MALE,0.01f);
        ReceivingDevice odinRec = new ReceivingDevice(TransmissionType.WIRE_MALE,10);
        for(int channel=0;channel<2;channel++) {
            module.addTransmissionDevice(JMEGeometryHelper.createTransmitter(module, module.getConnectors().get(channel),odinTrans));
            module.addReceivingDevice(JMEGeometryHelper.createReceiver(module, module.getConnectors().get(channel),odinRec));
        }                
        
        simulation.getModuleComponents().add(cylinderComponent);
        moduleNode.setMaterial(Material.GRANITE);
        moduleNode.setMass(0.05f); //50 grams in total?
    }
    private void createOdinHinge(int i, final Module module, Robot robot) {
        //create OdinHinge
        GeometryDescription cylinderShapeNorth  = robot.getDescription().getModuleGeometry().get(0);
        GeometryDescription cylinderShapeSouth  = robot.getDescription().getModuleGeometry().get(1);
        GeometryDescription centerShape         = robot.getDescription().getModuleGeometry().get(2);
        GeometryDescription externalConeShape   = robot.getDescription().getModuleGeometry().get(3);
        GeometryDescription internalConeShape   = robot.getDescription().getModuleGeometry().get(4);
        
        DynamicPhysicsNode moduleNodeNorth = simulation.getPhysicsSpace().createDynamicNode();
        DynamicPhysicsNode moduleNodeSouth= simulation.getPhysicsSpace().createDynamicNode();
        
        JMEModuleComponent cylinderComponentNorth = new JMEModuleComponent(simulation,robot,cylinderShapeNorth,"module#"+Integer.toString(i)+".north",module,moduleNodeNorth);
        JMEModuleComponent centerComponent = new JMEModuleComponent(simulation,robot,centerShape,"module#"+Integer.toString(i)+".north",module,moduleNodeNorth);
        
        
        JMEModuleComponent cylinderComponentSouth = new JMEModuleComponent(simulation,robot,cylinderShapeSouth,"module#"+Integer.toString(i)+".south",module,moduleNodeSouth);
        
        JMEModuleComponent coneNorth = new JMEModuleComponent(simulation,robot,externalConeShape,"module#"+Integer.toString(i)+".north",module,moduleNodeNorth);
        JMEModuleComponent coneSouth = new JMEModuleComponent(simulation,robot,internalConeShape,"module#"+Integer.toString(i)+".south",module,moduleNodeSouth);
         
        float unit = 0.06f/2f+0.035f; 
        cylinderComponentNorth.addConnector("Connector 1", new Vector3f(-unit, 0, 0));
        cylinderComponentSouth.addConnector("Connector 2", new Vector3f(unit, 0, 0));
        
        JMERotationalActuator hingeActuator = new JMERotationalActuator(simulation,"hinge");
        module.addActuator(new Actuator(hingeActuator));
        hingeActuator.attach(moduleNodeNorth,moduleNodeSouth);
        //hingeActuator.setControlParameters(0.05f*9.82f, 5f, (float) -Math.PI/2, (float) Math.PI/2);
        hingeActuator.setControlParameters(0.1f*9.82f, 5f, (float) -Math.PI/2, (float) Math.PI/2);
        //hingeActuator.setControlParameters(0f, 0f, 0, 0);
        hingeActuator.setDirection(0, 1, 0);
        
        module.addComponent(cylinderComponentNorth);
        module.addComponent(centerComponent);
        module.addComponent(cylinderComponentSouth);
        module.addComponent(coneNorth);
        module.addComponent(coneSouth);
        

        TransmissionDevice odinTrans = new TransmissionDevice(TransmissionType.WIRE_MALE,0.01f);
        ReceivingDevice odinRec = new ReceivingDevice(TransmissionType.WIRE_MALE,10);
        for(int channel=0;channel<2;channel++) {
            module.addTransmissionDevice(JMEGeometryHelper.createTransmitter(module, module.getConnectors().get(channel),odinTrans));
            module.addReceivingDevice(JMEGeometryHelper.createReceiver(module, module.getConnectors().get(channel),odinRec));
        }                
        
        simulation.getModuleComponents().add(cylinderComponentNorth);
        simulation.getModuleComponents().add(cylinderComponentSouth);
        moduleNodeNorth.setMaterial(Material.GRANITE);
        moduleNodeSouth.setMass(0.025f); //50 grams in total?
        moduleNodeNorth.setMass(0.025f); //50 grams in total?
    }
    private void createOdinWheel(int i, final Module module, Robot robot) {
        //create OdinWheel
        CylinderShape cylinderShape = (CylinderShape) robot.getDescription().getModuleGeometry().get(0);
        GeometryDescription wheel = (GeometryDescription) robot.getDescription().getModuleGeometry().get(1);
        
        ConeShape externalConeShape = (ConeShape) robot.getDescription().getModuleGeometry().get(2);
        ConeShape internalConeShape = (ConeShape) robot.getDescription().getModuleGeometry().get(3);
        
        DynamicPhysicsNode moduleNode = simulation.getPhysicsSpace().createDynamicNode();
        DynamicPhysicsNode wheelNode = simulation.getPhysicsSpace().createDynamicNode();
        
        JMEModuleComponent cylinderComponent = new JMEModuleComponent(simulation,robot,cylinderShape,"module#"+Integer.toString(i)+".north",module,moduleNode);
        JMEModuleComponent wheelComponent = new JMEModuleComponent(simulation,robot,wheel,"module#"+Integer.toString(i)+".north",module,wheelNode);
        
        JMEModuleComponent coneNorth = new JMEModuleComponent(simulation,robot,externalConeShape,"module#"+Integer.toString(i)+".north",module,moduleNode);
        JMEModuleComponent coneSouth = new JMEModuleComponent(simulation,robot,internalConeShape,"module#"+Integer.toString(i)+".south",module,moduleNode);
         
        float unit = 0.06f/2f+0.035f; 
        cylinderComponent.addConnector("Connector 1", new Vector3f(-unit, 0, 0));
        cylinderComponent.addConnector("Connector 2", new Vector3f(unit, 0, 0));
        
        JMERotationalActuator wheelActuator = new JMERotationalActuator(simulation,"wheel");
        module.addActuator(new Actuator(wheelActuator));
        wheelActuator.attach(moduleNode,wheelNode);
        wheelActuator.setControlParameters(0.5f*9.82f, 5f, 0, 0);
        wheelActuator.setDirection(1, 0, 0);
        wheelActuator.activate(10);
        
        module.addComponent(cylinderComponent);
        module.addComponent(wheelComponent);
        module.addComponent(coneNorth);
        module.addComponent(coneSouth);
        

        TransmissionDevice odinTrans = new TransmissionDevice(TransmissionType.WIRE_MALE,0.01f);
        ReceivingDevice odinRec = new ReceivingDevice(TransmissionType.WIRE_MALE,10);
        for(int channel=0;channel<2;channel++) {
            module.addTransmissionDevice(JMEGeometryHelper.createTransmitter(module, module.getConnectors().get(channel),odinTrans));
            module.addReceivingDevice(JMEGeometryHelper.createReceiver(module, module.getConnectors().get(channel),odinRec));
        }                
        
        simulation.getModuleComponents().add(cylinderComponent);
        moduleNode.setMaterial(Material.GRANITE);
        moduleNode.setMass(0.04f); //50 grams in total?
        
        wheelNode.setMaterial(Material.RUBBER);
        wheelNode.setMass(0.01f);
    }
    private void createOdinMuscle(int i, final Module module, Robot robot) {
        //create OdinMuscle
        CylinderShape externalCylinderShape = (CylinderShape) robot.getDescription().getModuleGeometry().get(0);
        CylinderShape internalCylinderShape = (CylinderShape) robot.getDescription().getModuleGeometry().get(1);
        ConeShape externalConeShape = (ConeShape) robot.getDescription().getModuleGeometry().get(2);
        ConeShape internalConeShape = (ConeShape) robot.getDescription().getModuleGeometry().get(3);
        
        DynamicPhysicsNode externalNode = simulation.getPhysicsSpace().createDynamicNode();
        DynamicPhysicsNode internalNode = simulation.getPhysicsSpace().createDynamicNode();
        
        JMEModuleComponent externalComponent = new JMEModuleComponent(simulation,robot,externalCylinderShape,"module#"+Integer.toString(i)+".north",module,externalNode);
        JMEModuleComponent externalCone = new JMEModuleComponent(simulation,robot,externalConeShape,"module#"+Integer.toString(i)+".north",module,externalNode);

        JMEModuleComponent internalComponent = new JMEModuleComponent(simulation,robot,internalCylinderShape,"module#"+Integer.toString(i)+".south",module,internalNode);
        JMEModuleComponent internalCone = new JMEModuleComponent(simulation,robot,internalConeShape,"module#"+Integer.toString(i)+".south",module,internalNode);
         
        float unit = 0.06f/2f+0.035f; 
        externalComponent.addConnector("Connector 1", new Vector3f(-unit, 0, 0));
        internalComponent.addConnector("Connector 2", new Vector3f(unit, 0, 0));
        
        
        module.addComponent(externalComponent);
        module.addComponent(externalCone);
        module.addComponent(internalComponent); 
        module.addComponent(internalCone);
        

        TransmissionDevice odinTrans = new TransmissionDevice(TransmissionType.WIRE_MALE,0.01f);
        ReceivingDevice odinRec = new ReceivingDevice(TransmissionType.WIRE_MALE,10);
        for(int channel=0;channel<2;channel++) {
            module.addTransmissionDevice(JMEGeometryHelper.createTransmitter(module, module.getConnectors().get(channel),odinTrans));
            module.addReceivingDevice(JMEGeometryHelper.createReceiver(module, module.getConnectors().get(channel),odinRec));
        }                
        JMELinearActuator centerActuator = new JMELinearActuator(simulation,"center");
        module.addActuator(new Actuator(centerActuator));
        centerActuator.attach(externalNode,internalNode);
        centerActuator.setControlParameters(9.82f,0.06f/0.25f,0f,0.06f); //odin muscle parametre - way too fast!
        //centerActuator.setControlParameters(0.5f*9.82f,0.06f,0f,0.06f); //odin muscle parametre - way too fast!
        
        JMETiltSensor tiltX = new JMETiltSensor(simulation,"tiltX",'x',externalNode);
        JMETiltSensor tiltY = new JMETiltSensor(simulation,"tiltY",'y',externalNode);
        JMETiltSensor tiltZ = new JMETiltSensor(simulation,"tiltZ",'z',externalNode);
        module.addSensor(new Sensor(tiltX));
        module.addSensor(new Sensor(tiltY)); 
        module.addSensor(new Sensor(tiltZ)); 
        
        
        simulation.getModuleComponents().add(externalComponent);
        simulation.getModuleComponents().add(internalComponent);
        externalNode.setMaterial(Material.GRANITE);
        internalNode.setMaterial(Material.GRANITE);
        externalNode.setMass(0.025f); //50 grams in total?
        internalNode.setMass(0.025f);
    }
    private void createOdinSpring(int i, final Module module, Robot robot) {
        CylinderShape externalCylinderShape = (CylinderShape) robot.getDescription().getModuleGeometry().get(0);
        CylinderShape internalCylinderShape = (CylinderShape) robot.getDescription().getModuleGeometry().get(0);
        ConeShape externalConeShape = (ConeShape) robot.getDescription().getModuleGeometry().get(1);
        ConeShape internalConeShape = (ConeShape) robot.getDescription().getModuleGeometry().get(2);
        
        DynamicPhysicsNode externalNode = simulation.getPhysicsSpace().createDynamicNode();
        DynamicPhysicsNode internalNode = simulation.getPhysicsSpace().createDynamicNode();
        
        JMEModuleComponent externalComponent = new JMEModuleComponent(simulation,robot,externalCylinderShape,"module#"+Integer.toString(i)+".north",module,externalNode);
        JMEModuleComponent externalCone = new JMEModuleComponent(simulation,robot,externalConeShape,"module#"+Integer.toString(i)+".north",module,externalNode);

        JMEModuleComponent internalComponent = new JMEModuleComponent(simulation,robot,internalCylinderShape,"module#"+Integer.toString(i)+".south",module,internalNode);
        JMEModuleComponent internalCone = new JMEModuleComponent(simulation,robot,internalConeShape,"module#"+Integer.toString(i)+".south",module,internalNode);
         
        float unit = 0.06f/2f+0.035f; 
        externalComponent.addConnector("Connector 1", new Vector3f(-unit, 0, 0));
        internalComponent.addConnector("Connector 2", new Vector3f(unit, 0, 0));
        
        
        module.addComponent(externalComponent);
        module.addComponent(externalCone);
        module.addComponent(internalComponent); 
        module.addComponent(internalCone);
        

        TransmissionDevice odinTrans = new TransmissionDevice(TransmissionType.WIRE_MALE,0.01f);
        ReceivingDevice odinRec = new ReceivingDevice(TransmissionType.WIRE_MALE,10);
        for(int channel=0;channel<2;channel++) {
            module.addTransmissionDevice(JMEGeometryHelper.createTransmitter(module, module.getConnectors().get(channel),odinTrans));
            module.addReceivingDevice(JMEGeometryHelper.createReceiver(module, module.getConnectors().get(channel),odinRec));
        }                
        JMELinearActuator centerActuator = new JMELinearActuator(simulation,"center");
        module.addActuator(new Actuator(centerActuator));
        centerActuator.attach(externalNode,internalNode);
        centerActuator.setControlParameters(0,0,0f,0.06f); //odin muscle parametre - way too fast!
      
        
        simulation.getModuleComponents().add(externalComponent);
        simulation.getModuleComponents().add(internalComponent);
        externalNode.setMaterial(Material.GRANITE);
        internalNode.setMaterial(Material.GRANITE);
        externalNode.setMass(0.025f); //50 grams in total?
        internalNode.setMass(0.025f);
    }
    public void setSimulation(JMESimulation simulation) {
        this.simulation = simulation;
    }

}
