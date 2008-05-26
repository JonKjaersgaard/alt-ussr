/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package sunTron.samples.futures;

import java.awt.Color;

import sunTron.API.SunTronAPI;
import sunTron.API.SunTronDelegateAPI;
import sunTron.futures.Future;
import sunTron.futures.FutureAction;
import sunTron.futures.FutureRotateTo;
import sunTron.futures.FutureExtend;
import ussr.model.Module;
import ussr.samples.atron.ATRONController;

/**
 * A controller for a two-wheeler ATRON robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONSimpleFuturesController1 extends SunTronAPI {
	
	int i = 0;

    public void activate() {
        yield();
        String name = getName();
        if(name=="RearRightWheel"){
        	System.out.println("test");
        	SunTronAPI atronDelegateAPI = new SunTronAPI();
        	System.out.println("Start pos. =" + atronDelegateAPI.getAngularPositionDegrees());
            Future f = atronDelegateAPI.rotateToDegreeInDegrees(90);
            Future f1 = atronDelegateAPI.connect(1);
            System.out.println("f + f1 running");
            Future.waitForFutures(f1, f);
            System.out.println("f + f1 completed");
 
            // Demo count
            while(i < 30){
            	System.out.println("Demo count = " + i++);
            	yield();
            }

        }
        
            
        while(true){
        	yield();
        }
        
    }
    @Override
    public void handleMessage(byte[] message, int messageSize, int channel) {
    	// TODO Auto-generated method stub
    	super.handleMessage(message, messageSize, channel);
    }
}
