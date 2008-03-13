/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.WorldDescription;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsSimulation;

/**
 * An abstract simulation implementation that sets up the necessary objects
 * before starting the simulation.  Hook methods are provided for customization.
 * 
 * 
 * @author Modular Robots @ MMMI
 *
 */
public abstract class GenericSimulation {
    
    /**
     * Last time user toggle activeness of connectors, help to avoid multiple re-activations
     */
    private static long lastConnectorToggleTime = -1;
    
    /**
     * Property indicating whether connectors should be active or not
     */
    private static boolean connectorsAreActive = false;
    
    /**
     * Property indicating whether actuators should be active or not
     */
    private static boolean actuatorsAreActive = true;
    
    protected abstract Robot getRobot();
    protected static PhysicsSimulation simulation;

    /**
     * Adapt description of simulation world, hook method that subclasses can override
     * @param world the world description to adapt
     */
    protected void adaptWorldToSimulationHook(WorldDescription world) { ; }
    
    public static PhysicsSimulation getPhysicsSimulation() {
    	return simulation;
    }
    public void runSimulation(WorldDescription world, boolean startPaused) {
        //System.out.println("java.library.path="+System.getProperty("java.library.path"));
        PhysicsLogger.setDefaultLoggingLevel();
        simulation = PhysicsFactory.createSimulator();
        simulation.setRobot(getRobot());
        this.simulationHook(simulation);
        if(world==null) world = createWorld();
        adaptWorldToSimulationHook(world);
        simulation.setWorld(world);
        simulation.setPause(startPaused);

        // Global connector activation toggle 
        simulation.addInputHandler("Z", new PhysicsSimulation.Handler() {
            public void handle() {
                // Avoid problem with keypress being registered twice
                if(System.currentTimeMillis()-lastConnectorToggleTime<1000) return;
                lastConnectorToggleTime = System.currentTimeMillis();
                // Toggle connector activeness flag
                setConnectorsAreActive(!getConnectorsAreActive());
                // Tell user what is happening
                if(getConnectorsAreActive()) System.out.println("Connectors are now active");
                else System.out.println("Connectors are now inactive");
            }
        });

        // Start
        simulation.start();
    }

    protected void simulationHook(PhysicsSimulation simulation) {
    	
    }
    
    
    /**
     * Create a world description for our simulation
     * @return the world description
     */
    private static WorldDescription createWorld() {
        WorldDescription world = new WorldDescription();
        world.setNumberOfModules(10);
        world.setPlaneSize(250);
        world.setObstacles(new VectorDescription[] {
                new VectorDescription(0,-2.5f,0),
                new VectorDescription(5,-1.5f,2)
        });
        return world;
    }

    /**
     * Return the state of whether connector are active or not, as controlled by user input.
     * @return true if the connectors should be active, false otherwise
     */
    public static boolean getConnectorsAreActive() {
        return connectorsAreActive;
    }

    /**
     * Set the tate of whether connector are active or not
     * @param connectorsAreActive set the states of whether the connectors should be active
     */
    public static void setConnectorsAreActive(boolean active) {
        connectorsAreActive = active;
    }

    public static void setActuatorsAreActive(boolean active) {
        actuatorsAreActive = true;
    }
    
    public static boolean getActuatorsAreActive() {
        return actuatorsAreActive;
    }
    

}
