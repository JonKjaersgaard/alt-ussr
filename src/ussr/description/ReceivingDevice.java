/**
 * 
 */
package ussr.description;

import ussr.comm.TransmissionType;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class ReceivingDevice extends Description {

    private TransmissionType type;
    private int bufferSize;
    
    public ReceivingDevice(TransmissionType _type, int _bufferSize) {
        this.type = _type; this.bufferSize = _bufferSize;
    }

    public TransmissionType getType() {
        return type;
    }

    public int getBufferSize() {
        return bufferSize;
    }

}
