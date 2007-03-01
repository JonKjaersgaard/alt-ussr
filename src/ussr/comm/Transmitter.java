/**
 * 
 */
package ussr.comm;

import ussr.physics.PhysicsEntity;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public interface Transmitter {

	public void send(Packet packet);
	public boolean canSendTo(Receiver receiver);
    public PhysicsEntity getHardware();
    public TransmissionType getType();
}
