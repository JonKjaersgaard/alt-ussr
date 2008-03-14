package onlineLearning.realAtron.comm.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public abstract class AtronSocket {
	DataInputStream input;
	DataOutputStream output;
	public byte[] read() {
		byte[] data = null;
		try {
			int length = input.readInt();
			data = new byte[length];
			input.readFully(data);
    		System.out.print("Reading: ");
			for(int i=0;i<data.length;i++) {
				System.out.print(" "+data[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return data;
	}
	public void send(byte[] data) {
		try {
			System.out.print("Sending: ");
			for(int i=0;i<data.length;i++) {
				System.out.print(" "+data[i]);
			}
			System.out.println();
			output.write(data.length);
			output.write(data);
    		output.flush();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
