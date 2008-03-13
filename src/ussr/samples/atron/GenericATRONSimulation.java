/**
 * Unified Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.atron;

import java.util.ArrayList;

import ussr.description.ModuleConnection;
import ussr.description.ModulePosition;
import ussr.description.Robot;
import ussr.description.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.samples.GenericSimulation;

/**
 * An abstract implementation of an ATRON simulation, defining a number of useful
 * helper functionalities.
 * 
 * @author Modular Robots @ MMMI
 *
 */
public abstract class GenericATRONSimulation extends GenericSimulation {
	
	public static boolean startPaused = true;
    
    public void main() {
        this.setupPhysicsHook();
        setConnectorsAreActive(true);
        WorldDescription world = new WorldDescription();
        world.setPlaneSize(100);
        
        ArrayList<ModulePosition> modulePos;
        modulePos = buildRobot();
        world.setModulePositions(modulePos);
        
        ArrayList<ModuleConnection> connections = new ATRONBuilder().allConnections(modulePos);
        world.setModuleConnections(connections);

        this.changeWorldHook(world);
        
        this.runSimulation(world,startPaused);
    }
    
    protected void setupPhysicsHook() { ; }
    
    protected void changeWorldHook(WorldDescription world) { ; }

    protected abstract Robot getRobot(); 

    protected abstract ArrayList<ModulePosition> buildRobot();
 
}
