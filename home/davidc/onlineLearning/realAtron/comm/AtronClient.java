package onlineLearning.realAtron.comm;

import onlineLearning.realAtron.comm.sockets.AtronSocketClient;
import onlineLearning.realAtron.comm.spot.AtronSpotComm;


public class AtronClient {
	
	final AtronSpotComm comm;
	final AtronSocketClient client;
	public AtronClient() {
		comm = new AtronSpotComm();
		client = new AtronSocketClient();
		new Thread() {
			public void run() {
				while(true) {
					byte[] data = comm.read();
					client.send(data);
				}
				
			}
		};
		new Thread() {
			public void run() {
				while(true) {
					byte[] data = client.read();
					comm.send(data);
				}
			}
		};
	}
	public static void main(String[] args) {
		new AtronClient();
	}
}
