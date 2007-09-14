package ussr.samples.atron;

import ussr.comm.Packet;
import ussr.comm.PacketReceivedObserver;
import ussr.comm.Receiver;
import ussr.model.ControllerImpl;
import ussr.model.Module;
import ussr.model.Sensor;

public abstract class ATRONController extends ControllerImpl implements PacketReceivedObserver {

	boolean blocking;
    public ATRONController() {
        super();
        setBlocking(true);
    }
    public void setModule(Module module) {
    	super.setModule(module);
        for(Receiver r: module.getReceivers()) { 
         	r.addPacketReceivedObserver(this); //packetReceived(..) will be called when a packet is received
        }
    }
    public boolean isRotating() {
    	return module.getActuators().get(0).isActive();
    }
    public void setBlocking(boolean blocking) {
    	this.blocking = blocking;
    }

    public int getJointPosition() {
    	float encVal = module.getActuators().get(0).getEncoderValue();
    	if(Math.abs(encVal-0.50)<=0.125f) return 0;
    	if(Math.abs(encVal-0.75)<=0.125f) return 1;
    	if(Math.abs(encVal-0)<=0.125f || Math.abs(encVal-1)<0.125f) return 2;
    	if(Math.abs(encVal-0.25)<=0.125f) return 3;
    	System.err.println("No ATRON rotation defined should not happend "+encVal);
    	return 0;
    }

    public void rotate(int dir) {
    	float goalRot = 0;
    	
    	if(getJointPosition()==0) goalRot = ((dir>0)?1:3);
    	else if(getJointPosition()==1) goalRot = ((dir>0)?2:0);
    	else if(getJointPosition()==2) goalRot = ((dir>0)?3:1);
    	else if(getJointPosition()==3) goalRot = ((dir>0)?0:2);
    	
    	System.out.println("RotPos "+getAngularPosition()+" go for "+goalRot/4f);
    	module.getActuators().get(0).activate(goalRot/4f);
    	while(isRotating()&&blocking) {
    		module.getActuators().get(0).activate(goalRot/4f);
    		Thread.yield();
    	}
	}
    
    public void rotateDegrees(int degrees) {
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
        float goal = (float)(rad/(Math.PI*2));
        do {
            module.getActuators().get(0).activate(goal);
            Thread.yield();
        } while(isRotating()&&blocking);
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
    			System.out.println(module.getID()+" Other connector at connector "+i);
    		}
    		if(canConnect(i)) {
    			connect(i);
    		}
    	}
    }

    public boolean canConnect(int i) {
    	return isOtherConnectorNearby(i)&&isMale(i)&&!isConnected(i);
    }

    public boolean canDisconnect(int i) {
    	return isMale(i)&&isConnected(i);
    }

    public boolean isMale(int i) {
    	return (i%2)==0;
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
    public void rotateContinuous(float dir) {
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