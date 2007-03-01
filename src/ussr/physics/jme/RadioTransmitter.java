package ussr.physics.jme;

import ussr.comm.GenericTransmitter;
import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.comm.TransmissionType;
import ussr.model.Entity;
import ussr.model.Module;

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
