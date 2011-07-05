/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package tests;

import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.extensions.languages.PythonGadget;
import ussr.extensions.languages.SchemeGadget;
import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMEBasicGraphicalSimulation;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.DisplacementPicker;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.ATRONController;
import ussr.samples.atron.GenericATRONSimulation;

/**
 * Basic car simulation used as test bed for the scheme gadget.
 * Note the call to setResourcePathPrefix which is currently specific to the user
 * 
 * @author Modular Robots @ MMMI
 */
public class ATRONCarSimulationWithGadgets extends GenericATRONSimulation {
	
	public static void main( String[] args ) {
		PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
		PhysicsParameters.get().setRealisticCollision(true);
		//JMEBasicGraphicalSimulation.setResourcePathPrefix("/users/ups/eclipse_workspace/ussr/");
		new ATRONCarSimulationWithGadgets().main();
    }
	
	protected ObstacleGenerator.ObstacleType obstacleType = ObstacleGenerator.ObstacleType.LINE;
	
	protected Robot getRobot() {

        ATRON robot = new ATRON() {
            public Controller createController() {
                return new ATRONController() {
                    @Override
                    public void activate() {
                        super.setBlocking(false);
                        System.out.println("Controller activated");
                    }
                };
            }
        };
        robot.setGentle();
        return robot;
    }
	
	protected ArrayList<ModulePosition> buildRobot() {
		return new ATRONBuilder().buildCar(4, new VectorDescription(0,-0.25f,0));
	}
    
	@Override
    protected void changeWorldHook(WorldDescription world) {
        ObstacleGenerator generator = new ObstacleGenerator();
        generator.obstacalize(obstacleType, world);
    }
	
	@Override
	protected void simulationHook(PhysicsSimulation simulation) {
	    simulation.addGadget(SchemeGadget.getInstance());
	    simulation.addGadget(PythonGadget.getInstance());
	}
    
}
