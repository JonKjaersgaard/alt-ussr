/**
 * 
 */
package ussr.comm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ussr.model.Module;
import ussr.physics.PhysicsLogger;

/**
 * @author ups
 *
 * TODO Write a nice and user-friendly comment here
 * 
 */
public class GenericReceiver implements Receiver {
    private Module module;
    private TransmissionType type;
    private Packet[] queue; 
    private int read_position, write_position;
    
    public GenericReceiver(Module _module, TransmissionType _type, int buffer_size) {
        this.module = _module; this.type = _type;
        queue = new Packet[buffer_size];
        read_position = write_position = 0;
    }

    public boolean isCompatible(TransmissionType other) {
        return this.type == other;
    }

    public synchronized void receive(Packet data) {
        queue[write_position] = data;
        write_position = (write_position+1)%queue.length;
        if(write_position==read_position) PhysicsLogger.log("comm buffer overrun");
        module.eventNotify();
    }

    public synchronized Packet getData() {
        if(!hasData()) throw new Error("empty comm buffer");
        Packet data = queue[read_position];
        read_position = (read_position+1)%queue.length;
        return data;
    }

    public boolean hasData() {
        return read_position!=write_position;
    }
    
}
