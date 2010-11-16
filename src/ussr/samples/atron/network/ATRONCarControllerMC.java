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
            System.out.println("Passive module "+name);
    }
    
    public void doNetworkServer() {
        while(true) {
            try{
                if(remoteControllerConnection.in.ready()){
                    System.out.println("Network module "+name+" waiting for data");
                    String inData = remoteControllerConnection.in.readLine();
                    System.out.println("Network module "+name+" received "+inData);
                    broadcastData(new byte[] { (byte)inData.length() });
                } else {
                    System.out.println("Network module "+name+": connection not ready");
                    try {
                        Thread.sleep(1000);
                    } catch(InterruptedException exn) {
                        throw new Error("Unexpected error during sleep");
                    }
                }
            } catch(IOException e) {
                    System.out.println("Communication error in module "+name);
                    System.exit(1);
            }
        }
    }

    
    
	private void broadcastData(byte[] bs) {
	    for(int c=0; c<7; c++)
	        if(this.isConnected(c)) this.sendMessage(bs, (byte)bs.length, (byte)c);
    }

    public void handleMessage(byte[] message, int messageSize, int channel) {
        System.out.println("Module "+name+" got message "+Arrays.toString(message));
    }

}
