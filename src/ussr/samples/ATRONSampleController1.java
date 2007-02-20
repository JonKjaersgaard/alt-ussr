/**
 * Uniform Simulator for Self-reconfigurable (modular) Robots
 * 
 * (C) 2006 University of Southern Denmark
 */
package ussr.samples;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import ussr.model.ControllerImpl;

/**
 * A simple controller for the ATRON, controlling connector stickiness based on
 * user-controlled state stored in the main simulator
 * 
 * @author ups
 *
 */
public class ATRONSampleController1 extends ControllerImpl {

    /**
     * @see ussr.model.ControllerImpl#activate()
     */
    public void activate() {
        while(true) {
        	if(!module.getSimulation().isPaused()) {
        		String name = module.getProperty("name");
    			float time = module.getSimulation().getTime();
        		//rotate((float)(Math.sin(time)+1)/2f);
    			if(name=="wheel1") rotateContinuous(1);
    			if(name=="wheel2") rotateContinuous(-1);
    			if(name=="wheel3") rotateContinuous(1);
    			if(name=="wheel4") rotateContinuous(-1);
    			if(name.contains("snake")) {
    				if(!isRotating()) {
    					rotate(1);
    					//delay(1000);
    					rotate(-1);
    					//delay(1000);
    				}
    			}
    			if(!GenericSimulation.getConnectorsAreActive()) {
    				disconnectAll();
    			}
        	}
        	Thread.yield();
        }
    }

	private boolean isRotating() {
    	return module.getActuators().get(0).isActive();
	}
	private int getRotation() {
    	float encVal = module.getActuators().get(0).getEncoderValue();
    	if(Math.abs(encVal-0.50)<0.125f) return 0;
    	if(Math.abs(encVal-0.75)<0.125f) return 1;
    	if(Math.abs(encVal-0)<0.125f || Math.abs(encVal-1)<0.125f) return 2;
    	if(Math.abs(encVal-0.25)<0.125f) return 3;
    	System.err.println("No ATRON rotation defined should not happend "+encVal);
    	return 0;    	
    }
    private void rotate(int dir) {
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
	private void disconnectAll() {
		for(int i=0;i<8;i++) {
			if(isConnected(i)) {
				disconnect(i);
			}
		}
	}
    
	private void disconnect(int i) {
		module.getConnectors().get(i).disconnect();
	}
	private boolean isConnected(int i) {
		return module.getConnectors().get(i).isConnected();
	}

    public void rotateContinuous(float dir) {
    	module.getActuators().get(0).activate(dir);
    }
    
    public boolean isOtherConnectorNearby(int connector) {
    	if(module.getConnectors().get(connector).isConnected()) {
    		return true;
    	}
    	else  {
    		return false;
    	}
    }
}
