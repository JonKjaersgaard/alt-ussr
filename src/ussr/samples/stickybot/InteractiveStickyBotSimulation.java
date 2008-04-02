/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.stickybot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import ussr.description.Robot;
import ussr.description.geometry.GeometryDescription;
import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.SphereShape;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.robots.JMEDefaultFactory;
import ussr.samples.GenericSimulation;
import ussr.physics.PhysicsParameters;

import com.jme.math.Vector3f;
import com.jme.util.LoggingSystem;

/**
 * A simple simulation with the sticky bot
 * of the connectors.
 * 
 * @author ups
 *
 */
public class InteractiveStickyBotSimulation extends StickyBotSimulation {
    
    public static final int NMODULES = 10;
    public static final int AREA = 5;
    
    public static void main( String[] args ) {
        new InteractiveStickyBotSimulation().main();
    }

    @Override
    protected Robot getRobot() {
        return new StickyBot() {
            public Controller createController() {
                return new InteractiveStickyBotSimController();
            }
            
        };
    }

   @Override
   protected void adaptWorldToSimulationHook(WorldDescription world) {
       PhysicsParameters.get().setResolutionFactor(5);
       world.setModulePositions(generateRandomizedPositions(NMODULES,AREA));
   }
}
