package ussr.comm;

import ussr.model.Entity;
import ussr.model.Module;
import ussr.physics.PhysicsEntity;
import ussr.robotbuildingblocks.RotationDescription;
import ussr.robotbuildingblocks.VectorDescription;

public class IRTransmitter extends GenericTransmitter {

	public IRTransmitter(Module _module, Entity _hardware, TransmissionType _type, float _range) {
		super(_module, _hardware, _type, _range);
	}

	public boolean canSendTo(Receiver receiver) {
		if(isCompatible(receiver.getType())) {
			VectorDescription rPos= ((PhysicsEntity)receiver.getHardware()).getPosition();
			RotationDescription rRot = ((PhysicsEntity)receiver.getHardware()).getRotation();
			VectorDescription tPos  = ((PhysicsEntity)getHardware()).getPosition();
			RotationDescription tRot= ((PhysicsEntity)getHardware()).getRotation();
			if(tPos.distance(rPos)>range) {
				//if(tPos.distance(rPos)<0.1f) System.out.println(" Dist = "+tPos.distance(rPos));
				return false;
			}
			
			
			//TODO some complicated test on orientation here :-)
			
			//System.out.println("reciever rot "+rRot);
			//System.out.println("transmit rot "+tRot);
			return true;
		}
		else {
			return false;
		}
	}
}
