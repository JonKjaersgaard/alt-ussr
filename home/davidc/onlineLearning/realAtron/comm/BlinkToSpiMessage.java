package onlineLearning.realAtron.comm;

public class BlinkToSpiMessage extends AtronMessage {
	private static int MSG_LENGTH =4;
	
	public short counter =0;
	public short nodeid = 0;
	
	public void fromByteArray(byte[] msg)  {
		if(matchType(msg)) {
			nodeid = byteToShort(msg[0], msg[1]);
			counter = byteToShort(msg[2], msg[3]);
		}
		else {
			System.err.println("Incorrect msg type");
		}
	}
	public byte[] toByteArray() {
		byte[] message = new byte[MSG_LENGTH];
		message[0] = shortToByteHigh(nodeid);
		message[1] = shortToByteLow(nodeid);
		message[2] = shortToByteHigh(counter);
		message[3] = shortToByteLow(counter);
		
		return message;
	}
	public static boolean matchType(byte[] msg) {
		return msg.length==MSG_LENGTH;
	}
}
