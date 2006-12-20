package ussr.comm;

public interface Receiver {

    /**
     * Test whether this receiver is compatible with (can receive) the type of transmission
     * indicated by the argument.
     * @param type the type of the transmission to compare with
     * @return true if they are compatible, false otherwise
     */
    boolean isCompatible(TransmissionType type);

    void receive(Packet data);

    public boolean hasData();
    
    public Packet getData();
}
