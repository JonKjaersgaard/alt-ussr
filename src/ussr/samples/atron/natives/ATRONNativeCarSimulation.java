/**
 * 
 */
package ussr.samples.atron.natives;

import ussr.model.Controller;
import ussr.physics.PhysicsParameters;
import ussr.robotbuildingblocks.Robot;
import ussr.samples.ObstacleGenerator;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.simulations.ATRONCarSimulation;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class ATRONNativeCarSimulation extends ATRONCarSimulation {
    public static void main(String argv[]) {
        new ATRONNativeCarSimulation().main();
    }
    
    @Override public void main() {
        PhysicsParameters.get().setMaintainRotationalJointPositions(true);
        PhysicsParameters.get().setRealisticCollision(true);
        PhysicsParameters.get().setPhysicsSimulationStepSize(0.01f); // before: 0.0005f
        PhysicsParameters.get().setWorldDampingLinearVelocity(0.5f);
        this.obstacleType = ObstacleGenerator.ObstacleType.LINE;
        super.main();
    }
    
    @Override
    protected Robot getRobot() {
        ATRON robot = new ATRON() {
            public Controller createController() {
                return new ATRONNativeController("dcdController");
            }
        };
        robot.setGentle();
        return robot;
    }
    
/*    @Override
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ATRONNativeController("carController");
            }
        };
    }*/
}
