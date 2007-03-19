package ussr.samples.atron;

import ussr.comm.Packet;
import ussr.comm.PacketReceivedObserver;
import ussr.comm.Receiver;
import ussr.model.ControllerImpl;
import ussr.model.Module;
import ussr.model.Sensor;

public abstract class ATRONController extends ControllerImpl implements PacketReceivedObserver {

    public ATRONController() {
        super();
    }
    public void setModule(Module module) {
    	super.setModule(module);
        for(Receiver r: module.getReceivers()) { 
         	r.addPacketReceivedObserver(this); //packetReceived(..) will be called when a packet is received
        }
    }
    protected boolean isRotating() {
    	return module.getActuators().get(0).isActive();
    }

    protected int getRotation() {
    	float encVal = module.getActuators().get(0).getEncoderValue();
    	if(Math.abs(encVal-0.50)<0.125f) return 0;
    	if(Math.abs(encVal-0.75)<0.125f) return 1;
    	if(Math.abs(encVal-0)<0.125f || Math.abs(encVal-1)<0.125f) return 2;
    	if(Math.abs(encVal-0.25)<0.125f) return 3;
    	System.err.println("No ATRON rotation defined should not happend "+encVal);
    	return 0;
    }

    protected void rotate(int dir) {
    	float goalRot = 0;
    	
    	if(getRotation()==0) goalRot = ((dir>0)?1:3);
    	else if(getRotation()==1) goalRot = ((dir>0)?2:0);
    	else if(getRotation()==2) goalRot = ((dir>0)?3:1);
    	else if(getRotation()==3) goalRot = ((dir>0)?0:2);
    	
//    	System.out.println("RotPos "+getRotation()+" go for "+goalRot/4f);
    	module.getActuators().get(0).activate(goalRot/4f);
    	while(isRotating()) {
    		module.getActuators().get(0).activate(goalRot/4f);
    		Thread.yield();
    	}
	}
    protected void rotateToDegree(float rad) {
    	module.getActuators().get(0).activate((float)(rad/(Math.PI*2)));
	}
    protected float getAngularPosition() {
    	return (float)(module.getActuators().get(0).getEncoderValue()*Math.PI*2);
    }
    

    protected void disconnectAll() {
    	for(int i=0;i<8;i++) {
    		if(isConnected(i)) {
    			disconnect(i);
    		}
    	}
    }

    protected void connectAll() {
    	for(int i=0;i<8;i++) {
    		if(isOtherConnectorNearby(i)) {
    			System.out.println(module.getID()+" Other connector at connector "+i);
    		}
    		if(canConnect(i)) {
    			connect(i);
    		}
    	}
    }

    protected boolean canConnect(int i) {
    	return isOtherConnectorNearby(i)&&isMale(i)&&!isConnected(i);
    }

    protected boolean canDisconnect(int i) {
    	return isMale(i)&&isConnected(i);
    }

    protected boolean isMale(int i) {
    	return (i%2)==0;
    }

    protected void connect(int i) {
    	module.getConnectors().get(i).connect();
    }

    protected void disconnect(int i) {
    	module.getConnectors().get(i).disconnect();
    }

    protected boolean isConnected(int i) {
    	return module.getConnectors().get(i).isConnected();
    }

    protected void rotateContinuous(float dir) {
    	module.getActuators().get(0).activate(dir);
    }
    protected void centerBrake() {
    	module.getActuators().get(0).disactivate();
    }
    protected void centerStop() {
    	centerBrake(); //TODO implement the difference from center brake
    }

    protected boolean isOtherConnectorNearby(int connector) {
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
    protected byte sendMessage(byte[] message, byte messageSize, byte connector) 
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