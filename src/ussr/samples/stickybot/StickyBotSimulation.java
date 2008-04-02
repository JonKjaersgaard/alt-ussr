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
public abstract class StickyBotSimulation extends GenericSimulation {
    
    public void main() {
        PhysicsFactory.addDefaultFactory("Sticky");
        PhysicsParameters.get().setWorldDampingAngularVelocity(0.1f);
        PhysicsParameters.get().setPhysicsSimulationStepSize(0.004f);
        this.runSimulation(null,false);
    }

    protected List<ModulePosition> generateGridPositions(int nmodules, int area) {
        final float scale = StickyBot.SCALE*20f;
        ArrayList<ModulePosition> positions = new ArrayList<ModulePosition>();
        int moduleCount = 0;
        placement: for(int y=0; y<area; y++)
            for(int x=-area/2; x<area/2; x++)
                for(int z=-area/2; z<area/2; z++) {
                    VectorDescription placement = new VectorDescription(x*scale,y*scale,z*scale);
                    positions.add(new ModulePosition("Bot#"+moduleCount++, "default", placement, new RotationDescription(0,0,0)));
                    if(moduleCount++>nmodules) break placement;
                }
        return positions;
    }
    
   protected List<ModulePosition> generateRandomizedPositions(int nmodules, int area) {
       final float scale = StickyBot.SCALE*3f;
       ArrayList<ModulePosition> positions = new ArrayList<ModulePosition>();
       Random random = new Random(); 
       int moduleCount = 0;
       populate: for(int i=0; i<nmodules*2; i++) {
           float x = scale*(random.nextInt(area)-area/2);
           float y = scale*(random.nextInt(area));
           float z = scale*(random.nextInt(area)-area/2);
           VectorDescription placement = new VectorDescription(x,y,z);
           for(ModulePosition existing: positions)
               if(existing.getPosition().distance(placement)<StickyBot.SCALE*4) continue populate; 
           positions.add(new ModulePosition("Bot#"+i, "default", placement, new RotationDescription(0,0,0)));
           if(moduleCount++>nmodules) break;
       }
       return positions;
   }
}
