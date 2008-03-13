/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package atron.delegate.samples.basicCar;

import java.util.List;

import atron.delegate.ATRONDelegateAPI;
import atron.spot.ATRONSPOTController;
import atron.spot.IATRONSPOTAPI;
import ussr.samples.GenericSimulation;
import ussr.samples.atron.ATRONController;
import ussr.model.ControllerImpl;
import ussr.model.Sensor;


/**
 * Delegation controller modified version of:
 * A simple controller for an ATRON car that reports data from the proximity sensors
 * 
 * This class enables the use of multiple "controllerLoop()"'s;
 * 
 * @author Lamik06 
 *
 */
public class ATRONCarControllerManager extends ATRONSPOTController {

    /**
     * @see ussr.model.ControllerImpl#activate()
     */



	
	
//	ATRONDelegateAPI aTRONAPI;
	ATRONCarControllerLoop aTRONControllerLoop;
	public void activate() {
//		aTRONAPI = new ATRONDelegateAPI(getModule());
		aTRONControllerLoop = new ATRONCarControllerLoop((IATRONSPOTAPI)this);
		yield();
    	this.delay(1000); /* rotateContinuous seem to fail sometimes if we do not wait at first */
 
        while(true) {
            if(!GenericSimulation.getActuatorsAreActive()) { yield(); continue; }
            aTRONControllerLoop.controllerLoop();
        	yield();
        }
    }



}
