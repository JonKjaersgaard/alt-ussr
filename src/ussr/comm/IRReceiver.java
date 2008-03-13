package ussr.comm;

import ussr.description.geometry.RotationDescription;
import ussr.description.geometry.VectorDescription;
import ussr.description.robot.ReceivingDevice;
import ussr.model.Entity;
import ussr.model.Module;
import ussr.physics.PhysicsConnector;
import ussr.physics.PhysicsEntity;

/**
 * An infrared receiver, which in currently means that it has a spreading angle of 30 degrees.
 * 
 * <p>TODO: use the spreading angle in the physical simulation, it currently is omnidirectional 
 * 
 * @author Modular Robots @ MMMI
 */
public class IRReceiver extends GenericReceiver {
	private float spreadAngle;
	public IRReceiver(Module module, Entity hardware, ReceivingDevice receiver) {
		super(module, hardware, receiver.getType(), receiver.getBufferSize());
		spreadAngle = (float)(30*2*Math.PI/360f);
	}
	public boolean canReceiveFrom(Transmitter transmitter) { //howto handle JME specific things?
		return true;
	}
}
