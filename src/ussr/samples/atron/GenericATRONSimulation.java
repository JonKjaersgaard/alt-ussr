/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron;

import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.setup.ModuleConnection;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
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
	
    public void main() {
        this.setupPhysicsHook();
        WorldDescription world = new WorldDescription();
        world.setPlaneSize(100);
        
        ArrayList<ModulePosition> modulePos;
        modulePos = buildRobot();
        world.setModulePositions(modulePos);
        
        ArrayList<ModuleConnection> connections = new ATRONBuilder().allConnections(modulePos);
        world.setModuleConnections(connections);

        this.changeWorldHook(world);
        
        this.runSimulation(world,PhysicsFactory.getOptions().getStartPaused());
    }
    
    protected void setupPhysicsHook() { ; }
    
    protected void changeWorldHook(WorldDescription world) { ; }

    protected abstract Robot getRobot(); 

    protected abstract ArrayList<ModulePosition> buildRobot();
 
}
