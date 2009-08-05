package ussr.samples.ckbot;

public class CKBotL7SampleController1 extends CKBotController {
	float next_angle = 30;
	boolean newmessage = false;
	
	public CKBotL7SampleController1() {
	}
	
	public void activate() {
		while(module.getSimulation().isPaused()) Thread.yield();
		while(true) {
    		try {
    			goToPosition(75);
    			Thread.sleep(1000);
    			goToPosition(-75);
    			Thread.sleep(1000);
            } catch (InterruptedException e) {
               // throw new Error("unexpected");
            }
    	}
	}
	
	public void waitForIt() {
		while(module.getSimulation().getTime()%10000 < 10) yield();
	}
	
	public void goToPosition(float angle) {
		float current = getEncoderPosition();
		float goal = 0.5f + (0.5f*angle)/90f;
		do {
		  if (goal > current) module.getActuators().get(0).setDesiredVelocity(1);
		  else module.getActuators().get(0).setDesiredVelocity(-1);
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
            module.getActuators().get(0).setDesiredPosition(goal);
            yield();
        } while(isRotating()&&blocking);
	}
    
    public boolean isRotating() {
    	return module.getActuators().get(0).isActive();
    }
    
    public void handleMessage(byte[] message, int messageSize, int channel) {
    	switch(message[0]) {
    	case 'a': next_angle=80; 
    		System.out.println("Module# " + module.getID() + "- Set angle=80"); break;
    	case 's': next_angle=25; 
    		System.out.println("Module# " + module.getID() + "- Set angle=25"); break;
    	default: System.out.println("Module# " + module.getID() + "- Unknown message"); break;
    	}
    	newmessage = true;
    }

}
