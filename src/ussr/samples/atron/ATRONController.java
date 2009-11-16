/**
 * Unified Simulator for Self-Reconfigurable Robots (USSR)
 * (C) University of Southern Denmark 2008
 * This software is distributed under the BSD open-source license.
 * For licensing see the file LICENCE.txt included in the root of the USSR distribution.
 */
package ussr.samples.atron;


import java.util.Random;

import ussr.comm.Packet;
import ussr.comm.PacketReceivedObserver;
import ussr.comm.RadioReceiver;
import ussr.comm.RadioTransmitter;
import ussr.comm.Receiver;
import ussr.comm.Transmitter;
import ussr.model.ControllerImpl;
import ussr.model.Module;
import ussr.model.Sensor;
import ussr.model.ModuleEventQueue.Event;
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

    private float targetPos, targetVel, zeroPos;
    protected static enum CenterStates {STOPPED, BRAKED, POSCONTROL, VELCONTROL, POSVELCONTROL}
    private CenterStates centerState;
	private boolean blocking;
	private boolean globalTime;
	private int leds=0;
	private boolean simulateCommFailure = false;
	private float[] commFailureRisk = new float[8];
	private Random commFailureRandomizer = new Random(); 
	
	/**
	 * Instantiate ATRON controller
	 */
    public ATRONController() {
        setBlocking(false);
        setHasGlobalTime(true);
    }
    
    public void setCommFailureRisk(float generalRiskMin, float generalRiskMax, float totalFailureRisk) {
        this.setCommFailureRisk(generalRiskMin, generalRiskMax, totalFailureRisk, null);
    }
    
    public void setCommFailureRisk(float generalRiskMin, float generalRiskMax, float totalFailureRisk, Integer seedMaybe) {
        simulateCommFailure = true;
        Random r;
        if(seedMaybe==null)
            r = commFailureRandomizer;
        else {
            int moduleOffset = module.getProperty("ussr.module.name").hashCode();
            r = new Random(seedMaybe+moduleOffset);
        }
        float scale = generalRiskMax-generalRiskMin;
        StringBuffer fingerPrint = new StringBuffer("<");
        for(int c=0; c<8; c++) {
            if(r.nextFloat()<totalFailureRisk) { commFailureRisk[c] = 1.0f; fingerPrint.append("1"); }
            else { commFailureRisk[c] = generalRiskMin+r.nextFloat()*scale; fingerPrint.append("0"); }
        }
        fingerPrint.append(">");
        System.out.println("(Module "+module.getProperty("ussr.module.name")+" randomization fingerprint: "+fingerPrint+")");
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#home()
	 */
    public void home() { 
        this.rotateToDegree(0);
    
    }
    /**
	 * @see ussr.samples.atron.IATRONAPI#home()
	 */
    public void setLeds(int val) { 
     	leds = val%256;
    }
    /**
	 * @see ussr.samples.atron.IATRONAPI#home()
	 */
    public int getLeds() { 
        return leds;    
    }
    /**
     * @see ussr.samples.atron.IATRONAPI#setup()
     */
    public void setup() {
    	yield();
        zeroPos = module.getActuators().get(0).getEncoderValue();
        setTargetPos(0);
        centerState = CenterStates.STOPPED;
        PhysicsLogger.logNonCritical("[Encoder = "+zeroPos+"]");
        if(zeroPos==Float.NaN) throw new Error("Unable to read encoder");
        if(module.getTransmitters().size()>8 && module.getTransmitters().get(8)!=null)  { //module has a radio
        	if(module.getProperty("radio")!=null && module.getProperty("radio").equals("disabled")) {
        		((RadioTransmitter) module.getTransmitters().get(8)).setEnabled(false);
        		((RadioReceiver) module.getReceivers().get(8)).setEnabled(false);
        	}
        }
        if(!globalTime){
        	setLocalTimeOffset(getRandom().nextFloat()*10.0f); //all modules are started within 10 seconds
        }
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
    	if(centerState == CenterStates.POSCONTROL) {
    		posActuateJoint();
    	}
    	else if(centerState == CenterStates.VELCONTROL) {
    		velActuateJoint();
    	}
    	else if(centerState == CenterStates.POSVELCONTROL) {
    		posVelActuateJoint();
    	}
    	else if(centerState == CenterStates.BRAKED) {
    		relaxJoint(); //?
    	}
    	else if(centerState == CenterStates.STOPPED) {
    		relaxJoint();
    	}
    }
    
    protected float readEncoderPosition() {
        float val = (module.getActuators().get(0).getEncoderValue()-zeroPos);
        if(val<0) val = 1+val;
        return val;
    }
    
    private void setTargetVel(float target) {
    	targetVel = target;
    }
    
    private void setTargetPos(float target) {
    	float val = target+zeroPos;
    	if(val>1) val = val-1;
    	targetPos = val;
    }
    private float getTargetErrorAbs() {
    	float error = Math.abs(targetPos-module.getActuators().get(0).getEncoderValue());
    	return error;
    }
    

    
    private void velActuateJoint() {
    	module.getActuators().get(0).setDesiredVelocity(targetVel);
    }
    
    private void posActuateJoint() {
    	module.getActuators().get(0).setDesiredPosition(targetPos);
    }
    
    private void posVelActuateJoint() {
    	float error = getTargetErrorAbs();//Math.abs(targetPos-readEncoderPosition());
    	if(error>0.5) error = 1-error;
    	if(error<0.1) {
    		centerState = CenterStates.POSCONTROL;
    	}
    	else {
    		module.getActuators().get(0).setDesiredVelocity(targetVel);
    	}
    }
    
    private void relaxJoint() {
    	module.getActuators().get(0).disactivate();
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
    public void setHasGlobalTime(boolean globalTime) {
    	this.globalTime = globalTime;
    }
    /**
	 * @see ussr.samples.atron.IATRONAPI#getJointPosition()
	 */
    public int getJointPosition() {
    	float encVal = readEncoderPosition();
    	if(Math.abs(encVal-0)<=0.125f || Math.abs(encVal-1)<0.125f) return 0;
    	if(Math.abs(encVal-0.25)<=0.125f) return 1;
    	if(Math.abs(encVal-0.5)<=0.125f) return 2;
    	if(Math.abs(encVal-0.75)<=0.125f) return 3;
    	PhysicsLogger.log("["+getName()+"] No ATRON rotation defined should not happend "+encVal);
    	return 0;
    }

    /**
	 * @see ussr.samples.atron.IATRONAPI#rotate(int)
	 */
    public void rotate(int dir) {
    	int goal = 0;
    	if(getJointPosition()==0) 		goal = ((dir<0)?90:270);
    	else if(getJointPosition()==1) 	goal = ((dir<0)?180:0);
    	else if(getJointPosition()==2) 	goal = ((dir<0)?270:90);
    	else if(getJointPosition()==3) 	goal = ((dir<0)?0:180);
    	rotateToDegreeInDegrees(goal);
    	PhysicsLogger.logNonCritical("["+getName()+"] Rotation done pos = "+module.getActuators().get(0).getEncoderValue());
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#rotateDegrees(int)
	 */
    public void rotateDegrees(int degrees) {
        float rad = (float)(degrees/360.0*2*Math.PI);
        float current = this.getAngularPosition();
        rotateToDegree(current+rad);
    }

    /**
	 * @see ussr.samples.atron.IATRONAPI#rotateToDegreeInDegrees(int)
	 */
    public void rotateToDegreeInDegrees(int degrees) {
        float rad = (float)(degrees/360.0*2*Math.PI);
        this.rotateToDegree(rad);
    }
    
    public void rotateToViaInDegrees(int degrees, int via) {
    	System.out.println("Function 'rotateToViaInDegrees' not implemented"); //TODO implement physics callback to actuators and doo it right 
    	float rad = (float)(degrees/360.0*2*Math.PI);
        this.rotateToDegree(rad);
    }
    public void rotateDirToInDegrees(int degrees, int dir) {
     	centerState = CenterStates.POSVELCONTROL;
     	float target = (float)(degrees/360.0);
     	setTargetPos(target);
    	setTargetVel(dir);
    	posVelActuateJoint();
        while(isRotating()&&blocking) yield();
    }
    /**
	 * @see ussr.samples.atron.IATRONAPI#rotateToDegree(float)
	 */
    public void rotateToDegree(float rad) {
     	centerState = CenterStates.POSCONTROL;
    	float target = (float)(rad/(Math.PI*2));
    	setTargetPos(target);
    	posActuateJoint();
    	//System.out.println("Starting to rotate... "+target);
        while(isRotating()&&blocking) yield();
        //System.out.println("...done rotation "+target+" vs "+readEncoderPosition() );
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#getAngularPosition()
	 */
    public float getAngularPosition() {
    	return (float)(readEncoderPosition()*Math.PI*2);
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#getAngularPositionDegrees()
	 */
    public int getAngularPositionDegrees() {
    	return (int)(readEncoderPosition()*360);
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
    	if(isConnected(i)&&!module.getConnectors().get(i).hasProximateConnector()) System.out.println(getName()+": Inconsistant connector state detected!");
    	return isOtherConnectorNearby(i)&&isMale(i)&&!isConnected(i);
    }

	/**
	 * @see ussr.samples.atron.IATRONAPI#canDisconnect(int)
	 */
	public boolean canDisconnect(int i) {
		if(isConnected(i)&&!module.getConnectors().get(i).hasProximateConnector()) System.out.println(getName()+": Inconsistant connector state detected!");
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
    public void rotateContinuous(float vel) {
    	if(vel<-1||vel>1) {
    		PhysicsLogger.log("Velocity out of range -1 to 1");
    		return;
    	}
    	centerState = CenterStates.VELCONTROL;
    	setTargetVel(vel);
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#centerBrake()
	 */
    public void centerBrake() {
    	centerState = CenterStates.BRAKED;
    }
    
    /**
	 * @see ussr.samples.atron.IATRONAPI#centerStop()
	 */
    public void centerStop() {
     	centerState = CenterStates.STOPPED;
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
    	else if(connector==8) { //radio
    		Transmitter trans =  module.getTransmitters().get(connector);
    		if(trans!=null &&  ((RadioTransmitter)trans).isEnabled()) {
    			trans.send(new Packet(message));
    		}
    		else {
    			System.err.println("Warning: module attempts to send message over non existing/disabled radio");
    		}
			return 1;
		}
		return 0;
	}
      
    /**
     * Called when a packet is received by the module
     * @param device the device that received an incoming packet 
     */
    public void packetReceived(Receiver device) {
    	for(int i=0;i<module.getReceivers().size();i++) {
    		if(module.getReceivers().get(i).equals(device)) {
    			final byte[] data = device.getData().getData();
    			final int index = i;
    			if(this.simulateCommFailure&&this.commFailureRisk[i]>this.commFailureRandomizer.nextFloat()) { 
    			    //System.err.println("Simulation packet lost");
    			    return;
    			}
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
