package ussr.samples.atron;

import ussr.model.Controller;
import ussr.robotbuildingblocks.Robot;
import ussr.samples.atron.EightToCarSimulationJ.EightController;

public class CarDemo extends EightToCarSimulationJ {

    public static void main(String argv[]) {
        new CarDemo().main();
    }
    
    @Override
    protected Robot getRobot() {
        return new ATRON() {
            public Controller createController() {
                return new CarStuffController();
            }
        };
    }

    protected class CarStuffController extends EightController {
        @Override
        public void activate_after_eight2car() {
            switch(this.getMyID()) {
            case 5: this.rotateContinuous(1); break;
            case 2: this.rotateContinuous(-1); break;
            case 3: this.rotateContinuous(1); break;
            case 4: this.rotateContinuous(-1); break;
            }
        }
        
        @Override
        protected boolean canHandleMessage(byte[] incoming, int messageSize, int channel) {
            return super.canHandleMessage(incoming, messageSize, channel);
        }
    }
}
