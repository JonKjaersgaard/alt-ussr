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
    
    public void install(PhysicsSimulation simulation, Host host);
    public SimulationGadget clone();

    /**
     * A textual simulation gadget implements user interactions specified through a textual interface 
     * 
     * @author ups
     */
    public interface Textual extends SimulationGadget {
        public String getKey();
        public String process(String command);
        public Textual clone();
    }

    /**
     * The host provides an interface and utility functionality for a simulation gadget
     * 
     * @author ups
     */
    public interface Host {
        Module findModule(String name);
        List<Module> getModules();
    }
}
