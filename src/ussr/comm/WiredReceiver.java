package ussr.comm;

import ussr.model.Entity;
import ussr.model.Module;
import ussr.robotbuildingblocks.ReceivingDevice;

/**
 * A wired receiver, is always assumed to be in range once connected.  The current
 * implementation however simply tests for the range (?)
 * 
 * TODO: Implement proper wired connections
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class WiredReceiver extends GenericReceiver {
	public WiredReceiver(Module module, Entity hardware, ReceivingDevice receiver) {
		super(module, hardware, receiver.getType(), receiver.getBufferSize());
	}
	public boolean canReceiveFrom(Transmitter transmitter) { 
		return true;
	}
}
