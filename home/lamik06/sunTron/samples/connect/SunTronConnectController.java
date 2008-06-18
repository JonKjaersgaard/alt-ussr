/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package sunTron.samples.connect;

import sunTron.api.SunTronAPIUSSR;
import sunTron.futures.FutureAction;
import ussr.samples.atron.ATRONController;

/**
 * A controller for a two-wheeler ATRON robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class SunTronConnectController extends SunTronAPIUSSR {
	
    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        yield();
        initConnectorList();
        yield();
        byte dir = 1;
//        for(int i = 0;i<8;i++){
//        	if (isConnected(i)) System.out.println(getName()+ " isConnected: " + i);
//        }


        //        if(name=="RearRightWheel") disconnect(4);
        while(true) {
            String name = getName();

            if(name=="RearRightWheel"){
            	System.out.println(getName() + " disconnect" + atronAPIImpl.isDisconnected(4));
            	atronAPIImpl.disconnect(4);
            	for(int i=0; i < 500 ;i++){ 
            		yield();
            	}
            	System.out.println(getName() + " disconnect" + atronAPIImpl.isDisconnected(4));
            	System.out.println(getName() + " connect" + atronAPIImpl.isConnected(4));
            	for(int i=0; i < 500 ;i++){ 
            		yield();
            	}
            	
            	System.out.println(getName() + " disconnect" + atronAPIImpl.isDisconnected(4));
            	connect(4).onCompletion(new FutureAction(){

					@Override
					public void execute() {
						// TODO Auto-generated method stub
						System.out.println("test");
					}});
            	for(int i=0; i < 200 ;i++){ 
            		yield();
            	}
            	
            	System.out.println(getName() + " connect" + atronAPIImpl.isConnected(4));
            }
            if(name=="driver0"){
            	
//            	for(int i=0; i < 1000 ;i++) yield();
//                for(int i = 0;i<8;i++){
//                	System.out.println(getName() + "isOtherConnectorNearby("+i+")" + atronAPIImpl.isOtherConnectorNearby(i));
//                }
//            	extend(5).onCompletion(new FutureAction(){
//
//					@Override
//					public void execute() {
//						// TODO Auto-generated method stub
//						System.out.println("test");
//					}});
            	
            }
            while (true) {
				yield();
			}
          
        	

        }
    }
}
