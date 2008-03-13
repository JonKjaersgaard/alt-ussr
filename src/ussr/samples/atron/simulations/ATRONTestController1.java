/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.simulations;

import java.awt.Color;
import java.util.List;

import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.physics.PhysicsParameters;
import ussr.samples.atron.ATRONController;

/**
 * A sample controller for the ATRON
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class ATRONTestController1 extends ATRONController {
	String testType;
    public ATRONTestController1(String testType) {
		this.testType=testType;
	}

	/**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        while(true) {
        	if(!module.getSimulation().isPaused()) {
        		controlYield();
    			if(testType.equals("velocityTest")) {
    				testFullRotation();
    			}
    			else if(testType.equals("torqueTest")) {
    				testTorque();
    			}
    			else if(testType.equals("torqueHoldTest")) {
    				testTorqueHold();
    			}
    			//testCommunication(5);
        	}
        	controlYield();
        }
    }
    private void testTorque() {
    	if(module.getID()==0) {
    		float startTime = module.getSimulation().getTime();
    		rotate(-1);	
    		rotate(-1);
    		rotate(-1);
    		rotate(-1);
    		System.out.println("One full double lift rotation took "+(module.getSimulation().getTime()-startTime)+" sec");
    	}
    	else {
    		home();
    	}
	}
    private void testTorqueHold() {
    	List<Color> colors = module.getColorList();
    	PhysicsParameters.get().setMaintainRotationalJointPositions(true);
    	if(module.getID()==0) {
    		while(true) {
	    		module.setColorList(colors);
	    		System.out.println();
	    		System.out.println("Starting to rotate -1");
	    		float startTime = module.getSimulation().getTime();
	    		rotate(-1);
	    		module.setColor(Color.MAGENTA);
	    		System.out.println();
	    		System.out.println("90 degree double lift rotation took "+(module.getSimulation().getTime()-startTime)+" sec");
	    		System.out.println("Delay");
	    		delay(10000);
	    		
    		}
    	}
    	else {
    		home();
    		while(true) controlYield();
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
