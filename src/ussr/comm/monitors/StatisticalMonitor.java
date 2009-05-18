package ussr.comm.monitors;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import ussr.comm.CommunicationMonitor;
import ussr.comm.GenericReceiver;
import ussr.comm.GenericTransmitter;
import ussr.comm.Packet;
import ussr.model.Module;
import ussr.physics.PhysicsSimulation;

public class StatisticalMonitor implements CommunicationMonitor {
	List<MsgStat> in = Collections.synchronizedList(new LinkedList<MsgStat>());
	List<MsgStat> out = Collections.synchronizedList(new LinkedList<MsgStat>());
	float timeWindow;
	PhysicsSimulation simulator = null;
//	boolean inTaken = false;
//	boolean outTaken = false;
	private class MsgStat {
		int moduleID;
		int channel;
		float timestamp;
		int bits;
	}
	public StatisticalMonitor(float timeWindow) {
		this.timeWindow =timeWindow; 
	}
	private void handlePackage(List<MsgStat> list, int id, int channel, int byteSize, float currentTime) {
		MsgStat msgStat = new MsgStat(); 
		msgStat.moduleID = id;
		msgStat.channel = channel;
		msgStat.bits = byteSize*8;
		msgStat.timestamp = currentTime; 
		list.add(msgStat);	
		cleanStat(list, currentTime, timeWindow);
	}
	private void cleanStat(List<MsgStat> list, float currentTime, float window) {
		while(!list.isEmpty()&&list.get(0).timestamp < currentTime-window)
			list.remove(0);
	}
	
	private int sumUpBits(List<MsgStat> list, int id, int channel) {
		int bitSum = 0;
		MsgStat ms;
		for(int i=0;i<list.size();i++) {
			ms = list.get(i);
			if(ms.channel == channel && ms.moduleID==id) {
				bitSum+=ms.bits;
			}
		}
		return bitSum;
	}
	
	public void packetReceived(Module module, GenericReceiver receiver, Packet packet) {
		if(simulator==null) simulator = module.getSimulation();
		int channel = module.getReceivers().indexOf(receiver);
	//	if(inTaken==false) {
	//		inTaken = true;
			handlePackage(in, module.getID(), channel, packet.getByteSize(), simulator.getTime());
	//		inTaken = false;
	//	}
	//	else { }//ignore package to avoid crach
	}
	
	public void packetSent(Module module, GenericTransmitter transmitter, Packet packet) {
		if(simulator==null) simulator = module.getSimulation();
		int channel = module.getTransmitters().indexOf(transmitter);
		//System.out.println("msg "+packet.getByteSize()+"bytes send on channel "+channel+" on module "+module.getID()+" at time "+ simulator.getTime());
	//	if(outTaken==false) {
	//		outTaken = true;
			handlePackage(out, module.getID(), channel, packet.getByteSize(), simulator.getTime());
	//		outTaken = false;
	//	}
	//	else { }//ignore package to avoid crach
	}
	
	public int getBitOutWindow(int moduleID, int channel) {
		if(simulator==null) return Integer.MIN_VALUE;
	//	if(outTaken ==false) {
	//		outTaken = true;
			cleanStat(out, simulator.getTime(), timeWindow);
			int bits = sumUpBits(out, moduleID, channel);
	//		outTaken = false;
			return bits;
	//	}
	//	else {
	//		return Integer.MIN_VALUE;
	//	}
	}
	
	public int getBitInWindow(int moduleID, int channel) {
		if(simulator==null) return Integer.MIN_VALUE;
	//	if(inTaken ==false) {
	//		inTaken = true;
			cleanStat(in, simulator.getTime(), timeWindow);
			int bits = sumUpBits(in, moduleID, channel);
	//		inTaken = false;
			return bits;
	//	}
	//	else {
	//		return Integer.MIN_VALUE;
	//	}
	}
}
