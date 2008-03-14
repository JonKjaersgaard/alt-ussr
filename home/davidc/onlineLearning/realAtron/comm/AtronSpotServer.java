package onlineLearning.realAtron.comm;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class AtronSpotServer {
	public AtronSpotServer() {
		ServerSocket atronService;
		Socket serviceSocket = null;
		DataInputStream input;
		DataOutputStream output;
		try {
			atronService = new ServerSocket(1058);
			serviceSocket = atronService.accept();
			input = new DataInputStream(serviceSocket.getInputStream());
			output = new DataOutputStream(serviceSocket.getOutputStream());
			while(true) {
				System.out.println("Client sending 1,2,3");
				output.write(new byte[] {1,2,3},0,3);
				output.flush();
				
			}
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	public static void main(String[] args) {
		new AtronSpotServer();
	}

}
