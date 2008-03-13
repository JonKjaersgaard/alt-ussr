/**
 * 
 */
package ussr.samples.atron.natives;

import ussr.model.Controller;
import ussr.robotbuildingblocks.Robot;
import ussr.robotbuildingblocks.WorldDescription;
import ussr.samples.atron.ATRON;
import ussr.samples.atron.ATRONCarSimulation;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class ATRONDcdStandardCarSimulation extends ATRONCarSimulation {
    public static void main(String argv[]) {
        new ATRONDcdStandardCarSimulation().main();
    }
    
    protected void changeWorldHook(WorldDescription world) {
        super.changeWorldHook(world);
        //world.setFlatWorld(false);
        //world.setPlaneTexture(WorldDescription.MARS_TEXTURE);
    }
    
    @Override
    protected Robot getRobot() {
        ATRON atron = new ATRON() {
            public Controller createController() {
                return new ATRONNativeController("dcdController");
            }
        };
        atron.setGentle();
        return atron;
    }
}
