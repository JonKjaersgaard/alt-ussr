package ussr.util.supervision;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import ussr.comm.Packet;
import ussr.comm.RadioReceiver;
import ussr.comm.RadioTransmitter;
import ussr.comm.TransmissionType;
import ussr.description.robot.ReceivingDevice;
import ussr.description.robot.TransmissionDevice;
import ussr.model.Entity;
import ussr.model.Module;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsSimulation;
import ussr.physics.jme.JMEGeometryHelper;

import com.jme.math.Vector3f;
import com.sun.corba.se.impl.ior.ByteBuffer;

public class RadioConnection {
	PhysicsSimulation simulation;
	
	RadioTransmitter radioTrans;
	RadioReceiver radioRec;
	
    BufferedReader reader;
	BufferedWriter writer;
	ServerSocket radioSocket;
	int socketPort;
	boolean packToASE = false;
	
	public RadioConnection(PhysicsSimulation simulation, int socketPort) {
		this.simulation = simulation;
		Module dummyModule = new Module(-1);
		dummyModule.setSimulation(simulation);
		radioTrans = new RadioTransmitter(dummyModule, dummyModule,TransmissionType.RADIO, Float.MAX_VALUE);
		radioRec = new RadioReceiver(dummyModule,dummyModule,new ReceivingDevice(TransmissionType.RADIO,100));
        radioTrans.setMaxBaud(19200);
        radioTrans.setMaxBufferSize(128);
        
        this.socketPort = socketPort;
        try {
			radioSocket = new ServerSocket(socketPort);
		} catch (IOException e) {
			e.printStackTrace();
		}
		(new ConnectThread()).start();
	}
	
	class ConnectThread extends Thread {
		public void run() {
			try {
				System.out.println("Radio Connection waiting for socket client");
				Socket connection = radioSocket.accept();
				InputStream input = connection.getInputStream();
				OutputStream output = connection.getOutputStream();
				reader = new BufferedReader(new InputStreamReader(input));
		        writer = new BufferedWriter(new OutputStreamWriter(output));
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("Radio Connection made through socket");
			
			readFromSocket();
		}
		
		public void readFromSocket() {
			try {
				while(true) {
					System.out.println("Radio connection waiting for packet");
					String data = reader.readLine();
					if(data!=null) {
						System.out.println("Radio connection got packet: "+data);
						//System.out.println("Radio connection got packet: "+format(data));
						byte[] byteData = stringToByte(data);
						sendDataToUSSR(byteData);
						//sendDataToUSSR(data.getBytes());//toCharArray()
					}
					else {
						System.err.println("Warning: packet was null");
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private byte[] stringToByte(String data) {
		String[] stringValues = data.split(",");
    	byte[] byteValues = new byte[stringValues.length]; 
    	for(int j=0;j<stringValues.length;j++) {
    		byteValues[j] = Byte.parseByte(stringValues[j]);	
    	}
    	return byteValues;
	}
	
	public void sendDataToSocket(char[] data) {
		try {
			writer.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	public void sendDataToSocket(byte[] data) {
		if(packToASE){
			data = unPackFromASE(data);
		}
		for(int i=0;i<data.length;i++) {
			try {
				writer.append((char) data[i]);
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendDataToUSSR(byte[] data) {
		
		if(packToASE){
			data = packToASE(data);
		}
		Packet packet = new Packet(data);
		System.out.println("Sending to ussr: "+data.length);
		radioTrans.send(packet);
	}
	
	public void sendDataToUSSR(char[] data) {
		byte[] byteData = new byte[data.length];
		for(int i=0;i<data.length;i++) {
			byteData[i] = (byte) data[i];
		}
		sendDataToUSSR(byteData);
	}
	
	private String format(String data) {
		StringBuffer sb = new StringBuffer();
		byte[] bData = data.getBytes();
		for(int i=0;i<data.length();i++){
				sb.append(bData[i]+"");
				sb.append(' ');
		}
		return sb.toString();
	}
	
	final byte MC_MESSAGE = 6; //to make ASE know its is a msg from modular commander
	protected byte[] packToASE(byte[] data) {
		byte[] headData = new byte[data.length+1];
		headData[0]=MC_MESSAGE;
		for(int i=0;i<data.length;i++) {
			headData[i+1]=data[i];
		}
		return headData;
	}
	
	private byte[] unPackFromASE(byte[] data) {
		byte[] noHeadData = new byte[data.length-1];
		for(int i=1;i<data.length;i++) {
			noHeadData[i-1]=data[i];
		}
		return noHeadData;
	}
	
	public void setPackToASE(boolean packToASE) {
		this.packToASE = packToASE;
	}
}
