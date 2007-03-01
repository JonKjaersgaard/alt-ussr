package ussr.comm;

import java.util.List;

import ussr.model.Entity;
import ussr.model.Module;
import ussr.physics.PhysicsEntity;
import ussr.robotbuildingblocks.TransmissionDevice;

public abstract class GenericTransmitter implements Transmitter {
	protected Module module;
    protected TransmissionType type;
    protected float range;
    TransmissionDevice transmitter;
    private Entity hardware;
    
    public GenericTransmitter(Module _module, Entity _hardware, TransmissionType _type, float _range) {
        this.module = _module; this.type = _type; this.range = _range; this.hardware = _hardware;
    }

    public GenericTransmitter(Module _module, TransmissionDevice _transmitter) {
    	this.transmitter = _transmitter;
	}

	public void send(Packet packet) {
        //module.getSimulation().sendMessage(type,module,range,packet);
        //module.getSimulation().sendMessage(transmitter,packet);
		//TODO optimize this function 
		//TODO make a time delay from sending to receiving which is more realistic - e.g using a timestamp 
		for(Module m : module.getSimulation().getModules()) {
			if(!m.equals(module)) {
				for(Receiver r : m.getReceivers()) {
					if(this.canSendTo(r)&&r.canReceiveFrom(this)) {
						r.receive(packet);
					}
				}
			}
		}
    }
	public boolean isCompatible(TransmissionType other) {
        return this.type == other;
    }
	public TransmissionType getType() {
		return type;
	}
    public PhysicsEntity getHardware() {
		return hardware.getPhysics().get(0);
	}
}
