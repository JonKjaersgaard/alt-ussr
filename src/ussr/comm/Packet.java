/**
 * 
 */
package ussr.comm;

import java.util.Arrays;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class Packet {
    
    byte[] payload;

    public Packet(int data) {
        payload = new byte[] { (byte)data };
    }
    public Packet(byte[] data) {
    	byte[] dataCopy= new byte[data.length];
    	for(int i=0;i<data.length;i++) dataCopy[i] = data[i];
        payload = dataCopy;
    }

    public byte get(int i) {
        return payload[i];
    }
    public byte[] getData() {
        return payload;
    }
}
