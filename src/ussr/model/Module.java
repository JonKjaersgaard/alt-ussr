/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.model;

import java.util.ArrayList;
import java.util.List;

import ussr.physics.PhysicsModule;
import ussr.physics.jme.JMEModule;

/**
 * A module is the basic unit from which the modular robot is built.  Specifically,
 * each module is associated with a controller, and is thus the unit of autonomous
 * behavior.
 * 
 * @author ups
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
    
    /**
     * Construct a module representing the physics module passed as an argument
     * @param module the physics module represented by this module
     */
    public Module(PhysicsModule module) {
        physics = module;
    }

    /**
     * Add a connector to the module
     * @param connector the connector to add to the module
     */
    public void addConnector(Connector connector) {
        connectors.add(connector);
    }
    
    /**
     * Set the controller of the module.  The module of the controller will in turn be set
     * to this module by calling setModule on the controller.
     * @param controller the controller to associate with this module
     */
    public void setController(Controller controller) {
        this.controller = controller;
        controller.setModule(this);
    }

    /**
     * Notify the controller (or anything else waiting for an event on this module) that
     * some external event has occurred which can be observed by the module.
     */
    public synchronized void eventNotify() {
        if(controller!=null) this.notify();
    }

    /**
     * Get the connectors associated with the module
     * @return the connectors associated with the module
     */
    public List<Connector> getConnectors() {
        return connectors;
    }

    /**
     * Get the controller associated with the module
     * @return the controller associated with the module
     */
    public Controller getController() {
        return controller;
    }
}
