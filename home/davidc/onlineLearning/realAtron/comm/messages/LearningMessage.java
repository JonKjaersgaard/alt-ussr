package onlineLearning.realAtron.comm.messages;

public class LearningMessage extends AtronMessage {
	private static int MSG_LENGTH = 6;
	
	public short state =0;
	public short timestep = 0;
	public short reward = 0;
	
	public static boolean matchType(byte[] msg) {
		return msg.length==MSG_LENGTH;
	}
	
	public void fromByteArray(byte[] msg)  {
		if(matchType(msg)) {
			state = byteToShort(msg[0], msg[1]);
			timestep = byteToShort(msg[2], msg[3]);
			reward= byteToShort(msg[4], msg[5]);
		}
		else {
			System.err.println("Incorrect msg type");
		}
	}
	
	public byte[] toByteArray() {
		byte[] message = new byte[MSG_LENGTH];
		message[0] = shortToByteHigh(state);
		message[1] = shortToByteLow(state);
		message[2] = shortToByteHigh(timestep);
		message[3] = shortToByteLow(timestep);
		message[4] = shortToByteHigh(reward);
		message[5] = shortToByteLow(reward);	
		return message;
	}
	
	public String toString() {
		return "(state, timestep, reward) = ("+state+", "+timestep+", "+reward+")";
	}
}