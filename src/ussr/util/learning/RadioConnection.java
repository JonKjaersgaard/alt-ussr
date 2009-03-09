package ussr.util.learning;

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
	
	public RadioConnection(PhysicsSimulation simulation, int socketPort) {
		this.simulation = simulation;
		Module dummyModule = new Module();
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
				Socket connection = radioSocket.accept();
				InputStream input = connection.getInputStream();
				OutputStream output = connection.getOutputStream();
				reader = new BufferedReader(new InputStreamReader(input));
		        writer = new BufferedWriter(new OutputStreamWriter(output));
			} catch (IOException e) {
				e.printStackTrace();
			}
			readFromSocket();
		}
		
		public void readFromSocket() {
			try {
				while(reader.ready()) {
					String data = reader.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
		
	public void sendDataToSocket(char[] data) {
		try {
			writer.write(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendDataToSocket(byte[] data) {
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
		Packet packet = new Packet(data);
		radioTrans.send(packet);
	}
	
	public void sendDataToUSSR(char[] data) {
		byte[] byteData = new byte[data.length];
		for(int i=0;i<data.length;i++) {
			byteData[i] = (byte) data[i];
		}
		sendDataToUSSR(byteData);
	}
}
