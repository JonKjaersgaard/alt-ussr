package ussr.samples.odin;

import ussr.comm.Packet;
import ussr.comm.PacketReceivedObserver;
import ussr.comm.PacketSentObserver;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.model.ControllerImpl;
import ussr.model.Module;
import ussr.model.ModuleEventQueue.Event;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;

public abstract class OdinTinyOSController extends ControllerImpl implements PacketSentObserver, PacketReceivedObserver, PhysicsObserver, IOdinTinyOSAPI {

	public boolean sendBusy;
	protected String type;
	
    public float getActuatorPosition() {
    	if(module.getActuators().size()!=0) {
    		if(type=="OdinMuscle" || type=="OdinSSRlinearActuator") {
    			return (float)(module.getActuators().get(0).getEncoderValue());
    		}
    	}
    	throw new RuntimeException("Not well defined method for module type "+type);
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
    
	public int getRandomNumber() {
		//Math.random() return a double between 0 and 1
		//we approx it to int16_t positive precision
		return (int)(Math.random()*30000);
	}
	
	public int moduleType() {
		if(type == "OdinBall")
			return 1;
		else if(type == "OdinSSRlinearActuator")
			return 2;
		else
			return 0; // unknown
	}
	
	public int sendMessage(byte[] message, int messageSize, int connector) {
		
		/* 
		 * for now blocks always on the same flag, in any case
		 * (i.e., as if the radio and all the neighbor-to-neighbor channels were served by the same device)
		 */		
		if(this.sendBusy)
			return EBUSY;
		if(messageSize > 128) //TODO: parameterize the max allowed payload
			return ESIZE;
		if(type=="OdinBall")  {
			if(!(connector>=0&&connector<=12)) {
				return EINVAL;
			}
		}
		else {
			if(!(connector==0||connector==1)) {
				return EINVAL;
			}			
		}
		/* radio to be implemented */
//		if(connector == 8) {
//    		Transmitter trans =  module.getTransmitters().get(connector);
//    		if(trans!=null &&  ((RadioTransmitter)trans).isEnabled()) {
//    			if (trans.send(new Packet(message, connector))) {
//    				this.sendBusy = true;
//    				return SUCCESS;    			
//    			}
//    			else {
//    				return FAIL;
//    			}
//    		}
//    		else
//    			return FAIL;
//		}
	
		
//		else {		
			if (module.getTransmitters().get(connector).send(new Packet(message, connector))) {
				this.sendBusy = true;
				return SUCCESS;
			}
			else {
				return FAIL;
			}
//		}

	
	}

		
    /**
     * 
     * @see ussr.model.ControllerImpl#setModule(ussr.model.Module)
     */
    public void setModule(Module module) {
    	super.setModule(module);
        for(Transmitter t: module.getTransmitters()) { 
         	t.addPacketSentObserver(this);
        }
        for(Receiver r: module.getReceivers()) { 
         	r.addPacketReceivedObserver(this); //packetReceived(..) will be called when a packet is received
        }
        module.getSimulation().subscribePhysicsTimestep(this);
    	
    }
    
    /**
     * 
     * @see ussr.physics.PhysicsObserver#physicsTimeStepHook(ussr.physics.PhysicsSimulation)
     */
    public synchronized void physicsTimeStepHook(PhysicsSimulation simulation) {
    	// we don't really do anything here, it's mostly for the native one
    }
    
    /**
     * Called when a packet has been sent by the module
     * @param device the device that sent the outgoing packet 
     */
    public synchronized void packetSent(Transmitter device, Packet pkt, int errorCode) {
    	if(PhysicsParameters.get().useModuleEventQueue()) {
    		final byte[] data = pkt.getData();
			final int error = errorCode;
			final int connector = pkt.getConnector();
		    module.getModuleEventsQueue().addEvent(new Event(0) {
                @Override public void trigger() { sendDone(data, error, connector); }
		    });
		}
		else {
		    sendDone(pkt.getData(), errorCode, pkt.getConnector());
		}
	}
    
     
    /**
	 * @see ussr.samples.atron.IATRONTinyOSAPI#sendDone(byte[] msg, int error);
	 */
    public synchronized void sendDone(byte[] msg, int error, int connector) {
        PhysicsLogger.log("Message sent but no sendDone implemented in "+this);
    }
	
    /**
     * Called when a packet is received by the module
     * @param device the device that received an incoming packet 
     */
    public synchronized void packetReceived(Receiver device) {
    	for(int i=0;i<module.getReceivers().size();i++) {
    		if(module.getReceivers().get(i).equals(device)) {
    			final byte[] data = device.getData().getData();
    			final int index = i;    			
    			if(PhysicsParameters.get().useModuleEventQueue())
    				module.getModuleEventsQueue().addEvent(new Event(0) {
                        @Override public void trigger() { handleMessage(data,data.length,index); }
    			    });
    			else
    			    handleMessage(data,data.length,index);
    			return;
    		}
    	}
    }
    
    public synchronized void handleMessage(byte[] message, int messageSize, int connector) {
        PhysicsLogger.log("Message received but no handleMessage implemented in "+this);
    }

}
