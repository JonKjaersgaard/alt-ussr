/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.comm;

import java.util.ArrayList;
import java.util.Set;

import ussr.model.Entity;
import ussr.model.Module;
import ussr.physics.PhysicsEntity;
import ussr.physics.PhysicsFactory;

/**
 * An abstract implementation of the <tt>Receiver</tt> interface. Provides a receiver device
 * for a module associated with a specific simulated hardware, using a specific type of transmission
 * and with a given buffer size.
 * 
 * @author Modular Robots @ MMMI
 */
public abstract class GenericReceiver implements Receiver {
    protected Module module;
    protected Entity hardware;
    protected TransmissionType type;
    protected Packet[] queue; 
    protected boolean fullDuplex = true;
    protected Transmitter transmitter; 
    protected int read_position, write_position;
    private ArrayList<PacketReceivedObserver> packetReceivedObservers = new ArrayList<PacketReceivedObserver>();
    protected int packageCounter = 0; //for debugging
    // Begin Horn
    Set<CommunicationMonitor> monitors;
    // End Horn
    
    public GenericReceiver(Module _module, Entity _hardware, TransmissionType _type, int buffer_size) {
        this.module = _module; this.type = _type; this.hardware = _hardware;
        queue = new Packet[buffer_size];
        read_position = write_position = 0;
        // Begin Horn
        this.monitors = PhysicsFactory.getOptions().getMonitors();
        // End Horn
    }
    

	public boolean isCompatible(TransmissionType other) {
        return this.type == other;
    }
    public PhysicsEntity getHardware() {
		return hardware.getPhysics().get(0);
	}
    
    public synchronized void receive(Packet data) {            
    	// Begin Horn
        if(monitors != null) {
        	for(CommunicationMonitor monitor: monitors) {
        		monitor.packetReceived(module,this,data);
        	}
        }
        // End Horn
    	packageCounter++;
        queue[write_position] = data;
        write_position = (write_position+1)%queue.length;
        //if(write_position==read_position) PhysicsLogger.log("comm buffer overrun ");
        if(write_position==read_position) System.out.println("ERROR: comm buffer overrun");
      //  module.eventNotify();
        for(PacketReceivedObserver pro: packetReceivedObservers) //TODO do not do this here (can block communicating neighbor module)
        	pro.packetReceived(this);
    }
    
    public synchronized Packet getData() {
        if(!hasData()) throw new Error("empty comm buffer");
        Packet data = queue[read_position];
        read_position = (read_position+1)%queue.length;
        return data;
    }
    public int size() {
    	int diff = read_position-write_position;
    	if(diff>0) return queue.length-diff;
    	else return  -1*diff; 
    }
    public boolean hasData() {
        return read_position!=write_position;
    }
    public TransmissionType getType() {
		return type;
	}
    public void addPacketReceivedObserver(PacketReceivedObserver pro) {
		packetReceivedObservers.add(pro);
    }
    public void removePacketReceivedObserver(PacketReceivedObserver pro) {
		packetReceivedObservers.remove(pro);
    }
    
    public void setFullDuplex(Transmitter transmitter, boolean fullDuplex) {
    	this.fullDuplex = fullDuplex;
    	this.transmitter = transmitter;
    }
    
    public boolean getFullDuplex() {
    	return fullDuplex;
    }
    
    public Transmitter getTransmitter() {
    	return transmitter;
    }
    
}
