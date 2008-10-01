/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ASETest2;

import java.util.ArrayList;

import ussr.description.Robot;
import ussr.description.geometry.VectorDescription;
import ussr.description.setup.ModulePosition;
import ussr.description.setup.WorldDescription;
import ussr.model.Controller;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.GenericATRONSimulation;
import ussr.samples.atron.natives.ATRONNativeController;

/**
 * Eight-to-car simulation using a native controller
 * 
 * @author ups
 */
public class ASESimulation extends GenericATRONSimulation {

	String controller = "ASEMain";
    public static void main(String argv[]) {
        PhysicsLogger.setDisplayInfo(true);
        PhysicsParameters.get().setMaintainRotationalJointPositions(true);
        PhysicsParameters.get().setRealisticCollision(true);
        PhysicsParameters.get().setPhysicsSimulationStepSize(0.0005f);
        new ASESimulation().main();
    }
    
    private class ASEController extends ATRONNativeController {
    	ASEController() {
            super(controller);
            //((ATRONController)this.getInternalController()).setBlocking(false);
        }
    }
    
    @Override
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ASEController();
            }
        };
    }

    protected void changeWorldHook(WorldDescription world) {
        world.setPlaneTexture(WorldDescription.WHITE_GRID_TEXTURE);
    }
    
    protected void simulationHook(PhysicsSimulation simulation) {
        ATRON zuper = new ATRON() {
            public Controller createController() {
                return new ATRONNativeController(controller);
            }
        };
        //zuper.setSuper();
        simulation.setRobot(zuper, "ATRON");
    }
    
    protected ArrayList<ModulePosition> buildRobot() {
        float Yoffset = 0.25f+2*ATRON.UNIT;
        ArrayList<ModulePosition> mPos = new ArrayList<ModulePosition>();
        mPos.add(new ModulePosition("#0", "ATRON", new VectorDescription(0*ATRON.UNIT,0*ATRON.UNIT-Yoffset,0*ATRON.UNIT), ATRON.ROTATION_NS_BROKEN));
        return mPos;
    }

}
