package onlineLearning.realAtron.comm.spot;

public class AtronPacketPacker {
	static byte counter=0;
	public static byte[] packPacket(byte[] payload) {
		/*byte[] packet = new byte[payload.length+8];
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
		}*/
		byte[] packet = new byte[payload.length+8];
		packet[0] = 0x00;
		packet[1] = 0x00;
		packet[2] = 0x00;
		packet[3] = 0x00;
		packet[4] = 0x00;
		packet[5] = (byte) payload.length;
		packet[6] = 0x00;
		packet[7] = 0x00;
		for(int i=0;i<payload.length;i++) {
			packet[i+8] = payload[i];
		}
		/*
		byte[] packet = new byte[12];
		packet[0] = 0x00;
		packet[1] = 0x00;
		packet[2] = 0x00;
		packet[3] = 0x00;
		packet[4] = 0x00;
		packet[5] = 0x04;
		packet[6] = 0x00;
		packet[7] = 0x00;
		packet[8] = 0x00;
		packet[9] = 0x01;
		packet[10] = 0x00;
		packet[11] = counter;*/
		counter++;
		//00 FF FF 00 00 04 00 00 00 01 00 2A 
		//new byte[]{0x00,0x00,0x00,0x00,0x01,0x04, 0x22, 0x06 ,0x00 ,0x00 ,0x03 ,counter}
		return packet;
	}

	public static byte[] unpackPacket(byte[] packet) {
		byte[] payload = new byte[packet.length-8];
		for(int i=0;i<payload.length;i++) {
			payload[i] = packet[i+8];
		}
		return payload;
	}
}
