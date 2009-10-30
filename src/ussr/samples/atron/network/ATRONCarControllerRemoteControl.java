/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron.network;

import ussr.samples.GenericSimulation;
import ussr.samples.atron.ATRONController;

import java.io.*;

//import org.lwjgl.util.Color;

import ussr.model.*;

/**
 * A simple controller for the ATRON Car with remote control and it is enabled for remote
 * ATRON network controllers for the wheels 
 * 
 * @author lamik06
 *
 */
public class ATRONCarControllerRemoteControl extends ATRONController {
	
    /**
     * @see ussr.model.ControllerImpl#activate()
     */

	protected byte dir = 1;
	private byte rot = 0;
	protected String name = " ";
    byte dataReceive = 0;
	private static final byte driveCmdstop = 0;
	private static final byte driveCmdForward = 1;
	private static final byte driveCmdReverse = 2;
	private static final byte steeringCmdNeutral = 0;
	private static final byte steeringCmdRigth = 1;
	private static final byte steeringCmdLeft = 2;
	byte[] message = new byte[2];
	private byte message_old;
	private byte[] controllerAction = new byte[2];
	private boolean printDebugInfo = true;
	private byte oldmessage = 0;
	private int physicSteps = 0;

	

	
    public void activate() {
    	controllerYield();
    	message[0] = 0;
		message[1] = 0;
		controllerAction[0] = 0;
		controllerAction[1] = 1;
        
        if(module.getProperty("name")==null) module.waitForPropertyToExist("name");
        if(module.getProperty("name").contains(":"))
        	(remoteControllerConnection = new RemoteControllerConnection()).openServerSocketPortInDec(module);
        System.out.println("Module: "+module.getProperty("name")+ " -> ready");
        while(true) {
        	if(!module.getSimulation().isPaused()) {
        		name = module.getProperty("name");
        		if(name.contains(":")){
	        		if(name.startsWith("d")) {
	        		  	try{
	    	        		if (remoteControllerConnection.in.ready()){
		        		  		String[] inData = remoteControllerConnection.in.readLine().split(":");
		        		        message[0] = Byte.parseByte(inData[0]);
		        		        message[1] = Byte.parseByte(inData[1]);
		        		        if (printDebugInfo == true) System.out.println("Data recieved form remote control: Dir: " + message[0] +" Rot: "+message[1]);
		        				byte[] messageSend = new byte[1+1];
		        				messageSend[0] = message[0];
		        				messageSend[1] = message[1];
		        				sendMessage(messageSend, (byte)messageSend.length, (byte)2);
		        				sendMessage(messageSend, (byte)messageSend.length, (byte)6);
		        				if (printDebugInfo == true) System.out.println("Name: " +name + " messageSend[0](dir): "+ messageSend[0]+ " messageSend[1](rot): "+ messageSend[1]);
	    	        		}
	        		  	
	        		  	
	        		  	
	        		  	} catch  (IOException e) {
	        		    	System.out.println("Com. error");
	        	            System.exit(1);
	        		    }
	        		}
	        		if(name.startsWith("w")) {
	        		  	try{
	        		  		if (oldmessage != message[0]){

	        		  			remoteControllerConnection.out.println(message[0]);
		        		  		if (printDebugInfo == true) System.out.println("Name: " + name + " -> Data send to remote ATRON controller: " + message[0] + ":0");
		        		  		physicSteps  = 0;
		        		  		while(!remoteControllerConnection.in.ready() && physicSteps<200){
		        		  			physicSteps ++;
		        		  			controllerYield();
		        		  		}

		        		  		if (remoteControllerConnection.in.ready()){
		        		  			String inData = remoteControllerConnection.in.readLine();
			        		        controllerAction[0] = Byte.parseByte(inData);
//		        		  			String inData[] = remoteControllerConnection.in.readLine().split(":");
//			        		        messageRecieve[0] = Byte.parseByte(inData[0]);
		        		  			oldmessage = message[0];
			        		  		controllerYield();
			        		  		if (printDebugInfo == true) System.out.println("inData: " + inData +" physicSteps: " +physicSteps );
			        		  		controllerYield();

		        		  		}
	        		  		}
	        		  		controllerYield();	

	        		  		switch (controllerAction[0]) {
	        				case 0:
	        					centerStop();
	        					break;
	        				default:
	        					rotateContinuous(controllerAction[0]);
	        					break;
        				}
	        		  		
	        				controllerYield();

	        		  	} catch  (IOException e) {
	        		    	System.out.println("Com. error");
	        	            System.exit(1);
	        		    }
	        		}
	        		
        		} else {
        			controller();
        			controllerYield();
        		}
        	}
//   			controllerYield();
        }
    }
    private void controllerYield() {
		// TODO Auto-generated method stub
    	yield();
		
	}
	public void handleMessage(byte[] message, int messageSize, int channel) {
        this.message = message;	
        if (printDebugInfo == true) System.out.println("handleMessage Name: " +name + " message[0](dir): "+ message[0]+ " message[1](rot): "+ message[1]);
    }
	/*
	 * ATRON controller application
	 */
    public void controller(){
   	
		if(name.startsWith("w")) {
			switch (message[0]) {
			case driveCmdstop:
				dir = 0;
				break;
			case driveCmdForward:
				dir = 1;
				break;
			case driveCmdReverse:
				dir = -1;
				break;
			default:
				break;
			}
			
			if(name.equals("wheel1") || name.equals("wheel3")) {
				if (dir != 0) {
					rotateContinuous(dir);
				}else{
					centerStop();
				}
			}
			if(name.equals("wheel2") || name.equals("wheel4")){
				if (dir != 0) {
					rotateContinuous(-dir);
				}else{
					centerStop();
				}
			}
			
		}	
		if(name.startsWith("a")) {			
			switch(message[1]){
				case steeringCmdNeutral:
					rot = 0;
					break;
				case steeringCmdRigth:
					rot = -1;
					break;
				case steeringCmdLeft:
					rot = 1;
					break;
				}
			if (message_old != message[0]){
				message_old = message[0];
				sendMessage(message, (byte)message.length, (byte)4);
				sendMessage(message, (byte)message.length, (byte)6);
				if (printDebugInfo == true) System.out.println("Name: " + name + " message[0] = " + message[0]);
			}
			if(name=="axleOne5"){
				if (rot != 0) {
					rotateToDegreeInDegrees((rot*15)+180);
				}else{
					rotateToDegreeInDegrees(180);
				}
			}
		}
	

    }

}
