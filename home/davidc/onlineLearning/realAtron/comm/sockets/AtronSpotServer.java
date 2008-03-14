package onlineLearning.realAtron.comm.sockets;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
public class AtronSpotServer extends AtronSocket {
	ServerSocket atronService;
	Socket serviceSocket = null;
	public AtronSpotServer() {
		try {
			System.out.println("Server Running");
			atronService = new ServerSocket(1058);
			serviceSocket = atronService.accept();
			System.out.println("Client accepted");
			input = new DataInputStream(serviceSocket.getInputStream());
			output = new DataOutputStream(serviceSocket.getOutputStream());
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public static void main(String[] args) {
		new AtronSpotServer();
	}

}
