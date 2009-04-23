package communication.filter;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import ussr.comm.Packet;
import ussr.model.Module;
import ussr.util.Pair;

public class CommunicationContainer {
	
	private Map<Pair<Point, Point>, Pair<Module, Module>> communicationMap;
		
	public CommunicationContainer() {
		communicationMap = new HashMap<Pair<Point,Point>, Pair<Module,Module>>();
	}
	
	public Map<Pair<Point, Point>, Pair<Module, Module>> getCommunicationMap() {
		return communicationMap;
	}
	
	public void addCommunicationPair(Module transmitter, Module receiver, Point transmitterPos, Point receiverPos) {
		communicationMap.put(new Pair<Point, Point>(transmitterPos, receiverPos), new Pair<Module, Module>(transmitter, receiver));
	}
			
	public String showPacketContent(Packet packet) {
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
