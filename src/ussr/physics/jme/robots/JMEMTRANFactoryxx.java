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
public class JMEMTRANFactoryxx implements ModuleFactory {
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
        
       
        module.addComponent(northCylinderComponent);
        module.addComponent(northBoxComponent);
        module.addComponent(southCylinderComponent); 
        module.addComponent(southBoxComponent);
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
      
        
        JMERotationalActuator southActuator = new JMERotationalActuator(simulation,"south");
        module.addActuator(new Actuator(southActuator));
        southActuator.attach(southNode,centerNode);
        southActuator.setControlParameters(10f, 1f, -pi/2, pi/2);
        southActuator.setDirection(0, 1, 0);
        ContactHandlingDetails con = simulation.getStaticPlane().getMaterial().getContactHandlingDetails(southNode.getMaterial());
        
    
    }

    public String getModulePrefix() {
        return "MTRAN";
    }

    public void setSimulation(PhysicsSimulation simulation) {
        this.simulation = (JMESimulation)simulation;
    }

}
