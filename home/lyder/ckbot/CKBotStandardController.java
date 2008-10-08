package ckbot;

public class CKBotStandardController extends CKBotController {

	public CKBotStandardController() {
	}
	
	public void activate() {
		while(module.getSimulation().isPaused()) Thread.yield();
		connectAll();
		while(true) {
    		try {
    			Thread.sleep(1000);
    			goToPosition((float)0.2);
    			Thread.sleep(1000);
    			goToPosition((float)0.8);
    			sendMessage(new byte[] {'T','e','s','t'},(byte)4,(byte)0);
            } catch (InterruptedException e) {
                throw new Error("unexpected");
            }
    	}
	}
	
	public void goToPosition(float goal) {
		float current = getEncoderPosition();
		//System.out.println("Encoder="+current);
		do {
		  if (goal > current) module.getActuators().get(0).activate(1);
		  else module.getActuators().get(0).activate(-1);
		  Thread.yield();
		  current = getEncoderPosition();
		} while(Math.abs(goal-current) > 0.001f);
		module.getActuators().get(0).disactivate();
	}
	
    public float getEncoderPosition() {
    	return (float)(module.getActuators().get(0).getEncoderValue());
    }
	
    public void rotateTo(float goal) {
        do {
            module.getActuators().get(0).activate(goal);
            yield();
        } while(isRotating()&&blocking);
        module.getActuators().get(0).disactivate();
	}
    
    public boolean isRotating() {
    	return module.getActuators().get(0).isActive();
    }

}
