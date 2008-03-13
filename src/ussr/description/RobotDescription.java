/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.description;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ussr.comm.Transmitter;

/**
 * 
 * A description of the geometry of a complete robot module, including the shape
 * of the core, the shape of a connector, and the positions of the connectors and
 * their range.
 *
 * @author Modular Robots @ MMMI
 *
 */

public class RobotDescription extends Description {
    
    /**
     * Abstract description of how the robot is assembled
     * 
     * TODO: not currently used, remove?
     * 
     * @author Modular Robots @ MMMI
     */
    public static enum PhysicsCompositionStructure {
        COMPONENT,
        MODULE,
        STRUCTURE
    }
    
    /**
     * The geometric shapes that constitute the core of the module
     */
    private List<ModuleComponentDescription> moduleComponentDescription = Collections.emptyList();
    
    private PhysicsCompositionStructure structure = PhysicsCompositionStructure.MODULE;
    
    private List<TransmissionDevice> transmitters = Collections.emptyList();
    private List<ReceivingDevice> receivers = Collections.emptyList();
 
    public RobotDescription(String type) {
        this.type = type;
    }
    
    /**
     * Get the module geometry: the geometric shapes that constitute the core of the module
     * @return the core geometry shapes
     */
    public List<ModuleComponentDescription> getModuleComponents() {
        return moduleComponentDescription;
    }
    
    /**
     * Set the module geometry: the geometric shapes that constitute the core of the module
     * @param descriptions the core geometry shapes
     */
    public void setModuleComponents(ModuleComponentDescription[] descriptions) {
        this.moduleComponentDescription = Arrays.asList(descriptions);        
    }

    public void setTransmitters(TransmissionDevice[] transmitters) {
        this.transmitters = Arrays.asList(transmitters);
    }

    public void setReceivers(ReceivingDevice[] transmitters) {
        this.receivers = Arrays.asList(transmitters);
    }

    public List<TransmissionDevice> getTransmitters() {
        return this.transmitters;
    }

    public List<ReceivingDevice> getReceivers() {
        return this.receivers;
    }

    /**
     * @return the structure
     */
    public PhysicsCompositionStructure getStructure() {
        return structure;
    }

    /**
     * @param structure the structure to set
     */
    public void setStructure(PhysicsCompositionStructure structure) {
        this.structure = structure;
    }

    private String type;
    /**
     * Type of robot
     * @return
     */
	public String getType() {
		return type;
	}
}
