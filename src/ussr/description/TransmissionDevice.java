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
public class TransmissionDevice extends Description {

    float range;
    TransmissionType type;
    
    public TransmissionDevice(TransmissionType _type, float _range) {
        this.type = _type;
        this.range = _range;
    }

    public TransmissionType getType() { return type; }
    public float getRange() { return range; }
}
