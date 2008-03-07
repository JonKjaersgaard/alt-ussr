package ussr.physics.jme;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import ussr.model.Module;
import ussr.physics.ModuleFactory;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.Robot;

import com.jmex.physics.DynamicPhysicsNode;
import com.jmex.physics.material.Material;

/**
 * Helper class for JMESimulation, responsible for creating modules in the simulation
 * using the module factories.
 * 
 * @author Modular Robots @ MMMI 
 */
public class JMEFactoryHelper {
    private JMESimulation simulation;
    private Map<String,ModuleFactory> factories;
    
    public JMEFactoryHelper(JMESimulation simulation, ModuleFactory[] factories_list) {
        this.simulation = simulation;
        factories = new HashMap<String,ModuleFactory>();
        for(int i=0; i<factories_list.length; i++) {
            factories.put(factories_list[i].getModulePrefix(),factories_list[i]);
            factories_list[i].setSimulation(simulation);
        }
    }
    public void createModule(int module_id, final Module module, Robot robot, String module_name) {
        if(robot==null) throw new Error("Robot specification object is null");
        if(robot.getDescription()==null) throw new Error("Robot description object is null, robot type "+robot);
        String module_type = robot.getDescription().getType();
        if(module_type==null) throw new Error("Module type is null");
        ModuleFactory factory;
        // Find a matching factory
        for(String prefix: factories.keySet())
            if(module_type.startsWith(prefix)) {
                factory = factories.get(prefix);
                factory.createModule(module_id, module, robot, module_name);
                return;
            }
        throw new Error("No factory found for robot type "+module_type+", registered factories: "+PhysicsFactory.display());
    }

}
