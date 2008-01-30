package ussr.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Used to create TCP server for a remote controller  
 * 
 * @author Modular Robots @ MMMI / lamik06@student.sdu.dk
 *
 */
public class RemoteControllerConnection {
	
	protected ServerSocket server = null;
	protected Socket client = null;
	public PrintWriter out = null;
	public BufferedReader in = null;
	private String[] name;
	
	public void openServerSocketPortInHex(Module module){
    	int port = 0;
    	name = module.getProperty("name").split(":");
    	port = Integer.parseInt(name[1].trim(),16); // converts hex string to int
    	openServerSocket(port);
	}

	public void openServerSocketPortInDec(Module module){
    	int port = 0;
    	name = module.getProperty("name").split(":");
    	port = Integer.parseInt(name[1]); 
    	openServerSocket(port);
	}

	
	private void openServerSocket(int portInDecimal){
    	try{
    		server = new ServerSocket(portInDecimal);
	    } catch (IOException e) {
	    	System.out.println("Could not listen on port: " + portInDecimal);
	    	System.exit(-1);
	    }

	    try{
	    	System.out.println("Module: " + name[0]+" -> TCP/IP socket server waiting for client to connect @ port: " + portInDecimal);

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
    }
}
