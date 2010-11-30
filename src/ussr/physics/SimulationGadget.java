/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.physics;

import java.util.List;
import ussr.model.Module;

/**
 * A simulation gadget is a plugin that allows some aspect of the user interface
 * to be tailored to the specific simulation that is running
 * 
 * @author ups
 */
public interface SimulationGadget {
    
    /**
     * Install the simulation gadget in a simulation, using a host object to interface to the simulation 
     * @param simulation the simulation in which to install the gadget 
     * @param host the host object used to interface to the simulation
     */
    public void install(PhysicsSimulation simulation, Host host);

    /**
     * Create a copy of this gadget if the gadget is intended to exist in multipe copies
     */
    public SimulationGadget clone();

    /**
     * A textual simulation gadget implements user interactions specified through a textual interface 
     * 
     * @author ups
     */
    public interface Textual extends SimulationGadget {
        /**
         * Get the prefix that indicates that interactive commands should be sent to this gadget
         * @return the prefix key for this gadget
         */
        public String getKey();
        /**
         * Process a command intended for this gadget
         * @param command the command to evaluate
         * @return the result of evaluating the command
         */
        public String process(String command);
        /**
         * @see SimulationGadget#clone()
         */
        public Textual clone();
    }

    /**
     * The host provides an interface and utility functionality for a simulation gadget
     * 
     * @author ups
     */
    public interface Host {
        /**
         * Look up a single module by its name property
         * @param name the name of the module to locate
         * @return the module, null if no matching modules were found
         */
        Module findModule(String name);
        /**
         * Return all modules currently in the simulation
         * @return the list of modules in the simulation
         */
        List<Module> getModules();
        /**
         * Print output in the console window
         * @param string the output to print
         */
        void println(String string);
    }
}
