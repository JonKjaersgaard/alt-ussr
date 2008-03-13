/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.comm;

/**
 * Observer interface for reacting to packet reception.  Similar to an interrupt routine
 * for handling packet reception.
 * 
 * @author Modular Robots @ MMMI
 */
public interface PacketReceivedObserver {
    /**
     * Notification method called by a receiver to which this observer has been subscribed
     * whenever a packet arrives
     * @param device the device where a packet has been received
     */
	public void packetReceived(Receiver device);
}
