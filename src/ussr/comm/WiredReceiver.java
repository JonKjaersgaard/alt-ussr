package ussr.comm;

import ussr.model.Entity;
import ussr.model.Module;
import ussr.robotbuildingblocks.ReceivingDevice;

public class WiredReceiver extends GenericReceiver {
	public WiredReceiver(Module module, Entity hardware, ReceivingDevice receiver) {
		super(module, hardware, receiver.getType(), receiver.getBufferSize());
	}
	public boolean canReceiveFrom(Transmitter transmitter) { 
		return true;
	}
}
