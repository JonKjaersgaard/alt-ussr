package sandbox;

import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public class HeadController implements QuickPrototypingController {
    // called at runtime by controller framework to see where this controller fits
    public String[] labels() { return new String[] { "head" }; }
    // run: called once, runs until controller finishes
    public void run(Module module, QuickPrototypingRuntime runtime) {
        ATRONController controller = (ATRONController)module.getController(); 
        while(true) {
            if(controller.isObjectNearby(1)||controller.isObjectNearby(3)) {
                runtime.send("wheel","evade",new Object[] {});
                controller.delay(2500);
            }
            controller.yield();
        }
    }
    // handle: called when a message arrives
    public void handle(String messageName, Object[] arguments) {
        ; // ignore all messages
    }
}
