/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.comm;

import ussr.physics.PhysicsEntity;

/**
 * A device that can receive packets from some transmission source.  A receiver devices is
 * compatible with a specific set of transmission devices and stores the data that it is receiving in
 * an incoming buffer which may be of fixed size.
 * 
 * @author Modular Robots @ MMMI
 *
 */
public interface Receiver {

    /**
     * Test whether this receiver is compatible with (can receive) the type of transmission
     * indicated by the argument.
     * @param type the type of the transmission to compare with
     * @return true if they are compatible, false otherwise
     */
    boolean isCompatible(TransmissionType type);

    /**
     * Deliver a packet of data to the receiver device which stores it in the buffer
     * @param data the data to deliver
     */
    void receive(Packet data);

    /**
     * Test whether data is available in the buffer
     * @return true if data is available, false otherwise
     */
    public boolean hasData();
    
    /**
     * Remove a packet of data from the buffer of the device and return it
     * @return a packet of data from the buffer
     */
    public Packet getData();
    
    /**
     * Get the size of the buffer
     * @return the buffer size
     */
    public int size();
    
    /**
     * Test whether this receiver can receive data from (is within the range of) some transmission device
     * @param transmitter the device to test compatibility with
     * @return true if they are compatible, false otherwise
     */
    public boolean canReceiveFrom(Transmitter transmitter);
    
    /**
     * Get a reference to the simulated hardware entity that represents the receiver device
     * @return the simulated hardware of the receiver
     */
    public PhysicsEntity getHardware();
    
    /**
     * Get the transmission type implemented by the receiver
     * @return the transmission type
     */
    public TransmissionType getType();
    
    /**
     * Add a packet receiver observer which will be notified (called) every time a packet is delivered to this receiver
     * device.
     * @param pro the packet receiver observer to add
     */
    public void addPacketReceivedObserver(PacketReceivedObserver pro);
    
    /**
     * Remove a packet receiver observer that has already been subscribed
     * @param pro the packet receiver observer to remove
     */
    public void removePacketReceivedObserver(PacketReceivedObserver pro);
}
