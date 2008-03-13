package onlineLearning.realAtron.comm;
import java.io.IOException;

import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PacketSource;
import net.tinyos.util.Dump;
import net.tinyos.util.PrintStreamMessenger;


public class AtronSpotComm {
	String sourceStr = "sf@localhost:9002";
	PacketSource source; 
	boolean debugPrint = false;
	public AtronSpotComm() {
		init();
	}
	public void init() {
		try {
			source = BuildSource.makePacketSource(sourceStr);
			System.out.print("Opening source");
			source.open(PrintStreamMessenger.err);
			System.out.println("...done");
		}
		catch (IOException e) {
			System.err.println("Error on " + source.getName() + ": " + e);
		}
	}
	
	public byte[] read() throws IOException
	{
		if(debugPrint) System.out.println("Reading package");
		byte[] packet = source.readPacket();
		if(debugPrint) {
			System.out.println("...done");
			Dump.printPacket(System.out, packet);
			System.out.println();
			System.out.flush();
		}
		return unpackPacket(packet);
	}
	
	public void send(byte[] payload) throws IOException
	{
		byte[] packet = packPacket(payload);
		try {
			if(debugPrint) System.out.println("Sending package");
			source.writePacket(packet);
			if(debugPrint) System.out.println("...done");
		}
		catch (IOException e) {
			System.err.println("Error on "+source.getName()+": "+ e);
		}
		if(debugPrint) Dump.printPacket(System.out, packet);
		if(debugPrint) System.out.println();
	}
	private byte[] packPacket(byte[] payload) {
		byte[] packet = new byte[payload.length+8];
		packet[0] = 0x00;
		packet[1] = 0x00;
		packet[2] = 0x00;
		packet[3] = 0x00;
		packet[4] = 0x01;
		packet[5] = (byte) payload.length;
		packet[6] = 0x22;
		packet[7] = 0x06;
		for(int i=0;i<payload.length;i++) {
			packet[i+8] = payload[i];
		}
		//new byte[]{0x00,0x00,0x00,0x00,0x01,0x04, 0x22, 0x06 ,0x00 ,0x00 ,0x03 ,counter}
		return packet;
	}

	private byte[] unpackPacket(byte[] packet) {
		byte[] payload = new byte[packet.length-8];
		for(int i=0;i<payload.length;i++) {
			payload[i] = packet[i+8];
		}
		return payload;
	}
}
