package atron.delegate.samples.basicCar;

import ussr.model.Sensor;
import atron.delegate.ATRONDelegateAPI;
import atron.delegate.controllerLoop.DelegateControllerLoopImpl;
import atron.spot.IUSSRSunTRONSAPI;

public class ATRONCarControllerLoop extends DelegateControllerLoopImpl{
    byte dir = 1;
    float lastProx = Float.NEGATIVE_INFINITY; /* for printing out proximity data */
    boolean firstTime = true;
    
	ATRONCarControllerLoop(IUSSRSunTRONSAPI iatronspotapi) {
		super(iatronspotapi);
	}

	public void controllerLoop() {
		String name = atronDelegateController.getName();
		if(firstTime) {
            firstTime = false;
		    if(name=="wheel1") atronDelegateController.rotateContinuous(dir);
		    if(name=="wheel2") atronDelegateController.rotateContinuous(-dir);
		    if(name=="wheel3") atronDelegateController.rotateContinuous(dir);
		    if(name=="wheel4") atronDelegateController.rotateContinuous(-dir);
		    if(name=="axleOne5" && firstTime) {
		    	atronDelegateController.rotateDegrees(10);
		    }
		}

        float max_prox = Float.NEGATIVE_INFINITY;
        for(Sensor s: atronDelegateController.getSensors()) {
            if(s.getName().startsWith("Proximity")) {
                float v = s.readValue();
                max_prox = Math.max(max_prox, v);
            }
        }
        if(name.startsWith("wheel")&&Math.abs(lastProx-max_prox)>0.01) {
            System.out.println("Proximity "+name+" max = "+max_prox);
            lastProx = max_prox; 
        }
	}

	public void handleIRCommunication(byte[] irMessage) {
		// TODO Auto-generated method stub
		
	}

	public void handleWireLessCommunication(byte[] wlanMessage) {
		// TODO Auto-generated method stub
		
	}

	public void setATRONDelegateAPI(ATRONDelegateAPI atronDelegateAPI) {
		// TODO Auto-generated method stub
		
	}

}
