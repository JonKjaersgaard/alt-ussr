/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.comm;

import ussr.physics.PhysicsEntity;

/**
 * A device that can transmit packets to some receiver device.  A transmitter device uses a specific
 * transmission type.
 * 
 * @author Modular Robots @ MMMI
 */
public interface Transmitter {
    /**
     * Send a data packet on the transmission device
     * @param packet the data to send
     */
	public void send(Packet packet);
	
	/**
	 * Test whether this transmitter can transmit to (is within range of) the designated receiver device 
	 * @param receiver the receiver device for which to test reception capability
	 * @return true if the receiver device can receive transmissions from this device, false otherwise
	 */
	public boolean canSendTo(Receiver receiver);
	
	/**
	 * Counts the number of communication channels that are within communication range  
	 * @return number of communication channels within range
	 */
	public int withinRangeCount();
	public void setMaxBaud(float maxBaud);
    public void setMaxBufferSize(int maxBufferSize);
    
    /**
     * Get a reference to the simulated hardware entity that represents the transmission device
     * @return the simulated hardware of the transmitter
     */
     public PhysicsEntity getHardware();
     /**
      * Get the transmission type implemented by the transmitter
      * @return the transmission type
      */
    public TransmissionType getType();
}
