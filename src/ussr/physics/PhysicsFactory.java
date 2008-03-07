/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.physics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.robots.JMEATRONFactory;
import ussr.physics.jme.robots.JMEDefaultFactory;
import ussr.physics.jme.robots.JMEMTRANFactory;
import ussr.physics.jme.robots.JMEOdinFactory;
import ussr.samples.white.JMEWhiteFactory;

/**
 * An factory for creating implementation-level objects used by higher-level parts of the
 * simulator (currently only the simulator itself).
 *
 * TODO: replace hard-coded object instantiation with something more flexible 
 * 
 * @author ups
 * 
 */
public class PhysicsFactory {
    private static final ModuleFactory[] INITIAL_FACTORIES = new ModuleFactory[] { new JMEATRONFactory(), new JMEOdinFactory(), new JMEMTRANFactory() };
    
    private static ArrayList<ModuleFactory> factories = new ArrayList<ModuleFactory>(Arrays.asList(INITIAL_FACTORIES));

    public synchronized static void addFactory(ModuleFactory factory) {
        factories.add(factory);
    }
    
    /**
     * Create a new physics simulation
     * @return a new physics simulation
     */
    public static PhysicsSimulation createSimulator() {
        return new JMESimulation(factories.toArray(INITIAL_FACTORIES));
    }

    public static void addDefaultFactory(String robotPrefix) {
        addFactory(new JMEDefaultFactory(robotPrefix));
    }
    
    public static String display() {
        StringBuffer result = new StringBuffer(PhysicsFactory.class.getName().toString()+"[ ");
        for(ModuleFactory factory: factories) {
            result.append(factory.getModulePrefix());
            result.append("* ");
        }
        result.append(']');
        return result.toString();
    }
}
