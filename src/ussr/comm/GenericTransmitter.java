package ussr.comm;

import ussr.model.Module;

public class GenericTransmitter implements Transmitter {
    private Module module;
    private TransmissionType type;
    private float range;
    
    public GenericTransmitter(Module _module, TransmissionType _type, float _range) {
        this.module = _module; this.type = _type; this.range = _range;
    }

    public void send(Packet packet) {
        module.getSimulation().sendMessage(type,module,range, packet);
    }

}
