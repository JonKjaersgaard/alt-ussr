package communication.filter;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import ussr.comm.Packet;
import ussr.model.Module;
import ussr.util.Pair;

public class CommunicationContainer {
	
	private Map<Pair<Point, Point>, Pair<Module, Module>> communicationMap;
	private Map<Pair<Point, Point>, Packet> packetMap;
		
	public CommunicationContainer() {
		communicationMap = new HashMap<Pair<Point,Point>, Pair<Module,Module>>();
		packetMap = new HashMap<Pair<Point, Point>, Packet>();
	}
	
	public Map<Pair<Point, Point>, Pair<Module, Module>> getCommunicationMap() {
		return communicationMap;
	}
	
	public Map<Pair<Point, Point>, Packet> getPacketMap() {
		return packetMap;
	}
		
	public void addCommunicationPair(Module transmitter, Module receiver, Point transmitterPos, Point receiverPos) {
		communicationMap.put(new Pair<Point, Point>(transmitterPos, receiverPos), new Pair<Module, Module>(transmitter, receiver));
	}
	
	public void addPacket(Packet packet, Point transmitterPos, Point receiverPos) {
		packetMap.put(new Pair<Point, Point>(transmitterPos, receiverPos), packet);
	}
}
