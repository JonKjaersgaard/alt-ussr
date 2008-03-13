/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.comm;

import ussr.model.Entity;
import ussr.model.Module;

/**
 * A radio omnidirectional transmission device
 * 
 * @author Modular Robots @ MMMI
 *
 */
public class RadioTransmitter extends GenericTransmitter {

	public RadioTransmitter(Module _module, Entity _hardware, TransmissionType _type, float _range) {
		super(_module, _hardware, _type, _range);
		// TODO Auto-generated constructor stub
	}

	public boolean canSendTo(Receiver receiver) {
		// TODO Auto-generated method stub
		return true;
	}


}
