/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.stickybot;

import ussr.description.Robot;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsParameters;

/**
 * A simple simulation with the sticky bot
 * of the connectors.
 * 
 * @author ups
 *
 */
public class LargeStickyBotSimulation extends StickyBotSimulation {
    
    public static float connectorSize = Float.NaN;
    
    public static final int NMODULES = 2;
    public static final int AREA = 2;
    
    public static void main( String[] args ) {
        new LargeStickyBotSimulation().main();
    }
    
    public void main() {
        PhysicsFactory.addDefaultFactory("Sticky");
        PhysicsParameters.get().setResolutionFactor(1);
        super.main();
    }

    @Override
    protected Robot getRobot() {
        connectorSize = StickyBot14.CONNECTORSIZE;
        return new StickyBot14() {
            public Controller createController() {
                return new LargeStickyBotSimController();
            }
            
        };
    }

   @Override
   protected void adaptWorldToSimulationHook(WorldDescription world) {
       world.setCameraPosition(WorldDescription.CameraPosition.DEFAULT);
       world.setIsFrameGrabbingActive(false);
       world.setModulePositions(generateGridPositions(NMODULES,AREA));
   }

}
