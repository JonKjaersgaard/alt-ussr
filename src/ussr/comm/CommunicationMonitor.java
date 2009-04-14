package ussr.comm;

import ussr.model.Module;

public interface CommunicationMonitor {

    void packetReceived(Module module, GenericReceiver receiver, Packet data);

    void packetSent(Module module, GenericTransmitter transmitter, Packet packet);

}
