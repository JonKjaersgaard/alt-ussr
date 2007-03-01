/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples;

import java.awt.Color;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.model.ControllerImpl;

/**
 * A simple controller for the ATRON, controlling connector stickiness based on
 * user-controlled state stored in the main simulator
 * 
 * @author ups
 *
 */
public class ATRONSampleController1 extends ControllerImpl {

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
		//delay(1000);
//		System.out.println(module.getProperty("name")+" start to self-reconfigure");
		snakeToWalker();
		//broadCastMessage();
		while(true) delay(1000);
	}
	private int getRole() {
		//quick hack mehtod
		String name = module.getProperty("name");
		if(!name.contains("snake")) throw new RuntimeException("Can not select role for this module: not a snake");
		for(int i=100;i>=0;i--) {
			if(name.contains(Integer.toString(i))) return i;
		}
		throw new RuntimeException("Can not select role for this module: no number in name");
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

	private boolean isRotating() {
    	return module.getActuators().get(0).isActive();
	}
	private int getRotation() {
    	float encVal = module.getActuators().get(0).getEncoderValue();
    	if(Math.abs(encVal-0.50)<0.125f) return 0;
    	if(Math.abs(encVal-0.75)<0.125f) return 1;
    	if(Math.abs(encVal-0)<0.125f || Math.abs(encVal-1)<0.125f) return 2;
    	if(Math.abs(encVal-0.25)<0.125f) return 3;
    	System.err.println("No ATRON rotation defined should not happend "+encVal);
    	return 0;
    }
    private void rotate(int dir) {
    	float goalRot = 0;
    	
    	if(getRotation()==0) goalRot = ((dir>0)?1:3);
    	else if(getRotation()==1) goalRot = ((dir>0)?2:0);
    	else if(getRotation()==2) goalRot = ((dir>0)?3:1);
    	else if(getRotation()==3) goalRot = ((dir>0)?0:2);
    	
//    	System.out.println("RotPos "+getRotation()+" go for "+goalRot/4f);
    	module.getActuators().get(0).activate(goalRot/4f);
    	while(isRotating()) {
    		module.getActuators().get(0).activate(goalRot/4f);
    		Thread.yield();
    	}
	}
	private void disconnectAll() {
		for(int i=0;i<8;i++) {
			if(isConnected(i)) {
				disconnect(i);
			}
		}
	}
	private void connectAll() {
		for(int i=0;i<8;i++) {
			if(isOtherConnectorNearby(i)) {
				System.out.println(module.getID()+" Other connector at connector "+i);
			}
			if(canConnect(i)) {
				connect(i);
			}
		}
	}
    
	private boolean canConnect(int i) {
		return isOtherConnectorNearby(i)&&isMale(i)&&!isConnected(i);
	}
	private boolean canDisconnect(int i) {
		return isMale(i)&&isConnected(i);
	}

	private boolean isMale(int i) {
		return i%2==0;
	}

	private void connect(int i) {
		module.getConnectors().get(i).connect();
	}
	
	private void disconnect(int i) {
		module.getConnectors().get(i).disconnect();
	}
	private boolean isConnected(int i) {
		return module.getConnectors().get(i).isConnected();
	}

    public void rotateContinuous(float dir) {
    	module.getActuators().get(0).activate(dir);
    }
    
    public boolean isOtherConnectorNearby(int connector) {
    	if(module.getConnectors().get(connector).isConnected()) {
    		return true;
    	}
    	if(module.getConnectors().get(connector).hasProximateConnector()) {
    		return true;
    	}
    	else  {
    		return false;
    	}
    }
}
