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
            	disconnect(5);
            	System.out.println(getName() + " isDisconnected() = " +  isDisconnected(5));
            	for(int i=0; i < 1000 ;i++) yield();

            	connect(5);
            	System.out.println(getName() + " isConnected = " + isConnected(5));
            	for(int i=0; i < 1000 ;i++) yield();
            }
            if(name=="driver0"){
                for(int i = 0;i<8;i++){
                	System.out.println(getName() + "isOtherConnectorNearby("+i+")" + isOtherConnectorNearby(i));
                }
                for(int i=0; i < 800 ;i++) yield();
            	
            }
            yield();
        }
    }
}
