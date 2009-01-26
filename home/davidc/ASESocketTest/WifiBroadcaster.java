package ASESocketTest;

import ussr.comm.Packet;
import ussr.comm.RadioTransmitter;
import ussr.comm.TransmissionType;
import ussr.model.Module;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsSimulation;

import com.jme.math.Vector3f;
import com.sun.corba.se.impl.ior.ByteBuffer;

public class WifiBroadcaster implements PhysicsObserver {
	final byte REWARDSIGNAL = 10;
	PhysicsSimulation simulation;
	double deltaT;
	double nextT;
	CMTracker tracker;
	RadioTransmitter radio;
	Vector3f oldPos;
	public WifiBroadcaster(PhysicsSimulation simulation, double deltaT, CMTracker tracker) {
		this.simulation = simulation;
		this.deltaT = deltaT;
		this.tracker = tracker;
		Module dummyModule = new Module();
		dummyModule.setSimulation(simulation);
		radio = new RadioTransmitter(dummyModule, dummyModule,TransmissionType.RADIO, Float.MAX_VALUE);
		nextT = simulation.getTime()+deltaT;
		oldPos = tracker.getRobotCM();
	}
	
	/**
	 * Transmit robots movement over wifi 
	 */
	public void physicsTimeStepHook(PhysicsSimulation simulation) {
		if(nextT<simulation.getTime()) {
			float dist = tracker.getRobotCM().distance(oldPos);
			byte reward = (byte)(500*dist);
			if(!Float.isNaN(dist)) {				
	 			ByteBuffer bb = new ByteBuffer(2);
				bb.append(REWARDSIGNAL);
				bb.append(reward);
				System.out.println("reward send = "+reward);
				Packet packet = new Packet(bb.toArray());
				radio.send(packet);
			}
			nextT += deltaT;
			oldPos = tracker.getRobotCM();
		}
	}
}
