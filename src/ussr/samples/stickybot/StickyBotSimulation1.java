/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.stickybot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;

import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMESimulation;
import ussr.physics.jme.robots.JMEDefaultFactory;
import ussr.robotbuildingblocks.GeometryDescription;
import ussr.robotbuildingblocks.ModulePosition;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.SphereShape;
import ussr.robotbuildingblocks.VectorDescription;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.samples.GenericSimulation;
import ussr.physics.PhysicsParameters;

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
    
    public static final int NMODULES = 50;
    public static final int AREA = 20;
    
    public static void main( String[] args ) {
        PhysicsFactory.addDefaultFactory("Sticky");
        PhysicsParameters.get().setWorldDampingAngularVelocity(0.01f);
        PhysicsParameters.get().setResolutionFactor(2);
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
       //world.setCameraPosition(WorldDescription.CameraPosition.FAROUT);
       world.setModulePositions(generatePositions());
   }
   
   private List<ModulePosition> generatePositions() {
       final float scale = StickyBot.SCALE*1.5f;
       ArrayList<ModulePosition> positions = new ArrayList<ModulePosition>();
       Random random = new Random(); 
       for(int i=0; i<NMODULES; i++) {
           float x = scale*(random.nextFloat()*AREA-AREA/2);
           float y = scale*(random.nextFloat()*AREA);
           float z = scale*(random.nextFloat()*AREA-AREA/2);
           positions.add(new ModulePosition("Bot#"+i, "default", new VectorDescription(x,y,z), new RotationDescription(0,0,0)));
       }
       return positions;
   }
}
