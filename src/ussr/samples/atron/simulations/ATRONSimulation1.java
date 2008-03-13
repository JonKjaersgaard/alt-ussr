/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.atron.simulations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ussr.description.ModuleConnection;
import ussr.description.ModulePosition;
import ussr.description.Robot;
import ussr.description.RotationDescription;
import ussr.description.VectorDescription;
import ussr.description.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.samples.GenericSimulation;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;

/**
 * A sample ATRON simulation
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONSimulation1 extends GenericSimulation {
	
    public static void main( String[] args ) {
        new ATRONSimulation1().main();
    }

    public void main() {
        PhysicsParameters.get().setPhysicsSimulationStepSize(0.001f);
        setConnectorsAreActive(true);
        WorldDescription world = new WorldDescription();
        world.setPlaneSize(5);
        List<ModulePosition> modulePos;
        ATRONBuilder builder = new ATRONBuilder(); 
        //modulePos = buildAsLattice(5,2,4,1);
        modulePos = builder.buildCar(4, new VectorDescription(0,-0.25f,0));
       // modulePos = buildSnake(2);
        // modulePos = Arrays.asList(new WorldDescription.ModulePosition[] { new WorldDescription.ModulePosition("hermit", new VectorDescription(2*0*unit,0*unit,0*unit), rotation_EW) });
        world.setModulePositions(modulePos);
        
        ArrayList<ModuleConnection> connections = builder.allConnections();
        world.setModuleConnections(connections);
        
        this.runSimulation(world,true);
    }

    @Override
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ATRONSampleController1();
            }
        };
    }

 

}
