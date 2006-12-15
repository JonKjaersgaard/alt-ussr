/**
 * 
 */
package ussr.model;

import java.util.ArrayList;
import java.util.List;

import ussr.physics.PhysicsModule;
import ussr.physics.jme.JMEModule;

/**
 * @author ups
 *
 * A module is the basic unit from which the modular robot is built.  Specifically,
 * each module is associated with a controller, and is thus the unit of autonomous
 * behavior.
 * 
 */
public class Module extends Entity {
    /**
     * The controller of the module
     */
    private Controller controller;
    
    /**
     * The physics model for the module
     */
    private PhysicsModule physics;
 
    /**
     * Connectors for the module
     */
    private List<Connector> connectors = new ArrayList<Connector>();
    
    public Module(PhysicsModule module) {
        physics = module;
    }

    public void addConnector(Connector connector) {
        connectors.add(connector);
    }
    
    public void setController(Controller controller) {
        this.controller = controller;
        controller.setModule(this);
    }

    public synchronized void eventNotify() {
        if(controller!=null) this.notify();
    }

    public List<Connector> getConnectors() {
        return connectors;
    }

    public Controller getController() {
        return controller;
    }
}
