package ussr.builder;

import ussr.samples.odin.OdinController;

/**
 * Default controller for Odin modular robot.
 * @author Konstantinas *
 */
public class OdinControllerDefault extends OdinController {

	@Override
	public void activate() {
		while(module.getSimulation().isPaused()) Thread.yield();
		String labels = module.getProperty(BuilderHelper.getModuleTypeKey());
    	if(labels.contains("ball")) while(true) ballControl();
    	if(labels.contains("wheel")) while(true) wheelTestControl();
    	while(true) {
    		module.getSimulation().waitForPhysicsStep(false);
    	}
		
	}
	
	 public void ballControl() {
	    	while(true) {
	    		try {
	                Thread.sleep(100000);
	            } catch (InterruptedException e) {
	                throw new Error("unexpected");
	            }
	    	}
	    }
	    public void wheelTestControl() {
	    	while(true) {
	    		//actuate(0.9f);
	    		while(getTime()<5) {
	    			actuate(0f);
	    			module.getSimulation().waitForPhysicsStep(false);
	    		}
	    		while(getTime()<15) {
	    			actuate(1f);
	    			module.getSimulation().waitForPhysicsStep(false);
	    		}
	    		while(getTime()<30) {
	    			float x = (float)(Math.sin(getTime()));
	    			actuate((x>0)?1:-1);
	    			module.getSimulation().waitForPhysicsStep(false);
	    		}
	    		while(getTime()<45) {
	    			actuate(-1f);
	    			module.getSimulation().waitForPhysicsStep(false);
	    		}
	    		while(true) {
	    			actuate(0f);
	    			module.getSimulation().waitForPhysicsStep(false);
	    		}
	    	}
	    }
	    
	    public float getTime() {
	    	return module.getSimulation().getTime();
		}
	

}
