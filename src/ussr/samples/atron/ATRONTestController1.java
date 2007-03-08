/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.atron;

import java.awt.Color;

import sun.rmi.runtime.GetThreadPoolAction;
import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.samples.GenericSimulation;

/**
 * A simple controller for the ATRON, controlling connector stickiness based on
 * user-controlled state stored in the main simulator
 * 
 * @author ups
 *
 */
public class ATRONTestController1 extends ATRONController {
	
    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        while(true) {
        	if(!module.getSimulation().isPaused()) {
        		//delay(30000);
        		String name = module.getProperty("name");
    			//if(name=="test 1") {
    				//testFullRotation();
    			//}
    			//testCommunication(5);
    			//}
    			//if(name=="test 2") rotateContinuous(-1);
        	}
        	Thread.yield();
        }
    }
    
    private void testFullRotation() {
    	float startTime = module.getSimulation().getTime();
    	System.out.println("Starting rotation - rad = "+getAngularPosition());
    	rotate(1);
    	System.out.println("                  - rad = "+getAngularPosition());
    	rotate(1);
    	System.out.println("                  - rad = "+getAngularPosition());
    	rotate(1);
    	System.out.println("                  - rad = "+getAngularPosition());
    	rotate(1);
    	System.out.println("                  - rad = "+getAngularPosition());
    	System.out.println("One full rotation took "+(module.getSimulation().getTime()-startTime)+" sec");
	}

    private void testCommunication(float sec) {
		for(int i=0;i<8;i++) {
			if(isOtherConnectorNearby(i)) {
				byte[] message = new byte[]{(byte)module.getID(),(byte)i};
				sendMessage(message, (byte)message.length,(byte)i);
				System.out.println(module.getID()+": Message send through connector "+i);
			}
			Thread.yield();
		}
		int recCounter =0;
		
		float stopTime = module.getSimulation().getTime()+sec;
    	while(stopTime>module.getSimulation().getTime()) {
			for(int i=0;i<8;i++) {
	    		 Receiver receiver = module.getReceivers().get(i);
		         if(receiver.hasData()) {
		        	 Packet data = receiver.getData();
		             
		             System.out.println(module.getID()+": Message recieved at channel "+i+" from "+data.get(0)+" send through channel "+data.get(1));
		             recCounter++;
		             if(recCounter==1) module.setColor(Color.YELLOW);
		             if(recCounter==2) module.setColor(Color.GREEN);
		             if(recCounter>2) module.setColor(Color.RED);
		         }
			}
			Thread.yield();
	     }
	}
}
