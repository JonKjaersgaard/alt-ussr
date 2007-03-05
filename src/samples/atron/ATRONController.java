package samples.atron;

import ussr.model.ControllerImpl;

public abstract class ATRONController extends ControllerImpl {

    public ATRONController() {
        super();
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
    	return i%2==0;
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

}