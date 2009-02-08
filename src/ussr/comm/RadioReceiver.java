/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.comm;

import ussr.description.robot.ReceivingDevice;
import ussr.model.Entity;
import ussr.model.Module;

/**
 * A radio omnidirectional reception device
 * 
 * @author Modular Robots @ MMMI
 */
public class RadioReceiver extends GenericReceiver {
	private boolean enabled = true;
	public RadioReceiver(Module module, Entity hardware, ReceivingDevice receiver) {
		super(module, hardware, receiver.getType(), receiver.getBufferSize());
		
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public boolean canReceiveFrom(Transmitter transmitter) { 
		if(isEnabled()) {
			if(isCompatible(transmitter.getType())) {
				return true;
			}
		}
		return false;
	}
}
