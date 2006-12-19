package ussr.comm;

public interface Receiver {

    boolean isCompatible(TransmissionType type);

    void receive(Packet data);

    public boolean hasData();
    
    public Packet getData();
}
