/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron;

import ussr.comm.Packet;
import ussr.comm.PacketReceivedObserver;
import ussr.comm.Receiver;
import ussr.model.ControllerImpl;
import ussr.model.Module;
import ussr.model.Sensor;
import ussr.physics.PhysicsLogger;
import ussr.physics.PhysicsObserver;
import ussr.physics.PhysicsParameters;
import ussr.physics.PhysicsSimulation;

/**
 * Controller class that provides the ATRON API
 * 
 * @author Modular Robots @ MMMI
 */
public abstract class ATRONController extends ControllerImpl implements PacketReceivedObserver, PhysicsObserver, IATRONAPI {

    private float targetPos, currentPos, zeroPos;
	private boolean blocking;
	private boolean locked = false;

	/**
	 * Instantiate ATRON controller
	 */
    public ATRONController() {
        setBlocking(false);
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#home()
	 */
    public void home() { 
        this.rotateToDegreeInDegrees(180);
    }
    
    /**
     * @see ussr.samples.atron.IATRONAPI#setup()
     */
    public void setup() {
        currentPos = targetPos = 0;
        zeroPos = module.getActuators().get(0).getEncoderValue();
        PhysicsLogger.logNonCritical("[Encoder = "+zeroPos+"]");
        if(zeroPos==Float.NaN) throw new Error("Unable to read encoder");
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#getName()
	 */
    public String getName() {
        return module.getProperty("name");
    }
    
    /**
     * 
     * @see ussr.model.ControllerImpl#setModule(ussr.model.Module)
     */
    public void setModule(Module module) {
    	super.setModule(module);
        for(Receiver r: module.getReceivers()) { 
         	r.addPacketReceivedObserver(this); //packetReceived(..) will be called when a packet is received
        }
        module.getSimulation().subscribePhysicsTimestep(this);
    }
    
    /**
     * 
     * @see ussr.physics.PhysicsObserver#physicsTimeStepHook(ussr.physics.PhysicsSimulation)
     */
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

    /**
	 * @see ussr.samples.atron.IATRONAPI#isRotating()
	 */
    public boolean isRotating() {
    	return module.getActuators().get(0).isActive();
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#setBlocking(boolean)
	 */
    public void setBlocking(boolean blocking) {
    	this.blocking = blocking;
    }

    /**
	 * @see ussr.samples.atron.IATRONAPI#getJointPosition()
	 */
    public int getJointPosition() {
    	float encVal = readEncoderPosition();
    	if(Math.abs(encVal-0.50)<=0.125f) return 0;
    	if(Math.abs(encVal-0.75)<=0.125f) return 1;
    	if(Math.abs(encVal-0)<=0.125f || Math.abs(encVal-1)<0.125f) return 2;
    	if(Math.abs(encVal-0.25)<=0.125f) return 3;
    	PhysicsLogger.log("["+getName()+"] No ATRON rotation defined should not happend "+encVal);
    	return 0;
    }

    /**
	 * @see ussr.samples.atron.IATRONAPI#rotate(int)
	 */
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
    		yield();
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
                   yield();
    	       }
    	        PhysicsLogger.logNonCritical("(unlocking actuator on "+this.getName()+")");
    	    }
    	}).start();
	}
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#rotateDegrees(int)
	 */
    public void rotateDegrees(int degrees) {
        locked = false;
        float rad = (float)(degrees/360.0*2*Math.PI);
        float current = this.getAngularPosition();
        do {
            this.rotateToDegree(current+rad);
            Thread.yield();
        } while(isRotating()&&blocking);
    }

    /**
	 * @see ussr.samples.atron.IATRONAPI#rotateToDegreeInDegrees(int)
	 */
    public void rotateToDegreeInDegrees(int degrees) {
        float rad = (float)(degrees/360.0*2*Math.PI);
        this.rotateToDegree(rad);
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#rotateToDegree(float)
	 */
    public void rotateToDegree(float rad) {
        locked = false;
        float goal = (float)(rad/(Math.PI*2));
        do {
            module.getActuators().get(0).activate(goal);
            yield();
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
                   yield();
               }
                PhysicsLogger.logNonCritical("(unlocking actuator on "+this.getName()+")"); System.out.flush();
            }
        }).start();
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#getTime()
	 */
    public float getTime() {
    	//TODO local version of this instead of global and syncronized
    	return getModule().getSimulation().getTime();
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#getAngularPosition()
	 */
    public float getAngularPosition() {
    	return (float)(module.getActuators().get(0).getEncoderValue()*Math.PI*2);
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#getAngularPositionDegrees()
	 */
    public int getAngularPositionDegrees() {
        return (int)(module.getActuators().get(0).getEncoderValue()*360);
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#disconnectAll()
	 */
    public void disconnectAll() {
    	for(int i=0;i<8;i++) {
    		if(isConnected(i)) {
    			disconnect(i);
    		}
    	}
    }

    /**
	 * @see ussr.samples.atron.IATRONAPI#connectAll()
	 */
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

    /**
	 * @see ussr.samples.atron.IATRONAPI#canConnect(int)
	 */
    public boolean canConnect(int i) {
    	if(isConnected(i)&&!module.getConnectors().get(i).hasProximateConnector()) System.out.println("Inconsistant connector state detected!");
    	return isOtherConnectorNearby(i)&&isMale(i)&&!isConnected(i);
    }

	/**
	 * @see ussr.samples.atron.IATRONAPI#canDisconnect(int)
	 */
	public boolean canDisconnect(int i) {
		if(isConnected(i)&&!module.getConnectors().get(i).hasProximateConnector()) System.out.println("Inconsistant connector state detected!");
    	return isMale(i)&&isConnected(i);
    }

    /**
	 * @see ussr.samples.atron.IATRONAPI#isMale(int)
	 */
    public boolean isMale(int i) {
    	return (i%2)==0;
    }

    /**
	 * @see ussr.samples.atron.IATRONAPI#connect(int)
	 */
    public void connect(int i) {
        isOtherConnectorNearby(i);
    	module.getConnectors().get(i).connect();
    }

    /**
	 * @see ussr.samples.atron.IATRONAPI#disconnect(int)
	 */
    public void disconnect(int i) {
    	module.getConnectors().get(i).disconnect();
    }

    /**
	 * @see ussr.samples.atron.IATRONAPI#isConnected(int)
	 */
    public boolean isConnected(int i) {
    	return module.getConnectors().get(i).isConnected();
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#isDisconnected(int)
	 */
    public boolean isDisconnected(int i) {
        return !isConnected(i);
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#rotateContinuous(float)
	 */
    public void rotateContinuous(float dir) {
        locked = false;
        System.out.println("Java: rotate cont "+dir);
    	module.getActuators().get(0).activate(dir);
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#centerBrake()
	 */
    public void centerBrake() {
    	module.getActuators().get(0).disactivate();
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#centerStop()
	 */
    public void centerStop() {
    	centerBrake(); //TODO implement the difference from center brake
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#isOtherConnectorNearby(int)
	 */
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
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#isObjectNearby(int)
	 */
    public boolean isObjectNearby(int connector) {
        return module.getSensors().get(connector).readValue()>0.1;
    }
    
    /**
     * Set the sensitivity (e.g., range) of a proximity sensor
     * @arg sensitivity a positive floating point value that is multiplied onto the base range
     */
    public void setSensorSensitivity(int connector, float sensitivity) {
        module.getSensors().get(connector).setSensitivity(sensitivity);
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#sendMessage(byte[], byte, byte)
	 */
    public byte sendMessage(byte[] message, byte messageSize, byte connector) 
	{
    	if(isOtherConnectorNearby(connector)&&connector<8) {
			module.getTransmitters().get(connector).send(new Packet(message));
			return 1;
		}
		return 0;
	}
      
    /**
     * Called when a packet is received by the module
     * @param device the device that received an incoming packet 
     */
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
	 * @see ussr.samples.atron.IATRONAPI#handleMessage(byte[], int, int)
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
    /**
	 * @see ussr.samples.atron.IATRONAPI#getTiltX()
	 */
    public byte getTiltX() { return read("TiltSensor:y"); }

    // Note: hard-coded to do side-to-side tilt sensoring for driver and axles
    /**
	 * @see ussr.samples.atron.IATRONAPI#getTiltY()
	 */
    public byte getTiltY() { return read("TiltSensor:x"); }

    /**
	 * @see ussr.samples.atron.IATRONAPI#getTiltZ()
	 */
    public byte getTiltZ() { return read("TiltSensor:z"); }
}