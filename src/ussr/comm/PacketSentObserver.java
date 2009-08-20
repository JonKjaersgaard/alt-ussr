/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.comm;

/**
 * Observer interface for reacting to the completion of packet transmission.
 * Similar to an interrupt routine for handling a send completion.
 * 
 * @author mrk
 */
public interface PacketSentObserver {
    /**
     * Notification method called by a transmitter to which this observer has been subscribed
     * whenever a packet arrives
     * @param device the device where a packet has been transmitted from
     * @param the packet that has been transmitted
     * @param the error code for this operation
     */
	public void packetSent(Transmitter device, Packet pkt, int errorCode);
}
