/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package sunTron.samples.carRemoteControl;

import sunTron.API.SunTronAPIImpl;
import sunTron.futures.Future;
import sunTron.futures.FutureExtend;
import ussr.samples.GenericSimulation;
import java.io.*;
import java.util.List;

import ussr.model.*;

//import org.lwjgl.util.Color;



/**
 * A simple controller for the ATRON, controlling connector stickiness based on
 * user-controlled state stored in the main simulator
 * 
 * @author ups
 *
 */
public class ATRONCarUSSRControllerRemoteControl extends SunTronAPIImpl {
	
    /**
     * @see ussr.model.ControllerImpl#activate()
     */

	protected byte dir = 0;
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
	private byte[] messageRecieve = new byte[2];
	private boolean printDebugInfo = true;
	private byte oldmessage = 0;
	private int physicSteps = 0;
	private RemoteControllerConnection remoteControllerConnection;

	

	
    public void activate() {
    	yield();
    	message[0] = 0;
		message[1] = 0;
        
     //   if(module.getProperty("name")==null) module.waitForPropertyToExist("name");
        if(getName().contains(":"))
        	(remoteControllerConnection = new RemoteControllerConnection()).openServerSocketPortInHex(atronAPIImpl.getModule());

        
        //        remoteControllerConnection.openServerSocket(module);
        System.out.println("Module: "+getName()+ " -> ready");
        while(true) {
        	name = getName();
        		if(name.contains(":")){
	        		if(name.startsWith("d")) {
	        		  	try{
	    	        		if (remoteControllerConnection.in.ready()){
		        		  		String[] inData = remoteControllerConnection.in.readLine().split(":");
		        		        message[0] = Byte.parseByte(inData[0]);
		        		        message[1] = Byte.parseByte(inData[1]);
		        		        if (printDebugInfo == true) System.out.println("data recieved: Dir: " + message[0] +" Rot: "+message[1]);
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
//	    	        		if (printDebugInfo == true) System.out.println(name);
//	        		  		if (remoteControllerConnection.in.ready()){
//	        		  			String inDataTest = remoteControllerConnection.in.readLine();
//	        		  			oldmessage = message[0];
//		        		  		controllerYield();
//		        		  		if (printDebugInfo == true) System.out.println("inDataTest: " + inDataTest);
//		        		  		controllerYield();
//		        		  		String[] inData = inDataTest.split(":");
//		        		        messageRecieve[0] = Byte.parseByte(inData[0]);
//		        		        messageRecieve[1] = Byte.parseByte(inData[1]);
//	        		  		}
	        		  		if (oldmessage != message[0]){

	        		  			remoteControllerConnection.out.println(message[0] + ":0" );
		        		  		if (printDebugInfo == true) System.out.println(name + "Data send: " + message[0] + ":0");
		        		  		physicSteps  = 0;
		        		  		while(!remoteControllerConnection.in.ready() && physicSteps<200){
		        		  			physicSteps ++;
		        		  			yield();
		        		  		}

		        		  		if (remoteControllerConnection.in.ready()){
//		        		  			while(remoteControllerConnection.in.ready()){
			        		  			String inDataTest = remoteControllerConnection.in.readLine();
			        		  			oldmessage = message[0];
				        		  		yield();
				        		  		if (printDebugInfo == true) System.out.println("inDataTest: " + inDataTest +" physicSteps: " +physicSteps );
				        		  		yield();
				        		  		String[] inData = inDataTest.split(":");
				        		        messageRecieve[0] = Byte.parseByte(inData[0]);
				        		        messageRecieve[1] = Byte.parseByte(inData[1]);
//		        		  			}
		        		  		
		        		  		
		        		        switch (messageRecieve[1]) {
			        				case 1:
			        					atronAPIImpl.getModule().setColor(java.awt.Color.RED);
			        					break;
			        				case 2:
			        					atronAPIImpl.getModule().setColor(java.awt.Color.YELLOW);
			        					break;
			        				default:
			        					atronAPIImpl.getModule().setColor(java.awt.Color.GREEN);
			        					break;
		        		        }
		        		        
	        		  		}
		        		  		}else{
	        			  		if (remoteControllerConnection.in.ready()){
		        		  			String inDataTest = remoteControllerConnection.in.readLine();
			        		  		if (printDebugInfo == true) System.out.println("Tøm buffer");
		        		  			oldmessage = message[0];
			        		  		yield();
			        		  		if (printDebugInfo == true) System.out.println("inDataTest: " + inDataTest);
			        		  		yield();
			        		  		String[] inData = inDataTest.split(":");
			        		        messageRecieve[0] = Byte.parseByte(inData[0]);
	        			  		}
	        		  		}
	        		  		yield();	

	        		  		switch (messageRecieve[0]) {
	        				case 0:
	        					centerStop();
	        					break;
	        				case 1:
	        					rotateContinuous(messageRecieve[0]);
	        					break;
	        				case -1:
	        					rotateContinuous(messageRecieve[0]);
	        					break;
	        				default:
	        					break;
        				}
	        		  		
	        				yield();
	        		  		
	    	        			        		  	
		        		  	
		        		  	
	        		  	} catch  (IOException e) {
	        		    	System.out.println("Com. error");
	        	            System.exit(1);
	        		    }
	        		}
	        		
        		} else {
        			controller();
        		}
        	
   			if(!GenericSimulation.getConnectorsAreActive()) {
   				disconnectAll();
   			}
   			yield();
        }
    }
    public void handleMessage(byte[] message, int messageSize, int channel) {
        this.message = message;	
        if (printDebugInfo == true) System.out.println("handleMessage Name: " +name + " message[0](dir): "+ message[0]+ " message[1](rot): "+ message[1]);
    }
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
			if(name=="wheel1") {
				if (dir != 0) {
					rotateContinuous(dir);
				}else{
					centerStop();
				}
			}
			if(name=="wheel2"){
				if (dir != 0) {
					rotateContinuous(-dir);
				}else{
					centerStop();
				}
			}
			if(name=="wheel3"){
				if (dir != 0) {
					rotateContinuous(dir);
				}else{
					centerStop();
				}
			}
			if(name=="wheel4"){
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
	@Override
	public FutureExtend extendConnector(int connectNo) {
		return null;
		// TODO Auto-generated method stub
		
	}
	@Override
	public Future retractConnector(int connectNo) {
		return null;
		// TODO Auto-generated method stub
		
	}




}
