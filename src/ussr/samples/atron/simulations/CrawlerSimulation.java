/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.simulations;

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
import ussr.samples.atron.ATRONController;
import ussr.samples.atron.GenericATRONSimulation;

/**
 * A sample ATRON simulation
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class CrawlerSimulation extends GenericATRONSimulation {
	
    public static void main( String[] args ) {
        new CrawlerSimulation().main();
    }

    @Override
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ATRONController() {
                    @Override
                    public void activate() {
                        System.out.println("Module activated: "+this.getModule().getProperty("name"));
                    }
                };
            }
        };
    }

    @Override
    protected ArrayList<ModulePosition> buildRobot() {
        return new ATRONBuilder().buildCrawler();
    }

 

}
