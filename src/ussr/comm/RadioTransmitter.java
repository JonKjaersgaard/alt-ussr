/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.comm;

import ussr.description.geometry.VectorDescription;
import ussr.model.Entity;
import ussr.model.Module;

/**
 * A radio omnidirectional transmission device
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class RadioTransmitter extends GenericTransmitter {

	private boolean enabled;
	public RadioTransmitter(Module _module, Entity _hardware, TransmissionType _type, float _range) {
		super(_module, _hardware, _type, _range);
		enabled = true;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public boolean canSendTo(Receiver receiver) {
		if(receiver.getType()!=getType()) return false;
		if(!((RadioReceiver)receiver).isEnabled()) return false;
		if(range==Float.MAX_VALUE) return true;
	    VectorDescription p1 = this.getHardware().getPosition();
	    VectorDescription p2 = receiver.getHardware().getPosition();
	    return p1.distance(p2)<range;
	}
}
