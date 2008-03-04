/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package atron.delegate.samples.basicCar;

import atron.delegate.ATRONDelegateAPI;
import ussr.samples.GenericSimulation;
import ussr.model.ControllerImpl;


/**
 * Delegation controller modified version of:
 * A simple controller for an ATRON car that reports data from the proximity sensors
 * 
 * @author Lamik06 
 *
 */
public class ATRONCarController1Delegate extends ControllerImpl {

    /**
     * @see ussr.model.ControllerImpl#activate()
     */



	
	
	ATRONDelegateAPI aTRONAPI;
	ATRONCarControllerLoopDelegate aTRONControllerLoop;
	public void activate() {
		aTRONAPI = new ATRONDelegateAPI(getModule());
		aTRONControllerLoop = new ATRONCarControllerLoopDelegate(aTRONAPI);
		controlYield();
    	this.delay(1000); /* rotateContinuous seem to fail sometimes if we do not wait at first */
 
        while(true) {
        	
        	if(!module.getSimulation().isPaused()) {
                if(!GenericSimulation.getActuatorsAreActive()) { controlYield(); continue; }
                aTRONControllerLoop.controllerLoop();
        	}
        	controlYield();
        }
    }


}
