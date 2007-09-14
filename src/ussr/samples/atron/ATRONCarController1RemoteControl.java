/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples.atron;

import java.awt.Color;
import java.util.List;

import com.jme.math.Matrix3f;
import com.jmex.physics.DynamicPhysicsNode;

import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.model.Sensor;
import ussr.physics.jme.JMEModuleComponent;
import ussr.samples.GenericSimulation;

import java.io.*;
import java.net.*;

/**
 * A simple controller for the ATRON, controlling connector stickiness based on
 * user-controlled state stored in the main simulator
 * 
 * @author ups
 *
 */
public class ATRONCarController1RemoteControl extends ATRONController {
	
    /**
     * @see ussr.model.ControllerImpl#activate()
     */
//	   Socket socket = null;
//	   PrintWriter out = null;
//	   BufferedReader in = null;
	private byte dir = 0;
	private byte rot = 0;
	private String name = " ";
	private boolean forwardCMD = false;
	private boolean stopCMD = false;
	private boolean revCMD;
	private char state = 's';
	private char stateRot = 'n';
	private boolean neutralCMD = false;;
	private boolean rigthCMD = false;;
	private boolean leftCMD = false;
	
	
	ServerSocket server = null;
	Socket client = null;
	Socket socket = null;
	PrintWriter out = null;
	BufferedReader in = null;
    String serverAddress = ""; // server address
    int port = 10000; //base port no.
	

	
    public void activate() {
        byte lastew = -127, lastns = -127;
        
        float lastProx = Float.NEGATIVE_INFINITY;
        boolean firstTime = true;
        boolean messageStopSend = false;
        int activateCount = 1;

        while(true) {
        	if(!module.getSimulation().isPaused()) {
        		name = module.getProperty("name");
        		if(name.startsWith("d")) {
    				if(firstTime==true) {
	    				openServerSocket();
	    				firstTime = false;
    				}
	    	        int dataReceive = 0;
	    	        try{
		    	        dataReceive = Integer.parseInt(in.readLine()); // data received from server
		    	    } catch  (IOException e) {
		    	    	System.out.println("Com. error");
			            System.exit(1);
			        }

	    	        System.out.println("data " + dataReceive);
	    	        int dirTmp = dataReceive & 3;
	    	        int rotTmp = dataReceive>>2 & 3;

//	    	       
	    	        System.out.println("dirTmp: " + dirTmp + " rotTmp: "+ rotTmp ); 
    				if (dirTmp == 1){
	    				byte[] message = new byte[1+1];
	    				message[0]=(byte) name.codePointAt(0);
	    				message[1]= 'f';
	        			sendMessage(message, (byte)message.length, (byte)2);
	        			sendMessage(message, (byte)message.length, (byte)6);
	    				System.out.println(name + " Message1: "+ (char)message[1]);
    				}

    				
    				if (dirTmp == 2){
	    				byte[] message = new byte[1+1];
	    				message[0]=(byte) name.codePointAt(0);
	    				message[1]= 'r';
	        			sendMessage(message, (byte)message.length, (byte)2);
	        			sendMessage(message, (byte)message.length, (byte)6);
	    				System.out.println(name + " Message1: "+ (char)message[1]);
    				}
				
    				

    				if (dirTmp == 0) {
	    				byte[] message = new byte[1+1];
	    				message[0]=(byte) name.codePointAt(0);
	    				message[1]= 's';
	        			sendMessage(message, (byte)message.length, (byte)2);
	        			sendMessage(message, (byte)message.length, (byte)6);
	    				System.out.println(name + " Message1: "+ (char)message[1]);
    				}
    				
    				if (rotTmp == 1){
//    					stateRot = 'h';
//    					forwardCMD=false;
	    				byte[] message = new byte[1+1];
	    				message[0]=(byte) name.codePointAt(0);
	    				message[1]= 'h';
	        			sendMessage(message, (byte)message.length, (byte)2);
	        			sendMessage(message, (byte)message.length, (byte)6);
	    				System.out.println(name + " Message1: "+ (char)message[1]);
    				}
    				if (rotTmp  == 2){
	    				byte[] message = new byte[1+1];
	    				message[0]=(byte) name.codePointAt(0);
	    				message[1]= 'l';
	        			sendMessage(message, (byte)message.length, (byte)2);
	        			sendMessage(message, (byte)message.length, (byte)6);
	    				System.out.println(name + " Message1: "+ (char)message[1]);
    				}
    				if (rotTmp == 0){
	    				byte[] message = new byte[1+1];
	    				message[0]=(byte) name.codePointAt(0);
	    				message[1]= 'n';
	        			sendMessage(message, (byte)message.length, (byte)2);
	        			sendMessage(message, (byte)message.length, (byte)6);
	    				System.out.println(name + " Message1: "+ (char)message[1]);
    				}
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
    			
    			if(name=="axleOne5"){
    				if (rot != 0) {
    					rotateToDegreeInDegrees((rot*15)+180);
    				}else{
//    				System.out.println("Angle: " + getAngularPositionDegrees());
    					rotateToDegreeInDegrees(180);
    				}
    			}
    			
    			
    			

    			if(!GenericSimulation.getConnectorsAreActive()) {
    				disconnectAll();
    			}
        	}
        }
    }
        public void handleMessage(byte[] message, int messageSize, int channel) {
        	name = module.getProperty("name");
        	if(name.startsWith("net")) {
				name = name.split(":")[1];
        	}
//        	System.out.println("handleMessage: " + name + " cahrAT: " + name.charAt(0) + " message 1: " + (char) message[1]);
        	switch (name.charAt(0)){
        		case 'w':
        			if ((char)message[1] == 's'){
        				dir = 0;
        				System.out.println(name +" dir: " + dir);
        			}
        			if ((char)message[1] == 'f'){
        				dir = 1;
        				System.out.println(name +" dir: " + dir);
        			}      			
        			if ((char)message[1] == 'r'){
        				dir = -1;
        				System.out.println(name +" dir: " + dir);
        			}
        			break;
        		case 'a':
        			if ((char)message[1] == 'n'){
        				rot = 0;
        				System.out.println(name +" rot: " + rot);
        				break;
        			}
        			if ((char)message[1] == 'h'){
        				rot = -1;
        				System.out.println(name +" rot: " + rot);
        				break;
        			}
        			if ((char)message[1] == 'l'){
        				rot = 1;
        				System.out.println(name +" rot: " + rot);
        				break;
        			}
        			sendMessage(message, (byte)message.length, (byte)5);
        			sendMessage(message, (byte)message.length, (byte)7);
        			break;
        		default:
        			System.out.println("Error message[1] = " + (char) message[1]);
		
        }
        }

        public void openServerSocket(){
//        	String name = module.getProperty("name").split(":")[1];
//        	port += Integer.parseInt(Character.toString( name.charAt(5)));
        	String name = module.getProperty("name");
        	try{
        	port += module.getID();
        	  server = new ServerSocket(port);
    	    } catch (IOException e) {
    	      System.out.println("Could not listen on port: " + port);
    	      System.exit(-1);
    	    }

    	    try{
    	      System.out.println(name + " - Server listen on port: " + port);
    	      client = server.accept();
    	    } catch (IOException e) {
    	      System.out.println("Accept failed");
    	      System.exit(-1);
    	    }

    	    try{
    	      in = new BufferedReader(new InputStreamReader(client.getInputStream()));
    	      out = new PrintWriter(client.getOutputStream(), true);
    	    } catch (IOException e) {
    	      System.out.println("Accept failed");
    	      System.exit(-1);
    	    }
    	    out.println(name);
    	    System.out.println("Connected to :" + server.getLocalSocketAddress());	          

        }

        
        
}
