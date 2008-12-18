/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.tests;

import java.io.PrintWriter;
import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.pickers.DisplacementPicker;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONBuilder;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.util.XMLTopologyWriter;

/**
 * Basic car simulation
 * 
 * @author Modular Robots @ MMMI
 */
public class StructureTopologyDumping extends GenericATRONSimulation {
	
	public static void main( String[] args ) {
		PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
		PhysicsParameters.get().setRealisticCollision(true);
		PhysicsParameters.get().setTopologyWriter(new XMLTopologyWriter(new PrintWriter(System.out)));
		new StructureTopologyDumping().main();
    }
	
	protected ObstacleGenerator.ObstacleType obstacleType = ObstacleGenerator.ObstacleType.LINE;
	
	protected Robot getRobot() {

        ATRON robot = new ATRON() {
            public Controller createController() {
                return new ATRONCarController1();
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
        world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
    }
	
	@Override
	protected void simulationHook(PhysicsSimulation simulation) {
	    //((JMESimulation)simulation).setPicker(new DisplacementPicker());
	}
    
}
