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
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

//import org.lwjgl.util.Color;

import ussr.model.*;

/**
 * A simple controller for the ATRON Car with remote control and it is enabled for remote
 * ATRON network controllers for the wheels 
 * 
 * @author lamik06
 *
 */
public class ATRONCarControllerMC extends ATRONController {
	
    /**
     * @see ussr.model.ControllerImpl#activate()
     */

    private String name;
    private BlockingQueue<String> handleQueue;
    
    /**
     * Which programs that the Module can perform and this is expandable. The integer is the command to perform the
     * action: <br>
     * <br>
     * <b>Program ROTATE(0)</b> <br> - the method: rotate(dir) <br>
     * <b>Program ROTATE_CONTINUOUS(1)</b> <br> - the method: rotate_continuous(vel)<br>
     * <b>Program ROTATE_DEGREE(2)</b> <br> - the method: rotate_degree(degree)<br>
     * <b>Program ROTATE_TO_RADIAN(3)</b> <br> - the method: rotate_to_degree(rad)<br>
     * <b>Program ROTATE_TO_DEGREE(4)</b> <br> - the method: rotateToDegreeInDegrees(degrees)<br>
     * <b>Program ROTATE_DIR_DEGREE(5)</b> <br> - the method: rotateDirToInDegrees(degrees, dir)<br>
     * <b>Program ROTATE_VIA_DEGREE(6)</b> <br> - the method: rotateToViaInDegrees(degrees, via)<br>
     * <b>Program ROTATE_REVERSE_CONTINUOUS(6)</b> <br> - the method: <br>
     * <b>Program ROTATE_REVERSE_RADIAN(7)</b> <br> - the method: <br>
     * <b>Program ROTATE_REVERSE_TO_RADIAN(8)</b> <br> - the method: <br>
     * <b>Program ROTATE_REVERSE_TO_DEGREE(9)</b> <br> - the method: <br>
     * <b>Program ROTATE_REVERSE_DIR_DEGREE(10)</b> <br> - the method: <br>
     * <b>Program ROTATE_REVERSE_VIA_DEGREE(11)</b> <br> - the method: <br>
     * <b>Program STOP_ROTATE(12)</b> <br> - the method: <br>
     * <b>Program STOP_EMERGENCY(13)</b> <br> - the method: <br>
     * <br>
     * <b>Special programs for Car-construction</b> <br> 
     * 	- If axleModules id is odd: it is front axles; and even ids: it is rear axles. <br>
     *    If wheelModules id is odd: it is left wheels; and even ids: it is right wheels. <br>
     * <b>Program STOP_MOVE(14)</b> <br> - the method: <br>
     * <b>Program MOVE_FORWARD_CONTINUOUS(15)</b> <br> - the method: <br>
     * <b>Program MOVE_FORWARD_RADIAN(16)</b> <br> - the method: <br>
     * <b>Program MOVE_BACKWARD_CONTINUOUS(17)</b> <br> - the method: <br>
     * <b>Program MOVE_BACKWARD_RADIAN(18)</b> <br> - the method: <br>
     * <br>
     * <b>Special programs for X-construction</b> <br>
     * @author Sebastian Brian Therkildsen
     *
     */
    public enum Programs
    {
    	ROTATE(0),
    	ROTATE_CONTINUOUS(1),
    	ROTATE_DEGREE(2),
    	ROTATE_TO_RADIAN(3),
    	ROTATE_TO_DEGREE(4),
    	ROTATE_DIR_DEGREE(5),
    	ROTATE_VIA_DEGREE(6),

    	ROTATE_REVERSE_CONTINUOUS(6),
    	ROTATE_REVERSE_RADIAN(7),
    	ROTATE_REVERSE_TO_RADIAN(8),
    	ROTATE_REVERSE_TO_DEGREE(9),
    	ROTATE_REVERSE_DIR_DEGREE(10),
    	ROTATE_REVERSE_VIA_DEGREE(11),
    	STOP_ROTATE(12),
    	STOP_EMERGENCY(13),
    	
    	STOP_MOVE(14),
    	MOVE_FORWARD_CONTINUOUS(15),
    	MOVE_FORWARD_RADIAN(16),
    	MOVE_BACKWARD_CONTINUOUS(17),
    	MOVE_BACKWARD_RADIAN(18),
    	DEFAULT(999);
    	
    	private int p_representation;
    	
    	Programs(int representation)
    	{
    		this.p_representation = representation;
    	}
    	
    	public static Programs decode(int program)
    	{
    		for(Programs pr : values()){
    			if(pr.p_representation == program)
    				return pr;
    		}
    		return DEFAULT;
    	}

    }
    
    public void activate() {
		// Set up controller connection?
        if(module.getProperty("name")==null) module.waitForPropertyToExist("name");
        this.name = module.getProperty("name");
        if(name.contains(":"))
        	(remoteControllerConnection = new RemoteControllerConnection()).openServerSocketPortInDec(module);
        System.out.println("Module: "+name+ " -> ready");
        if(name.contains(":")) {
            System.out.println("Network server module "+name);
            doNetworkServer();
        }
        else
        {
        	System.out.println("Passive module "+name);
        	handleQueue = new LinkedBlockingQueue<String>();
        	doPacketHandling();
        }
        	
    }
    
    public void doPacketHandling(){
    	while(true){
    			
    		try {
	    		String message = handleQueue.take();
	   
	    		try
	            {	
	    			String[] channelRemover = message.split(":");
	    			int channel = Integer.parseInt(channelRemover[1]);
	    			String msg = channelRemover[0];
	    			
	               	String[] messageList = msg.split(",");
	               	String idString = ((String[])name.split(","))[1];
	               	int id = Integer.parseInt(idString);
	               	int idRight = Integer.parseInt(messageList[1]);
	               	if(id==idRight)
	               	{
	               		System.out.println("Module "+name+" has its package.");
	               		String programString = messageList[2];
	               		String valueString = messageList[3];
	               		int program = Integer.parseInt(programString);
	               		int value = Integer.parseInt(valueString);
	               		this.doProgeam(program, value, channel);
	               	}
	               	else
	               	{
	               		for(int c=0; c<7; c++)
	               			if(this.isConnected(c) && c!=channel) this.sendMessage(msg.getBytes(), (byte)msg.getBytes().length, (byte)c);
	               	}
	            }
	            catch(NumberFormatException nfe){
	                System.out.println("Module "+name+" got an exception with input: "+message+".");
	                nfe.printStackTrace();
	            }
	            catch(ArrayIndexOutOfBoundsException obe){
	              	System.out.println("Module "+name+" got an exception with input: "+name);
	               	obe.printStackTrace();
	            }
            } catch (InterruptedException e) {
                throw new Error("Command processor thread interrupted while waiting for new command");
            }
            
    	}
    }
    
    public void doProgeam(int program, int value, int channel)
    {
    	switch(Programs.decode(program))
    	{
    		case MOVE_FORWARD_CONTINUOUS:
    			if(true);
    			int i = 0;
    			int[] cs = new int[3];
    			
    			for(int c=0; c<7; c++)
        		    if(this.isConnected(c)){
        		    	cs[i]=c;
        		    	i++;
        		    }
    			if(i == 1) {
	               	String idString = ((String[])name.split(","))[1];
	               	int id = Integer.parseInt(idString);
	               	
    				doBasicProgram((id%2==0 ? 1: 6), value);
    			}

    			//for(int m=0; m)
		    	String st = "-21,1,"+program+","+value;
		        System.out.println("Module "+name+" sends message:" + st);
    			for(int c=0; c<7; c++)
           			if(this.isConnected(c) && c!=channel) 
        		        this.sendMessage(st.getBytes(), (byte)st.getBytes().length, (byte)c);
    				
        		    	
    		    
    			break;
    		case MOVE_FORWARD_RADIAN:
    			
    			break;
    		case MOVE_BACKWARD_CONTINUOUS:
    			this.rotate(value);
    			break;
    		case MOVE_BACKWARD_RADIAN:
    			
    			break;
    		case STOP_MOVE:
    			this.rotate(0);
    			break;
    		case STOP_EMERGENCY:
    			this.rotate(0);
    			break;
    		default:
    			doBasicProgram(program, value);
    			break;
    	}
    }
    
    private void doBasicProgram(int program, int value)
    {
    	switch(Programs.decode(program))
    	{
    		case ROTATE:
    			this.rotate(value); //this.rotate(dir)
    			break;
    		case ROTATE_CONTINUOUS:
    			this.rotateContinuous(value); //this.rotateContinuous(vel)
    			break;
    		case ROTATE_DEGREE:
    			this.rotateDegrees(value); //this.rotateDegrees(degrees)
    			break;
    		case ROTATE_TO_RADIAN:
    			this.rotateToDegree(value); //this.rotateToDegree(rad)
    			break;
    		case ROTATE_TO_DEGREE:
    			this.rotateToDegreeInDegrees(value); //this.rotateToDegreeInDegrees(degrees)
    			break;
    		case ROTATE_DIR_DEGREE:
    			this.rotateDirToInDegrees(value, value); //this.rotateDirToInDegrees(degrees, dir)
    			break;
    		case ROTATE_VIA_DEGREE:
    			this.rotateToViaInDegrees(value, value); //this.rotateToViaInDegrees(degrees, via)
    			break;
    		case ROTATE_REVERSE_CONTINUOUS:
    			this.rotateContinuous(-value);
    			break;
    		case ROTATE_REVERSE_RADIAN:
    			this.rotateDegrees(-value);
    			break;
    		case STOP_MOVE:
    			this.rotate(0);
    			break;
    		case STOP_ROTATE:
    			this.rotate(0);
    			break;
    		case STOP_EMERGENCY:
    			this.rotate(0);
    			break;
    		default:
    			System.out.println("Module "+name+"got an program, which is not correct. Program: "+program+".");
    			break;
    	}
    }
    
    public void doNetworkServer() {
        while(true) {
            try{
                if(remoteControllerConnection.in.ready()){
                    System.out.println("Network module "+name+" waiting for data.");
                    String inData = remoteControllerConnection.in.readLine();
                    System.out.println("Network module "+name+" received "+inData+".");
                    //broadcastData(new byte[] { (byte)inData.length() });
                    broadcastData(inData.getBytes());
                } else {
                    System.out.println("Network module "+name+": connection not ready.");
                    try {
                        Thread.sleep(1000);
                    } catch(InterruptedException exn) {
                        throw new Error("Unexpected error during sleep.");
                    }
                }
            } catch(IOException e) {
                    System.out.println("Communication error in module "+name+".");
                    System.exit(1);
            }
        }
    }

	private void broadcastData(byte[] bs) {
	    for(int c=0; c<7; c++)
	        if(this.isConnected(c)){
	        		String st = new String(bs);
	        		System.out.println("Module "+name+" sends message:" + st);
	        		this.sendMessage(bs, (byte)bs.length, (byte)c);
	        	}
    }

    public void handleMessage(byte[] message, int messageSize, int channel) {
        String messageString = new String(message);
        System.out.println("Module "+name+" got message "+messageString+" from "+channel+".");
        messageString = messageString+":"+channel;
        
        try {
            handleQueue.put(messageString);
            System.out.println("Module "+name+" puts message into queue.");
        } catch (InterruptedException e) {
            throw new Error("Unable to enqueue command for execution, queue full");
        }

    }
}
