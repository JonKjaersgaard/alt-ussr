/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package sunTron.samples.connect;

import sunTron.API.SunTronAPI;
import ussr.samples.atron.ATRONController;

/**
 * A controller for a two-wheeler ATRON robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class SunTronConnectController extends SunTronAPI {
	
    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        yield();
        initConnectorList();
        yield();
        byte dir = 1;
        for(int i = 0;i<8;i++){
        	if (isConnected(i)) System.out.println(getName()+ " isConnected: " + i);
        }


        //        if(name=="RearRightWheel") disconnect(4);
        while(true) {
            String name = getName();
            if(name=="RearRightWheel"){
            	disconnect(4);
            	System.out.println(getName() + " disconnect" + atronAPIImpl.isOtherConnectorNearby(4));
            	for(int i=0; i < 1000 ;i++) yield();

            	connect(4);
            	System.out.println(getName() + " connect" + atronAPIImpl.isOtherConnectorNearby(4));
            	for(int i=0; i < 1000 ;i++) yield();
            }
        	
        	
        	
//            String name = getName();
//            if(name=="RearRightWheel") disconnect(4);//rotateContinuous(dir);
//            if(name=="RearLeftWheel") //rotateContinuous(-dir);
            yield();
        }
    }
}
