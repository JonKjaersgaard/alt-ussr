/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics.jme;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import ussr.description.Robot;
import ussr.description.geometry.GeometryDescription;
import ussr.model.Module;
import ussr.physics.ModuleFactory;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;

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
    private int fresh_id = 0;
    
    public JMEFactoryHelper(JMESimulation simulation, ModuleFactory[] factories_list) {
        this.simulation = simulation;
        factories = new HashMap<String,ModuleFactory>();
        for(int i=0; i<factories_list.length; i++) {
            factories.put(factories_list[i].getModulePrefix(),factories_list[i]);
            factories_list[i].setSimulation(simulation);
        }
    }
    public synchronized void createModule(final Module module, Robot robot, String module_name) {
        int module_id = fresh_id++; 
        if(robot==null) throw new Error("Robot specification object is null");
        if(robot.getDescription()==null) throw new Error("Robot description object is null, robot type "+robot);
        String module_type = robot.getDescription().getType();
        if(module_type==null) throw new Error("Module type is null");
        module.setProperty("ussr.module.type", module_type);
        module.setDescription(robot.getDescription());
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
