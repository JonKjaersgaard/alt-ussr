/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples;

import java.awt.Color;

import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;

/**
 * A simple controller for the ATRON, controlling connector stickiness based on
 * user-controlled state stored in the main simulator
 * 
 * @author ups
 *
 */
public class ATRONSampleController1 extends ATRONController {

	private int[] roleOfConnector = {-1,-1,-1,-1,-1,-1,-1,-1};
    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        while(true) {
        	if(!module.getSimulation().isPaused()) {
        		String name = module.getProperty("name");
    			float time = module.getSimulation().getTime();
        		//rotate((float)(Math.sin(time)+1)/2f);
    			if(name=="wheel1") rotateContinuous(1);
    			if(name=="wheel2") rotateContinuous(-1);
    			if(name=="wheel3") rotateContinuous(1);
    			if(name=="wheel4") rotateContinuous(-1);
    			if(name.contains("snake")) snakeControl();
    			if(!GenericSimulation.getConnectorsAreActive()) {
    				disconnectAll();
    			}
        	}
        	Thread.yield();
        }
    }

	private void snakeControl() {
		//oscillate(3.0f); //ossilate in 10 sek
		//muscleControl();
		//delay(1000);
//		System.out.println(module.getProperty("name")+" start to self-reconfigure");
		//snakeToWalker();
		//broadCastMessage();
		testCommunication();
	//	while(true) delay(1000);
	}
	private void testCommunication() {
		for(int i=0;i<8;i++) {
			module.getTransmitters().get(i).send(new Packet(new byte[]{(byte)module.getID(),(byte)i}));
			Thread.yield();
		}
		int recCounter =0;
		while(true) {
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

	private void muscleControl() {
		while(true) {
			rotateContinuous(1);
			delay(1500);
			rotateContinuous(-1);
			delay(1500);
			//Thread.yield();
		}
	}
	static int globalState=0;
	private void snakeToWalker() {
		//works for 9-module snake only 
		int role = getRole();
		int tempConnector=-1;
		System.out.println("role = "+role);
		/*Step 1*/
		waitForState(0);
		if(role==0||role==8) {
			rotate(1); 
		}
		if(role==1||role==7) {
			rotate(1); 
			rotate(1);
			if(role==1) globalState++;
		}
		if(role==2||role==6) {
			rotate(-1); 
			rotate(-1);
		}
		waitForState(1);
		/*Step 2*/
		if(role==0||role==8) {
			connectAll(); 
			if(role==0) globalState++;
		}
		waitForState(2);
		/*Step 3*/
		if(role==7) {
			disconnectFromRole(8);
		}
		if(role==0) {
			disconnectFromRole(1);
		}
	}
	private boolean disconnectFromRole(int role) {
		for(int i=0;i<8;i++) {
			if(roleOfConnector(i)==role) {
				if(canDisconnect(i)) {
					disconnect(i);
					return true;
				}
			}
		}
		return false;
	}
	private void broadCastMessage() {
		 Transmitter transmitter = module.getTransmitters().get(0);
		 transmitter.send(new Packet(module.getID()));
		 System.out.println(module.getID()+" Message send");
	     Receiver receiver = module.getReceivers().get(0);
	     while(true) {
	         if(receiver.hasData()) {
	        	 Packet data = receiver.getData();
	             module.setColor(Color.RED);
	             System.out.println(module.getID()+" Message recieved from "+data.get(0));
	         }
	     }
	}
	private int roleOfConnector(int i) {
	/*	roleOfConnector[i];
		module.getConnectors().get(i).*/
		return 0;
	}

	private void waitForState(int i) {
		//System.out.println(module.getID()+": Waiting for state "+i);
		while(globalState!=i)
			Thread.yield();
		if(module.getID()==0) System.out.println(module.getID()+": In state "+globalState);
		
	}

	private void oscillate(float sec) {
		float startTime = module.getSimulation().getTime();
		while(module.getSimulation().getTime()<(sec+startTime)) {
			rotate(1); //blocking rotate
			rotate(-1);
		}
	}
}
