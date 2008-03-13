/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.odin.simulations;

import ussr.samples.odin.OdinController;


/**
 * A simple controller for the ODIN robot, uses tilt sensor to stretch upwards
 * 
 * @author david
 *
 */
public class OdinBallController extends OdinController {
    public OdinBallController(String type) {
    	this.type =type;
    }
	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
    	while(true) {
    		try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                throw new Error("Unexpected exception in ball");
            }
    	}
	}
}
