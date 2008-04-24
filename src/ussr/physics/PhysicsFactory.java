/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
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
 * @author ups
 * 
 */
public class PhysicsFactory {
    
    /**
     * Options for creating the simulation
     * @author ups
     */
    public static class Options implements Cloneable {
        private boolean exitOnQuit = true;

        public Options copy() { 
            try {
                return (Options)clone();
            } catch (CloneNotSupportedException e) {
                throw new Error("Internal error");
            } 
        }

        public boolean getExitOnQuit() {
            return exitOnQuit;
        }
        
        public void setExitOnQuit(boolean exitOnQuit) {
            this.exitOnQuit = exitOnQuit;
        }
    }
    
    private static final ModuleFactory[] INITIAL_FACTORIES = new ModuleFactory[] { new JMEATRONFactory(), new JMEOdinFactory(), new JMEMTRANFactory() };
    
    private static ArrayList<ModuleFactory> factories = new ArrayList<ModuleFactory>(Arrays.asList(INITIAL_FACTORIES));

    private static final Options options = new Options();
    
    /**
     * Register a factory such that it can be used to create modules. 
     * @param factory
     */
    public synchronized static void addFactory(ModuleFactory factory) {
        factories.add(factory);
    }
    
    /**
     * Create a new physics simulation.  Parameterized with the currently registered set of factories
     * and the current options.
     * @return a new physics simulation
     */
    public static PhysicsSimulation createSimulator() {
        return new JMESimulation(factories.toArray(INITIAL_FACTORIES),options.copy());
    }

    /**
     * Register a default factory such that it can be used to create modules based
     * on the robot description.
     * @param robotPrefix the module type prefix that should use this factory 
     * @see ussr.physics.jme.robots.JMEDefaultFactory
     */
    public static void addDefaultFactory(String robotPrefix) {
        addFactory(new JMEDefaultFactory(robotPrefix));
    }
    
    /**
     * Display debug information about the physics factory
     * @return String representing the debug information
     */
    public static String display() {
        StringBuffer result = new StringBuffer(PhysicsFactory.class.getName().toString()+"[ ");
        for(ModuleFactory factory: factories) {
            result.append(factory.getModulePrefix());
            result.append("* ");
        }
        result.append(']');
        return result.toString();
    }

    /**
     * Obtain the options instance, used to set parameters for the simulator
     * @return the options instance
     */
    public static Options getOptions() {
        return options;
    }
}
