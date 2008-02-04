package ussr.samples.atron;

import java.awt.Color;

import ussr.comm.Packet;
import ussr.comm.PacketReceivedObserver;
import ussr.comm.Receiver;
import ussr.model.ControllerImpl;
import ussr.model.Module;
import ussr.model.Sensor;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsSimulation;
import ussr.physics.PhysicsParameters;

/**
 * Controller class that provides the ATRON API
 * 
 * @author Modular Robots @ MMMI
 */
public abstract class ATRONController extends ControllerImpl implements PacketReceivedObserver, PhysicsObserver {

    float targetPos, currentPos, zeroPos;
	private boolean blocking;
	private boolean locked = false;
    public ATRONController() {
        super();
        setBlocking(true);
    }
    
    public void home() { 
        this.rotateToDegreeInDegrees(180);
    }
    public void setup() {
        currentPos = targetPos = 0;
        zeroPos = module.getActuators().get(0).getEncoderValue();
        PhysicsLogger.logNonCritical("[Encoder = "+zeroPos+"]");
        if(zeroPos==Float.NaN) throw new Error("Unable to read encoder");
    }
    
    public String getName() {
        return module.getProperty("name");
    }
    
    public void setModule(Module module) {
    	super.setModule(module);
        for(Receiver r: module.getReceivers()) { 
         	r.addPacketReceivedObserver(this); //packetReceived(..) will be called when a packet is received
        }
        module.getSimulation().subscribePhysicsTimestep(this);
    }
    public void physicsTimeStepHook(PhysicsSimulation simulation) {
        // If the module is supposed to be at the right position, make it stay at the right position
        if(currentPos==targetPos)
            maintainJoint(currentPos);
    }
    private float readEncoderPosition() {
        return module.getActuators().get(0).getEncoderValue();//-zeroPos;
    }
    private void actuateJoint(float target) {
        module.getActuators().get(0).activate(target/*+zeroPos*/);
    }
    private void maintainJoint(float target) {
        //module.getActuators().get(0).maintain(target+zeroPos);
    }
    public boolean isRotating() {
    	return module.getActuators().get(0).isActive();
    }
    public void setBlocking(boolean blocking) {
    	this.blocking = blocking;
    }

    public int getJointPosition() {
    	float encVal = readEncoderPosition();
    	if(Math.abs(encVal-0.50)<=0.125f) return 0;
    	if(Math.abs(encVal-0.75)<=0.125f) return 1;
    	if(Math.abs(encVal-0)<=0.125f || Math.abs(encVal-1)<0.125f) return 2;
    	if(Math.abs(encVal-0.25)<=0.125f) return 3;
    	PhysicsLogger.log("["+getName()+"] No ATRON rotation defined should not happend "+encVal);
    	return 0;
    }

    public void rotate(int dir) {
    	float goalRot = 0;
    	locked = false;
    	if(getJointPosition()==0) goalRot = ((dir>0)?1:3);
    	else if(getJointPosition()==1) goalRot = ((dir>0)?2:0);
    	else if(getJointPosition()==2) goalRot = ((dir>0)?3:1);
    	else if(getJointPosition()==3) goalRot = ((dir>0)?0:2);
    	
    	PhysicsLogger.logNonCritical("["+getName()+"] RotPos "+getAngularPosition()+" go for "+goalRot/4f);
    	module.getActuators().get(0).activate(goalRot/4f);
    	while(isRotating()&&blocking) {
    		module.getActuators().get(0).activate(goalRot/4f);
    		ussrYield();
    	}
    	PhysicsLogger.logNonCritical("["+getName()+"] Rotation done pos = "+module.getActuators().get(0).getEncoderValue());
    	final float maintain = goalRot/4f;
    	if(!PhysicsParameters.get().getMaintainRotationalJointPositions()) return;
    	locked = true;
        // Note: the following is basically a hack, should be replaced by an implementation in the
        // physics actuator that updates at each time step
    	(new Thread() {
    	    public void run() { 
    	        PhysicsLogger.logNonCritical("(locking actuator on "+this.getName()+" to maintain "+maintain+") ");
    	        while(locked) {
    	           module.getActuators().get(0).activate(maintain);
                   ussrYield();
    	       }
    	        PhysicsLogger.logNonCritical("(unlocking actuator on "+this.getName()+")");
    	    }
    	}).start();
	}
    
    public void rotateDegrees(int degrees) {
        locked = false;
        float rad = (float)(degrees/360.0*2*Math.PI);
        float current = this.getAngularPosition();
        do {
            this.rotateToDegree(current+rad);
            Thread.yield();
        } while(isRotating()&&blocking);
    }

    public void rotateToDegreeInDegrees(int degrees) {
        float rad = (float)(degrees/360.0*2*Math.PI);
        this.rotateToDegree(rad);
    }
    
    public void rotateToDegree(float rad) {
        locked = false;
        float goal = (float)(rad/(Math.PI*2));
        do {
            module.getActuators().get(0).activate(goal);
            ussrYield();
        } while(isRotating()&&blocking);
        final float maintain = goal;
        if(!PhysicsParameters.get().getMaintainRotationalJointPositions()) return;
        locked = true;
        // Note: the following is basically a hack, should be replaced by an implementation in the
        // physics actuator that updates at each time step
        (new Thread() {
            public void run() { 
                PhysicsLogger.logNonCritical("(locking actuator on "+this.getName()+")");
                while(locked) {
                   module.getActuators().get(0).activate(maintain);
                   ussrYield();
               }
                PhysicsLogger.logNonCritical("(unlocking actuator on "+this.getName()+")"); System.out.flush();
            }
        }).start();
    }
    public float getTime() {
    	//TODO local version of this instead of global and syncronized
    	return getModule().getSimulation().getTime();
    }
    public float getAngularPosition() {
    	return (float)(module.getActuators().get(0).getEncoderValue()*Math.PI*2);
    }
    
    public int getAngularPositionDegrees() {
        return (int)(module.getActuators().get(0).getEncoderValue()*360);
    }
    

    public void disconnectAll() {
    	for(int i=0;i<8;i++) {
    		if(isConnected(i)) {
    			disconnect(i);
    		}
    	}
    }

    public void connectAll() {
    	for(int i=0;i<8;i++) {
    		if(isOtherConnectorNearby(i)) {
    			PhysicsLogger.logNonCritical(module.getID()+" Other connector at connector "+i);
    		}
    		if(canConnect(i)) {
    			connect(i);
    		}
    	}
    }

    public boolean canConnect(int i) {
    	if(isConnected(i)&&!module.getConnectors().get(i).hasProximateConnector()) System.out.println("Inconsistant connector state detected!");
    	return isOtherConnectorNearby(i)&&isMale(i)&&!isConnected(i);
    }

	public boolean canDisconnect(int i) {
		if(isConnected(i)&&!module.getConnectors().get(i).hasProximateConnector()) System.out.println("Inconsistant connector state detected!");
    	return isMale(i)&&isConnected(i);
    }

    public boolean isMale(int i) {
    	return (i%2)==0;
    }

    public void connect(int i) {
        isOtherConnectorNearby(i);
    	module.getConnectors().get(i).connect();
    }

    public void disconnect(int i) {
    	module.getConnectors().get(i).disconnect();
    }

    public boolean isConnected(int i) {
    	return module.getConnectors().get(i).isConnected();
    }
    public boolean isDisconnected(int i) {
        return !isConnected(i);
    }
    public void rotateContinuous(float dir) {
        locked = false;
    	module.getActuators().get(0).activate(dir);
    }
    public void centerBrake() {
    	module.getActuators().get(0).disactivate();
    }
    public void centerStop() {
    	centerBrake(); //TODO implement the difference from center brake
    }
    public boolean isOtherConnectorNearby(int connector) {
    	if(module.getConnectors().get(connector).isConnected()) {
    		return true;
    	}
    	if(module.getConnectors().get(connector).hasProximateConnector()) {
    		if(module.getTransmitters().get(connector).withinRangeCount()!=0) {
    			return true;
    		}
    	}
   		return false;
    }
    
    public boolean isObjectNearby(int connector) {
        return module.getSensors().get(connector).readValue()>0.1;
    }
    
    public byte sendMessage(byte[] message, byte messageSize, byte connector) 
	{
		if(isOtherConnectorNearby(connector)&&connector<8) {
			module.getTransmitters().get(connector).send(new Packet(message));
			return 1;
		}
		return 0;
	}
    public void packetReceived(Receiver device) {
    	for(int i=0;i<8;i++) {
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
        PhysicsLogger.log("Message received but no handleMessage implemented in "+this);
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