package ussr.samples.odin;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

import com.jme.math.Vector3f;

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
import ussr.physics.jme.JMEModuleComponent;

public abstract class OdinTinyOSController extends ControllerImpl implements PacketSentObserver, PacketReceivedObserver, PhysicsObserver, IOdinTinyOSAPI {

	public boolean sendBusy;
	protected String type;
	
	static final int MODULE_TYPE_UNKNOWN = 0;	
	static final int MODULE_TYPE_ODIN_BALL = 1;
	static final int MODULE_TYPE_SSR_LINEAR_ACTUATOR = 2;	

    public void setColor(int c) {
    	if(c == 1)
    		module.setColor(Color.YELLOW);
    	else if (c == 2)
    		module.setColor(Color.BLUE);   		
    	else if (c == 3)
    		module.setColor(Color.BLACK); 
    	else if (c == 4)
    		module.setColor(Color.WHITE);
    }	
	
    public void setColor(float r, float g, float b) {
   		module.setColor(new Color(r,g,b));
    }
    
    public float getTilt() {	
    	float[] angles = new float[3];
    	((JMEModuleComponent)(module.getComponent(0))).getModuleNode().getLocalRotation().toAngles(angles);
    	return angles[2]; //pitch?
    }
    
    public float getDistanceSensor() {
    	if(this.module.getSimulation().getObstaclePositions().size() != 0) {
        	//the target from which we calculate the distance is the obstacle 0
    		Vector3f source = this.module.getSimulation().getObstaclePositions().get(0).getVector();   		
			Vector3f modulePosition = ((JMEModuleComponent)(module.getComponent(0))).getModuleNode().getLocalTranslation();
			return (modulePosition.clone()).distance(source);
    	}
    	else
    		return Float.POSITIVE_INFINITY;
    }
       
	/* return a 0-100 output */
    public int getActuatorPosition() {
    	if(module.getActuators().size()!=0) {
    		if(type=="OdinMuscle" || type=="OdinSSRlinearActuator") {
    			return (int) (module.getActuators().get(0).getEncoderValue()*100);
    		}
    	}
    	//throw new RuntimeException("Not well defined method for module type "+type);
    	return 0;
    }
    
    public boolean isActuating() {
    	boolean actuating = false;
    	for(int i=0;i<module.getActuators().size();i++) {
    		actuating &=module.getActuators().get(i).isActive();
    	}
    	return actuating;
    }   
   
    /* input [-100, 100] */
    public void setActuatorSpeed(int val) {
    	if(module.getActuators().size()!=0) {
    		if(type=="OdinMuscle" || type=="OdinSSRlinearActuator") {
    			module.getActuators().get(0).setDesiredVelocity( ((float)val)/100 );
    		}
    	}
    	//throw new RuntimeException("Not well defined method for module type "+type);    	
    }
    
	public int getRandomNumber() {
		//Math.random() return a double between 0 and 1
		//we approx it to int16_t positive precision
		return (int)(Math.random()*30000);
	}
	
	public int moduleType() {
		if(type == "OdinBall")
			return MODULE_TYPE_ODIN_BALL;
		else if(type == "OdinSSRlinearActuator")
			return MODULE_TYPE_SSR_LINEAR_ACTUATOR;
		else
			return MODULE_TYPE_UNKNOWN;
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
