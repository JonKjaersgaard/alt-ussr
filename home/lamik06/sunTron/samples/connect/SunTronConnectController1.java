/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package sunTron.samples.connect;


import ussr.samples.atron.ATRONController;

/**
 * A controller for a two-wheeler ATRON robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class SunTronConnectController1 extends ATRONController {
	
    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        yield();
        while(true) {
            String name = getName();
            if(name=="RearRightWheel"){
            	disconnect(4);
            	System.out.println(getName() + " isDisconnected() = " +  isDisconnected(4));
            	for(int i=0; i < 1000 ;i++) yield();

            	connect(4);
            	System.out.println(getName() + " isConnected = " + isConnected(4));
            	for(int i=0; i < 1000 ;i++) yield();
            }

            yield();
        }
    }
}
