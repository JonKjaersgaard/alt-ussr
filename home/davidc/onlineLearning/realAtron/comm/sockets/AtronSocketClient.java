package onlineLearning.realAtron.comm.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class AtronSocketClient extends AtronSocket {

	Socket atronClient;
	public AtronSocketClient() {
	    try {
	    	atronClient = new Socket("10.194.95.5", 1058);
	    	input = new DataInputStream(atronClient.getInputStream());
			output = new DataOutputStream(atronClient.getOutputStream());
	    }
	    catch (IOException e) {
	        System.out.println(e);
	    }
	}
	public static void main(String[] args) {
		new AtronSocketClient();
	}
}
