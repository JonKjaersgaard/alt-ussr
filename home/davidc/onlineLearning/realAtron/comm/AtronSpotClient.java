package onlineLearning.realAtron.comm;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class AtronSpotClient {

	public AtronSpotClient() {
		Socket atronClient;
		DataInputStream input;
		DataOutputStream output;
	    try {
	    	atronClient = new Socket("10.194.127.3", 1058);
	    	input = new DataInputStream(atronClient.getInputStream());
			output = new DataOutputStream(atronClient.getOutputStream());
			byte[] data = new byte[3];
			while(true) {
		    	try {
		    		input.readFully(data);
					System.out.println("Server Reading: "+data[0]+" "+data[1]+" "+data[2]);
				} catch (Exception e) {
					e.printStackTrace();
					break;
				}
		    }
	    }
	    catch (IOException e) {
	        System.out.println(e);
	    }
	}
	public static void main(String[] args) {
		new AtronSpotClient();
	}
}
