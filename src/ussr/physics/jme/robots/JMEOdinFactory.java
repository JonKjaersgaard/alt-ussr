package ussr.physics.jme.robots;

import java.util.List;

import com.jme.math.Vector3f;
import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.material.Material;

import ussr.comm.TransmissionType;
import ussr.model.Actuator;
import ussr.model.Module;
import ussr.model.Sensor;
import ussr.physics.ModuleFactory;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMEGeometryHelper;
import ussr.physics.jme.JMEModuleComponent;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.actuators.JMELinearActuator;
import ussr.physics.jme.actuators.JMERotationalActuator;
import ussr.physics.jme.sensors.JMETiltSensor;
import ussr.robotbuildingblocks.ConeShape;
import ussr.robotbuildingblocks.CylinderShape;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.ModuleComponentDescription;
import ussr.robotbuildingblocks.ReceivingDevice;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.TransmissionDevice;

/**
 * Factory for creating ODIN modules; the robot argument is used to designate the specific
 * module to create.
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class JMEOdinFactory implements ModuleFactory {
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

    /**
     * Create an Odin ball module
     * @param i
     * @param module
     * @param robot
     */
    private void createOdinBall(int i, final Module module, Robot robot) {
        DynamicPhysicsNode moduleNode = simulation.getPhysicsSpace().createDynamicNode();            
        int j=0;
        for(ModuleComponentDescription component: robot.getDescription().getModuleComponents()) {
            JMEModuleComponent physicsModule = new JMEModuleComponent(simulation,robot,component,"module#"+Integer.toString(i)+"."+(j++),module,moduleNode);
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

    /**
     * Create an Odin battery module
     * @param i
     * @param module
     * @param robot
     */
    private void createOdinBattery(int i, final Module module, Robot robot) {
        DynamicPhysicsNode moduleNode = simulation.getPhysicsSpace().createDynamicNode();
        JMEModuleComponent battery = new JMEModuleComponent(simulation,robot,robot.getDescription().getModuleComponents().get(0),"module#"+i,module,moduleNode);
        module.addComponent(battery);
        simulation.getModuleComponents().add(battery);
        TransmissionDevice odinTrans = new TransmissionDevice(TransmissionType.WIRE_MALE,0.01f);
        ReceivingDevice odinRec = new ReceivingDevice(TransmissionType.WIRE_MALE,10);
        for(int channel=0;channel<2;channel++) {
            module.addTransmissionDevice(JMEGeometryHelper.createTransmitter(module, module.getConnectors().get(channel),odinTrans));
            module.addReceivingDevice(JMEGeometryHelper.createReceiver(module, module.getConnectors().get(channel),odinRec));
        }                
        moduleNode.setMaterial(Material.GRANITE);
        moduleNode.setMass(0.05f); //50 grams in total?
    }

    private abstract class CreateOdinLongishActuatedThing {
        protected abstract void adapt(Module module, DynamicPhysicsNode north, DynamicPhysicsNode south);
        public void createLongishThing(int i, final Module module, Robot robot) {
            DynamicPhysicsNode moduleNodeNorth = simulation.getPhysicsSpace().createDynamicNode();
            DynamicPhysicsNode moduleNodeSouth= simulation.getPhysicsSpace().createDynamicNode();
            List<ModuleComponentDescription> components = robot.getDescription().getModuleComponents();
            JMEModuleComponent componentNorth = new JMEModuleComponent(simulation,robot,components.get(0),"module#"+Integer.toString(i)+".north",module,moduleNodeNorth);
            JMEModuleComponent componentSouth = new JMEModuleComponent(simulation,robot,components.get(1),"module#"+Integer.toString(i)+".south",module,moduleNodeSouth);
            module.addComponent(componentNorth);
            module.addComponent(componentSouth);
            simulation.getModuleComponents().add(componentNorth);
            simulation.getModuleComponents().add(componentSouth);
            
            this.adapt(module, moduleNodeNorth, moduleNodeSouth);
            
            TransmissionDevice odinTrans = new TransmissionDevice(TransmissionType.WIRE_MALE,0.01f);
            ReceivingDevice odinRec = new ReceivingDevice(TransmissionType.WIRE_MALE,10);
            for(int channel=0;channel<2;channel++) {
                module.addTransmissionDevice(JMEGeometryHelper.createTransmitter(module, module.getConnectors().get(channel),odinTrans));
                module.addReceivingDevice(JMEGeometryHelper.createReceiver(module, module.getConnectors().get(channel),odinRec));
            }                
            
            moduleNodeNorth.setMaterial(Material.GRANITE);
            moduleNodeSouth.setMass(0.025f); //50 grams in total?
            moduleNodeNorth.setMass(0.025f); //50 grams in total?
        }
    }
    
    /**
     * Create an Odin hinge module
     * @param i
     * @param module
     * @param robot
     */
    private void createOdinHinge(int i, Module module, Robot robot) {
        new CreateOdinLongishActuatedThing() {
            @Override
            protected void adapt(Module module, DynamicPhysicsNode north, DynamicPhysicsNode south) {
                JMERotationalActuator hingeActuator = new JMERotationalActuator(simulation,"hinge");
                module.addActuator(new Actuator(hingeActuator));
                hingeActuator.attach(north,south);
                hingeActuator.setControlParameters(0.1f*9.82f, 5f, (float) -Math.PI/2, (float) Math.PI/2);
                hingeActuator.setDirection(0, 1, 0);
            }
            
        }.createLongishThing(i, module, robot);
    }
        
    /**
     * Create an Odin wheel module
     * @param i
     * @param module
     * @param robot
     */
    private void createOdinWheel(int i, final Module module, Robot robot) {
        
        DynamicPhysicsNode axleNode = simulation.getPhysicsSpace().createDynamicNode();
        DynamicPhysicsNode wheelNode = simulation.getPhysicsSpace().createDynamicNode();
        List<ModuleComponentDescription> components = robot.getDescription().getModuleComponents();
        
        JMEModuleComponent axleComponent = new JMEModuleComponent(simulation,robot,components.get(0),"module#"+Integer.toString(i)+".axle",module,axleNode);
        JMEModuleComponent wheelComponent = new JMEModuleComponent(simulation,robot,components.get(1),"module#"+Integer.toString(i)+".wheel",module,wheelNode);
        module.addComponent(axleComponent);
        module.addComponent(wheelComponent);
        simulation.getModuleComponents().add(axleComponent);
        simulation.getModuleComponents().add(wheelComponent);
        
        JMERotationalActuator wheelActuator = new JMERotationalActuator(simulation,"wheel");
        module.addActuator(new Actuator(wheelActuator));
        wheelActuator.attach(axleNode,wheelNode);
        wheelActuator.setControlParameters(0.5f*9.82f, 5f, 0, 0);
        wheelActuator.setDirection(1, 0, 0);
        wheelActuator.activate(10);
        
        TransmissionDevice odinTrans = new TransmissionDevice(TransmissionType.WIRE_MALE,0.01f);
        ReceivingDevice odinRec = new ReceivingDevice(TransmissionType.WIRE_MALE,10);
        for(int channel=0;channel<2;channel++) {
            module.addTransmissionDevice(JMEGeometryHelper.createTransmitter(module, module.getConnectors().get(channel),odinTrans));
            module.addReceivingDevice(JMEGeometryHelper.createReceiver(module, module.getConnectors().get(channel),odinRec));
        }                
        
        axleNode.setMaterial(Material.GRANITE);
        axleNode.setMass(0.04f); //50 grams in total?
        
        wheelNode.setMaterial(Material.RUBBER);
        wheelNode.setMass(0.01f);
    }
    
    /**
     * Create an Odin muscle module
     * @param i
     * @param module
     * @param robot
     */
    private void createOdinMuscle(int i, final Module module, Robot robot) {
        new CreateOdinLongishActuatedThing() {
            @Override
            protected void adapt(Module module, DynamicPhysicsNode north, DynamicPhysicsNode south) {
                JMELinearActuator centerActuator = new JMELinearActuator(simulation,"center");
                module.addActuator(new Actuator(centerActuator));
                centerActuator.attach(north,south);
                centerActuator.setControlParameters(9.82f,0.06f/0.25f,0f,0.06f); //odin muscle parametre - way too fast!
                JMETiltSensor tiltX = new JMETiltSensor(simulation,"tiltX",'x',north);
                JMETiltSensor tiltY = new JMETiltSensor(simulation,"tiltY",'y',north);
                JMETiltSensor tiltZ = new JMETiltSensor(simulation,"tiltZ",'z',north);
                module.addSensor(new Sensor(tiltX));
                module.addSensor(new Sensor(tiltY)); 
                module.addSensor(new Sensor(tiltZ)); 
            }
        }.createLongishThing(i, module, robot);

    }
    private void createOdinSpring(int i, final Module module, Robot robot) {
        new CreateOdinLongishActuatedThing() {
            @Override
            protected void adapt(Module module, DynamicPhysicsNode north, DynamicPhysicsNode south) {
                JMELinearActuator centerActuator = new JMELinearActuator(simulation,"center");
                module.addActuator(new Actuator(centerActuator));
                centerActuator.attach(north,south);
                centerActuator.setControlParameters(0,0,0f,0.06f); //odin muscle parametre - way too fast!
            }
            
        }.createLongishThing(i, module, robot);
    }
    
    public void setSimulation(PhysicsSimulation simulation) {
        this.simulation = (JMESimulation)simulation;
    }

}
