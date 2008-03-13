package onlineLearning.realAtron.comm;
public abstract class AtronMessage {
	public byte shortToByteLow(short value) {
		return (byte) value;	
	}
	public byte shortToByteHigh(short value) {
         return (byte)(value >>> 8);
	}
	public short byteToShort(byte high, byte low) {
        return  (short) (((high & 0xFF) << 8) + (low & 0xFF));
	}
	public abstract void fromByteArray(byte[] msg);
	public abstract byte[] toByteArray();
}
