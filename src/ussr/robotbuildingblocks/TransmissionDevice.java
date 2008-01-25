/**
 * 
 */
package ussr.robotbuildingblocks;

import ussr.comm.TransmissionType;

/**
 * Abstract description of a transmission device
 * 
 * @author Modular Robots @ MMMI
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
