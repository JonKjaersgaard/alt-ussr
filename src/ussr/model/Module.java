/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.physics.PhysicsEntity;
import ussr.physics.PhysicsModuleComponent;
import ussr.physics.PhysicsSimulation;

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
    private List<PhysicsModuleComponent> physics = new ArrayList<PhysicsModuleComponent>();
 
    /**
     * Connectors for the module
     */
    private List<Connector> connectors = new ArrayList<Connector>();

    /**
     * Globally unique ID for this module
     */
    private int uniqueID;

    /**
     * Transmitters for the module
     */
    private List<Transmitter> transmitters = new ArrayList<Transmitter>();

    private List<Receiver> receivers = new ArrayList<Receiver>();
    
    /**
     * Construct a module 
     */
    public Module() {
        synchronized(this) {
            uniqueID = idCounter++;
        }
    }

    /**
     * Set the physics for the module
     * @param components the physics components represented by this module
     */
    public void setPhysics(List<PhysicsModuleComponent> components) {
        physics = components;
    }

    /**
     * Get the globally unique ID of the module
     */
    public int getID() {
        return uniqueID;
    }
    
    /**
     * Add a connector to the module
     * @param connector the connector to add to the module
     */
    public void addConnector(Connector connector) {
        connectors.add(connector);
    }
    
    /**
     * Add a transmitter to the module
     * @param transmitter the transmitter to add to the module
     */
    public void addTransmitter(Transmitter transmitter) {
        transmitters.add(transmitter);
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

    /**
     * Counter for assigning globally unique IDs to modules
     */
    private static int idCounter = 0;

    public void setColor(Color color) {
        for(PhysicsModuleComponent module: physics) module.setColor(color);        
    }

    public List<Transmitter> getTransmitters() {
        return transmitters;
    }

    public void addTransmissionDevice(Transmitter transmitter) {
        transmitters.add(transmitter);        
    }

    public void addReceivingDevice(Receiver receiver) {
        receivers.add(receiver);
    }
    
    public PhysicsSimulation getSimulation() {
        return physics.get(0).getSimulation(); // All modules are in the same simulation
    }
    
    public List<? extends PhysicsEntity> getPhysics() {
        return physics;
    }

    public List<Receiver> getReceivers() {
        return receivers;
    }

    public void addComponent(PhysicsModuleComponent physicsModule) {
        physics.add(physicsModule);        
    }
}
