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
public class StickyBotSimulation1 extends GenericSimulation {
    
    public static final boolean LARGE_SIMULATION = false;
    public static final int NMODULES = LARGE_SIMULATION ? 1000 : 50;
    public static final int AREA = LARGE_SIMULATION ? 50 : 20;
    
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
       populate: for(int i=0; i<NMODULES; i++) {
           float x = scale*(random.nextFloat()*AREA-AREA/2);
           float y = scale*(random.nextFloat()*AREA);
           float z = scale*(random.nextFloat()*AREA-AREA/2);
           VectorDescription placement = new VectorDescription(x,y,z);
           for(ModulePosition existing: positions)
               if(existing.getPosition().equals(placement)) continue populate; 
           positions.add(new ModulePosition("Bot#"+i, "default", placement, new RotationDescription(0,0,0)));
       }
       return positions;
   }
}
