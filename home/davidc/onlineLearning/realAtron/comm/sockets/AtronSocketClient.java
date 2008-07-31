package onlineLearning.realAtron.comm.sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class AtronSocketClient extends AtronSocket {

	Socket atronClient;
	public AtronSocketClient() {
		String ip = "10.194.95.5";
		int port = 1058;
 		System.out.print("Opening Socket to "+ip+" on port "+port);
	    try {
	    	atronClient = new Socket(ip, port);
	    	input = new DataInputStream(atronClient.getInputStream());
			output = new DataOutputStream(atronClient.getOutputStream());
	    }
	    catch (IOException e) {
	        System.out.println(e);
	    }
	    System.out.println("...done");
	}
	public static void main(String[] args) {
		new AtronSocketClient();
	}
}
