package onlineLearning.realAtron.comm.spot;

import java.io.IOException;

import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PacketSource;
import net.tinyos.util.Dump;
import net.tinyos.util.PrintStreamMessenger;

public class AtronSpotComm {
	
	String sourceStr = "sf@localhost:9002";
//	String sourceStr = "sf@10.194.95.255:9002";
	PacketSource source;
	boolean debugPrint = true;
	//boolean remote = true;
	public AtronSpotComm() {
		init();
	}
	
	public void init() {
		try {
			source = BuildSource.makePacketSource(sourceStr);
			//source = BuildSource.makeArgsSF("10.194.95.255:9002");
			System.out.print("Opening source");
			source.open(PrintStreamMessenger.err);
			System.out.println("...done");
		}
		catch (IOException e) {
			System.err.println("Error on " + source.getName() + ": " + e);
		}
	}
	
	public byte[] read()
	{
		if(debugPrint) System.out.println("Reading package");
		byte[] packet=null;
		try {
			packet = source.readPacket();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(debugPrint) {
			System.out.println("...done");
			Dump.printPacket(System.out, packet);
			System.out.println();
			System.out.flush();
		}
		return AtronPacketPacker.unpackPacket(packet);
	}
	
	public void send(byte[] payload)
	{
		byte[] packet = AtronPacketPacker.packPacket(payload);
		try {
			if(debugPrint) System.out.println("Sending package (length="+packet.length+")");
			source.writePacket(packet);
			if(debugPrint) System.out.println("...done");
		}
		catch (IOException e) {
			System.err.println("Error on "+source.getName()+": "+ e);
		}
		if(debugPrint) Dump.printPacket(System.out, packet);
		if(debugPrint) System.out.println();
	}
}
