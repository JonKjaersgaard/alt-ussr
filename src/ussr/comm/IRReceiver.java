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
 * An infrared receiver, which in currently means that it has a spreading angle of 30 degrees.
 * 
 * <p>TODO: use the spreading angle in the physical simulation, it currently is omnidirectional 
 * 
 * @author Modular Robots @ MMMI
 */
public class IRReceiver extends GenericReceiver {
	private float spreadAngle;
	public IRReceiver(Module module, Entity hardware, ReceivingDevice receiver) {
		super(module, hardware, receiver.getType(), receiver.getBufferSize());
		spreadAngle = (float)(30*2*Math.PI/360f);
	}
	public boolean canReceiveFrom(Transmitter transmitter) {
		if(!getFullDuplex()) {
			if(getTransmitter().isSending()) {
				//System.out.println("Communication Collision Detected...");
				return false;
			}
			else {
				//System.out.println("No Collision...");
			}
		}
		return true;
	}
}
