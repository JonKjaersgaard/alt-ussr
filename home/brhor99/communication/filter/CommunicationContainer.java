package communication.filter;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import ussr.comm.Packet;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.model.Module;
import ussr.util.Pair;


public class CommunicationContainer {
	
	protected Module module;

	private volatile List<Packet> packetList;
	private volatile Queue<Packet> packetQueue;
	private volatile List<Pair<Module, Module>> communicationList;
	//private volatile Queue<Pair<Module, Module>> communicationQueue;
	private volatile Queue<Pair<Transmitter, Receiver>> communicationQueue;
	
	public CommunicationContainer(Module module) {
		this.module = module;		
		packetList = new LinkedList<Packet>();
		packetQueue = new LinkedList<Packet>();
		communicationList = new LinkedList<Pair<Module, Module>>();
		//communicationQueue = new LinkedList<Pair<Module,Module>>();
		communicationQueue = new LinkedList<Pair<Transmitter,Receiver>>();
	}
		
	public void addCommnuncationPair(Module m1, Module m2) {
		if(!m1.equals(m2)) {
			for(Transmitter t: m1.getTransmitters()) {
				for(Receiver r: m2.getReceivers()) {
					if(t.canSendTo(r) && r.canReceiveFrom(t)) {
						communicationList.add(new Pair<Module, Module>(m1, m2));						
					}
				}
			}
		}
	}
		
	public synchronized List<Packet> getPacketList() {
		return packetList;
	}
	
	public synchronized Queue<Packet> getPacketQueue() {
		return packetQueue;
	}
		
	public synchronized void addPacket(Packet packet) {
		packetList.add(packet);
	}
	
	public synchronized void addPacketToQueue(Packet packet) {		
		packetQueue.add(packet);
	}
	
	public synchronized void addCommunicationPairToQueue(Transmitter transmitter, Receiver receiver) {
		communicationQueue.add(new Pair<Transmitter, Receiver>(transmitter, receiver));
	}
	
	public synchronized Packet removePacket() {
		return packetList.remove(0);
	}
	
	public synchronized Packet removePacketFromQueue() {
		return packetQueue.poll();
	}
	

	
	public synchronized boolean hasMorePackets() {
		return packetList.isEmpty();
	}
	
	public synchronized boolean hasMorePacketsInQueue() {
		return packetQueue.isEmpty();
	}
	
	public synchronized List<Pair<Module, Module>> getCommunicationList() {
		return communicationList;
	}
	
	public synchronized Queue<Pair<Transmitter, Receiver>> getCommunicationQueue() {
		return communicationQueue;
	}
	
	public synchronized String showPacketContent(Packet packet) {
		StringBuilder s = new StringBuilder();
		byte[] data = packet.getData();
		int i;
		for(i = 0; i < data.length; i++) {
			byte b = data[i];
			s.append(b);
			if(i != data.length - 1) {
				s.append(", ");
			}
		}
		return s.toString();			
	}
}
