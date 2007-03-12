/**
 * 
 */
package ussr.samples.atron;

import ussr.model.Controller;
import ussr.robotbuildingblocks.Robot;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class ATRONNativeSnakeSimulation extends ATRONSnakeSimulation {
    public static void main(String argv[]) {
        new ATRONNativeSnakeSimulation().main();
    }
    
    @Override
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new ATRONNativeController("snakeController") {
                    void iterationSimulatorHook() {
                        do { 
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new Error("Unexpected interruption");
                            } 
                        } while(module.getSimulation().isPaused());
                    }
                };
            }
        };
    }
}
