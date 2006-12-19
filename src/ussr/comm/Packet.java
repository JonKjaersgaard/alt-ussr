/**
 * 
 */
package ussr.comm;

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

    public byte get(int i) {
        return payload[i];
    }
}
