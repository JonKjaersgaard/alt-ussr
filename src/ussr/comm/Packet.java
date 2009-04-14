/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.comm;

import java.util.Arrays;

/**
 * A simulated packet, can be transmitted using all types of transmission
 * 
 * @author Modular Robots @ MMMI
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
    public int getByteSize() {
        return payload.length; 
    }
    public byte get(int i) {
        return payload[i];
    }
    public byte[] getData() {
        return payload;
    }
    public String toString() {
        StringBuffer result = new StringBuffer("[ ");
        for(int i=0; i<payload.length; i++) {
            result.append(payload[i]);
            result.append(' ');
        }
        result.append(']');
        return result.toString();
    }
}
