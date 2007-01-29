/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples;

import java.util.logging.Level;

import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMESimulation;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.SphereShape;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;

import com.jme.math.Vector3f;
import com.jme.util.LoggingSystem;

/**
 * A simple simulation with the sticky bot, using the key "Z" to globally toggle stickiness
 * of the connectors.
 * 
 * @author ups
 *
 */
public class StickyBotSimulation1 extends GenericSimulation {
    
    public static void main( String[] args ) {
        new StickyBotSimulation1().runSimulation(null,false);
    }

    @Override
    protected Robot getRobot() {
        return new StickyBot() {
            public Controller createController() {
                return new StickyBotController1();
            }
            
        };
    }

   @Override
   protected void adaptWorldToSimulationHook(WorldDescription world) {
       world.setCameraPosition(WorldDescription.CameraPosition.FAROUT);
   }

}
