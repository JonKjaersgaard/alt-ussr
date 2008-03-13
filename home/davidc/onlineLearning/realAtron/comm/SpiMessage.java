package onlineLearning.realAtron.comm;

public class SpiMessage extends AtronMessage {
	private static int MSG_LENGTH = 2*7;
	
	public short state =0;
	public short timestep = 0;
	public short reward = 0;
	public short action = 0;
	public short Q0 = 0;
	public short Q1 = 0;
	public short Q2 = 0;
	
	
	public static boolean matchType(byte[] msg) {
		return msg.length==MSG_LENGTH;
	}
	
	public void fromByteArray(byte[] msg)  {
		if(matchType(msg)) {
			state = byteToShort(msg[0], msg[1]);
			timestep = byteToShort(msg[2], msg[3]);
			reward = byteToShort(msg[4], msg[5]);
			action = byteToShort(msg[6], msg[7]);
			Q0 = byteToShort(msg[8], msg[9]);
			Q1 = byteToShort(msg[10], msg[11]);
			Q2 = byteToShort(msg[12], msg[13]);
			
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
		message[6] = shortToByteHigh(action);
		message[7] = shortToByteLow(action);	
		message[8] = shortToByteHigh(Q0);
		message[9] = shortToByteLow(Q0);	
		message[10] = shortToByteHigh(Q1);
		message[11] = shortToByteLow(Q1);	
		message[12] = shortToByteHigh(Q2);
		message[13] = shortToByteLow(Q2);	
	
		return message;
	}
	
	public String toString() {
		return "(state, timestep, reward, action, Q0, Q1, Q2) = ("+state+", "+timestep+", "+reward+", "+action+", "+Q0+", "+Q1+", "+Q2+")";
	}
}