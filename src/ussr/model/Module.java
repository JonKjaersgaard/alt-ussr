/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jme.math.Matrix4f;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.physics.PhysicsEntity;
import ussr.physics.PhysicsModuleComponent;
import ussr.physics.PhysicsSimulation;
import ussr.physics.PhysicsSimulationHelper;

/**
 * A module is the basic unit from which the modular robot is built.  Specifically,
 * each module is associated with a controller, and is thus the unit of autonomous
 * behavior.  This class represents an abstraction of a module that is physics engine
 * independent.
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
     * Actuators for the module
     */
    private List<Actuator> actuators = new ArrayList<Actuator>();
    
    /**
     * Sensors for the module
     */
    private List<Sensor> sensors = new ArrayList<Sensor>();

    /**
     * Globally unique ID for this module
     */
    private int uniqueID;

    /**
     * Communication transmitters for the module
     */
    private List<Transmitter> transmitters = new ArrayList<Transmitter>();

    /**
     * Communication receivers for the module
     */
    private List<Receiver> receivers = new ArrayList<Receiver>();

    PhysicsSimulation simulation;
    
    /**
     * Indicates whether module is ready for simulation (placed in the world, properly connected, etc)
     */
    private boolean isReady = false;
    
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
     * Add a actuator to the module
     * @param actuator to add to the module
     */
    public void addActuator(Actuator actuator) {
        actuators.add(actuator);
    }
    /**
     * Add a sensor to the module
     * @param sensor to add to the module
     */
    public void addSensor(Sensor sensor) {
        sensors.add(sensor);
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
        if(controller!=null) controller.setModule(this);
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
     * Get the actuators associated with the module
     * @return the actuators associated with the module
     */
    public List<Actuator> getActuators() {
        return actuators;
    }
    /**
     * Get the sensors associated with the module
     * @return the sensors associated with the module
     */
	public List<Sensor> getSensors() {
		return sensors;
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

    /**
     * Set the color of the module by assigning the color to every physics component of the module
     * @param color the color to assign to the module
     */
    public void setColor(Color color) {
        for(PhysicsModuleComponent module: physics) module.setModuleComponentColor(color);
    }

    /**
     * Set the color of a physics component of the module
     * @param color the color to assign to the physics component
     */
    public void setColor(Color color, int index) {
    	physics.get(index).setModuleComponentColor(color);
    }
    
    /**
     * Get the color of the module as a list of the color of its physics components
     * @return a list of colors
     */
    public List<Color> getColorList() {
    	List<Color> colors = new ArrayList<Color>();
        for(PhysicsModuleComponent component: physics) {
        	colors.add(component.getModuleComponentColor());
        }
        return colors;
    }
    /**
     * Set a list of color as the color of the modules physics components
     * @param colors list of colors
     */
    public void setColorList(List<Color> colors) {
    	int index = 0;
    	for(PhysicsModuleComponent component: physics) {
        	component.setModuleComponentColor(colors.get(index));
        	index++;
        }
    }

    /**
     * Obtain a list of communication transmitters in the module
     * @return communication transmitters in the module
     */
    public List<Transmitter> getTransmitters() {
        return transmitters;
    }

    /**
     * Add a communication transmitter to the module
     * @param transmitter the transmitter to add
     */
    public void addTransmissionDevice(Transmitter transmitter) {
        transmitters.add(transmitter);        
    }

    /**
     * Add a communication receiver to the module
     * @param receiver the receiver to add
     */
    public void addReceivingDevice(Receiver receiver) {
        receivers.add(receiver);
    }
    
    /**
     * Obtain a reference to the physics simulation in which the module is situated 
     * @return the physics simulation of the module
     */
    public PhysicsSimulation getSimulation() {
    	if(simulation==null)
    		return physics.get(0).getSimulation(); // All modules are in the same simulation
    	else return simulation;
    }
    
    public PhysicsSimulationHelper getSimulationHelper() {
        return physics.get(0).getSimulationHelper();
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

	public PhysicsModuleComponent getComponent(int index) {
		return physics.get(index);
	}
	
	public int getNumberOfComponents() {
	    return physics.size();
	}

	public void reset() {
		List<? extends PhysicsEntity> components = getPhysics();
		for(PhysicsEntity pe: components)
            pe.reset();
		
		for(Connector c: getConnectors())
            c.reset();
		
		for(Actuator a: getActuators())
            a.reset();
		
		for(Sensor s: getSensors())
            s.reset();
	}

	
	public void setPosition(VectorDescription position) {
		List<? extends PhysicsEntity> components = getPhysics();
		for(PhysicsEntity c1: components) {
			c1.setPosition(position);
        }
	}
	
	public void setRotation(RotationDescription rotation) {
		List<? extends PhysicsEntity> components = getPhysics();
		for(PhysicsEntity c1: components) {
			c1.setRotation(rotation);
        }
	}
	public void moveTo(VectorDescription position, RotationDescription rotation) {
		List<? extends PhysicsEntity> components = getPhysics();
		for(PhysicsEntity c1: components) {
			c1.moveTo(position, rotation);
        }
	}
	

	public void clearDynamics() {
		List<? extends PhysicsEntity> components = getPhysics();
		for(PhysicsEntity c1: components) {
			c1.clearDynamics();
        }
	}

	public void addExternalForce(float forceX, float forceY, float forceZ) {
		List<? extends PhysicsEntity> components = getPhysics();
		for(PhysicsEntity c1: components) {
			c1.addExternalForce(forceX, forceY, forceZ);
        }
	}

    public synchronized void setReady(boolean isReady) {
        this.isReady = isReady;
        this.notifyAll();
    }
    
    public synchronized void waitForReady() {
        while(!isReady)
            try {
                this.wait();
            } catch (InterruptedException e) {
                throw new Error("Unexpected interruption");
            }
    }

    public void assignToModulePosition(ModulePosition p) {
        this.setProperty("name", p.getName());
        this.setProperties(p.getProperties());
        this.moveTo(p.getPosition(),p.getRotation());
        this.clearDynamics();
        this.reset();
    }

	public void setSimulation(PhysicsSimulation simulation) {
		this.simulation = simulation;
	}
}
