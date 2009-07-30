/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.mtran;

import java.awt.Color;
import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.model.Entity;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.physics.PhysicsParameters.Material;
import ussr.samples.GenericSimulation;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.samples.atron.network.ATRONReflectionController;
import ussr.samples.atron.network.ATRONReflectionEventController;
import ussr.util.supervision.CMTracker;
import ussr.util.supervision.WifiCMBroadcaster;

/**
 * Snake simulation using ASE for control
 * 
 * @author ups
 */

public class ASESnakeSimulation extends MTRANSimulation {
	
	public static void main( String[] args ) {
	    new ASESnakeSimulation().runSimulation(null, true);
    }
	
	protected Robot getRobot() {

        MTRAN robot = new MTRAN() {
            public Controller createController() {
                return new MTRANReflectionEventController();
            }
        };
        
        return robot;
    }
	
    protected void constructRobot() {
        addModule(0,0,0,ORI2,"M0",makeProperty(0)+Entity.mkprop(Entity.PROPERTY_COLOR,Color.YELLOW.getRGB()));
        addModule(2,0,0,ORI2,"M1",makeProperty(1));
        addModule(4,0,0,ORI2,"M2",makeProperty(2));
        addModule(6,0,0,ORI2,"M3",makeProperty(3));
        addModule(8,0,0,ORI2,"M4",makeProperty(4));
        addModule(10,0,0,ORI2,"M5",makeProperty(5));
        addModule(12,0,0,ORI2,"M6",makeProperty(6));
        addModule(14,0,0,ORI2,"M7",makeProperty(7));
    }

    private String makeProperty(int id) {
        return Entity.mkprop(Entity.PROPERTY_PORTRC, 9900+id*2)+Entity.mkprop(Entity.PROPERTY_PORTEVENT, 9900+id*2+1);
    }
    
    @Override
    public Controller getController(String type) {
        return new MTRANReflectionEventController();
    }

    public void physicsTimeStepHook(PhysicsSimulation simulation) {

    }
}
