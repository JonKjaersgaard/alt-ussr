package onlineLearning.realAtron.comm;
import java.io.IOException;

import onlineLearning.realAtron.comm.messages.LearningMessage;
import onlineLearning.realAtron.comm.spot.AtronSpotComm;


public class TestComm {
	static AtronSpotComm atronComm;
	static LearningMessage atronMsg;
	public static void main(String[] args)  throws IOException {
		atronMsg = new LearningMessage();
		atronComm = new AtronSpotComm();
		while(true) {
			LearningMessage msg = new LearningMessage();
			byte[] data = atronComm.read();
			msg.fromByteArray(data);
			System.out.println("Recieved: "+msg);
			atronComm.send(atronMsg.toByteArray());
			atronMsg.timestep++;
		}
	}	
}
