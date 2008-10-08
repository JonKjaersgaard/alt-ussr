/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ckbot;

import ussr.comm.Packet;
import ussr.comm.PacketReceivedObserver;
import ussr.comm.Receiver;
import ussr.model.ControllerImpl;
import ussr.model.Module;
import ussr.model.Sensor;

/**
 * Controller for the CKBot robot
 * 
 * @author ups
 */
public abstract class CKBotController extends ControllerImpl implements PacketReceivedObserver {

	boolean blocking;
    public CKBotController() {
        super();
        setBlocking(true);
    }
    public void setModule(Module module) {
    	super.setModule(module);
        for(Receiver r: module.getReceivers()) { 
         	r.addPacketReceivedObserver(this); //packetReceived(..) will be called when a packet is received
        }
    }
    
    public void setBlocking(boolean blocking) {
    	this.blocking = blocking;
    } 

    public void disconnectAll() {
    	for(int i=0;i<4;i++) {
    		if(isConnected(i)) {
    			disconnect(i);
    		}
    	}
    }

    public void connectAll() {
    	for(int i=0;i<4;i++) {
    		if(isOtherConnectorNearby(i)) {
    			System.out.println(module.getID()+" Other connector at connector "+i);
    			connect(i);
    		}
    	}
    }

    public void connect(int i) {
    	module.getConnectors().get(i).connect();
    }

    public void disconnect(int i) {
    	module.getConnectors().get(i).disconnect();
    }

    public boolean isConnected(int i) {
    	return module.getConnectors().get(i).isConnected();
    }
    
    public boolean isOtherConnectorNearby(int connector) {
    	if(module.getConnectors().get(connector).isConnected()) {
    		return true;
    	}
    	if(module.getConnectors().get(connector).hasProximateConnector()) {
    		return true;
    	}
    	else  {
    		return false;
    	}
    }
    
    public boolean isObjectNearby(int connector) {
        return module.getSensors().get(connector).readValue()>0.1;
    }
    
    public byte sendMessage(byte[] message, byte messageSize, byte connector) 
	{
		if(isOtherConnectorNearby(connector)&&connector<4) {
			module.getTransmitters().get(connector).send(new Packet(message));
			return 1;
		}
		return 0;
	}
    
    public void packetReceived(Receiver device) {
    	for(int i=0;i<4;i++) {
    		if(module.getReceivers().get(i).equals(device)) {
    			byte[] data = device.getData().getData();
    			handleMessage(data,data.length,i);
    			return;
    		}
    	}
    }
    /**
     * Controllers should generally overwrite this method to receieve messages 
     * @param message
     * @param messageSize
     * @param channel
     */
    public void handleMessage(byte[] message, int messageSize, int channel) {
    	System.out.println("Message recieved please overwrite this method");
    }
    private byte read(String name) {
        for(Sensor sensor: module.getSensors()) {
            if(name.equals(sensor.getName())) {
                float value = sensor.readValue();
                return (byte)(value/Math.PI*180-90); 
            }
                
        }
        throw new Error("Unknown sensor: "+name);
    }
    // Note: hard-coded to do forward-backward tilt sensoring for axles
    public byte getTiltX() { return read("TiltSensor:y"); }
    // Note: hard-coded to do side-to-side tilt sensoring for driver and axles
    public byte getTiltY() { return read("TiltSensor:x"); }
    public byte getTiltZ() { return read("TiltSensor:z"); }
}