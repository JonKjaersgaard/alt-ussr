/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.odin;

import java.awt.Color;

import ussr.comm.Packet;
import ussr.comm.PacketReceivedObserver;
import ussr.comm.Receiver;
import ussr.model.ControllerImpl;
import ussr.model.Module;
import ussr.model.Sensor;
import ussr.physics.PhysicsLogger;

/**
 * Controller implementation for the Odin robot
 * 
 * @author Modular Robots @ MMMI
 *
 */
public abstract class OdinController extends ControllerImpl implements PacketReceivedObserver {
	protected String type;
    
	public OdinController() {
        super();
    } 
//    
    public void setup() {
    }
    
    public void setModule(Module module) {
    	super.setModule(module);
        for(Receiver r: module.getReceivers()) { 
         	r.addPacketReceivedObserver(this); //packetReceived(..) will be called when a packet is received
        }
    }
    public boolean isActuating() {
    	boolean acutating = false;
    	for(int i=0;i<module.getActuators().size();i++) {
    		acutating &=module.getActuators().get(i).isActive();
    	}
    	return acutating;
    }   
    public void actuate(float pos) {
    	if(module.getActuators().size()!=0) {
    		module.getActuators().get(0).setDesiredPosition(pos);
    	}
	}
    public void actuateContinuous(float vel) {
    	module.getActuators().get(0).setDesiredVelocity(vel);
	}
    public void disactivate() {
    	if(module.getActuators().size()!=0) {
    		module.getActuators().get(0).disactivate();
    	}
	}
    public float getTime() {
    	return getModule().getSimulation().getTime();
    }
    public String getName() {
        return module.getProperty("name");
    }
    
    public void setColor(float r, float g, float b) {
    	if(type=="OdinBattery") {
			module.setColor(new Color(r,g,b));
		}
    	else {
    		module.setColor(new Color(r,g,b));
    		//throw new RuntimeException("SetColor not valid for module type "+type);
    	}
    }
    public float getActuatorPosition() {
    	if(module.getActuators().size()!=0) {
    		if(type=="OdinMuscle") {
    			return (float)(module.getActuators().get(0).getEncoderValue());
    		}
    	}
    	throw new RuntimeException("Not well defined method for module type "+type);
    }
    public boolean isConnected(int i) {
    	if(i==0||i==1) {
    		return module.getConnectors().get(i).isConnected();
    	}
    	else throw new RuntimeException("Odin module only have two connectors");
    	
    }
    //protected boolean[] modulesAtConnector(int connector);
    
    public byte sendMessage(byte[] message, byte messageSize, byte connector) 
	{
    	//if(message.length!=1) throw new RuntimeException("Can't send message longer than one byte");
    	
    	if(type=="OdinBall")  {
    		if(connector>=0&&connector<=12) {
    			module.getTransmitters().get(connector).send(new Packet(message));
    			return 1;
    		}
    		else {
    			throw new RuntimeException("Odin balls can not send data to connector "+connector);
    		}
    	}
    	else {
	    	if(connector==0||connector==1) {
	    		module.getTransmitters().get(connector).send(new Packet(message));
	    		return 1;
	    	}
	    	else {
	    		throw new RuntimeException("Odin modules can not send data to connector "+connector);
	    	}
    	}
	}
    public void packetReceived(Receiver device) {
    	if(type=="OdinBall") {
    		byte[] message = device.getData().getData();
    		for(int i=0;i<12;i++) {
    			if(!module.getReceivers().get(i).equals(device)) {
    				sendMessage(message.clone(), (byte)message.length,(byte)i);
        		}
    		}
    		return;
    	}
    	else { //all other types 
    		for(int i=0;i<2;i++) {
        		if(module.getReceivers().get(i).equals(device)) {
        			byte[] data = device.getData().getData();
        			handleMessage(data,data.length,i);
        			return;
        		}
        	}
    	}
    	
    	/*if(module.get) {
    		
    	}
    	*/
    }
    public int getDebugID() {
    	return getModule().getID();
    }
    
    public String getType() {
    	return type;
    }
    /**
     * Controllers should generally overwrite this method to receive messages 
     * @param message
     * @param messageSize
     * @param channel
     */
    public void handleMessage(byte[] message, int messageSize, int channel) {
    	System.out.println("Message recieved please overwrite this method");
    }
    
    public byte read(String name) {
        for(Sensor sensor: module.getSensors()) {
            if(name.equals(sensor.getName())) {
                float value = sensor.readValue();
                return (byte)(value/Math.PI*180-90); 
            }
                
        }
        throw new Error("Unknown sensor: "+name);
    }

}