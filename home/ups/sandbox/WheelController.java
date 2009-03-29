package sandbox;

import ussr.model.Module;
import ussr.samples.atron.ATRONController;

public abstract class WheelController implements QuickPrototypingController {

    private ATRONController controller;
    private boolean evading = false;

    public void handle(String messageName, Object[] arguments) {
        if(messageName.equals("evade")) {
            this.evading = true;
            controller.rotateContinuous(getEvasionDirection());
            controller.delay(2500);
            this.evading = false;
        }
    }

    public abstract String[] labels();

    public void run(Module module, QuickPrototypingRuntime runtime) {
        controller = (ATRONController)module.getController();
        while(true) {
            if(!evading ) controller.rotateContinuous(getRotationDirection());
            controller.yield();
        }
    }

    protected abstract float getRotationDirection();

    protected abstract float getEvasionDirection();
    
}
