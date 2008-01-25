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

    public interface Textual extends SimulationGadget {
        public String getKey();
        public String process(String command);
        public Textual clone();
    }

    public interface Host {
        Module findModule(String name);
        List<Module> getModules();
    }
}
