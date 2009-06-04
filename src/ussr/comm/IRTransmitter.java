/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.comm;

import java.awt.Color;

import ussr.description.geometry.VectorDescription;
import ussr.model.Entity;
import ussr.model.Module;
import ussr.physics.PhysicsEntity;

import com.jme.math.Quaternion;
import com.jme.math.Vector3f;

/**
 * An infrared transmitter, which in means that it should use a spreading angle.
 * 
 * <p>TODO: implement and use the spreading angle in the physical simulation, it currently is omnidirectional 
 * 
 * @author Modular Robots @ MMMI
 */
public class IRTransmitter extends GenericTransmitter {

	float spreadAngle = (float)(24*Math.PI/180); //24 degree as default (true for ATRON)
	public IRTransmitter(Module _module, Entity _hardware, TransmissionType _type, float _range) {
		super(_module, _hardware, _type, _range);
	}
	
	public void setSpreadAngle(float spreadAngle) {
		this.spreadAngle = spreadAngle;
	}
	public boolean canSendTo(Receiver receiver) {
		if(isCompatible(receiver.getType())) {
			if(!withinRange(receiver)) return false;
			if(!withinAngle(receiver)) return false;
			return true;
		}
		else {
			return false;
		}
	}
	private float computeAngle(Receiver receiver) {
		Quaternion rRot = ((PhysicsEntity)receiver.getHardware()).getRotation().getRotation();
		Quaternion tRot= ((PhysicsEntity)getHardware()).getRotation().getRotation();
		return rRot.inverse().mult(tRot).toAngleAxis(new Vector3f());
	}
	public boolean withinAngle(Receiver receiver) {
		float angle = computeAngle(receiver);
		if(Math.abs(angle-Math.PI)>spreadAngle/2) return false;
		return true;
	}
	public float computeDistance(Receiver receiver) {
		VectorDescription rPos= ((PhysicsEntity)receiver.getHardware()).getPosition();
		VectorDescription tPos  = ((PhysicsEntity)getHardware()).getPosition();
		return tPos.distance(rPos); 
	}
	public boolean withinRange(Receiver receiver) {
		float distance = computeDistance(receiver); 
		if(distance>range) { 
			return false;
		}
		return true;
	}
}
