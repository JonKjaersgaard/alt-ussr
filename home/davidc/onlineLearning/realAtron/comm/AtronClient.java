package onlineLearning.realAtron.comm;

import onlineLearning.realAtron.comm.sockets.AtronSocketClient;
import onlineLearning.realAtron.comm.spot.AtronSpotComm;


public class AtronClient {
	
	AtronSpotComm comm;
	AtronSocketClient client = null;
	boolean hasClient = true;
	public AtronClient() {
		comm = new AtronSpotComm();
		if(hasClient) client = new AtronSocketClient();
		new Thread() {
			public void run() {
				while(true) {
					byte[] data = comm.read();
					if(hasClient) client.send(data);
				}
			}
		}.start();
		new Thread() {
			public void run() {
				while(true) {
					if(hasClient) {
						byte[] data = client.read();
						comm.send(data);
					}
				}
			}
		}.start();
	}
	public static void main(String[] args) {
		new AtronClient();
	}
}
