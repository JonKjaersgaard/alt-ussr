package ussr.physics.jme;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import ussr.model.Module;
import ussr.physics.PhysicsLogger;
import ussr.physics.jme.robots.JMEModuleFactory;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.Robot;

import com.jmex.physics.DynamicPhysicsNode;

public class JMEFactoryHelper {
    private JMESimulation simulation;
    private Map<String,JMEModuleFactory> factories;
    
    public JMEFactoryHelper(JMESimulation simulation, JMEModuleFactory[] factories_list) {
        this.simulation = simulation;
        factories = new HashMap<String,JMEModuleFactory>();
        for(int i=0; i<factories_list.length; i++) {
            factories.put(factories_list[i].getModulePrefix(),factories_list[i]);
            factories_list[i].setSimulation(simulation);
        }
    }
    public void createModule(int module_id, final Module module, Robot robot, String module_name) {
        if(robot.getDescription()==null) throw new Error("Internal error: robot description is null, robot type "+robot);
        String module_type = robot.getDescription().getType();
        JMEModuleFactory factory = null;
        // Find a matching factory
        for(String prefix: factories.keySet())
            if(module_type.startsWith(prefix)) {
                factory = factories.get(prefix);
                factory.createModule(module_id, module, robot, module_name);
                return;
            }
        // Fallback: create generic module geometry based on description
        if(robot.getDescription().getModuleGeometry().size()==1) {
            PhysicsLogger.log("Warning: creating default robot for module type "+module_type);
            // Create central module node
            DynamicPhysicsNode moduleNode = simulation.getPhysicsSpace().createDynamicNode();            
            int j=0;
            for(GeometryDescription geometry: robot.getDescription().getModuleGeometry()) {
                JMEModuleComponent physicsModule = new JMEModuleComponent(simulation,robot,geometry,"module#"+Integer.toString(module_id)+"."+(j++),module,moduleNode);
                module.addComponent(physicsModule);
                simulation.getModuleComponents().add(physicsModule);
            }
        } else {
            throw new RuntimeException("Module type can not be constructed");
        }
    }

}
