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
	public RadioReceiver(Module module, Entity hardware, ReceivingDevice receiver) {
		super(module, hardware, receiver.getType(), receiver.getBufferSize());
		
	}
	public boolean canReceiveFrom(Transmitter transmitter) { 
		if(isCompatible(transmitter.getType())) {
			return true;
		}
		return false;
	}
}
