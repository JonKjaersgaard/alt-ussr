/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.comm;

import java.util.LinkedList;
import java.util.Set;

import communication.filter.CommunicationContainer;

import ussr.description.robot.TransmissionDevice;
import ussr.model.Entity;
import ussr.model.Module;
import ussr.physics.PhysicsEntity;
import ussr.physics.PhysicsFactory;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;

/**
 * An abstract implementation of the <tt>Transmitter</tt> interface.  Provides a transmission device
 * for a module associated with a specific simulated hardware, using a specific type of transmission
 * and with a given transmission range.
 *
 * @author Modular Robots @ MMMI
 *
 */
public abstract class GenericTransmitter implements Transmitter {
	protected Module module;
    protected TransmissionType type;
    protected GenericTransmitManager transmitManager;
    protected float range;
    TransmissionDevice transmitter;
    private Entity hardware;

    // Begin Horn
    Set<CommunicationMonitor> monitors;
    // End Horn
    
    //Begin Horn
    protected CommunicationContainer communicationContainer;
    //End Horn
    
    
    public GenericTransmitter(Module _module, Entity _hardware, TransmissionType _type, float _range) {
        this.module = _module; this.type = _type; this.range = _range; this.hardware = _hardware;
        transmitManager = new GenericTransmitManager(Integer.MAX_VALUE,Integer.MAX_VALUE,this);

        // Begin Horn
        this.monitors = PhysicsFactory.getOptions().getMonitors();
        // End Horn

        // Begin Horn
        //communicationContainer = new CommunicationContainer(_module);        
        // End Horn
    }

    public GenericTransmitter(Module _module, TransmissionDevice _transmitter) {
    	this.transmitter = _transmitter;
    	transmitManager = new GenericTransmitManager(Integer.MAX_VALUE,Integer.MAX_VALUE,this);
    	// Begin Horn
    	//communicationContainer = new CommunicationContainer(_module);
    	// End Horn
	}
    
    //Begin Horn
    public CommunicationContainer getCommunicationContainer() {
    	return communicationContainer;
    }    
    //End Horn
    
    //Begin Horn
    public Module getModule() {
    	return module;
    }
    //End Horn

    public void setMaxBaud(float maxBaud) {
    	transmitManager.setMaxBaud(maxBaud);
    }
    public void setMaxBufferSize(int maxBufferSize) {
    	transmitManager.setMaxBufferSize(maxBufferSize);
	}
    public void send(Packet packet) {
    	// Begin Horn
    	if(monitors != null) {
    		for(CommunicationMonitor monitor : monitors) {
    			monitor.packetSent(module, this, packet);
    		}
    	}
    	// End Horn        

		//TODO optimize this function 
		//TODO make a time delay from sending to receiving which is more realistic - e.g using a timestamp 
		/*for(Module m : module.getSimulation().getModules()) {
			if(!m.equals(module)) {
				for(Receiver r : m.getReceivers()) {
					if(this.canSendTo(r)&&r.canReceiveFrom(this)) {
						r.receive(packet);
					}
				}
			}
		}*/    	    	
		transmitManager.addPacket(packet);
    }
	public boolean isCompatible(TransmissionType other) {
        return this.type == other;
    }
	public TransmissionType getType() {
		return type;
	}
    public PhysicsEntity getHardware() {
		return hardware.getPhysics().get(0);
	}
    
    public int withinRangeCount() {
		int count = 0;
		for(Module m : module.getSimulation().getModules()) { //FIXME expensive implementation
			if(!m.equals(module)) {
				for(Receiver r : m.getReceivers()) {
					if(canSendTo(r)&&r.canReceiveFrom(this)) {
						count++;
					}
				}
			}
		}
		return count;
    }
	private class GenericTransmitManager implements PhysicsObserver {
		volatile LinkedList<Packet> packets; 
		volatile int maxBytes;
		volatile int currentBytes = 0;
		volatile int timeStepsSinceLastSend = 0;
		float maxBytePerTimeStep = Float.POSITIVE_INFINITY;
		volatile boolean subscribing = false;
		GenericTransmitter transmitter; 
		
		public GenericTransmitManager(int maxBytes, int maxBaud, GenericTransmitter transmitter) {
			packets = new LinkedList<Packet>();
			this.transmitter = transmitter;
			setMaxBufferSize(maxBytes);
			
			setMaxBaud(maxBaud);
			
		}
		public void setMaxBaud(float maxBaud) {
			if(maxBaud != Integer.MAX_VALUE) {
				float stepSize = PhysicsParameters.get().getPhysicsSimulationStepSize();
				maxBytePerTimeStep = (maxBaud/10.0f)*stepSize;
			}
			else {
				maxBytePerTimeStep = Float.POSITIVE_INFINITY;
			}
		}
		public void setMaxBufferSize(int maxBufferSize) {
			this.maxBytes = maxBufferSize;
		}
		
		public synchronized boolean addPacket(Packet p) 
		{
			if((p.getByteSize()+currentBytes)<maxBytes) {
				packets.add(p);
				currentBytes+=p.getByteSize();
				setSubscribe(true);
				return true;
			}
			else {
				return false;
			}
		}
		
		private synchronized void setSubscribe(boolean subscribe) {
			if(subscribing != subscribe) {
				if(subscribe) {
					module.getSimulation().subscribePhysicsTimestep(this);
				}
				else {
					module.getSimulation().unsubscribePhysicsTimestep(this);
				}
				subscribing = subscribe;
			}
		}
		public synchronized void physicsTimeStepHook(PhysicsSimulation simulation) {
			Packet p = packets.peek();
			if(p!=null) {
				if(sendIfTime(p,timeStepsSinceLastSend)) {
					packets.removeFirst();
					currentBytes-=p.getByteSize();
					timeStepsSinceLastSend=0;
				}
				else {
					timeStepsSinceLastSend++;
				}
			}
			else {
				setSubscribe(false);
			}
		}
		private boolean sendIfTime(Packet p, int timeSteps) {
			float byteCapacity = maxBytePerTimeStep*timeStepsSinceLastSend;
			if(byteCapacity>p.getByteSize()||maxBytePerTimeStep==Float.POSITIVE_INFINITY) {
				if(!send(p)) {
				    PhysicsLogger.logNonCritical(module.getID()+": Trying to send a package but no one is there to receive it... removing it from buffer");
				}
				return true;
			}
			return false;
		}
		
		//Begin Horn
		private String showPacketContent(Packet packet) {
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
		//End Horn

		private boolean send(Packet packet) {
			boolean sendt = false;
			for(Module m : module.getSimulation().getModules()) { //FIXME expensive implementation
				if(!m.equals(module)) {
					for(Receiver r : m.getReceivers()) {
						if(transmitter.canSendTo(r)&&r.canReceiveFrom(transmitter)) {
							r.receive(packet);
							sendt = true;
							//Begin Horn
							//System.out.println(showPacketContent(packet));
							//communicationContainer.addPacket(packet);
							//communicationContainer.addPacketToQueue(packet);
							//System.out.println("Size of packet queue for module " + m.getID() + ": " + communicationContainer.getPacketQueue().size());
							//End Horn
						}
					}
				}
			}
			return sendt;
	    }
	}
}
