/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package communication.local;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ussr.description.Robot;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModuleConnection;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
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
public class ATRONSimulation extends GenericSimulation {
	
    public static void main( String[] args ) {
        new ATRONSimulation().main();
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
                return new ATRONControllerLocal();
            }
        };
    }

 

}
